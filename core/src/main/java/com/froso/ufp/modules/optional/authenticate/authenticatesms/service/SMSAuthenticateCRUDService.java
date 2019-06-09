package com.froso.ufp.modules.optional.authenticate.authenticatesms.service;

import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.core.util.*;
import com.froso.ufp.modules.core.applicationproperty.interfaces.*;
import com.froso.ufp.modules.core.authenticate.service.*;
import com.froso.ufp.modules.core.templatesv2.model.*;
import com.froso.ufp.modules.core.templatesv2.service.*;
import com.froso.ufp.modules.optional.authenticate.authenticatesms.controller.*;
import com.froso.ufp.modules.optional.authenticate.authenticatesms.model.*;
import com.google.i18n.phonenumbers.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class SMSAuthenticateCRUDService extends AbstractCoreAuthenticationsService<SMSAuthenticateModel> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SMSAuthenticateCRUDService.class);
    private static final String PROPERTY_NAME_DEFAULT_PHONE_REGION = "com.froso.ufp.modules.optional.authenticate.authenticatesms.phone.defaultregion";

    private final TemplateService templateService;

    private final IPropertyService propertyService;

    @Autowired
    public SMSAuthenticateCRUDService(IPropertyService propertyService, TemplateService templateService) {
        super(SMSAuthenticateModel.TYPE_NAME);
        this.propertyService = propertyService;
        this.templateService = templateService;
    }

    public SMSAuthenticateModel findByUserId(DataDocumentLink link) {
        if (link != null) {
            return findByUserId(link.getId());
        }
        return null;
    }

    public SMSAuthenticateModel findByUserId(String userId) {

        return findOneByKeyValue("coreUser.id", userId);

    }

    @Override
    protected void prepareSave(SMSAuthenticateModel object) {
        super.prepareSave(object);
        object.getData().setPhoneNumber(validatePhoneNumberAndReturnCleaned(object.getData().getPhoneNumber()));
    }

    String parseSMSTemplate(SMSAuthenticateModel model, String template) {

        TemplateSettings settings = new TemplateSettings();
        settings.setTemplatePath("sms-auth");
        HashMap<String, Object> data = new HashMap<>();
        data.put("authsms", model);

        byte[] result = templateService.parseTemplateBytes("Default", template, data, settings);
        return new String(result).trim();

    }

    public String validatePhoneNumberAndReturnCleaned(String phoneNumber) {
        if (phoneNumber == null) {

            throw new SMSAuthenticateException.InvalidPhoneNumber();
        }

        String cleanedUpPhoneNumber = UFPPhoneNumberUtil.phoneNumberRemoveNonNumbers(phoneNumber);

        // validate phonenumber
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        Phonenumber.PhoneNumber parsedPhone = null;
        try {
            parsedPhone = phoneUtil.parse(cleanedUpPhoneNumber, propertyService.getPropertyValue(PROPERTY_NAME_DEFAULT_PHONE_REGION));
            LOGGER.info("parsed phone number is" + parsedPhone.toString());

            LOGGER.info("parsed phone number is valid?" + phoneUtil.isValidNumber(parsedPhone));
            LOGGER.info("parsed phone number E164" + phoneUtil.format(parsedPhone, PhoneNumberUtil.PhoneNumberFormat.E164));
            LOGGER.info("parsed phone number INTERNATIONAL" + phoneUtil.format(parsedPhone, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL));
            LOGGER.info("parsed phone number NATIONAL" + phoneUtil.format(parsedPhone, PhoneNumberUtil.PhoneNumberFormat.NATIONAL));
            if (!phoneUtil.isValidNumber(parsedPhone)) {

                throw new SMSAuthenticateException.InvalidPhoneNumber();
            }
        } catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e.toString());
            throw new SMSAuthenticateException.InvalidPhoneNumber(e);
        }
        return phoneUtil.format(parsedPhone, PhoneNumberUtil.PhoneNumberFormat.E164);

    }
}
