package com.froso.ufp.modules.optional.textcomponent.model.textcomponent

import spock.lang.*

class TextComponentTest extends Specification {
    def "test getKey"() {
        given:
        def item = new TextComponent()
        def testKey = "testKey"
        when:
        // TODO implement stimulus
        item.setKey(testKey)
        then:
        // TODO implement assertions
        item.getKey() == testKey
    }

}
