package com.linkup.global.config

import com.linkup.global.websocket.handler.StompHandler
import org.springframework.boot.autoconfigure.amqp.RabbitProperties
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.ChannelRegistration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.util.AntPathMatcher
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig(
    private val rabbitProperties: RabbitProperties,
    private val stompHandler: StompHandler
) : WebSocketMessageBrokerConfigurer {
    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        registry.setPathMatcher(AntPathMatcher("."))
        registry.setApplicationDestinationPrefixes("/pub")
        registry.enableStompBrokerRelay("/queue", "/topic", "/exchange", "/amq/queue")
            .setRelayHost(rabbitProperties.host)
            .setRelayPort(61613)
            .setClientLogin(rabbitProperties.username)
            .setClientPasscode(rabbitProperties.password)
            .setSystemLogin(rabbitProperties.username)
            .setSystemPasscode(rabbitProperties.password)
            .setVirtualHost(rabbitProperties.virtualHost)
    }

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint("/ws")
            .setAllowedOrigins("*")
    }

    override fun configureClientInboundChannel(registration: ChannelRegistration) {
        registration.interceptors(stompHandler)
    }
}