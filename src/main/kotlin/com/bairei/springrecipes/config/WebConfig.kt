package com.bairei.springrecipes.config

import com.bairei.springrecipes.domain.Recipe
import com.bairei.springrecipes.services.RecipeService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RequestPredicates.GET
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

@Configuration
class WebConfig {
//    @Bean
//    fun routes(recipeService: RecipeService): RouterFunction<*> = RouterFunctions.route(GET("api/recipes"),
//            { serverRequest -> ServerResponse
//                                        .ok()
//                                        .contentType(MediaType.APPLICATION_JSON)
//                                        .body(recipeService.findAll(), Recipe::class.java)})
    @Bean
                                                                // router - KotlinDSL function, replaces
                                                                // spring's default RouterFunctions.route method
    fun routes(recipeService: RecipeService): RouterFunction<*> = router {
        GET("/api/recipes") {
            serverRequest ->  ServerResponse.ok()
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .body(recipeService.findAll(), Recipe::class.java)
        }
    }
}