package com.froso.ufp.modules.core.user.service.util

import com.froso.ufp.core.exceptions.*
import com.froso.ufp.modules.core.user.exception.*
import com.froso.ufp.modules.core.user.model.*
import spock.lang.*

class UserValidatorTest extends Specification {


    def "Test Validate"() {
        given:

        def user = Mock(ICoreUser)


        when:
        new UserValidator()
        then:
        thrown UtilityClassInstanciationException


        when:
        UserValidator.validateUser(user)

        then:
        1 * user.getActive() >> true
        1 * user.getBlocked() >> false

        noExceptionThrown()

        when:
        UserValidator.validateUser(user)

        then:
        1 * user.getActive() >> false
        0 * user.getBlocked() >> true

        thrown UserException.UserNotActive

        when:
        UserValidator.validateUser(user)

        then:
        1 * user.getActive() >> true
        1 * user.getBlocked() >> true

        thrown UserException.UserBlocked
        when:
        UserValidator.validateUser(null)

        then:

        thrown UserException.UserNotExistant

    }

}
