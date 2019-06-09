package com.froso.ufp.core.util

import com.froso.ufp.core.exceptions.*
import spock.lang.*

import java.nio.charset.*

class Base64UtilTest extends Specification {

    public static final String BASE64ENCODED_STRING = "SGVsbG8gUGVvcGxlIDEyMyBeJQ"
    public static final String SOME_STRING = "1234"
    public static final String UNDECODED_STRING = "Hello People 123 ^%"

    def "Base64UtilTest Create"() {

        when:

        new Base64Util()
        then:
        thrown UtilityClassInstanciationException
    }

    def "Base64UtilTest Encode"() {

        when:

        def result = new String(Base64Util.base32Decode(BASE64ENCODED_STRING), StandardCharsets.UTF_8)
        then:
        result == UNDECODED_STRING
    }

    def "Base64UtilTest Encode Error"() {
        when:
        def result = new String(Base64Util.base32Decode(BASE64ENCODED_STRING + "=="), StandardCharsets.UTF_8)
        then:
        thrown IllegalArgumentException
    }

    def "Base64UtilTest Encode Error2"() {
        when:
        new String(Base64Util.base32Decode(SOME_STRING), StandardCharsets.UTF_8)
        then:
        noExceptionThrown()
    }

    def "Base64UtilTest base32Encode"() {

        when:
        def result = Base64Util.base32Decode(BASE64ENCODED_STRING)
        def result2 = Base64Util.base32Encode(result)
        then:
        result2 == BASE64ENCODED_STRING
    }

    def "Base64UtilTest base32EncodeNull"() {
        when:
        Base64Util.base32Encode(null)
        then:
        thrown NullPointerException
    }

}
