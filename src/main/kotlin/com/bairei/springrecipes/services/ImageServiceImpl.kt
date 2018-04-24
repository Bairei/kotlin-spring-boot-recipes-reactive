package com.bairei.springrecipes.services

import com.bairei.springrecipes.repositories.reactive.RecipeReactiveRepository
import org.bson.types.Binary
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import reactor.core.publisher.Mono
import java.io.IOException

@Service
open class ImageServiceImpl (private val recipeRepository: RecipeReactiveRepository) : ImageService {

    private val log : Logger = LoggerFactory.getLogger(ImageServiceImpl::class.java)

    override fun saveImageFile(id: String, file: MultipartFile) : Mono<Void>{
        val recipeMono = recipeRepository.findById(id).map { recipe ->
            var byteObjects = ByteArray(0)
            try {
                byteObjects = ByteArray(file.bytes.size)
                var i = 0

                for (b in file.bytes){
                    byteObjects[i++] = b
                }

                recipe.image = Binary(byteObjects)

                return@map recipe
            } catch (e: IOException) {
                e.printStackTrace()
                throw RuntimeException(e)
            }
        }
        recipeRepository.save(recipeMono.block()).block()

        return Mono.empty()
    }
//        {
//        try {
//            val recipe = recipeRepository.findById(id).block()
//
//            val byteObjects = ByteArray(file.bytes.size)
//            var i = 0
//
//            for (b in file.bytes){
//                byteObjects[i++] = b
//            }
//
//            recipe.image = Binary(byteObjects)
//
//            recipeRepository.save(recipe).block()
//        } catch(e: IOException){
//            // todo handle it better
//            log.error("error occurred: $e")
//            e.printStackTrace()
//        }
//    }
}