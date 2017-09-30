package com.bairei.springrecipes.services

import com.bairei.springrecipes.repositories.RecipeRepository
import org.bson.types.Binary
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException

@Service
class ImageServiceImpl (private val recipeRepository: RecipeRepository) : ImageService {

    private val log : Logger = LoggerFactory.getLogger(ImageServiceImpl::class.java)

    override fun saveImageFile(id: String, file: MultipartFile) {

        try {
            val recipe = recipeRepository.findById(id).get()

            val byteObjects = ByteArray(file.bytes.size)
            var i = 0

            for (b in file.bytes){
                byteObjects[i++] = b
            }

            recipe.image = Binary(byteObjects)

            recipeRepository.save(recipe)
        } catch(e: IOException){
            // todo handle it better
            log.error("error occurred: $e")
            e.printStackTrace()
        }
    }
}