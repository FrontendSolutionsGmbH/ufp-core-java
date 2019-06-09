package com.froso.ufp.core

import com.froso.ufp.core.exceptions.*
import com.froso.ufp.core.util.*
import spock.lang.*

class StringUtilTest extends Specification {

    def "String UtilConstrucot Test"() {

        when:

        new StringUtil()
        then:
        thrown UtilityClassInstanciationException
    }

    def "String UtilConstrucot String in List"() {
        given:
        def myList = ["Apple", "Banana", "Orange"]
        def search = "Orange"
        when:

        def result = StringUtil.stringInList(search, myList)
        then:
        notThrown()
        result == true
        when:

        result = StringUtil.stringInList("notfound", myList)
        then:
        notThrown()
        result == false
        when:

        result = StringUtil.stringInList(null, myList)
        then:
        notThrown()
        result == false
        when:

        result = StringUtil.stringInList(search, null)
        then:
        notThrown()
        result == false
        when:

        result = StringUtil.stringInList(null, null)
        then:
        notThrown()
        result == false
    }

}
