package com.linkup.global.websocket.handler

import com.linkup.domain.chat.room.service.ChatRoomService
import com.linkup.global.error.CustomException
import com.linkup.global.security.jwt.error.JwtError
import com.linkup.global.security.jwt.provider.JwtProvider
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.SimpAttributesContextHolder
import org.springframework.messaging.simp.SimpMessageType
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component
import java.util.*

@Component
class StompHandler(
    private val jwtProvider: JwtProvider,
    private val chatRoomService: ChatRoomService,
) : ChannelInterceptor {
    override fun preSend(message: Message<*>, channel: MessageChannel): Message<*>? {
        val accessor = StompHeaderAccessor.wrap(message)

        when (accessor.messageType) {
            SimpMessageType.CONNECT -> {
                val accessToken =
                    accessor.getFirstNativeHeader("Authorization")?.removePrefix("Bearer ") ?: throw CustomException(
                        JwtError.INVALID_TOKEN
                    )
                val email = jwtProvider.getEmail(accessToken)

                SimpAttributesContextHolder.currentAttributes().setAttribute("email", email)

                return MessageBuilder.createMessage(message.payload, accessor.messageHeaders)
            }

            SimpMessageType.MESSAGE,
            SimpMessageType.CONNECT_ACK,
            SimpMessageType.SUBSCRIBE -> {
                val destination = accessor.destination ?: throw CustomException(JwtError.INVALID_TOKEN)

                if (destination.startsWith("/exchange/linkup.exchange/room.")) {
                    val roomName = destination.removePrefix("/exchange/linkup.exchange/room.")

                    if (roomName == "general") {
                        chatRoomService.subscribeGeneralChatRoom()
                    } else {
                        val roomId = UUID.fromString(roomName)

                        chatRoomService.subscribeChatRoom(roomId)
                    }
                }

                return MessageBuilder.createMessage(message.payload, accessor.messageHeaders)
            }

            SimpMessageType.UNSUBSCRIBE -> {
                chatRoomService.unsubscribeChatRoom()

                return MessageBuilder.createMessage(message.payload, accessor.messageHeaders)
            }

            SimpMessageType.DISCONNECT -> {
                SimpAttributesContextHolder.currentAttributes().removeAttribute("email")
            }

            else -> {
            }
        }

        return message
    }
}