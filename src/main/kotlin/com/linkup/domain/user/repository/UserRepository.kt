package com.linkup.domain.user.repository

import com.linkup.domain.user.domain.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, UUID> {
    fun findByEmail(email: String): User?
    fun findByLinkupId(linkupId: String): User?

    fun existsByEmail(email: String): Boolean
    fun existsByLinkupId(linkupId: String): Boolean
    fun existsByPhoneNumber(phoneNumber: String): Boolean
}