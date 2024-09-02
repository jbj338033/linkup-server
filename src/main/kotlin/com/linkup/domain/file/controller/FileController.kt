package com.linkup.domain.file.controller

import com.linkup.domain.file.service.FileService
import com.linkup.global.common.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@Tag(name = "파일", description = "File")
@RestController
@RequestMapping("/files")
class FileController(
    private val fileService: FileService
) {
    @Operation(summary = "파일 업로드")
    @PostMapping("/upload", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun uploadFiles(@RequestPart("files") files: List<MultipartFile>) =
        BaseResponse.of(data = fileService.uploadFiles(files), status = 201)
}