package com.linkup.global.websocket.handler

import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.SimpMessageType
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.messaging.support.MessageHeaderAccessor
import org.springframework.stereotype.Component
import org.springframework.web.socket.handler.TextWebSocketHandler

@Component
class StompHandler : ChannelInterceptor {
    override fun preSend(message: Message<*>, channel: MessageChannel): Message<*>? {
            val accessor = StompHeaderAccessor.wrap(message)

            when (accessor.messageType) {
                SimpMessageType.CONNECT -> {
                    println("CONNECT")
                }


                else -> {
                    println("ELSE")
                }
            }

            return message
    }
}