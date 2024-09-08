package com.linkup.global.config

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.boot.autoconfigure.amqp.RabbitProperties
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableRabbit
class RabbitConfig(private val rabbitProperties: RabbitProperties): ApplicationListener<ApplicationReadyEvent> {
    companion object {
        private const val QUEUE_NAME = "linkup.queue"
        private const val EXCHANGE_NAME = "linkup.exchange"
        private const val ROUTING_KEY = "*.room.*"
    }

    @Bean
    fun queue() = Queue(QUEUE_NAME, true)

    @Bean
    fun exchange() = TopicExchange(EXCHANGE_NAME)

    @Bean
    fun binding(): Binding = BindingBuilder.bind(queue()).to(exchange()).with(ROUTING_KEY)

    @Bean
    fun rabbitTemplate() = RabbitTemplate(rabbitConnectionFactory()).apply {
        setExchange(EXCHANGE_NAME)
        routingKey = ROUTING_KEY
    }

    @Bean
    fun rabbitAdmin() = RabbitAdmin(rabbitConnectionFactory()).apply {
        declareExchange(exchange())
        declareQueue(queue())
        declareBinding(binding())
    }

    private fun rabbitConnectionFactory() = CachingConnectionFactory().apply {
        setHost(rabbitProperties.host)
        port = rabbitProperties.port
        username = rabbitProperties.username
        setPassword(rabbitProperties.password)
        virtualHost = rabbitProperties.virtualHost
    }

    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        rabbitAdmin().initialize()
    }
}