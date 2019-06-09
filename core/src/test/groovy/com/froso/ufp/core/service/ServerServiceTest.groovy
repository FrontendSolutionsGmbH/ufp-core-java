package com.froso.ufp.core.service

import com.froso.ufp.modules.core.applicationproperty.interfaces.*
import spock.lang.*

class ServerServiceTest extends Specification {

    public static final String SOME_PROPERTY_RESULT = "HULU"

    def "ServerServiceTest Create"() {
        given:
        def propMock = Mock(IPropertyService)
        def service = new ServerService()
        service.propertyService = propMock
        when:
        def result = service.getApiUrl()
        then:
        1 * propMock.getPropertyValue(ServerService.PROP_NAME_APPLICATION_SERVER_API) >> SOME_PROPERTY_RESULT
        result == SOME_PROPERTY_RESULT
    }

    def "ServerServiceTest Create Null"() {
        given:
        def service = new ServerService()
        when:
        def result = service.getApiUrl()
        then:
        notThrown()
        result == null
    }

    def "ServerServiceTest Create2"() {
        given:
        def propMock = Mock(IPropertyService)
        def service = new ServerService()
        service.propertyService = propMock
        when:
        def result = service.getServerSettings()
        then:
        1 * propMock.getPropertyValue(ServerService.PROP_NAME_APPLICATION_SERVER_API) >> SOME_PROPERTY_RESULT
        result.get("api") == SOME_PROPERTY_RESULT
    }

}
