package com.froso.ufp.modules.optional.smsprovider.slacksmsprovider.model

import spock.lang.Specification

class SlackWebhookBodyTest extends Specification {

    public static final String SOME_TEXT = "someText"

    def "test setText"() {
        given:
          def toTest=new SlackWebhookBody()
        when:
        toTest.setText(SOME_TEXT)
        then:
        toTest.getText()==SOME_TEXT

    }
}
