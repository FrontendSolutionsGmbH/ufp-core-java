package com.froso.ufp.core.response;

/**
 * Created by ck on 13.04.2016.
 */
public class SwaggerDocSnippets {
    /**
     * The constant RESPONSE_START_HELP.
     */
    public static final String RESPONSE_START_HELP = "\n|Filter|Description|\n---|---\n";
    /**
     * The constant RESPONSE_END_HELP.
     */
    public static final String RESPONSE_END_HELP = "\n";
    /**
     * The constant RESPONSE_START.
     */
    public static final String RESPONSE_START = "Return codes of this endpoint:\n\n|possible errors|Description|\n---|---\n";
    /**
     * The constant ERROR_CREATE_USER_ALREADY_EXISTANT.
     */
    public static final String ERROR_CREATE_USER_ALREADY_EXISTANT = "|ERROR_CREATE_USER_ALREADY_EXISTANT|A user with these credentials is already registered|\n";
    /**
     * The constant ERROR_CREATE_USER_INVALID_INPUT.
     */
    public static final String ERROR_CREATE_USER_INVALID_INPUT = "|ERROR_CREATE_USER_INVALID_INPUT|Input data has wrong format, e.g. the email is in bad format, required fields not provided. |\n";
    /**
     * The constant ERROR_UPDATE_USER_INVALID_INPUT.
     */
    public static final String ERROR_UPDATE_USER_INVALID_INPUT = "|ERROR_UPDATE_USER_INVALID_INPUT|Input data has wrong format, e.g. the email is in bad format, required fields not provided. |\n";
    /**
     * The constant ERROR_LOGIN_USER_PASSWORD_NAME_NOT_VALID.
     */
    public static final String ERROR_LOGIN_USER_PASSWORD_NAME_NOT_VALID = "|ERROR_LOGIN_USER_PASSWORD_NAME_NOT_VALID|The login failed, either because user is not existant or the provided auth_email does not match|\n";
    /**
     * The constant ERROR_TOKEN_INVALID.
     */
    public static final String ERROR_TOKEN_INVALID = "|ERROR_TOKEN_INVALID|Token invalid, usually means a re-login is required.|\n";
    /**
     * The constant ERROR_RESOURCE_NOTAVAILABLE.
     */
    public static final String ERROR_RESOURCE_NOTAVAILABLE = "|ERROR_RESOURCE_NOTAVAILABLE|The requested resource is not available.|\n";
    /**
     * The constant ERROR_USER_REQUIRES_ACTIVATION.
     */
    public static final String ERROR_USER_REQUIRES_ACTIVATION = "|ERROR_USER_REQUIRES_ACTIVATION|CoreUser needs to activate his account before using this functionality.|\n";
    /**
     * The constant ERROR_USER_IS_BLOCKED.
     */
    public static final String ERROR_USER_IS_BLOCKED = "|ERROR_USER_IS_BLOCKED|CoreUser is blocked and is not allowed to access functionality. Administration actions is required to un-block user.|\n";
    /**
     * The constant ERROR_NO_READ_PRIVILEGE.
     */
    public static final String ERROR_NO_READ_PRIVILEGE = "|ERROR_NO_READ_PRIVILEGE|For READ operations on this resource the user has not the required privileges, check associated user groups. |\n";
    /**
     * The constant ERROR_NO_UPDATE_PRIVILEGE.
     */
    public static final String ERROR_NO_UPDATE_PRIVILEGE = "|ERROR_NO_UPDATE_PRIVILEGE|For UPDATE operations on this resource the user has not the required privileges, check associated user groups.|\n";
    /**
     * The constant ERROR_NO_DELETE_PRIVILEGE.
     */
    public static final String ERROR_NO_DELETE_PRIVILEGE = "|ERROR_NO_DELETE_PRIVILEGE|For DELETE operations on this resource the user has not the required privileges, check associated user groups.|\n";
    /**
     * The constant ERROR_NO_CREATE_PRIVILEGE.
     */
    public static final String ERROR_NO_CREATE_PRIVILEGE = "|ERROR_NO_CREATE_PRIVILEGE|For CREATE operations on this resource the user has not the required privileges, check associated user groups.|\n";
    /**
     * The constant RESPONSE_END.
     */
    public static final String RESPONSE_END = "";
    /**
     * The constant FILTER_RULE.
     */
    public static final String FILTER_RULE = "\n\nRequest Filter Syntax:\n\n" +
            "|Filter Option|Description|\n---|---\n" +
            "|property.subproperty=foo|Any property of an object may be submitted and used for filtering. Standard dot syntax is used to filter sub properties.|\n" +
            "|property.subproperty==foo|Filter for fields containing exactly the provided value (attention: ==)|\n" +
            "|property.subproperty.gte=|ISO 8601 'Greater than equal' suffix can be added to DATE properties to allow for constraining above provided value.|\n" +
            "|property.subproperty.lte=|ISO 8601 'Less than equal' suffix can be addod to DATE properties to allow for constraining below provided value.|\n" +
            "|$method=[and,or]|If more than one property is provided this field controls if all values need to be inside result (and) or at least one value needs to be present in result (or)|\n" +
            "|$sort={+/-}property.subproperty|Field to be used for sorting, comma separated list of properties is allowed, sort direction defaults to '+'|\n" +
            "|$limit=[NUMBER]|Page size for number of returned items.|\n" +
            "|$page=[NUMBER]|According to the '$limit' parameter, which determines pagesize the offset is given here|\n";

    /**
     * Create row string.
     *
     * @param name        the name
     * @param description the description
     * @return the string
     */
    public static final String createRow(String name, String description) {
        return "|" + name + "|" + description + "|";

    }

}
