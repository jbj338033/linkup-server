package com.linkup.global.config

import org.springframework.boot.autoconfigure.amqp.RabbitProperties
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig(
    private val rabbitProperties: RabbitProperties
) : WebSocketMessageBrokerConfigurer {
    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        registry.enableStompBrokerRelay("/queue", "/topic")
            .setRelayHost(rabbitProperties.host)
            .setRelayPort(rabbitProperties.port)
            .setClientLogin(rabbitProperties.username)
            .setClientPasscode(rabbitProperties.password)
        registry.setApplicationDestinationPrefixes("/app")
    }

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint("/ws")
            .setAllowedOrigins("*")
    }
}