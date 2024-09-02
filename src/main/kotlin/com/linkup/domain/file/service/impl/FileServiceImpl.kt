package com.linkup.domain.file.service.impl

import com.linkup.domain.file.service.FileService
import org.apache.commons.io.FilenameUtils
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

@Service
class FileServiceImpl : FileService {
    companion object {
        private const val UPLOAD_DIR = "/Users/dgsw52/Uploads"
    }

    override fun uploadFiles(files: List<MultipartFile>): List<String> {
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