package com.bairei.springrecipes.services

import com.bairei.springrecipes.domain.Recipe
import com.bairei.springrecipes.repositories.RecipeRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.springframework.mock.web.MockMultipartFile
import java.util.*


class ImageServiceImplTest {

    @Mock
    lateinit var recipeRepository: RecipeRepository

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
        val recipeOptional = Optional.of(recipe)

        `when`(recipeRepository.findById(anyString())).thenReturn(recipeOptional)

        val argumentCaptor = ArgumentCaptor.forClass(Recipe::class.java)

        //when
        imageService.saveImageFile(id, multipartFile)

        //then
        verify<RecipeRepository>(recipeRepository, times(1)).save(argumentCaptor.capture())
        val savedRecipe = argumentCaptor.value
        assertEquals(multipartFile.bytes.size.toLong(), savedRecipe.image.data.size.toLong())
    }

}