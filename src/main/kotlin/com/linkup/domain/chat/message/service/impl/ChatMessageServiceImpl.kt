package com.linkup.domain.chat.message.service.impl

import com.linkup.domain.chat.message.domain.entity.ChatMessage
import com.linkup.domain.chat.message.domain.entity.ChatMessageMention
import com.linkup.domain.chat.message.domain.entity.GeneralChatMessage
import com.linkup.domain.chat.message.dto.request.SendGeneralMessageRequest
import com.linkup.domain.chat.message.dto.request.SendMessageRequest
import com.linkup.domain.chat.message.dto.response.ChatMessageResponse
import com.linkup.domain.chat.message.dto.response.GeneralChatMessageResponse
import com.linkup.domain.chat.message.repository.ChatMessageRepository
import com.linkup.domain.chat.message.repository.GeneralChatMessageRepository
import com.linkup.domain.chat.message.service.ChatMessageService
import com.linkup.domain.chat.room.error.ChatRoomError
import com.linkup.domain.chat.room.repository.ChatRoomRepository
import com.linkup.domain.user.error.UserError
import com.linkup.global.error.CustomException
import com.linkup.global.security.holder.SecurityHolder
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class ChatMessageServiceImpl(
    private val rabbitTemplate: RabbitTemplate,
    private val chatMessageRepository: ChatMessageRepository,
    private val generalChatMessageRepository: GeneralChatMessageRepository,
    private val chatRoomRepository: ChatRoomRepository,
    private val securityHolder: SecurityHolder
) : ChatMessageService {
    @Transactional
    override fun sendMessage(request: SendMessageRequest) {
        val sender = securityHolder.user
        val room = chatRoomRepository.findByIdOrNull(request.roomId)
            ?: throw CustomException(ChatRoomError.CHAT_ROOM_NOT_FOUND)
        val message = chatMessageRepository.save(
            ChatMessage(
                content = request.content,
                sender = sender,
                room = room,
            ).apply {
                mentions = request.mentions.map {
                    ChatMessageMention(
                        message = this,
                        member = room.participants.find { participant -> participant.user.linkupId == it }
                            ?: throw CustomException(UserError.USER_NOT_FOUND_BY_LINKUP_ID, it))
                }.toSet()
            }
        )

        val response = ChatMessageResponse.of(message)

        rabbitTemplate.convertAndSend("linkup.exchange", "room.${room.id}", response)
    }

    @Transactional(readOnly = true)
    override fun getGeneralMessages(): List<GeneralChatMessageResponse> {
        return generalChatMessageRepository.findAll().sortedBy { it.createdAt }.map {
            GeneralChatMessageResponse.of(it)
        }
    }

    @Transactional
    override fun sendGeneralMessage(request: SendGeneralMessageRequest) {
        val sender = securityHolder.user
        val message = generalChatMessageRepository.save(
            GeneralChatMessage(
                content = request.content,
                sender = sender
            )
        )

        val response = GeneralChatMessageResponse.of(message)

        rabbitTemplate.convertAndSend("linkup.exchange", "room.general", response)
    }

    @Transactional(readOnly = true)
    override fun getMessages(chatRoomId: UUID): List<ChatMessageResponse> {
        val room =
            chatRoomRepository.findByIdOrNull(chatRoomId) ?: throw CustomException(ChatRoomError.CHAT_ROOM_NOT_FOUND)

        return chatMessageRepository.findAllByRoom(room).sortedBy { it.createdAt }.map {
            ChatMessageResponse.of(it)
        }
    }
}