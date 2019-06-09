package com.froso.ufp.modules.core.authenticate.service

import spock.lang.*

class AuthenticateNonceServiceTest extends Specification {
    def "test verifyNonceToModel"() {
        given:
        def authSerivce = new AuthenticateNonceService()
        when:
        // TODO implement stimulus
        def res = authSerivce.verifyNonceToModel(null, null, null)
        then:
        res == false
        noExceptionThrown()
    }
}
