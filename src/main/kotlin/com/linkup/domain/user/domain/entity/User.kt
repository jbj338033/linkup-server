package com.linkup.domain.user.domain.entity

import com.linkup.domain.friend.domain.entity.Friend
import com.linkup.domain.user.domain.enums.UserGender
import com.linkup.domain.user.domain.enums.UserRole
import com.linkup.global.common.BaseTimeEntity
import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column(name = "nickname", nullable = false)
    var nickname: String,

    @Column(name = "linkup_id", nullable = false, unique = true)
    var linkupId: String,

    // linkup id changed at
    @Column(name = "linkup_id_updated_at", nullable = false)
    var linkupIdUpdatedAt: LocalDateTime,

    @Column(name = "email", nullable = false, unique = true)
    val email: String,

    @Column(name = "password", nullable = false)
    var password: String,

    @Column(name = "birthday", nullable = false)
    var birthday: LocalDate,

    @Column(name = "phone_number", length = 15, unique = true, nullable = false)
    val phoneNumber: String,

    @Column(name = "status_message", nullable = false)
    var statusMessage: String = "",

    @Column(name = "profile_image", nullable = false)
    var profileImage: String = "https://cdn2.ppomppu.co.kr/zboard/data3/2022/0509/m_20220509173224_d9N4ZGtBVR.jpeg",

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    val role: UserRole = UserRole.USER,

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    var gender: UserGender,

    // friends
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    val friends: MutableSet<Friend> = mutableSetOf(),
) : BaseTimeEntity()

@JvmInline
value class UserEmail(val value: String) {
    init {
        require(value.length in 5..50) { "email length must be between 5 and 50" }
    }
}

@JvmInline
value class UserPhoneNumber(val value: String) {
    init {
        require(value.length in 10..15) { "phone number length must be between 10 and 15" }
    }
}

@JvmInline
value class UserLinkupId(val value: String) {
    init {
        require(value.length in 4..20) { "linkup id length must be between 4 and 20" }
    }
}