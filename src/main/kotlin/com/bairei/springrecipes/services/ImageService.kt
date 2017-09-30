package com.bairei.springrecipes.services

import org.springframework.web.multipart.MultipartFile

interface ImageService {

    fun saveImageFile(id: String, file: MultipartFile)
}