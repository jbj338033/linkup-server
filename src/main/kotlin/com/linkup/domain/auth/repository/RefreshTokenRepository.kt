package com.linkup.domain.auth.repository

import com.linkup.domain.auth.domain.entity.RefreshToken
import org.springframework.data.repository.CrudRepository

interface RefreshTokenRepository : CrudRepository<RefreshToken, String>