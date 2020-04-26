package com.froso.ufp.core.service;

import com.fasterxml.jackson.databind.*;
import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.core.domain.documents.simple.plain.*;
import com.froso.ufp.core.domain.events.*;
import com.froso.ufp.core.domain.interfaces.*;
import com.froso.ufp.core.exceptions.ValidationException;
import com.froso.ufp.core.exceptions.*;
import com.froso.ufp.core.response.binding.*;
import com.froso.ufp.core.service.operations.*;
import com.froso.ufp.core.service.util.*;dsf
import com.froso.ufp.core.service.util.query.*;
import com.froso.ufp.core.util.*;
import com.froso.ufp.modules.core.resourcemetadata.model.*;
import com.froso.ufp.modules.core.session.service.*;
import com.mongodb.client.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.*;
import org.apache.commons.lang3.exception.*;
import org.bson.*;
import org.joda.time.*;
import org.slf4j.*;
import org.springframework.beans.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.*;
import org.springframework.data.mongodb.core.index.*;
import org.springframework.data.mongodb.core.mapreduce.*;
import org.springframework.data.mongodb.core.query.*;

import javax.annotation.*;
import javax.validation.*;
import java.beans.*;
import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.regex.*;
xzvxzv

public class AbstractRepositoryService2<T extends IDataDocument>
        extends EventPublisherImpl implements DataService<T>, RepositoryService<T>, IMetaDataResource {
    private static final String DEFAULT_ID = "default";
    //get log4j handler
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractRepositoryService2.class);

    protected List<String> containsDefinition = new ArrayList<>();
    protected String typeName;
    protected Set<String> searchEqualsFields;
    @Autowired
    protected MongoOperations mongoOperations;
    @Autowired
    protected MongoTemplate mongoTemplate;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired(required = false)
    private SessionService sessionService;
    // TBD: factor out talking to resourcesservice using an event perhaps
    @Autowired
    private ResourcesService resourcesService;

    private ResourceMetadata metadata;

    /**
     * favoring constructor autowiring/dependency injection over field injection now, following style guide of spring
     *
     * @param thetype
     */
    @Deprecated
    public AbstractRepositoryService2(String thetype) {
        typeName = thetype;
        searchEqualsFields = new HashSet<>();
    }

    /**
     * use this it has many parameters and might be split, but this is the constructor style autowiring for this base class
     *
     * @param thetype
     * @param mongoOperations
     * @param mongoTemplate
     * @param objectMapper
     * @param sessionService
     * @param resourcesService
     */
    public AbstractRepositoryService2(String thetype,
                                      MongoOperations mongoOperations,
                                      MongoTemplate mongoTemplate,
                                      ObjectMapper objectMapper,
                                      SessionService sessionService,
                                      ResourcesService resourcesService) {
        typeName = thetype;
        searchEqualsFields = new HashSet<>();

        this.mongoOperations = mongoOperations;
        this.mongoTemplate = mongoTemplate;
        this.objectMapper = objectMapper;
        this.sessionService = sessionService;
        this.resourcesService = resourcesService;
    }

    @Deprecated
    public AbstractRepositoryService2() {
        searchEqualsFields = new HashSet<>();
    }

    private static <T> void validateObject(T object) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> errors = validator.validate(object);
        if (errors.size() > 0) {
            UfpBindingResult bindingResult = new UfpBindingResult();
            for (ConstraintViolation<T> violation : errors) {
                violation.getPropertyPath();
                UfpBindingResultItem bindingResultItem = new UfpBindingResultItem();
                bindingResultItem.setFieldName(violation.getPropertyPath().toString());
                bindingResultItem.setMessage(violation.getMessage());
                bindingResultItem.setRejectedValue(violation.getInvalidValue());
                bindingResult.getBindingResults().add(bindingResultItem);
            }
            throw new ValidationException("Validation Exception on ", bindingResult);
        }
    }

    /**
     * Gets old collection name.
     *
     * @return the old collection name
     */
// utility helper method, if collection name changed, copy all from old to current
    private static String getOldCollectionName() {
        // template method
        return null;
    }

    private static void printSearchKeyValues(Map<String, String> searchkeyvaluesIn) {

        for (Map.Entry<String, String> entry : searchkeyvaluesIn.entrySet()) {
            LOGGER.debug("{} => {}", entry.getKey(), entry.getValue());
        }

    }

    @PostConstruct
    private void initialise() {

        if (typeName == null) {
            try {
                typeName = getClassOfRepository().getField("TYPE_NAME").get(null).toString();
                LOGGER.info(" CLASS TYPE TYPE_NAME FOUND {} {}", getClassOfRepository(), typeName);

            } catch (ReflectiveOperationException e) {
                LOGGER.error(" CLASS TYPE TYPE_NAME NOT FOUND {} {} ", getClassOfRepository(), e);
            }

        }
        resourcesService.registerRessource(typeName, this);
        copyOldData();
        //   metadata = createMetaData();
        GODDAMMANNUALLYCREATEINDEXES();
        // make check for resource definition
    }

    private void GODDAMMANNUALLYCREATEINDEXES() {

        LOGGER.info("DB COllection is " + getCollectionName());
        LOGGER.info("Manually ensuring indexes ...");
        MongoCollection<Document> collection = mongoOperations.getCollection(getCollectionName());
//
        ListIndexesIterable<Document> indexinfo = collection.listIndexes();

        for (Document dbObject : indexinfo) {

            LOGGER.info("DB index is " + dbObject.toString());

        }
        if (getClassOfRepository().isAnnotationPresent(CompoundIndexes.class)) {

            LOGGER.info("DB COllection is " + collection.toString());

        }
//        IndexOperations ind = mongoOperations.indexOps(getClassOfRepository());
        //       ind.dropAllIndexes();
        //     LOGGER.info("DB INDEX OPS ARE: " + ind);

        // MongoPersistentEntityIndexResolver mongoPersistentEntityIndexResolver;
//          monoMongoPersistentEntityIndexResolver.resolveIndexFor(getClassOfRepository());

    }

    private ResourceMetadata createMetaData() {
        ResourceMetadata result = ExtractViewFromClass.getMetaDataForResourceFromAnnotations(getClassOfRepository());
        result.getForeignKeys().addAll(ExtractViewFromClass.getForeignKeys(getClassOfRepository()));
        result.getTags().addAll(ExtractViewFromClass.getResourceTagsFrom(getClassOfRepository()));

        List<Class<?>> interfaces = ClassUtils.getAllInterfaces(getClassOfRepository());
        for (Class<?> clazz : interfaces) {
            result.getInterfaces().add(clazz.getSimpleName());
        }

        //  result.getTags().add(globalsService.getAllProperties().get("applicationName").toString().toUpperCase());
        /*for(String s : this.getClassOfRepository().getPackage().getName().split(".")) {
            result.getTags().add(s.toUpperCase());
        }
        result.getTags().add(this.getClassOfRepository().getPackage().getName().toUpperCase());
*/
        //   result.getDefaultVisibleColumns().addAll(ExtractViewFromClass.getDefaultVisibleColumns(this.getClassOfRepository()));
//        result.setName("Metadata " + getTypeName());
        result.setResourceName(getTypeName());

//        result.getViews().add(ResourcesService.getCountView());
        return result;

    }

    @Override
    public ResourceMetadata getMetadata() {

        // if (metadata == null) {

        metadata = createMetaData();
        //  }
        return metadata;

    }

    private void copyOldData() {
        if (getOldCollectionName() != null) {
            LOGGER.trace("Old Collection name set, transferring data" + getOldCollectionName() + "=>" + getCollectionName());
            // copy all from old to new table that is not present in current
            // assuming same type
            List<T> oldValues = mongoOperations.findAll(getClassOfRepository(), getOldCollectionName());
            for (T item : oldValues) {
                T current = findOneBrute(item.getId());
                if (current == null) {
                    // if id not found in current collection, just save it
                    LOGGER.trace("Copying item" + item.getId());
                    try {
                        saveWithoutValidation(item);
                    } catch (Exception e) {
                        LOGGER.trace("Copying item problem" + e.getMessage(), e);
                    }
                }
            }
        }
    }

    /**
     * Import default.
     */
   /* @Override
    public void importDefault() {
        try {
            importFromFileRessource("default/" + getTypeName().toLowerCase() + ".json");
        } catch (Exception e) {
            LOGGER.error("Import Default Ressources Problem ", e);
        }
    }
*/
    @Override
    public void importFromFileRessource(String filename) throws
            IOException {
        InputStream inputStream = FileUtil.getFileInputStream(filename);

        List<T> myObjects = objectMapper.readValue(inputStream, objectMapper.getTypeFactory().constructCollectionType(List.class, getClassOfRepository()));
        inputStream.close();
        for (T element : myObjects) {
            save(element);
        }
    }

    @Override
    public String getTypeName() {
        return typeName;
    }

    @Override
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public Class<T> getClassOfRepository() {
        ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<T>) superclass.getActualTypeArguments()[0];
    }

    private List<String> getTextIndexFields(String colname) {
        // TODO: fixme: make memory copy and reuse it after created it once
        List<String> textindexfieldname = new ArrayList<>();
        // fixme: how to deal qwith it?!

        ListIndexesIterable<Document> indexes = mongoTemplate.getCollection(colname).listIndexes();
        LOGGER.info("Indexes are " + indexes.toString());
        for (Document dbObject : indexes) {

            if (dbObject.get("textIndexVersion") != null) {

                Document weights = (Document) dbObject.get("weights");
//                 LOGGER.info("text info {}",weights);
                for (Map.Entry entry : weights.entrySet()) {

//                    LOGGER.info(entry.getKey() + "==" + entry.getValue());
                    textindexfieldname.add(entry.getKey().toString());

                }

            }
        }

        return textindexfieldname;
    }

    private String getCollectionName() {
        //      return RepositoryConstants.UFP_REPOSITORY_PREFIX + getTypeName();
        String result = "";

        try {
            result = mongoTemplate.getCollectionName(getClassOfRepository());
        } catch (Exception e) {
            // this removes existing indexes and creates them new upon a problem ...
            LOGGER.error("Problem ", e);
            Pattern pattern = Pattern.compile(".*in collection '(.*)'.*");
            Matcher matcher = pattern.matcher(e.getMessage());
            while (matcher.find()) {

                mongoTemplate.getCollection(matcher.group(1)).dropIndexes();
            }
            result = mongoTemplate.getCollectionName(getClassOfRepository());

        }
        return result;
    }

    @Override
    public T save(T element) {
        return doSave(element, true);
    }

    private T saveWithoutValidation(T element) {
        return doSave(element, false);
    }

    private T doSave(T element, Boolean validate) {
//        LOGGER.debug("Saving" + typeName + " " + element);
        if (element instanceof IDataDocument) {
            IDataDocument document = element;
            document.getMetaData().setLastChangedTimestamp(DateTime.now());
        }

        // allow adjustment of model data by concrete service BEFORE validation
        prepareSave(element);

        // call validation before each save!
        if (validate) {
            validateObject(element);
        }

        boolean isExistant = isExistant(element.getId());
        String currentUserId = sessionService.getUserIdForToken(RequestTokenRetriever.getCurrentUserToken());
        if (isExistant) {

            // store initial editor user
            if (currentUserId != null) {
                element.getMetaData().setLastEditorUserLink(new DataDocumentLink<>(currentUserId));
            }

        } else {

            // store initial creator user
            if (currentUserId != null) {
                if (element.getMetaData().getCreatorUserLink() == null) {
                    /** incoming object  already has creator user set, keep as it is**/
                    element.getMetaData().setCreatorUserLink(new DataDocumentLink<>(currentUserId));

                }
                /** set last editor user to creator user on creation as well ... **/
                element.getMetaData().setLastEditorUserLink(element.getMetaData().getCreatorUserLink());
            }

        }

        if (publisher != null) {
            if (isExistant) {
                publisher.publishEvent(new DataDocumentSaveEvent<>(element, this));
            } else {
                publisher.publishEvent(new DataDocumentCreateEvent<>(element, this));
                //     this.publisher.publishEvent(new DataDocumentSaveEvent<>(element, this));
            }
        }

        if (isExistant) {

            mongoOperations.save(element, getCollectionName());
        } else {
            // Create new
            //  String currentUserId = sessionService.getUserIdForToken(RequestTokenRetriever.getCurrentUserToken());

            mongoOperations.insert(element, getCollectionName());
        }
        if (publisher != null) {
            publisher.publishEvent(new DataDocumentPostSaveEvent<>(element, this));
        }
        if (postSaveOperation(element)) {
            // if returned true means something changed in object, so save it again
            mongoOperations.save(element, getCollectionName());
        }
        prepareResultElement(element);
        return element;
    }

//    private void validateObject(T object) {
//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        Validator validator = factory.getValidator();
//        Set<ConstraintViolation<T>> errors = validator.validate(object);
//        if (errors.size() > 0) {
//            UfpBindingResult bindingResult = new UfpBindingResult();
//            for (ConstraintViolation<T> violation : errors) {
//                violation.getPropertyPath();
//                UfpBindingResultItem bindingResultItem = new UfpBindingResultItem();
//                bindingResultItem.setFieldName(violation.getPropertyPath().toString());
//                bindingResultItem.setMessage(violation.getMessage());
//                bindingResultItem.setRejectedValue(violation.getInvalidValue());
//                bindingResult.getBindingResults().add(bindingResultItem);
//            }
//            throw new ValidationException("Validation Exception on " + getTypeName() + " " + " " + object.getId(), bindingResult);
//        }
//    }

    /**
     * it is not abstract because it is not mandatory
     *
     * @param object the object
     */
    protected void prepareSave(T object) {
        // template method, called before saving an object...
    }

    /**
     * Prepare result element.
     *
     * @param element the element
     */
    protected void prepareResultElement(T element) {
        // virtual template function to modify result
    }

    /**
     * Post save operation.
     *
     * @param element the element
     * @return the boolean
     */
    private Boolean postSaveOperation(T element) {
        // virtual template function to modify result
        return false;
    }

    @Override
    public T createNewDefault() {
        LOGGER.info("createNewDefault" + typeName);
        // check if a default exists in db
        T newObject = getDefault();
        // give the result a new element id
        newObject.setId(AbstractDataDocument.generateID());
        //   fillDefaultObject(newObject);
        return newObject;
        // TODO: FIXME: dont save objects when created
        //  return save(newObject);
    }

//    private String getUserIDFromToken(String token) {
//        String result = token;
//        if (null != sessionService) {
//            result = sessionService.getUserIdForToken(token);
//        }
//        return result;
//    }

    /**
     * Create duplicate t.
     *
     * @param id the id
     * @return the t
     */
    //@Override
//    public T createDuplicate(String id) {
//        LOGGER.info("createDuplicatet" + typeName + id);
//        // copy handling, a copy is created when the "id" field of the body is empty
//        T item = findOneBrute(id);
//        // Save as Copy straight from database, ignore incoming document, search by id and duplicate
//        if (item != null) {
//            // Set new ID
//            item.setId(RandGen.getRandomUUID());
//            prepareDuplication(item);
//        }
//        //change id and name in duplicate element
//        if (item instanceof AbstractDataDocumentWithName) {
//            AbstractDataDocumentWithName nameddocument = (AbstractDataDocumentWithName) item;
//            nameddocument.setName("Copy of " + nameddocument.getName());
//        }
//        item = save(item);
//        return item;
//    }

    /**
     * Prepare duplication.
     *
     * @param object the object
     */
//    protected void prepareDuplication(T object) {
//        // template method, sub classes may initialises their objects as they desire...
//    }

    /**
     * Gets default.
     *
     * @return the default
     * @throws Exception the exception
     */
    public final T getDefault() {
        // check if a default exists in db
        T defaultone = findOneBrute(DEFAULT_ID);
        if (defaultone == null) {
            // no default found, return "new()"
            try {
                defaultone = getClassOfRepository().newInstance();
            } catch (IllegalAccessException e) {
                LOGGER.error("AbstractRepository getDefault() exception " + getTypeName() + ExceptionUtils.getRootCauseMessage(e), e);
            } catch (InstantiationException e) {
                LOGGER.error("AbstractRepository getDefault() exception " + getTypeName() + ExceptionUtils.getRootCauseMessage(e), e);
            }
        }
        fillDefaultObject(defaultone);
        if (defaultone instanceof AbstractDataDocumentWithName) {
            ((AbstractDataDocumentWithName) defaultone).setName("New " + typeName);
        }
        return defaultone;
    }

    /**
     * it is not abstract because it is not mandatory
     *
     * @param object the object
     */
    protected void fillDefaultObject(T object) {
        // template method, sub classes may initialises their objects as they desire...
    }

    /**
     * Change id of object.
     *
     * @param idFrom the id from
     * @param idTo   the id to
     * @return the t
     */
    /* @Override
    public T changeIdOfObject(String idFrom, String idTo) {
        // check if target id is in use, if yes throw exception
        T targetElementCheck = findOne(idTo);
        if (targetElementCheck != null) {
            throw new ResourceException("ChangeID Operation Target ID is in use ", getTypeName(), idTo);
        }
        T element = findOne(idFrom);
        if (element != null) {
            // exists, make copy by setting id
            // but first delete in repository
            delete(element);
            // then assign newid
            element.setId(idTo);
            // and save with new id
            save(element);
            return element;
        }
        return element;
    }
    */

    /**
     * Find one brute.
     *
     * @param id the id
     * @return the t
     */
    @Override
    public T findOneBrute(String id) {

//        LOGGER.debug("findOneBrute {} {}", typeName, id);
        if (id == null) {
            return null;
        }
        Query query2 = new Query();
        query2.addCriteria(Criteria.where("_id").is(id));
        T result = null;
        try {
            result = mongoOperations.findOne(query2, getClassOfRepository(), getCollectionName());
        } catch (Exception e) {
            LOGGER.error("findoneBrute: {}", e);
        }
        if (result != null) {
            prepareResultElement(result);
        }
        return result;
    }

    /**
     * Find one.
     *
     * @param id the id
     * @return the t
     */
    @Override
    public T findOne(String id) {
        T result = null;
        if ((!StringUtils.EMPTY.equals(id)) && (null != id)) {
            result = findOneBrute(id);
            if (null == result) {
                throw new MainResourceNotFoundException(typeName, id);
            }
        } else {
            throw new MainResourceNotFoundException(typeName, id);
        }
        prepareResultElement(result);
        return result;
    }

    @Override
    public T findOne(DataDocumentLink link) {
        if (link == null) {
            return null;
        }
        T result = null;
        if ((!StringUtils.EMPTY.equals(link.getId())) && (null != link.getId())) {
            result = findOneBrute(link.getId());
            if (null == result) {
                throw new MainResourceNotFoundException(typeName, link.getId());
            }
        }
        prepareResultElement(result);
        return result;
    }

    /**
     * Delete void.
     *
     * @param element the element
     */
    @Override
    public void delete(T element) {

        LOGGER.info("delete" + typeName + element);
        if (element == null) {
            return;
        }
        prepareDelete(element);
        if (publisher != null) {
            publisher.publishEvent(new DataDocumentDeleteEvent<>(element, this));
        }
        mongoOperations.remove(element, getCollectionName());
    }

    /**
     * Delete.
     *
     * @param elements the elements
     */
    public final void delete(List<T> elements) {
        for (T element : elements) {
            //  prepareDelete(element);
            delete(element);
        }
    }

    /**
     * Prepare delete.
     *
     * @param object the object
     */
    protected void prepareDelete(T object) {
        // template method
    }

    /**
     * Gets mongo operations.
     *
     * @return the mongo operations
     */
    protected MongoOperations getMongoOperations() {
        return mongoOperations;
    }

    /**
     * Find all get first t.
     *
     * @return the t
     */
    public T findAllGetFirst() {
        List<T> result = mongoOperations.findAll(getClassOfRepository(), getCollectionName());
        T res = null;
        if (result != null) {
            if (!result.isEmpty()) {
                prepareResult(result);
                res = result.get(0);
            }
        }
        return res;
    }

    /**
     * Find all.
     *
     * @return list list
     */
    @Override
    public List<T> findAll() {
        List<T> result = mongoOperations.findAll(getClassOfRepository(), getCollectionName());
        prepareResult(result);
        return result;
    }

    /**
     * Find by key values.
     *
     * @param key   the key
     * @param value the value
     * @return list list
     */
    @Override
    public List<T> findByKeyValue(String key, String value) {
        Map<String, String> search = new HashMap<>();
        search.put(key, value);
        List<T> result = findByKeyValues(search);
        prepareResult(result);
        return result;
    }

    /**
     * Find by key value equals list.
     *
     * @param key   the key
     * @param value the value
     * @return the list
     */
    public List<T> findByKeyValueEquals(String key, String value) {
        Map<String, String> search = new HashMap<>();
        search.put(key, "=" + value);
        List<T> result = findByKeyValues(search);
        prepareResult(result);
        return result;
    }

    /**
     * Find one by key value list.
     *
     * @param key   the key
     * @param value the value
     * @return the list
     */
    public T findOneByKeyValue(String key, String value) {
        Map<String, String> search = new HashMap<>();
        search.put(key, value);
        search.put(SearchQuery.LIMIT, "1");
        search.put(SearchQuery.PAGE, "0");

        List<T> result = findByKeyValues(search);
        prepareResult(result);
        if (!result.isEmpty()) {
            return result.get(0);
        }
        return null;
    }

    /**
     * Find by key values list.
     *
     * @param keyvalues the keyvalues
     * @return the list
     */
    @Override
    public List<T> findByKeyValues(Map<String, String> keyvalues) {
        List<T> result = null;
        if (keyvalues.isEmpty()) {
            result = findAll();
        } else {
            result = searchWithPageable(keyvalues);
        }
        prepareResult(result);
        return result;
    }

    /**
     * Find one by key values list.
     *
     * @param keyvalues the keyvalues
     * @return the list
     */
    public T findOneByKeyValues(Map<String, String> keyvalues) {
        List<T> result = null;
        if (keyvalues.isEmpty()) {
            result = findAll();
        } else {
            keyvalues.put(SearchQuery.LIMIT, "1");
            result = findByKeyValues(keyvalues);
        }
        prepareResult(result);
        if (!result.isEmpty()) {
            return result.get(0);
        }
        return null;
    }

    /**
     * Search paged.
     *
     * @param searchkeyvalues the searchkeyvalues
     * @return data object list
     */
    @Override
    public DataObjectList searchPaged(Map<String, String> searchkeyvalues) {
        return searchPaged(searchkeyvalues, null);
    }

    /**
     * Search paged.
     *
     * @param searchkeyvaluesIn the searchkeyvalues in
     * @param sort              the sort
     * @return data object list
     */
    protected DataObjectList searchPaged(Map<String, String> searchkeyvaluesIn, Sort sort) {
        Map<String, String> searchkeyvalues = cleanInputRequestParams(searchkeyvaluesIn);
        return doSearchPaged(searchkeyvalues, sort);
    }

    /**
     * Clean input request params map.
     *
     * @param searchkeyvaluesIn the searchkeyvalues in
     * @return map map
     */
    private Map<String, String> cleanInputRequestParams(Map<String, String> searchkeyvaluesIn) {
        // Klone the map
        Map<String, String> searchkeyvalues = new HashMap<>();
        searchkeyvalues.putAll(searchkeyvaluesIn);
        /** FIXME: apply additional exclusions from input vars other way
         searchkeyvalues.remove(HMACFilter.HMAC_PROPERTY);
         searchkeyvalues.remove(HMACFilter.HMAC_BODY);
         searchkeyvalues.remove(HMACFilter.HMAC_TIMESTAMP);      */
        if (searchkeyvalues.containsKey("contains")) {
            // split contains field into multiple fields as defined by the "containsdefinition
            String valuetosearchforincontainslogic = searchkeyvalues.get("contains");
            searchkeyvalues.remove("contains");
            for (String propertyname : containsDefinition) {
                searchkeyvalues.put(propertyname, valuetosearchforincontainslogic);
            }
            // and set to or combination of fields
            searchkeyvalues.put(SearchQuery.SEARCH_METHOD_FIELD_NAME, SearchQuery.SEARCH_METHOD_OR);
        }
        return searchkeyvalues;
    }

    /**
     * @param searchkeyvaluesIn
     * @param sort
     * @return
     */
    private DataObjectList doSearchPaged(Map<String, String> searchkeyvaluesIn, Sort sort) {
        // Klone the map
        Map<String, String> searchkeyvalues = cleanInputRequestParams(searchkeyvaluesIn);

        transformAnyTextSearchInputRequest(searchkeyvalues);

        // Remove unneded !   And global Communication parts...
        List<T> listresult = new ArrayList<>();
        Pageable pageable = null;
//        printSearchKeyValues(searchkeyvalues);
        try {
            pageable = PageRequestHelper.getPageRequestFromParameters(searchkeyvalues);
        } catch (Exception e) {
            LOGGER.error("Error creating page request ", e);
        }
        long count = 0;
        try {
            Query query = SearchQuery.buildSearchQuery(searchkeyvalues, searchEqualsFields);
            // Count Possible Results
            count = mongoOperations.count(query, getClassOfRepository(), getCollectionName());
            // add pager to search
            if (null != sort) {
                query.with(sort);
            }
            if (pageable != null) {
                // just execute if paging is valid
                query.with(pageable);
                listresult = mongoOperations.find(query, getClassOfRepository(), getCollectionName());
            }
        } catch (Exception e) {
            // Search Problem
            // Search Problem
            LOGGER.error("doSearchPaged Problem " + typeName + " " + e.getMessage(), e);
            throw new UFPRuntimeException(e.getMessage());
        }
        prepareResult(listresult);
        DataObjectList result = new DataObjectList(listresult);
        ListHeader listHeader = result.getListHeader();
        listHeader.setListSize(count);
        listHeader.setPaging(pageable);
        return result;
    }

    private void transformAnyTextSearchInputRequest(Map<String, String> searchkeyvalues) {
        if (searchkeyvalues.containsKey(SearchQuery.FILTER_ANY_KEY)) {

            List<String> textindexes = getTextIndexFields(getCollectionName());
            for (String string : textindexes) {

                searchkeyvalues.put(string, searchkeyvalues.get(SearchQuery.FILTER_ANY_KEY));

            }
            searchkeyvalues.put(SearchQuery.SEARCH_METHOD_FIELD_NAME, SearchQuery.SEARCH_METHOD_OR);
            searchkeyvalues.remove(SearchQuery.FILTER_ANY_KEY);

        }
    }

    /**
     * iterate over result and modify ... in service subclass
     *
     * @param list
     */
    private void prepareResult(List<T> list) {
        for (T element : list) {
            prepareResultElement(element);
        }
    }

    private String getResourceNameForDataDocumentLinkRef(Class<?> t, String path) {
        String[] itemsArray = path.split("\\.");
        List<String> items = Arrays.asList(itemsArray);

        PropertyDescriptor desc = BeanUtils.getPropertyDescriptor(t, items.get(0));
        if (desc != null) {
            if (desc.getPropertyType().equals(DataDocumentLink.class)) {
                Class<?> property = desc.getPropertyType();

                Class<T> persistentClass;
                LOGGER.info("WE HAVE A LINK " + property.getName());
                LOGGER.info("WE HAVE A LINK " + ((ParameterizedType) property.getGenericSuperclass()).getActualTypeArguments()[0].toString());
                LOGGER.info("WE HAVE A LINK " + property.getGenericSuperclass().toString());
                LOGGER.info("WE HAVE A LINK " + property.getCanonicalName());
                LOGGER.info("WE HAVE A LINK " + property.getSimpleName());
                LOGGER.info("WE HAVE A LINK " + property.toGenericString());
                persistentClass = (Class<T>) ((ParameterizedType) property.getGenericSuperclass()).getActualTypeArguments()[0];
                if (persistentClass != null) {
                    LOGGER.info("WE HAVE A LINK " + persistentClass.getName());
                }

            }
        }
        if (items.size() > 1) {
            items.remove(0);

            return getResourceNameForDataDocumentLinkRef(desc.getPropertyType(), String.join(".", items));
        }
        return "jo";
    }

    /**
     * Perform group 2 object.
     *
     * @param groupByPropertyPath the group by property path
     * @param method              the method
     * @param searchkeyvaluesIn   the searchkeyvalues in
     * @return the object
     */
    public final DataObjectList performGroup(String groupByPropertyPath, String groupByValuePath, GroupByMethods method, Map<String, String> searchkeyvaluesIn) {
//; tbd return a datadocumentlist groupbyresult
        GroupByResults groupResult = performGroup2(groupByPropertyPath, groupByValuePath, method, searchkeyvaluesIn);
        DataObjectList result = new DataObjectList();

        return result;

    }

    @Override
    public final GroupByResults performGroup2(String groupByPropertyPath, String groupByValuePath, GroupByMethods method, Map<String, String> searchkeyvaluesIn) {

        if (null == groupByValuePath) {
            groupByValuePath = groupByPropertyPath;
        }

        LOGGER.info("Performing Group operation " + typeName + groupByPropertyPath + method);
        // default return - just create stringified id from data toString()
        String keyReturn = "return {id: data.toString() }";

        // check for transforms
        // regexp for finding propertyPath.$controllCommand in property path
        final String patternString = ".*(.\\$.*)";

        if (groupByPropertyPath.matches(patternString)) {
            Pattern pattern = Pattern.compile(patternString);

            Matcher matcher = pattern.matcher(groupByPropertyPath);
            // must call matches for accessing group results :(
            matcher.matches();
            String found = matcher.group(1);
            switch (found) {

                case ".$month":
                    // adjust key generation according to control field $ for now create key from date
                    keyReturn = "var date=new Date(data);" +
                            "return {id:date.getFullYear()+'-'+(date.getMonth()+1)};";

                    break;
                case ".$day":
                    // adjust key generation according to control field $ for now create key from date
                    keyReturn = "var date=new Date(data);" +
                            "return {id:date.getFullYear()+'-'+(date.getMonth()+1)+'-'+date.getDate()};";

                    break;
                case ".$year":
                    // adjust key generation according to control field $ for now create key from date
                    keyReturn = "var date=new Date(data);" +
                            "return {id:date.getFullYear()};";

                    break;
                case ".$hour":
                    // adjust key generation according to control field $ for now create key from date
                    keyReturn = "var date=new Date(data);" +
                            "return {id:date.getFullYear()+'-'+(date.getMonth()+1)+'-'+date.getDate()+'-'+date.getHours()};";

                    break;
                default:
                    // keep default value initialised above
                    break;

            }
            groupByPropertyPath = groupByPropertyPath.replace(found, "");
        }

        if (groupByPropertyPath.contains("id")) {

            Map<String, ResourceMetadata> resources = resourcesService.getRessourceNames();
            ResourceMetadata def = resources.get(getTypeName());
            for (ForeignKey key : def.getForeignKeys()) {
                if (key.getFieldName().equals(groupByPropertyPath)) {

                    LOGGER.info("--------------------------yay");
                    LOGGER.info(key.getResourceName());
                    // we have to resort original link resource if just interface is provided
                    String resourceName = resourcesService.getResourceNameForInterface(key.getResourceName());
                    LOGGER.info(resourceName);
                    if (resourceName == null) {
                        resourceName = key.getResourceName();
                    }
                    LOGGER.info("--------------------------yay");
                    // mark idgeneration with resourcename, it will be parsed out afterwards by grouping method of this class
                    keyReturn = "return {id: '" + resourceName + "#'+data.toString() }";
                }

            }

            // a id is present, check if we can resolve it to a typed datadocumentlink element

        }

        if ("id".equals(groupByPropertyPath)) {
            groupByPropertyPath = "_id";
        }
        groupByPropertyPath = groupByPropertyPath.replace(".id", "._id");

        // ----------------------------------------------------------
        // extract grouping id if property Path contains commas - , - then split and make key from multi
        String groupingKeyFunction = null;

        // single property
        groupingKeyFunction = "function(doc){" +
                "var data= doc." + groupByPropertyPath + "||'ufp-not-set';" + keyReturn + "}";

        // ----------------------------------------------------------
        //
        // initialize with zero
        String initialValue = "";
        // the result will always be our doc
        final String finalizeFunction = "function(doc){return doc;}";
        // the reduce function defines the grouping operation
        String reduceFunction = "";
        /**
         * mongo style group by implementation
         *
         * fixme: todo: use latest mongo 4 syntax here
         */
        switch (method) {
            case AVG:

                reduceFunction = "function(doc, prev) { prev.count  =(prev.count+doc." + groupByValuePath + ")/2;}";
                initialValue = "{ count: 0 }";
                break;
            case COUNT:

                reduceFunction = "function(doc, prev) { prev.count += 1; }";
                initialValue = "{ count: 0 }";
                break;
            case MAX:

                reduceFunction = "function(doc, prev) { prev.count = Math.max(doc." + groupByValuePath + ",prev.count); }";
                initialValue = "{ count: Number.MIN_SAFE_INTEGER }";
                break;
            case MIN:

                reduceFunction = "function(doc, prev) { prev.count = Math.min(doc." + groupByValuePath + ",prev.count); }";
                initialValue = "{ count: Number.MAX_SAFE_INTEGER }";
                break;
            case SUM:

                reduceFunction = "function(doc, prev) { prev.count+= doc." + groupByValuePath + "; }";
                initialValue = "{ count: 0 }";
                break;
            default:
                reduceFunction = "function(doc, prev) {   }";
                initialValue = "{   }";

        }

        // performGroupxxx(groupByPropertyPath, method, searchkeyvaluesIn);
        return performGroup(groupingKeyFunction, initialValue, reduceFunction, finalizeFunction, searchkeyvaluesIn);
    }
//
//    private GroupByResults performGroupxxx(String groupByPropertyPath, GroupByMethods method, Map<String, String> searchkeyvaluesIn) {
//
//        Map<String, String> searchkeyvalues = cleanInputRequestParams(searchkeyvaluesIn);
//        GroupByResults result = null;
////        Criteria query = SearchQuery.buildSearchQueryCriteria(searchkeyvalues, searchEqualsFields);
//
//
//        // AggregationOperation match = Aggregation.match(query);
////        AggregationOperation group1 = Aggregation.project(groupByPropertyPath).andExpression("day(" + groupByPropertyPath + ")").as("id");
//        AggregationOperation group = Aggregation.group(groupByPropertyPath).count().as("count");
//        AggregationOperation sort = Aggregation.sort(Sort.Direction.ASC, "joined");
//        Aggregation aggregation = Aggregation.newAggregation(group, sort);
////        AggregationResults<StringMapForResults> result2 = mongoOperations.aggregate(aggregation, this.getCollectionName(), StringMapForResults.class);
//        //LOGGER.info(result.getServerUsed());
//        return null;
//    }
//

    private GroupByResults performGroup(String groupingKeyFunction, String initialDOcumentJSON, String reduceFunctionJS, String finalizeFunction, Map<String, String> searchkeyvaluesIn) {
        Map<String, String> searchkeyvalues = cleanInputRequestParams(searchkeyvaluesIn);
        GroupByResults result = null;
        try {
            Criteria query = SearchQuery.buildSearchQueryCriteria(searchkeyvalues, searchEqualsFields);

            // Count Possible Results
            result = mongoOperations.group(query, getCollectionName(), GroupBy.keyFunction(groupingKeyFunction).initialDocument(initialDOcumentJSON).reduceFunction(reduceFunctionJS).finalizeFunction(finalizeFunction), getClassOfRepository());

            // try to resolve any id with a - sign to a named resource

//            resourcesService.getResource("willi");

            for (Document item : (List<Document>) result.getRawResults().get("retval")) {
                LOGGER.info(item.get("id").toString());
                String idValue = item.get("id").toString();
                if (idValue.contains("#")) {

                    // try to resolve resource name
                    // syntax is
                    // ResourceName#id
                    String[] idValues = idValue.split("#");
                    String resourceName = idValues[0];
                    if (resourcesService.getResource(resourceName) == null) {
                        item.put("id", "RESOURCE-NOT-EXISTANT-" + idValues[1]);
                    } else {

                        IDataDocument linked = resourcesService.getResource(resourceName).findOneBrute(idValues[1]);
                        if (linked == null) {
                            item.put("id", "NOT-FOUND-" + idValues[1]);
                        } else if (linked instanceof INamedObject) {
                            item.put("id", ((INamedObject) linked).getName());
                        }
                    }
                }
            }

        } catch (Exception e) {
            // Search Problem
            LOGGER.error("Search GetCount Problem in BaseRepository Service " + typeName + " " + e.getMessage(), e);
            throw e;
        }
        return result;

    }

    /**
     * Search get count.
     *
     * @param searchkeyvaluesIn the searchkeyvalues in
     * @return long long
     */
    @Override
    public long searchGetCount(Map<String, String> searchkeyvaluesIn) {
        Map<String, String> searchkeyvalues = cleanInputRequestParams(searchkeyvaluesIn);
        long result = 0;
        try {
            transformAnyTextSearchInputRequest(searchkeyvalues);
            Query query = SearchQuery.buildSearchQuery(searchkeyvalues, searchEqualsFields);
            // Count Possible Results
            result = mongoOperations.count(query, getClassOfRepository(), getCollectionName());
        } catch (Exception e) {
            // Search Problem
            LOGGER.error("Search GetCount Problem in BaseRepository Service " + typeName + " " + e.getMessage(), e);
        }
        return result;
    }

    /**
     * Clean input request params from everything.
     *
     * @param searchkeyvaluesIn the searchkeyvalues in
     * @return the map
     */
    @Override
    public Map<String, String> cleanInputRequestParamsFromEverything(Map<String, String> searchkeyvaluesIn) {
        // Klone the map
        Map<String, String> result = cleanInputRequestParamsFromSort(searchkeyvaluesIn);
        return cleanInputRequestParamsFromPageable(result);

    }

    /**
     * Clean input request params from sort map.
     *
     * @param searchkeyvaluesIn the searchkeyvalues in
     * @return the map
     */
    private Map<String, String> cleanInputRequestParamsFromSort(Map<String, String> searchkeyvaluesIn) {
        // Klone the map
        Map<String, String> searchkeyvalues = cleanInputRequestParams(searchkeyvaluesIn);
        searchkeyvalues.remove(SearchQuery.SORT_PROPERTY);
        searchkeyvalues.remove(SearchQuery.LIMIT);
        searchkeyvalues.remove(SearchQuery.PAGE);
        return searchkeyvalues;
    }

    /**
     * Clean input request params from pageable map.
     *
     * @param searchkeyvaluesIn the searchkeyvalues in
     * @return the map
     */
    private Map<String, String> cleanInputRequestParamsFromPageable(Map<String, String> searchkeyvaluesIn) {
        // Klone the map
        Map<String, String> searchkeyvalues = cleanInputRequestParams(searchkeyvaluesIn);

        searchkeyvalues.remove(SearchQuery.LIMIT);
        searchkeyvalues.remove(SearchQuery.PAGE);
        searchkeyvalues.remove("key");
        return searchkeyvalues;
    }

    /**
     * Search list.
     *
     * @param searchkeyvaluesIn the searchkeyvalues in
     * @return list list
     */
    private List<T> searchWithPageable(Map<String, String> searchkeyvaluesIn) {
        //  LOGGER.info("SEARCHING: " + searchkeyvaluesIn.toString());

        Map<String, String> copy = cleanInputRequestParamsFromPageable(searchkeyvaluesIn);
//        copy.putAll(searchkeyvaluesIn);
        transformAnyTextSearchInputRequest(copy);
        Query query = SearchQuery.buildSearchQuery(copy, searchEqualsFields);

        Pageable pageable = PageRequestHelper.getPageRequestFromParameters(searchkeyvaluesIn);

        List<T> result = mongoOperations.find(query.with(pageable), getClassOfRepository(), getCollectionName());
        prepareResult(result);

        return result;
    }

    @Override
    public List<T> search(Map<String, String> searchkeyvaluesIn) {

        LOGGER.info("Performing search " + typeName);
        Map<String, String> copy = cleanInputRequestParamsFromEverything(searchkeyvaluesIn);

        transformAnyTextSearchInputRequest(copy);
        Query query = SearchQuery.buildSearchQuery(copy, searchEqualsFields);

        List<T> result = mongoOperations.find(query, getClassOfRepository(), getCollectionName());
        prepareResult(result);

        return result;
    }

    /**
     * Delete all.
     *
     * @return the long
     */
    @Override
    public void deleteAll() {
        mongoOperations.remove(new Query(), getCollectionName());
    }

    /**
     * convenience method to delete by simple key-value query
     *
     * @param key   the key
     * @param value the value
     * @return the long
     */
    @Override
    public long deleteAllBySingleKeyValue(String key, String value) {
        Query query1 = new Query();
        query1.addCriteria(Criteria.where(key).is(value));
        return deleteByQuery(query1);
    }

    /**
     * returns deleted cound
     *
     * @param query the query
     * @return long long
     */
    private long deleteByQuery(Query query) {
        long count = mongoOperations.count(query, getClassOfRepository(), getCollectionName());
        mongoOperations.remove(query, getClassOfRepository());
        return count;
    }

    /**
     * Gets count.
     *
     * @return count count
     */
    @Override
    public long getCount() {
        return mongoOperations.count(new Query(), getCollectionName());
    }

    /**
     * Delete by id.
     *
     * @param id the id
     */
    /*@Override
    public final void deleteById(String id) {
        T element = findOneBrute(id);
        mongoOperations.remove(element, getCollectionName());
    }
*/

    /**
     * Is existant.
     *
     * @param id the id
     * @return boolean boolean
     */
    @Override
    public boolean isExistant(String id) {
        T element = findOneBrute(id);
        return null != element;
    }

    /**
     * Is existant with throw error.
     *
     * @param id the id
     * @return boolean boolean
     */
    @Override
    public boolean isExistantWithThrowError(String id) {
        return null != findOne(id);
    }

    /**
     * Is existant without throw error boolean.
     *
     * @param id the id
     * @return the boolean
     */
    protected boolean isExistantWithoutThrowError(String id) {
        return null != findOneBrute(id);
    }

}
