package com.froso.ufp.modules.core.client.model.service

import com.froso.ufp.modules.core.applicationproperty.interfaces.*
import com.froso.ufp.modules.core.client.model.*
import com.froso.ufp.modules.core.client.service.*
import org.springframework.data.mongodb.core.*
import spock.lang.*

class ClientServiceTest extends Specification {

    public static final String someClientId = "HALLO"
    public static final String theCurrentPropertyLocation = "ufp.modules.core.client.defaultClientId"

    def "Client Service fillDefaultObject Test"() {
        given:

        def service = new ClientService();
        service.mongoTemplate = Mock(MongoTemplate)
        service.mongoOperations = Mock(MongoTemplate)
        service.propertyService = Mock(IPropertyService)

        when:
        def data = new Client()
        service.fillDefaultObject(data)
        then:
        2 * service.propertyService.getPropertyValue(theCurrentPropertyLocation) >> someClientId
        data != null
        data.getId() == someClientId
        1 * service.mongoOperations.findOne(_, _, _) >> null
        1 * service.mongoTemplate.getCollectionName(_) >> "someCollectionName"
        data.toString().contains(someClientId)
        noExceptionThrown()

    }

    def "Client Service fillDefaultObject Test existant"() {
        given:

        def service = new ClientService();
        service.mongoTemplate = Mock(MongoTemplate)
        service.mongoOperations = Mock(MongoTemplate)
        service.propertyService = Mock(IPropertyService)

        when:
        def data = new Client()
        service.fillDefaultObject(data)
        then:
        1 * service.propertyService.getPropertyValue(theCurrentPropertyLocation) >> someClientId
        data != null
        data.getId() != someClientId
        1 * service.mongoOperations.findOne(_, _, _) >> new Client()
        1 * service.mongoTemplate.getCollectionName(_) >> "someCollectionName"
        noExceptionThrown()

    }

}
