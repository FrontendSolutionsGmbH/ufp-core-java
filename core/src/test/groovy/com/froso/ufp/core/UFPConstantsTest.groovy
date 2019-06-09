package com.froso.ufp.core

import com.froso.ufp.core.exceptions.*
import spock.lang.*

class UFPConstantsTest extends Specification {

    def "UFPConstantsTest Test"() {
        when:

        new UFPConstants()
        then:
        thrown UtilityClassInstanciationException

    }

}
