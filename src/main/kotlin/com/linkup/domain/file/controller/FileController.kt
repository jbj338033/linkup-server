package com.linkup.domain.file.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.apache.commons.io.FilenameUtils
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

@Tag(name = "파일", description = "File")
@RestController
@RequestMapping("/files")
class FileController {
    companion object {
        private const val UPLOAD_DIR = "/Users/dgsw52/Uploads"
    }

    @Operation(summary = "파일 업로드")
    @PostMapping("/upload", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun uploadFile(@RequestPart("files") files: List<MultipartFile>): List<String> {
        val filenames = mutableListOf<String>()

        files.forEach {
            val basename = FilenameUtils.getBaseName(it.originalFilename)
            val extension = FilenameUtils.getExtension(it.originalFilename)
            val filename = generateFilename(basename, extension)
            val file = File("$UPLOAD_DIR/$filename")

            if (!Files.exists(Paths.get(UPLOAD_DIR))) {
                Files.createDirectories(Paths.get(UPLOAD_DIR))
            }

            it.transferTo(file)
            filenames.add(filename)
        }

        return filenames
    }

    private fun generateFilename(filename: String, extension: String): String {
        var newFilename = "${filename}.${extension}"
        var count = 1

        while (Files.exists(Paths.get("$UPLOAD_DIR/$newFilename"))) {
            newFilename = "${filename}(${count++}).${extension}"
        }

        return newFilename
    }
}