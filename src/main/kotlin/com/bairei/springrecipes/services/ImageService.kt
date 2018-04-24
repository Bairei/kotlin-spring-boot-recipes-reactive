package com.bairei.springrecipes.services

import org.springframework.web.multipart.MultipartFile
import reactor.core.publisher.Mono

interface ImageService {

    fun saveImageFile(id: String, file: MultipartFile): Mono<Void>
}