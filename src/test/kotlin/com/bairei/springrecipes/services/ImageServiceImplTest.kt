package com.bairei.springrecipes.services

import com.bairei.springrecipes.domain.Recipe
import com.bairei.springrecipes.repositories.reactive.RecipeReactiveRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.springframework.mock.web.MockMultipartFile
import reactor.core.publisher.Mono


class ImageServiceImplTest {

    @Mock
    lateinit var recipeRepository: RecipeReactiveRepository

    lateinit var imageService: ImageService

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        imageService = ImageServiceImpl(recipeRepository)
    }

    @Test
    @Throws(Exception::class)
    fun saveImageFile() {
        //given
        val id = "1"
        val multipartFile = MockMultipartFile("imagefile", "testing.txt", "text/plain",
                "Spring Framework Guru".toByteArray())

        val recipe = Recipe()
        recipe.id = id

        `when`(recipeRepository.findById(anyString())).thenReturn(Mono.just(recipe))
        `when`(recipeRepository.save(any(Recipe::class.java))).thenReturn(Mono.just(recipe))

        val argumentCaptor = ArgumentCaptor.forClass(Recipe::class.java)

        //when
        imageService.saveImageFile(id, multipartFile)

        //then
        verify<RecipeReactiveRepository>(recipeRepository, times(1)).save(argumentCaptor.capture())
        val savedRecipe = argumentCaptor.value
        assertEquals(multipartFile.bytes.size.toLong(), savedRecipe.image.data.size.toLong())
    }

}