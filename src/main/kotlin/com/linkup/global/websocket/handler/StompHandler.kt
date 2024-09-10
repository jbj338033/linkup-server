package com.linkup.global.websocket.handler

import com.linkup.domain.chat.room.repository.ChatRoomRepository
import com.linkup.domain.chat.room.service.ChatRoomService
import com.linkup.domain.user.repository.UserRepository
import com.linkup.global.security.holder.SecurityHolder
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
    private val securityHolder: SecurityHolder,
    private val userRepository: UserRepository,
    private val chatRoomService: ChatRoomService,
    private val chatRoomRepository: ChatRoomRepository,
) : ChannelInterceptor {
    override fun preSend(message: Message<*>, channel: MessageChannel): Message<*>? {
        val accessor = StompHeaderAccessor.wrap(message)

        when (accessor.messageType) {
            SimpMessageType.CONNECT -> {
                val accessToken = accessor.getFirstNativeHeader("Authorization") ?: return null
                val email = jwtProvider.getEmail(accessToken)

                SimpAttributesContextHolder.currentAttributes().setAttribute("email", email)

                return MessageBuilder.createMessage(message.payload, accessor.messageHeaders)
            }

            SimpMessageType.MESSAGE,
            SimpMessageType.CONNECT_ACK,
            SimpMessageType.SUBSCRIBE -> {
                val destination = accessor.destination ?: return null

                if (destination.startsWith("/exchange/linkup.exchange/room.")) {
                    val roomId = UUID.fromString(destination.removePrefix("/exchange/linkup.exchange/room."))

                    chatRoomService.subscribeChatRoom(roomId)
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