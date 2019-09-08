package com.froso.ufp.core.configuration;

import com.froso.ufp.core.*;
import com.froso.ufp.modules.core.applicationproperty.interfaces.*;
import com.froso.ufp.modules.core.templatesv2.service.*;
import org.joda.time.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.*;
import springfox.documentation.builders.*;
import springfox.documentation.service.*;
import springfox.documentation.spi.*;
import springfox.documentation.spi.service.contexts.*;
import springfox.documentation.spring.web.plugins.*;
import springfox.documentation.swagger.web.*;
import springfox.documentation.swagger2.annotations.*;

import java.util.*;

@EnableSwagger2
/*

this class acts as a base class for swagger initialisation, it is not marked as @Configuration, it should be used by subclassing and providing values for the
abstract template methods

@Configuration
*/
public abstract class AbstractSwaggerConfig extends WebMvcConfigurerAdapter {

    public static final String securitySchemaOAuth2 = "oauth2schema";
    public static final String authorizationScopeGlobal = "global";
    public static final String authorizationScopeGlobalDesc = "accessEverything";

    public AbstractSwaggerConfig() {
        super();
    }

    abstract protected ApiInfo getSwaggerApiDefinition();

    protected OAuth securitySchema() {
        AuthorizationScope authorizationScope = new AuthorizationScope(authorizationScopeGlobal, authorizationScopeGlobal);
        LoginEndpoint loginEndpoint = new LoginEndpoint("http://localhost/api/Session/Sms");
        GrantType grantType = new ImplicitGrant(loginEndpoint, "x-ufp-token");

        OAuth result = new OAuth(securitySchemaOAuth2, makeList(authorizationScope), makeList(grantType));

        return result;
    }

    protected SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("/Session.*"))
                .build();
    }

    protected List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope(authorizationScopeGlobal, authorizationScopeGlobalDesc);
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return makeList(
                new SecurityReference(securitySchemaOAuth2, authorizationScopes));
    }

    @Bean
    SecurityConfiguration security() {
        return new SecurityConfiguration(
                "test-app-client-id",
                "test-app-client-secret",
                "test-app-realm",
                "test-app",
                "apiKey",
                ApiKeyVehicle.HEADER,
                "api_key",
                "," /*scope separator*/);
    }

    protected List<SecurityScheme> makeList(SecurityScheme schema) {
        List<SecurityScheme> result = new ArrayList<>();

        result.add(schema);

        return result;

    }

    protected List<GrantType> makeList(GrantType schema) {
        List<GrantType> result = new ArrayList<>();

        result.add(schema);

        return result;

    }

    protected List<AuthorizationScope> makeList(AuthorizationScope schema) {
        List<AuthorizationScope> result = new ArrayList<>();

        result.add(schema);

        return result;

    }

    protected List<SecurityReference> makeList(SecurityReference schema) {
        List<SecurityReference> result = new ArrayList<>();

        result.add(schema);

        return result;

    }

    protected List<SecurityContext> makeList(SecurityContext schema) {
        List<SecurityContext> result = new ArrayList<>();

        result.add(schema);

        return result;

    }

    @Bean
    @Autowired
    public Docket swaggerAdminApiCoreDocumentation(IPropertyService propertyService) {

        Contact contact = new Contact("Frontend Solutions GmbH", "http://www.froso.de/", "contact@froso.de");
        return new Docket(DocumentationType.SWAGGER_2)

                //.directModelSubstitute(DataDocumentMetaData.class,Objects.class)

                // global date output config
                .directModelSubstitute(DateTime.class, Date.class)
                .directModelSubstitute(LocalDate.class, Date.class)
                // .directModelSubstitute(GlobalSettings3.class, HashMap.class)
                .useDefaultResponseMessages(false)
                .produces(getContentType()).consumes(getContentType())
                .groupName("ufp-admin-api").select()
                // .paths(PathSelectors.ant("/" + UFPConstants.ADMIN_FULL + "/**"))
                .paths(PathSelectors.regex(".*"))
                //  .build().securitySchemes(makeList(apiKey()))
                .build().securitySchemes(makeList(securitySchema()))
                .securityContexts(makeList(securityContext()))
                .apiInfo(new ApiInfo(
                        "UFP Admin Api",
                        "UFP Core Admin functionality",
                        propertyService.getPropertyValue(UFPConstants.PROPERTY_APPLICATION_VERSION),
                        "",
                        contact,
                        "License",
                        "https://froso.de",
                        new ArrayList<VendorExtension>()

                ))
                .host(propertyService.getPropertyValue(UFPConstants.PROPERTY_APPLICATION_HOSTNAME))
                ;
    }

    @Bean
    UiConfiguration uiConfig() {
        return new UiConfiguration("validatorUrl",// url
                "none",       // docExpansion          => none | list
                "alpha",      // apiSorter             => alpha
                "schema",     // defaultModelRendering => schema
                UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS,
                false,        // enableJsonEditor      => true | false
                true,         // showRequestHeaders    => true | false
                60000L);      // requestTimeout => in milliseconds, defaults to null (uses jquery xh timeout)
    }

    protected Set<String> getContentType() {
        return new HashSet<String>() {{
            add(UFPConstants.APPLICATION_DEFAULT_CONTENT_TYPE_UTF8);

        }};
    }

    @Bean
    @Autowired
    public Docket swaggerPublicApiCoreDocumentation(IPropertyService propertyService) {

        return new Docket(DocumentationType.SWAGGER_2)
                // global date output config
                .directModelSubstitute(DateTime.class, java.sql.Date.class)
                .directModelSubstitute(LocalDate.class, Date.class)
                // .directModelSubstitute(GlobalSettings3.class, Set.class)
                .groupName("ufp-public-api")
                .produces(getContentType())
                .consumes(getContentType())
                .useDefaultResponseMessages(false).select()
                .paths(PathSelectors.regex("^((?!" + UFPConstants.API + "\\/" + UFPConstants.ADMIN + ").)*$"))
                .build().securitySchemes(makeList(securitySchema()))
                .securityContexts(makeList(securityContext()))
                .apiInfo(getSwaggerApiDefinition())
                .host(propertyService.getPropertyValue(UFPConstants.PROPERTY_APPLICATION_HOSTNAME))
                ;
    }

    @Autowired(required = false)
    public void configureSwaggerResources(TemplateGlobalsService templateGlobalsService) {
        if (templateGlobalsService != null) {
            templateGlobalsService.registerTemplateVariable(TemplateGlobalsService.TEMPLATE_VAR_RESOURCES, "Swagger JSON", ":baseUrl/v2/api-docs?group=ufp-public-api");
            templateGlobalsService.registerTemplateVariable(TemplateGlobalsService.TEMPLATE_VAR_RESOURCES, "Swagger UI", ":baseUrl/swagger-ui.html");
        }

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}
