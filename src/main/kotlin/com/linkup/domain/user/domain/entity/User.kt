package com.kakaotalk.domain.user.domain.entity

import com.kakaotalk.domain.user.domain.enums.UserGender
import com.kakaotalk.domain.user.domain.enums.UserRole
import com.kakaotalk.global.common.BaseTimeEntity
import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.hibernate.validator.constraints.URL
import java.time.LocalDate
import java.util.UUID

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
    var gender: UserGender
): BaseTimeEntity()