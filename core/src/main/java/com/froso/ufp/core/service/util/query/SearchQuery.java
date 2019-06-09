package com.froso.ufp.core.service.util.query;

import com.froso.ufp.core.domain.documents.simple.plain.*;
import java.nio.charset.*;
import java.util.*;
import org.apache.commons.lang3.*;
import org.joda.time.*;
import org.slf4j.*;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.*;

/**
 * Created with IntelliJ IDEA. Entiteit: ck Date: 20.03.14 Time: 12:06 To change this template use File | Settings | File
 * Templates.
 */
public class SearchQuery {

    /**
     * The constant GTE_SUFFIX.
     */
    public static final String PAGE = "$page";
    /**
     * The constant LIMIT.
     */
    public static final String LIMIT = "$limit";

    /**
     * The constant GTE_SUFFIX.
     */
    public static final String GTE_SUFFIX = ".gte";
    /**
     * The constant LTE_SUFFIX.
     */
    public static final String LTE_SUFFIX = ".lte";
    /**
     * The constant SEARCH_METHOD_FIELD_NAME.
     */
    public static final String SEARCH_METHOD_FIELD_NAME = "$method";
    /**
     * The constant SEARCH_METHOD_AND.
     */
    public static final String SEARCH_METHOD_AND = "and";
    /**
     * The constant SEARCH_METHOD_OR.
     */
    public static final String SEARCH_METHOD_OR = "or";
    /**
     * The constant SORT_PROPERTY.
     */
    public static final String SORT_PROPERTY = "$sort";
    /**
     * The constant SORT_ASC.
     */
    public static final String SORT_ASC = "ASC";
    /**
     * The constant SORT_DESC.
     */
    public static final String SORT_DESC = "DESC";
    /**
     * The constant SORT_DIRECTION_PROPERTY.
     */
    public static final String SORT_DIRECTION_PROPERTY = "$direction";
    /**
     * The constant FILTER_TEXT_KEY.
     */
    public static final String FILTER_TEXT_KEY = "$text";
    /**
     * The constant FILTER_ANY_KEY.
     */
    public static final String FILTER_ANY_KEY = "$any";
    //get log4j handler
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchQuery.class);

    private static Sort getSort(String sortInput) {

        Sort sort = null;
        String[] elems = sortInput.split(",");
        for (int i = 0; i < elems.length; i++) {
            Sort sortInner = null;
            String cleaned = elems[i].replace("+", StringUtils.EMPTY).replace("-", StringUtils.EMPTY);
            if (elems[i].indexOf('-') != -1) {
                sortInner = new Sort(Sort.Direction.DESC, cleaned);
            } else {
                sortInner = new Sort(Sort.Direction.ASC, cleaned);
            }
            if (sort == null) {
                sort = sortInner;
            } else {
                sort = sort.and(sortInner);
            }
        }
        return sort;
    }

    /**
     * Build search query.
     *
     * @param searchkeyvalues the searchkeyvalues
     * @return query query
     */
    public static Sort getSort2(Map<String, String> searchkeyvalues) {
        Sort sort = null;

        searchkeyvalues.remove(SEARCH_METHOD_FIELD_NAME);
        // Apply Sorting
        if (searchkeyvalues.get(SORT_PROPERTY) != null) {
       /*     if (Sort.Direction.DESC.toString().equals(searchkeyvalues.get(SORT_DIRECTION_PROPERTY))) {
                sort = new Sort(Sort.Direction.DESC, searchkeyvalues.get(SORT_PROPERTY));
            } else {
                sort = new Sort(Sort.Direction.ASC, searchkeyvalues.get(SORT_PROPERTY));
            }*/
            sort = getSort(searchkeyvalues.get(SORT_PROPERTY));
        }
        return sort;
    }

    private static String getMethod(Map<String, String> searchkeyvalues) {
        String method = SEARCH_METHOD_AND;
        if (SEARCH_METHOD_OR.equals(searchkeyvalues.get(SEARCH_METHOD_FIELD_NAME))) {
            method = SEARCH_METHOD_OR;
        } else if (SEARCH_METHOD_AND.equals(searchkeyvalues.get(SEARCH_METHOD_FIELD_NAME))) {
            method = SEARCH_METHOD_AND;
        }
        return method;
    }

    /**
     * Build search query criteria criteria.
     *
     * @param searchkeyvaluesIn  the searchkeyvalues in
     * @param searchEqualsFields the search equals fields
     * @return the criteria
     */
    public static Criteria buildSearchQueryCriteria(Map<String, String> searchkeyvaluesIn,
                                                    Set<String> searchEqualsFields) {
        // copy input data
        Map<String, String> searchkeyvalues = new HashMap<>(searchkeyvaluesIn);

        // Remove Sort relevant elements
        searchkeyvalues.remove(SORT_PROPERTY);
        searchkeyvalues.remove(SORT_DIRECTION_PROPERTY);
        searchkeyvalues.remove(FILTER_TEXT_KEY);
        List<CriteriaDefinition> criterias = buildCriteriasFromMap(searchkeyvalues, searchEqualsFields);


        Criteria criteria = new Criteria();
        Criteria[] array = new Criteria[criterias.size()];
        Criteria finalcriteria;
        if (criterias.isEmpty()) {
            finalcriteria = null;
        } else {
            if (SEARCH_METHOD_AND.equals(getMethod(searchkeyvaluesIn))) {
                if (criterias.size() == 1) {
                    finalcriteria = (Criteria) criterias.get(0);

                } else {
                    finalcriteria = criteria.andOperator(criterias.toArray(array));
                }
            } else {
                if (criterias.size() == 1) {
                    finalcriteria = (Criteria) criterias.get(0);

                } else {
                    finalcriteria = criteria.orOperator(criterias.toArray(array));
                }
            }
        }


        return finalcriteria;
    }

    /**
     * Build search query query.
     *
     * @param searchkeyvalues    the searchkeyvalues
     * @param searchEqualsFields the search equals fields
     * @return the query
     */
    public static Query buildSearchQuery(Map<String, String> searchkeyvalues,
                                         Set<String> searchEqualsFields) {

        CriteriaDefinition textCriteria = null;
        if (searchkeyvalues.containsKey(FILTER_TEXT_KEY)) {

            // make text search
            textCriteria = TextCriteria.forDefaultLanguage()
                    .matching(searchkeyvalues.get(FILTER_TEXT_KEY));

            //     criterias.add(criteria);


        }

        Criteria finalcriteria = buildSearchQueryCriteria(searchkeyvalues, searchEqualsFields);
        Sort sort = getSort2(searchkeyvalues);
        org.springframework.data.mongodb.core.query.Query result;
        if (sort == null) {
            if (finalcriteria == null) {
                result = new org.springframework.data.mongodb.core.query.Query();
            } else {
                result = org.springframework.data.mongodb.core.query.Query.query(finalcriteria);
            }
        } else {
            if (finalcriteria == null) {
                result = new org.springframework.data.mongodb.core.query.Query().with(sort);
            } else {

                result = org.springframework.data.mongodb.core.query.Query.query(finalcriteria).with(sort);
            }
        }
        if (textCriteria != null) {
            result.addCriteria(textCriteria);
        }
        return result;
    }


    private static String makeUTF8String(String string) {

        try {
            byte[] b = string.getBytes(StandardCharsets.UTF_8);

            // byte[] b = {(byte) 99, (byte)97, (byte)116};
            return new String(b, StandardCharsets.US_ASCII);
        } catch (Exception e) {
            LOGGER.error("search query error", e);
            return string;
        }

    }

    /**
     * @param searchkeyvalues
     * @return
     */
    private static List<CriteriaDefinition> buildCriteriasFromMap(Map<String, String> searchkeyvalues,
                                                                  Set<String> searchEqualsFields) {

        Map<String, List<Map.Entry<String, String>>> datemap = new HashMap<>();
        List<CriteriaDefinition> criterias = new ArrayList<>();
        for (Map.Entry<String, String> keyvalueOrig : searchkeyvalues.entrySet()) {

            KeyValueItem keyvalue = new KeyValueItem();
            keyvalue.setValue(makeUTF8String(keyvalueOrig.getValue()));
            keyvalue.setKey(keyvalueOrig.getKey());

            if (keyvalue.getKey().contains(".id")) {
                keyvalue.setKey(keyvalue.getKey().replace(".id", "._id"));
            } else if (keyvalue.getKey().equals("id")) {
                keyvalue.setKey("_id");
            }


            if (keyvalue.getKey().equals(SEARCH_METHOD_FIELD_NAME)) {
                // just ignore searchmethod,. which is used to glue all props together :/
            } else if (keyvalue.getKey().contains(GTE_SUFFIX) || keyvalue.getKey().contains(LTE_SUFFIX)) {
                String keycleaned = StringUtils.EMPTY;
                // Split key and remove ".start" and ".end"
                // Special date Start<>End Query
                if (keyvalue.getKey().contains(GTE_SUFFIX)) {

                    keycleaned = keyvalue.getKey().substring(0, keyvalue.getKey().indexOf(GTE_SUFFIX));
                }
                if (keyvalue.getKey().contains(LTE_SUFFIX)) {
                    // Insert Start value to table
                    keycleaned = keyvalue.getKey().substring(0, keyvalue.getKey().indexOf(LTE_SUFFIX));
                }
                if (datemap.get(keycleaned) == null) {
                    // create entry if not date start/end has been saveduntil now
                    datemap.put(keycleaned, new ArrayList<Map.Entry<String, String>>());
                }
                datemap.get(keycleaned).add(keyvalueOrig);
            } else if ("true".equals(keyvalue.getValue()) || "false".equals(keyvalue.getValue())) {
                // Boolean query
                Criteria nextCriteria = Criteria.where(keyvalue.getKey()).is(Boolean.valueOf(keyvalue.getValue()));
                criterias.add(nextCriteria);
            } else if (keyvalue.getValue() != null && (keyvalue.getValue().indexOf('[') == 0) && (keyvalue.getValue().indexOf(']') != -1)) {
                // Array query
                String[] values = keyvalue.getValue().substring(1, keyvalue.getValue().length() - 1).split(",");
                Criteria inCriteria = Criteria.where(keyvalue.getKey()).in(Arrays.asList(values));
                criterias.add(inCriteria);
            } else {
                // fulltext query
                if (keyvalue.getValue() == null) {
                    Criteria nextCriteria = Criteria.where(keyvalue.getKey()).is(null);
                    criterias.add(nextCriteria);
                } else if (searchEqualsFields.contains(keyvalue.getKey())) {
                    // Make an equals search on this field
                    Criteria nextCriteria = Criteria.where(keyvalue.getKey()).is(keyvalue.getValue());
                    criterias.add(nextCriteria);
                } else if (keyvalue.getValue().startsWith("=")) {
                    String rawValue = keyvalue.getValue().substring(1);
                    /*

                    check for or concatenation
                     */
                    if (keyvalue.getValue() != null && keyvalue.getValue().contains("|")) {
                        // use $in operator
                        String[] orelements = rawValue.split("\\|");
                        Criteria nextCriteria = Criteria.where(keyvalue.getKey()).in(Arrays.asList(orelements));
                        criterias.add(nextCriteria);
                    } else {
                        // equal sign in search term uses equality
                        Criteria nextCriteria = Criteria.where(keyvalue.getKey()).is(rawValue);
                        criterias.add(nextCriteria);
                    }
                } else {
                    // make a regexp search on this field

                    String search = cleanSearchFromRegexp(keyvalue.getValue());


                    Criteria nextCriteria = Criteria.where(keyvalue.getKey()).regex(search, "i");
                    // change, warning, using where is horribly slow, but allows regexp on non string fields ....
                    // Criteria nextCriteria = Criteria. whereJS("/" + search + "/.test(this." + keyvalue.getKey() + ")");

                    criterias.add(nextCriteria);
                }
            }
        }
        // Ok, process all entries in the date query list
        for (Map.Entry<String, List<Map.Entry<String, String>>> entryset : datemap.entrySet()) {
            DateTime start = null;
            DateTime end = null;
            LOGGER.debug("Additional date query entry." + entryset.getKey());
            for (Map.Entry<String, String> param : entryset.getValue()) {
                LOGGER.debug("Entry is" + param.getKey() + "=" + param.getValue());
                if (param.getKey().contains(GTE_SUFFIX)) {
                    start = new DateTime((param.getValue()));
                }
                if (param.getKey().contains(LTE_SUFFIX)) {
                    end = new DateTime((param.getValue()));
                }
                // now build date query from the both dates
                // if just start date is set, all dates greater than that are returned
                // if just end date is set, all dates before that are returned
            }
            if ((start != null) && (end == null)) {
                // Just start date set, return all greater than that
                Criteria nextCriteria = Criteria.where(entryset.getKey()).gte(start);
                criterias.add(nextCriteria);
            } else if ((start == null) && (end != null)) {
                // Just end is set,return all before that
                Criteria nextCriteria = Criteria.where(entryset.getKey()).lte(end);
                criterias.add(nextCriteria);
            } else if ((start != null) && (end != null)) {
                // both dates are set, use them
                Criteria nextCriteria1 = Criteria.where(entryset.getKey()).gte(start);
                Criteria nextCriteria2 = Criteria.where(entryset.getKey()).lte(end);
                Criteria nextCriteria = new Criteria();
                nextCriteria.andOperator(nextCriteria1, nextCriteria2);
                criterias.add(nextCriteria);
            }
        }
        /*
        Criteria datecrit=Criteria.where("metaData.lastChangedTimestamp").lte(new DateTime("2014-2-26"));

        Criteria datecrit2=Criteria.where("metaData.lastChangedTimestamp").gte(new DateTime("2014-2-25"));
        criterias.add(datecrit)   ;
         criterias.add(datecrit2)   ;     */
        return criterias;
    }

    private static String cleanSearchFromRegexp(String input) {

        String result;
        result = input.replace("[", "\\[");
        result = result.replace("]", "\\]");
        result = result.replace(".", "\\.");
        return result;
    }

}
