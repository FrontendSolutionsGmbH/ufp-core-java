package com.froso.ufp.modules.optional.websockets.configuration;

import org.springframework.context.annotation.*;
import org.springframework.messaging.simp.config.*;
import org.springframework.web.socket.config.annotation.*;

/**
 * The type Web socket message broker config.
 */
@Configuration
@EnableWebSocketMessageBroker
@EnableWebSocket
public class WebSocketMessageBrokerConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // config.setApplicationDestinationPrefixes("/events");

        /*
        remark about usage and meaning here by ckleinhuis

        the /events mark is where clients connect, websocketurl/events/resourcename/resourceid/eventname is the subscriber format of standard ufp events
         */

        config.enableSimpleBroker("/events");

    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {


        registry.addEndpoint("/socket").setAllowedOrigins("*");
        //.addInterceptors(new HttpSessionHandshakeInterceptor());

//registry.setOrder(1000);
    }


}
