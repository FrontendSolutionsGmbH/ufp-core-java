package com.froso.ufp.modules.optional.authenticate.authenticatesms.service

import com.froso.ufp.modules.core.authenticate.service.AuthenticateNonceService
import com.froso.ufp.modules.optional.sms.service.LowLevelSMSService
import spock.lang.Specification

class SMSAuthenticateServiceTest extends Specification {
//    def "test getAuthenticationsForUser"() {
//        given:
//
//        when:
//        // TODO implement stimulus
//        then:
//        // TODO implement assertions
//    }
//
//    def "test createNewNonceForPhoneNumber"() {
//        given:
//
//        SMSAuthenticateService serviceToTest = new SMSAuthenticateService()
//        AuthenticationsService authenticationsServiceMock = Mock()
//        SMSAuthenticateCRUDService smsAuthenticateCRUDServiceMock = Mock()
//        IPropertyService propertyServiceMock = Mock()
//        LowLevelSMSService lowLevelSMSServiceMock = Mock()
//        serviceToTest.smsAuthenticateCRUDService = smsAuthenticateCRUDServiceMock
//        serviceToTest.authenticationsService = authenticationsServiceMock
//        serviceToTest.applicationPropertyService = propertyServiceMock
//        serviceToTest.lowLevelSMSService = lowLevelSMSServiceMock
//
//         SMSAuthenticateRequestDataPhonenumberOnly data = new SMSAuthenticateRequestDataPhonenumberOnly()
//        when:
//
//        data.phoneNumber = "illegal"
//        serviceToTest.createNewNonceForPhoneNumber(data)
//        then:
//        1* authenticationsServiceMock.isAutoRegisterEnabled(_)>>false

    // }
//
//    def "test authenticateSMSData"() {
//        given:
//
//        when:
//        // TODO implement stimulus
//        then:
//        // TODO implement assertions
//    }

    def "test getAuthenticationName"() {
        given:
        SMSAuthenticateService serviceToTest = new SMSAuthenticateService(
                Mock(AuthenticateNonceService),
                Mock(LowLevelSMSService),
                Mock(SMSAuthenticateCRUDService))
        when:
        def result = serviceToTest.getAuthenticationName()
        then:
        result == "Sms"
    }
}
