package com.linkup.domain.chat.room.service.impl

import com.linkup.domain.chat.room.domain.entity.ChatRoom
import com.linkup.domain.chat.room.domain.entity.ChatRoomParticipant
import com.linkup.domain.chat.room.domain.entity.ChatRoomSubscription
import com.linkup.domain.chat.room.domain.enums.ChatRoomType
import com.linkup.domain.chat.room.dto.request.CreateGroupChatRoomRequest
import com.linkup.domain.chat.room.dto.request.CreateOpenChatRoomRequest
import com.linkup.domain.chat.room.dto.request.CreatePersonalChatRoomRequest
import com.linkup.domain.chat.room.dto.response.ChatRoomResponse
import com.linkup.domain.chat.room.error.ChatRoomError
import com.linkup.domain.chat.room.repository.ChatRoomRepository
import com.linkup.domain.chat.room.repository.ChatRoomSubscriptionRepository
import com.linkup.domain.chat.room.service.ChatRoomService
import com.linkup.domain.user.error.UserError
import com.linkup.domain.user.repository.UserRepository
import com.linkup.global.error.CustomException
import com.linkup.global.security.holder.SecurityHolder
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class ChatRoomServiceImpl(
    private val securityHolder: SecurityHolder,
    private val userRepository: UserRepository,
    private val chatRoomRepository: ChatRoomRepository,
    private val chatRoomSubscriptionRepository: ChatRoomSubscriptionRepository
) : ChatRoomService {
    @Transactional(readOnly = true)
    override fun getChatRooms(): List<ChatRoomResponse> {
        val personal = chatRoomRepository.findAllByParticipantsUserAndType(securityHolder.user, ChatRoomType.PERSONAL)
        val group = chatRoomRepository.findAllByParticipantsUserAndType(securityHolder.user, ChatRoomType.GROUP)

        return (personal + group).sortedBy { it.messages.lastOrNull()?.createdAt }
            .map { ChatRoomResponse.of(it) }
    }

    @Transactional(readOnly = true)
    override fun getOpenChatRooms(): List<ChatRoomResponse> {
        val me = securityHolder.user
        return chatRoomRepository.findAllByParticipantsUserAndType(me, ChatRoomType.OPEN)
            .sortedBy { it.messages.lastOrNull()?.createdAt }
            .map { ChatRoomResponse.of(it) }
    }

    @Transactional(readOnly = true)
    override fun getPersonalChatRooms(): List<ChatRoomResponse> {
        val me = securityHolder.user

        return chatRoomRepository.findAllByParticipantsUserAndType(me, ChatRoomType.PERSONAL)
            .sortedBy { it.messages.lastOrNull()?.createdAt }
            .map {
                it.name = it.participants.find { it.user != me }?.user?.nickname ?: ""
                it.profileImage = it.participants.find { it.user != me }?.user?.profileImage ?: ""

                it
            }
            .map { ChatRoomResponse.of(it) }
    }

    @Transactional
    override fun createPersonalChatRoom(request: CreatePersonalChatRoomRequest): ChatRoomResponse {
        val me = securityHolder.user
        val other = userRepository.findByLinkupId(request.linkupId) ?: throw CustomException(UserError.USER_NOT_FOUND)

        var room = ChatRoom(
            name = "",
            type = ChatRoomType.PERSONAL,
            profileImage = ""
        )

        room.participants.add(ChatRoomParticipant(user = me, room = room))
        room.participants.add(ChatRoomParticipant(user = other, room = room))

        room = chatRoomRepository.save(room)

        return ChatRoomResponse.of(room)
    }

    @Transactional(readOnly = true)
    override fun getGroupChatRooms(): List<ChatRoomResponse> {
        val me = securityHolder.user
        return chatRoomRepository.findAllByParticipantsUserAndType(me, ChatRoomType.GROUP)
            .sortedBy { it.messages.lastOrNull()?.createdAt }
            .map { ChatRoomResponse.of(it) }
    }

    @Transactional
    override fun createGroupChatRoom(request: CreateGroupChatRoomRequest): ChatRoomResponse {
        val me = securityHolder.user
        var room = ChatRoom(
            name = request.name,
            type = ChatRoomType.GROUP,
            profileImage = request.profileImage
                ?: "https://cdn2.ppomppu.co.kr/zboard/data3/2022/0509/m_20220509173224_d9N4ZGtBVR.jpeg"
        )

        room.participants.add(ChatRoomParticipant(user = me, room = room))

        request.linkupIds.forEach {
            val user =
                userRepository.findByLinkupId(it) ?: throw CustomException(UserError.USER_NOT_FOUND_BY_LINKUP_ID, it)
            room.participants.add(ChatRoomParticipant(user = user, room = room))
        }

        room = chatRoomRepository.save(room)

        return ChatRoomResponse.of(room)
    }

    @Transactional
    override fun createOpenChatRoom(request: CreateOpenChatRoomRequest): ChatRoomResponse {
        val me = securityHolder.user
        var room = ChatRoom(
            name = request.name,
            type = ChatRoomType.OPEN,
            profileImage = request.profileImage
                ?: "https://cdn2.ppomppu.co.kr/zboard/data3/2022/0509/m_20220509173224_d9N4ZGtBVR.jpeg"
        )

        room.participants.add(ChatRoomParticipant(user = me, room = room))

        room = chatRoomRepository.save(room)

        return ChatRoomResponse.of(room)
    }

    @Transactional
    override fun joinOpenChatRoom(chatRoomId: UUID): ChatRoomResponse {
        val me = securityHolder.user
        val room =
            chatRoomRepository.findById(chatRoomId).orElseThrow { CustomException(ChatRoomError.CHAT_ROOM_NOT_FOUND) }

        if (room.type != ChatRoomType.OPEN) {
            throw CustomException(ChatRoomError.CHAT_ROOM_NOT_OPEN)
        }

        if (room.participants.any { it.user == me }) {
            throw CustomException(ChatRoomError.CHAT_ROOM_ALREADY_JOINED)
        }

        room.participants.add(ChatRoomParticipant(user = me, room = room))

        return ChatRoomResponse.of(room)
    }

    @Transactional
    override fun leaveOpenChatRoom(chatRoomId: UUID): ChatRoomResponse {
        val me = securityHolder.user
        val room =
            chatRoomRepository.findById(chatRoomId).orElseThrow { CustomException(ChatRoomError.CHAT_ROOM_NOT_FOUND) }
        if (room.type != ChatRoomType.OPEN) {
            throw CustomException(ChatRoomError.CHAT_ROOM_NOT_OPEN)
        }

        if (room.participants.none { it.user == me }) {
            throw CustomException(ChatRoomError.CHAT_ROOM_NOT_JOINED)
        }

        room.participants.removeIf { it.user == me }

        return ChatRoomResponse.of(room)
    }


    @Transactional
    override fun subscribeChatRoom(chatRoomId: UUID) {
        val room = chatRoomRepository.findByIdOrNull(chatRoomId) ?: throw CustomException(ChatRoomError.CHAT_ROOM_NOT_FOUND)
        val user = securityHolder.user

        if (user !in room.participants.map { it.user }) {
            throw CustomException(ChatRoomError.NOT_PARTICIPANT)
        }

        chatRoomSubscriptionRepository.save(ChatRoomSubscription(securityHolder.user.linkupId, chatRoomId))
    }

    @Transactional
    override fun unsubscribeChatRoom() {
        chatRoomSubscriptionRepository.deleteById(securityHolder.user.linkupId)
    }
}