package com.froso.ufp.modules.core.applicationproperty.service

import com.froso.ufp.core.response.*
import com.froso.ufp.modules.core.applicationproperty.model.*
import org.springframework.beans.factory.support.*
import org.springframework.data.mongodb.core.*
import spock.lang.*

class ApplicationPropertyServiceTest extends Specification {

    public static final String someProperty = "somoeProperty"
    public static final String defaultValue = "defaultValue"
    public static final String someValue = "someString"
    public static final String someCollectionName = "someCollectionName"

    def "Test getProperties  "() {

        given:
        def toTest = new ApplicationPropertyService()
        toTest.mongoTemplate = Mock(MongoTemplate)
        toTest.beanFactory = Mock(AbstractBeanFactory)
        toTest.mongoOperations = Mock(MongoOperations)
        when:

        def result = toTest.getProperties()
        then:
        1 * toTest.mongoTemplate.getCollectionName(_) >> someCollectionName
//        1 * toTest.mongoOperations.find(_, _, _) >> new ArrayList<ApplicationProperty>()
        1 * toTest.mongoOperations.findAll(_, someCollectionName) >> new ArrayList<ApplicationProperty>()
//        1 * toTest.beanFactory.resolveEmbeddedValue('${' + someProperty + '}') >> someValue
        result != null
        result.get('DB') != null
        toTest != null
        noExceptionThrown()

    }

    def "Test ApplicationPropertyService Constructor"() {

        when:
        def toTest = new ApplicationPropertyService()
        then:
        toTest != null
        noExceptionThrown()

    }

    def "Test ApplicationPropertyService getPropertyValue"() {

        given:
        def toTest = new ApplicationPropertyService()
        toTest.mongoTemplate = Mock(MongoTemplate)
        toTest.beanFactory = Mock(AbstractBeanFactory)
        toTest.mongoOperations = Mock(MongoOperations)
        when:
        def result = toTest.getPropertyValue(someProperty)

        then:
//        2 * toTest.mongoTemplate.getCollectionName(_) >> "someCollectionName"
        1 * toTest.mongoOperations.find(_, _, _) >> new ArrayList<ApplicationProperty>()
        1 * toTest.beanFactory.resolveEmbeddedValue('${' + someProperty + '}') >> someValue
        result == "someString"
        toTest != null
        noExceptionThrown()

    }

    def "Test ApplicationPropertyService getPropertyValueDefault"() {

        given:
        def toTest = new ApplicationPropertyService()
        toTest.mongoTemplate = Mock(MongoTemplate)
        toTest.beanFactory = Mock(AbstractBeanFactory)
        toTest.mongoOperations = Mock(MongoOperations)
        when:
        def result = toTest.getPropertyValue(someProperty, defaultValue)

        then:
//        2 * toTest.mongoTemplate.getCollectionName(_) >> "someCollectionName"
        1 * toTest.mongoOperations.find(_, _, _) >> new ArrayList<ApplicationProperty>()
        1 * toTest.beanFactory.resolveEmbeddedValue('${' + someProperty + '}') >> null
        result == defaultValue
        toTest != null
        noExceptionThrown()
        when: 'getPropertyValue'
        result = toTest.getPropertyValue(someProperty, defaultValue)

        then: 'return somevalue'
//        2 * toTest.mongoTemplate.getCollectionName(_) >> "someCollectionName"
        1 * toTest.mongoOperations.find(_, _, _) >> new ArrayList<ApplicationProperty>()
        1 * toTest.beanFactory.resolveEmbeddedValue('${' + someProperty + '}') >> someValue
        result == someValue
        toTest != null
        noExceptionThrown()

        when: 'getPropertyBoolean'
        def resultBoolean = toTest.getPropertyValueBoolean(someProperty)

        then:'return "' + someValue + '"'
//        2 * toTest.mongoTemplate.getCollectionName(_) >> "someCollectionName"
        1 * toTest.mongoOperations.find(_, _, _) >> new ArrayList<ApplicationProperty>()
        1 * toTest.beanFactory.resolveEmbeddedValue('${' + someProperty + '}') >> someValue
        resultBoolean == false
        toTest != null
        noExceptionThrown()

        when: 'looking for boolean value'
        resultBoolean = toTest.getPropertyValueBoolean(someProperty)

        then: 'return "true"'
//        2 * toTest.mongoTemplate.getCollectionName(_) >> "someCollectionName"
        1 * toTest.mongoOperations.find(_, _, _) >> new ArrayList<ApplicationProperty>()
        1 * toTest.beanFactory.resolveEmbeddedValue('${' + someProperty + '}') >> 'true'
        resultBoolean == true
        toTest != null
        noExceptionThrown()



        when: 'looking for integer value wrong'
        def resultInteger = toTest.getPropertyValueInteger(someProperty)

        then: 'return "true"'
//        2 * toTest.mongoTemplate.getCollectionName(_) >> "someCollectionName"
        1 * toTest.mongoOperations.find(_, _, _) >> {[]}
        1 * toTest.beanFactory.resolveEmbeddedValue('${' + someProperty + '}') >> 'someValue'
        thrown NumberFormatException
      when: 'looking for integer value number correct'
        resultInteger = toTest.getPropertyValueInteger(someProperty)

        then: 'return "true"'
//        2 * toTest.mongoTemplate.getCollectionName(_) >> "someCollectionName"
        1 * toTest.mongoOperations.find(_, _, _) >> {[]}
        1 * toTest.beanFactory.resolveEmbeddedValue('${' + someProperty + '}') >> '123'
        resultInteger==123
        notThrown()

    }

    def "Test deleteSingleProperty   "() {

        given:
        def toTest = new ApplicationPropertyService()
        toTest.mongoTemplate = Mock(MongoTemplate)
        toTest.beanFactory = Mock(AbstractBeanFactory)
        toTest.mongoOperations = Mock(MongoOperations)
        def response = Mock(ResponseHandler)
        when:

        toTest.deleteSingleProperty(someProperty, response)
        then:
        1 * response.setStatus(ResultStatusEnumCode.ERROR_RESOURCE_NOTAVAILABLE)
        1 * toTest.mongoTemplate.getCollectionName(_) >> someCollectionName
        1 * toTest.mongoOperations.find(_, _, someCollectionName) >> new ArrayList<ApplicationProperty>()
//        1 * toTest.mongoOperations.findAll(_, "someCollectionName") >> new ArrayList<ApplicationProperty>()
//        1 * toTest.beanFactory.resolveEmbeddedValue('${' + someProperty + '}') >> someValue

        toTest != null
        noExceptionThrown()

    }

    def "Test deleteSingleProperty existant  "() {

        given:
        def toTest = new ApplicationPropertyService()
        toTest.mongoTemplate = Mock(MongoTemplate)
        toTest.beanFactory = Mock(AbstractBeanFactory)
        toTest.mongoOperations = Mock(MongoOperations)
        def response = Mock(ResponseHandler)
        when:

        toTest.deleteSingleProperty(someProperty, response)
        then:
        1 * response.setStatus(ResultStatusEnumCode.OK)
        2 * toTest.mongoTemplate.getCollectionName(_) >> someCollectionName
        1 * toTest.mongoOperations.find(_, _, someCollectionName) >> { [new ApplicationProperty()] }
//        1 * toTest.mongoOperations.findAll(_, "someCollectionName") >> new ArrayList<ApplicationProperty>()
//        1 * toTest.beanFactory.resolveEmbeddedValue('${' + someProperty + '}') >> someValue

        toTest != null
        noExceptionThrown()

    }

    def "Test getSingleProperty existant  "() {

        given:
        def toTest = new ApplicationPropertyService()
        toTest.mongoTemplate = Mock(MongoTemplate)
        toTest.beanFactory = Mock(AbstractBeanFactory)
        toTest.mongoOperations = Mock(MongoOperations)
        def response = Mock(ResponseHandler)
        when:

        def result = toTest.getSingleProperty(someProperty, response)
        then:
        1 * response.setStatus(ResultStatusEnumCode.OK)
        1 * toTest.mongoTemplate.getCollectionName(_) >> someCollectionName
        1 * toTest.mongoOperations.find(_, _, someCollectionName) >> { [new ApplicationProperty()] }
//        1 * toTest.mongoOperations.findAll(_, "someCollectionName") >> new ArrayList<ApplicationProperty>()
//        1 * toTest.beanFactory.resolveEmbeddedValue('${' + someProperty + '}') >> someValue
        result != null
        toTest != null
        noExceptionThrown()

    }

    def "Test getSingleProperty not existant  "() {

        given:
        def toTest = new ApplicationPropertyService()
        toTest.mongoTemplate = Mock(MongoTemplate)
        toTest.beanFactory = Mock(AbstractBeanFactory)
        toTest.mongoOperations = Mock(MongoOperations)
        def response = Mock(ResponseHandler)
        when:

        def result = toTest.getSingleProperty(someProperty, response)
        then:
        1 * response.setStatus(ResultStatusEnumCode.ERROR_RESOURCE_NOTAVAILABLE)
        1 * toTest.mongoTemplate.getCollectionName(_) >> someCollectionName
        1 * toTest.mongoOperations.find(_, _, someCollectionName) >> { [] }
//        1 * toTest.mongoOperations.findAll(_, "someCollectionName") >> new ArrayList<ApplicationProperty>()
//        1 * toTest.beanFactory.resolveEmbeddedValue('${' + someProperty + '}') >> someValue
        result == null
        toTest != null
        noExceptionThrown()

    }
}
