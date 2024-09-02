package com.linkup.domain.file.service

import org.springframework.web.multipart.MultipartFile

interface FileService {
    fun uploadFiles(files: List<MultipartFile>): List<String>
}