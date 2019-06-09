package com.froso.ufp.modules.core.globals.controller


import com.froso.ufp.modules.core.globals.service.GlobalsServiceImpl
import spock.lang.Specification

import javax.servlet.http.HttpServletRequest

class GlobalsControllerTest extends Specification {
    def "test getGlobals"() {
        given:
        GlobalsController toTest = new GlobalsController(Mock(GlobalsServiceImpl))
        HttpServletRequest requestMock = Mock()
        when:
        def res = toTest.getGlobals(requestMock)
        then:
        1 * toTest.globalsServiceImplImpl.getGlobals3()
        res.statusCode.toString() == "200"
        noExceptionThrown()
        when:
        res = toTest.getGlobals(requestMock)
        then:
        1 * toTest.globalsServiceImplImpl.getGlobals3() >> null
        res.statusCode.toString() == "200"
        when:
        res = toTest.getGlobals(requestMock)
        then:
        1 * toTest.globalsServiceImplImpl.getGlobals3() >> { throw new Exception() }

        thrown(Exception)

    }
}
