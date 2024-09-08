package com.linkup.domain.chat.message.service.impl

import com.linkup.domain.chat.message.domain.entity.GeneralChatMessage
import com.linkup.domain.chat.message.dto.request.SendGeneralMessageRequest
import com.linkup.domain.chat.message.dto.request.SendMessageRequest
import com.linkup.domain.chat.message.dto.response.GeneralChatMessageResponse
import com.linkup.domain.chat.message.repository.GeneralChatMessageRepository
import com.linkup.domain.chat.message.service.ChatMessageService
import com.linkup.domain.user.dto.response.UserResponse
import com.linkup.domain.user.error.UserError
import com.linkup.domain.user.repository.UserRepository
import com.linkup.global.error.CustomException
import com.linkup.global.security.holder.SecurityHolder
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.messaging.simp.SimpAttributesContextHolder
import org.springframework.stereotype.Service

@Service
class ChatMessageServiceImpl(
    private val securityHolder: SecurityHolder,
    private val rabbitTemplate: RabbitTemplate,
    private val generalChatMessageRepository: GeneralChatMessageRepository,
    private val userRepository: UserRepository
) : ChatMessageService {
    override fun sendMessage(request: SendMessageRequest) {
        rabbitTemplate.convertAndSend("", request)
    }

    override fun getGeneralMessages(): List<GeneralChatMessageResponse> {
        return generalChatMessageRepository.findAll().map {
            GeneralChatMessageResponse.of(it)
        }
    }

    override fun sendGeneralMessage(request: SendGeneralMessageRequest) {
        val linkupId = SimpAttributesContextHolder.currentAttributes().getAttribute("linkupId") as String
        val sender = userRepository.findByLinkupId(linkupId) ?: throw CustomException(UserError.USER_NOT_FOUND)
        val message = generalChatMessageRepository.save(GeneralChatMessage(
            content = request.content,
            sender = sender
        ))

        val response = GeneralChatMessageResponse.of(message)

        rabbitTemplate.convertAndSend("room.general", response)
    }
}