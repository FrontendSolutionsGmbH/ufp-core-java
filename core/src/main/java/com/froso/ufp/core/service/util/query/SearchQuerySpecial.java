package com.froso.ufp.core.service.util.query;

import java.lang.reflect.*;
import java.util.*;
import org.slf4j.*;
import org.springframework.data.mongodb.core.query.*;

/**
 * Created with IntelliJ IDEA. Entiteit: ck Date: 20.03.14 Time: 12:06 To change this template use File | Settings | File
 * Templates.
 */
public class SearchQuerySpecial {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchQuerySpecial.class);
    /**
     * warning: these constants correspond to the function names!!!! the methods themselve are marked "uncalled" but
     * they are called "dynamically" using these function names!
     */
    private static final String FUNC_GET_FULL_WORDS_REGEX_STRING = "getFullWordsRegexString";
    private static final String FUNC_GET_REGEX_AT_BEGINNIN_OR_END = "getRegexAtBeginOrEnd";
    private static final String FUNC_GET_REGEX_ANY = "getRegexAny";

    /*
                CAUTION: DO NOT REMOVE THOSE "UNUSED" METHODS; THEY ARE REFERED TO USING JAVA REFLECTION !
                 */
//    private static String getFullWordsRegexString(String searchTerm) {
//
//        return "\b" + searchTerm + "\b";
//    }

    /*
    CAUTION: DO NOT REMOVE THOSE "UNUSED" METHODS; THEY ARE REFERED TO USING JAVA REFLECTION !
     */
//    private static String getRegexAtBeginOrEnd(String searchTerm) {
//
//        return "\b" + searchTerm + "|" + searchTerm + "\b";
//    }

    /*
    CAUTION: DO NOT REMOVE THOSE "UNUSED" METHODS; THEY ARE REFERED TO USING JAVA REFLECTION !
     */
//    private static String getRegexAny(String searchTerm) {
//
//        return searchTerm;
//    }

    /**
     * public method to return a query object that executes a search for searchterm that is CENTERED inside a WORD in
     * the "name" field
     *
     * @param searchTerm              the search term
     * @param injectedCriteriaPerItem the injected criteria per item
     * @return query query
     */
    public static Query buildSearchQueryCenteredInWordsOnly(String searchTerm,
                                                            CriteriaFactory injectedCriteriaPerItem) {

        return new Query(buildSearchCriteriaAllWordsExceptSingleAndBeginningEnd(searchTerm, injectedCriteriaPerItem));
    }

    /**
     * internal wrapper method to make sure that an executed query does NOT contain previous ones in this case they
     * are:
     * NO FULL WORDS Are counting as result    , AND NO BEGINNING OR ENDING WORDS ARE INCLUDED
     *
     * @param searchTerm
     * @param injectedCriteriaPerItem
     * @return
     */
    private static Criteria buildSearchCriteriaAllWordsExceptSingleAndBeginningEnd(String searchTerm,
                                                                                   CriteriaFactory injectedCriteriaPerItem) {

        return new Criteria().andOperator(buildSearchCriteriaForWordsByRegexpMethod(FUNC_GET_REGEX_ANY, searchTerm, injectedCriteriaPerItem),
                buildNotSearchCriteriaForWordsByRegexpMethod(FUNC_GET_REGEX_AT_BEGINNIN_OR_END, searchTerm),
                buildNotSearchCriteriaForWordsByRegexpMethod(FUNC_GET_FULL_WORDS_REGEX_STRING, searchTerm));
    }

    /**
     * helper method to create a single criteria out of a space separated list of search words, including the injected
     * criteria
     *
     * @param regexpMethod
     * @param searchTerm
     * @param injectedCriteriaPerItem
     * @return
     */
    private static Criteria buildSearchCriteriaForWordsByRegexpMethod(String regexpMethod,
                                                                      String searchTerm,
                                                                      CriteriaFactory injectedCriteriaPerItem) {

        List<Criteria> criterias = new ArrayList<>();
        criterias.add(injectedCriteriaPerItem.createCriteria());
        criterias.addAll(buildCriterias(regexpMethod, searchTerm));
        // Create And Concatenated Criteria from all
        Criteria[] arraycrits = new Criteria[criterias.size()];
        criterias.toArray(arraycrits);
        return new Criteria().andOperator(arraycrits);
    }

    /**
     * helper method to create a single NEGATED criteria out of a space separated list of search words, including the
     * injected criteria
     *
     * @param regexpMethod
     * @param searchTerm
     * @return
     */
    private static Criteria buildNotSearchCriteriaForWordsByRegexpMethod(String regexpMethod,
                                                                         String searchTerm) {

        List<Criteria> criterias = new ArrayList<>();
        criterias.addAll(buildNotCriterias(regexpMethod, searchTerm));
        // Create And Concatenated Criteria from all
        Criteria[] arraycrits = new Criteria[criterias.size()];
        criterias.toArray(arraycrits);
        return new Criteria().andOperator(arraycrits);
    }

    /**
     * this method takes a RegexpGenerator Method which gets a single search term as input and returns the according
     * regular expression the method then generates the Criterias needed..
     *
     * @param thisClassMethodName the name of the regexp generator method, needs to be static, *can* be private, needs
     *                            to belong to THIS class!
     * @param searchTerm          a search term with controller space separated words, which become AND combined
     * @return list of criterias
     */
    private static List<Criteria> buildCriterias(String thisClassMethodName,
                                                 String searchTerm) {

        List<Criteria> criterias = new ArrayList<>();
        // override "private" classifier
        try {
            // Get Method From this object!
            Method regexpMethod = SearchQuerySpecial.class.getDeclaredMethod(thisClassMethodName, String.class);
            regexpMethod.setAccessible(true);
            String[] words = getSplittedStrings(searchTerm);
            for (String word : words) {
                criterias.add(Criteria.where("name").regex((String) regexpMethod.invoke(null, word), "i"));
            }
        } catch (NoSuchMethodException e) {
            // InvocationException IllegalAccessException
            LOGGER.error("buildCriterias Exception", e);
        } catch (IllegalAccessException e) {
            // InvocationException IllegalAccessException
            LOGGER.error("buildCriterias Exception", e);
        } catch (InvocationTargetException e) {
            // InvocationException IllegalAccessException
            LOGGER.error("buildCriterias Exception", e);
        }
        return criterias;
    }

    /**
     * NEGATE METHOD! this method takes a RegexpGenerator Method which gets a single search term as input and returns
     * the according regular expression the method then generates the INVERTED Criterias needed..
     *
     * @param thisClassMethodName the name of the regexp generator method, needs to be static, *can* be private, needs
     *                            to belong to THIS class!
     * @param searchTerm          a search term with controller space separated words, which become AND combined
     * @return list of INVERTED criterias
     */
    private static List<Criteria> buildNotCriterias(String thisClassMethodName,
                                                    String searchTerm) {

        List<Criteria> criterias = new ArrayList<>();
        // override "private" classifier
        try {
            // Get Method From this object!
            Method regexpMethod = SearchQuerySpecial.class.getDeclaredMethod(thisClassMethodName, String.class);
            regexpMethod.setAccessible(true);
            String[] words = getSplittedStrings(searchTerm);
            for (String word : words) {
                criterias.add(Criteria.where("name").not().regex((String) regexpMethod.invoke(null, word), "i"));
            }
        } catch (IllegalAccessException | InvocationTargetException | SecurityException | NoSuchMethodException e) {
            // InvocationException IllegalAccessException
            LOGGER.error("buildNotCriterias exception ", e);
        }
        return criterias;
    }

    /**
     * returns a string splitted by spaces, convenience method
     *
     * @param baseString
     * @return
     */
    private static String[] getSplittedStrings(String baseString) {

        return baseString.split((" "));
    }


    /**
     * internal wrapper method to make sure that an executed query does NOT contain previous ones in this case they
     * are:
     * NO FULL WORDS Are counting as result
     *
     * @param searchTerm
     * @param injectedCriteriaPerItem
     * @return
     */
    private static Criteria buildSearchCriteriaBeginningEndExceptWithSingleWords(String searchTerm,
                                                                                 CriteriaFactory injectedCriteriaPerItem) {

        return new Criteria()
                .andOperator(buildSearchCriteriaForWordsByRegexpMethod(FUNC_GET_REGEX_AT_BEGINNIN_OR_END, searchTerm, injectedCriteriaPerItem),
                        buildNotSearchCriteriaForWordsByRegexpMethod(FUNC_GET_FULL_WORDS_REGEX_STRING, searchTerm));
    }

    /**
     * public method to return a query object that executes a search for searchterm that is fully existand as SINGLE
     * Word in the "name" field
     *
     * @param searchTerm              the search term
     * @param injectedCriteriaPerItem the injected criteria per item
     * @return query query
     */
    public static Query buildSearchQueryAllSingleWords(String searchTerm,
                                                       CriteriaFactory injectedCriteriaPerItem) {

        return new Query(buildSearchCriteriaForWordsByRegexpMethod(FUNC_GET_FULL_WORDS_REGEX_STRING, searchTerm, injectedCriteriaPerItem));
    }
}
