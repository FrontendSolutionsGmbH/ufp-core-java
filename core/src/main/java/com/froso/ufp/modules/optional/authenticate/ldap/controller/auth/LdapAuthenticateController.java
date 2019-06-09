package com.froso.ufp.modules.optional.authenticate.ldap.controller.auth;

import com.froso.ufp.core.annotations.*;
import com.froso.ufp.core.response.*;
import com.froso.ufp.core.util.*;
import com.froso.ufp.modules.core.applicationproperty.service.*;
import com.froso.ufp.modules.core.authenticate.model.*;
import com.froso.ufp.modules.optional.authenticate.ldap.configuration.*;
import com.froso.ufp.modules.optional.authenticate.ldap.model.*;
import com.froso.ufp.modules.optional.authenticate.ldap.service.*;
import com.github.javafaker.*;
import io.swagger.annotations.*;
import java.util.*;
import javax.naming.Name;
import javax.naming.*;
import javax.naming.directory.*;
import javax.servlet.http.*;
import javax.validation.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.ldap.core.*;
import org.springframework.ldap.core.support.*;
import org.springframework.ldap.query.*;
import org.springframework.ldap.support.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA. Christian Kleinhuis (ck@froso.de) Date: 15.11.13 Time: 14:58 To change this
 * template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value = LdapConstants.PATH_AUTH)
@Api(description = UFPAuthenticateConstants.AUTHENTICATION_RESOURCE_DESCRIPTION,
        tags = UFPAuthenticateConstants.AUTHENTICATION_TAG)
@UFPPublicController
public class LdapAuthenticateController {


    private static final Logger LOGGER = LoggerFactory.getLogger(LdapAuthenticateController.class);
    @Autowired
    private LdapTemplate ldapTemplate;
    @Autowired
    private LdapContextSource ldapContextSource;
    @Autowired
    private LdapAuthenticateService ldapAuthenticateService;

    /**
     * Authenticate response entity.
     *
     * @param loginData     the login data
     * @param bindingResult the binding result
     * @param request       the request
     * @return the response entity
     */
    @RequestMapping(value = "",
            method = RequestMethod.POST)
    @ApiOperation(value = "authenticate/register user via username/password number",
            notes = "enter your credentials")

    @ResponseBody
    public ResponseEntity<BackendResponseTemplate2SingleObject<TokenData, LdapResultStatusEnumCode>> authenticate(
            @RequestBody
            @ApiParam(required = true,
                    value = "UsernamePassword Login credentials")
            @Valid LdapAuthenticateRequestData loginData, BindingResult bindingResult, HttpServletRequest request) {

        ValidateBindingResult.validate(bindingResult);
        LOGGER.info(" new String(Base64Utils.encode(password.getBytes()))" + new String(Base64Utils.encode("password".getBytes())));
        ResponseHandlerTemplate2SingleObject<TokenData, LdapResultStatusEnumCode> responseHandler = new ResponseHandlerTemplate2SingleObject<>(request);
        TokenData tokenData = ldapAuthenticateService.authenticateUsernamePasswordData(loginData);
        responseHandler.addResult(tokenData);
        responseHandler.setStatus(LdapResultStatusEnumCode.OK);
        return responseHandler.getResponseEntity(HttpStatus.OK);
    }

    /**
     * Gets all person names.
     *
     * @return the all person names
     */
    public List<String> getAllPersonNames() {
        return ldapTemplate.search(
                LdapQueryBuilder.query().where("objectclass").is("person"),
                new AttributesMapper<String>() {
                    public String mapFromAttributes(Attributes attrs)
                            throws NamingException {
                        return attrs.get("cn").get().toString();
                    }
                });
    }

    /**
     * Create.
     *
     * @param username the username
     * @param password the password
     */
    public void createUser(String username, String password) {
        Name dn = LdapNameBuilder
                .newInstance()
                .add("ou", "users")
                .add("cn", username)
                .build();
        DirContextAdapter context = new DirContextAdapter(dn);

        context.setAttributeValues(
                "objectclass",
                new String[]
                        {"top",
                                "person",
                                "organizationalPerson",
                                "inetOrgPerson"});
        context.setAttributeValue("cn", username);
        context.setAttributeValue("sn", username);
        context.setAttributeValue
                ("userPassword", Base64Utils.encode(password.getBytes()));

        try {
            ldapTemplate.bind(context);
        } catch (Exception e) {
            LOGGER.error("ldap create user error",e);

        }
    }

    /**
     * Create.
     *
     * @param unitName the unit name
     */
    public void createOrganizationalUnit(String unitName) {
        Name dn = LdapNameBuilder
                .newInstance()
//                .add("dc", "org")
//                .add("dc", "ufp")
//                .add("dc", "startupzoom")
                .add("ou", unitName)
                .build();
        DirContextAdapter context = new DirContextAdapter(dn);

        context.setAttributeValues(
                "objectclass",
                new String[]
                        {"top",
                                "organizationalUnit"});
        context.setAttributeValue("ou", unitName);

        try {
            ldapTemplate.bind(context);
        } catch (Exception e) {
            LOGGER.error("ldap create organization error",e);
        }
    }


    /**
     * Test ldap response entity.
     *
     * @param request the request
     * @return the response entity
     * @throws Exception the exception
     */
    @RequestMapping(value = "/testldap",
            method = RequestMethod.GET)
    @ApiOperation(value = "ldap test endpoint"
    )

    @ResponseBody
    public ResponseEntity<BackendResponseTemplate2Empty<LdapResultStatusEnumCode>> testLDAP(
            @ApiParam("The Nonce Value to check")

                    HttpServletRequest request) throws Exception

    {

        ResponseHandlerTemplate2Empty<LdapResultStatusEnumCode> responseHandler = new ResponseHandlerTemplate2Empty<>(request);
        // createCopyOfSettings("sss","sss");
        createOrganizationalUnit("users");
        createOrganizationalUnit("config");
        createUser(Faker.instance().name().username(), "password");

        LOGGER.info(getAllPersonNames().toString());
        return responseHandler.getResponseEntity(HttpStatus.OK);
    }

    /**
     * Auth ldap response entity.
     *
     * @param user     the user
     * @param password the password
     * @param request  the request
     * @return the response entity
     */
    @RequestMapping(value = "/testldapauth",
            method = RequestMethod.GET)
    @ApiOperation(value = "ldap test endpoint"
    )

    @ResponseBody
    public ResponseEntity<BackendResponseTemplate2Empty<LdapResultStatusEnumCode>> AuthLdap(
            @RequestParam String user,
            @RequestParam String password,
            HttpServletRequest request) {

        ResponseHandlerTemplate2Empty<LdapResultStatusEnumCode> responseHandler = new ResponseHandlerTemplate2Empty<>(request);
        // createCopyOfSettings("sss","sss");
        ldapContextSource
                .getContext(
                        "cn=" + user +
                                ",ou=users," + LdapAuthenticateModuleConfiguration.LDAP_SUFFIX, new String(Base64Utils.encode(password.getBytes())));

        LOGGER.info(getAllPersonNames().toString());
        return responseHandler.getResponseEntity(HttpStatus.OK);
    }

}
