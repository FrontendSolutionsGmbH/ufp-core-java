package com.froso.ufp.core.service;

import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.core.domain.documents.simple.plain.*;
import com.froso.ufp.core.domain.interfaces.*;
import com.froso.ufp.core.service.operations.*;
import org.springframework.data.mongodb.core.mapreduce.*;

import java.io.*;
import java.util.*;

/**
 * Created by ckleinhuix on 24.12.2015.
 *
 * @param <T> the type parameter
 */
public interface RepositoryService<T extends IDataDocument> {

    /**
     * Import from file ressource.
     *
     * @param filename the filename
     * @throws IOException the io exception
     */
    void importFromFileRessource(String filename) throws IOException;

    /**
     * Gets type name.
     *
     * @return the type name
     */
    String getTypeName();

    /**
     * Sets type name.
     *
     * @param typeName the type name
     */
    void setTypeName(String typeName);

    /**
     * Gets class of repository.
     *
     * @return the class of repository
     */
    Class<T> getClassOfRepository();

    /**
     * Save t.
     *
     * @param element the element
     * @return the t
     */
    T save(T element);

    /**
     * Create new default t.
     *
     * @return the t
     */
    T createNewDefault();

    /**
     * Create duplicate t.
     *
     * @param id the id
     * @return the t
     */
//    T createDuplicate(String id);

    /**
     * Find one brute t.
     *
     * @param id the id
     * @return the t
     */
    T findOneBrute(String id);

    /**
     * Find one t.
     *
     * @param id the id
     * @return the t
     */
    T findOne(String id);

    /**
     * findone by a datadocumentlink
     *
     * @param id the id
     * @return t
     */
    T findOne(DataDocumentLink id);

    /**
     * Delete.
     *
     * @param element the element
     */
    void delete(T element);

    /**
     * Find all list.
     *
     * @return the list
     */
    List<T> findAll();

    /**
     * Find by key value list.
     *
     * @param key   the key
     * @param value the value
     * @return the list
     */
    List<T> findByKeyValue(String key, String value);

    /**
     * Find by key values list.
     *
     * @param keyvalues the keyvalues
     * @return the list
     */
    List<T> findByKeyValues(Map<String, String> keyvalues);

    /**
     * Search paged data object list.
     *
     * @param searchkeyvalues the searchkeyvalues
     * @return the data object list
     */
    DataObjectList searchPaged(Map<String, String> searchkeyvalues);


    /**
     * Perform group 2 object.
     *
     * @param propertyPath    the property path
     * @param method          the method
     * @param searchkeyvalues the searchkeyvalues
     * @return the object
     */
    GroupByResults performGroup2(String propertyPath, String propertyValuePath, GroupByMethods method, Map<String, String> searchkeyvalues);

    /**
     * Search get count long.
     *
     * @param searchkeyvaluesIn the searchkeyvalues in
     * @return the long
     */
    long searchGetCount(Map<String, String> searchkeyvaluesIn);

    /**
     * Clean input request params from everything map.
     *
     * @param searchkeyvaluesIn the searchkeyvalues in
     * @return the map
     */
    Map<String, String> cleanInputRequestParamsFromEverything(Map<String, String> searchkeyvaluesIn);

    /**
     * Search list.
     *
     * @param searchkeyvaluesIn the searchkeyvalues in
     * @return the list
     */
    List<T> search(Map<String, String> searchkeyvaluesIn);

    /**
     * Delete all.
     */
    void deleteAll();

    /**
     * Delete all by single key value long.
     *
     * @param key   the key
     * @param value the value
     * @return the long
     */
    long deleteAllBySingleKeyValue(String key, String value);

    /**
     * Gets count.
     *
     * @return the count
     */
    long getCount();

//    void deleteById(String id);

    /**
     * Is existant with throw error boolean.
     *
     * @param id the id
     * @return the boolean
     */
    boolean isExistantWithThrowError(String id);

    /**
     * Is existant boolean.
     *
     * @param id the id
     * @return the boolean
     */
    boolean isExistant(String id);
}
