package com.bairei.springrecipes.controllers

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.servlet.ModelAndView

@ControllerAdvice
class ControllerExceptionHandler {

    val log: Logger = LoggerFactory.getLogger(ControllerExceptionHandler::class.java)

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException::class)
    fun numberFormatExceptionHandler(ex: Exception) : ModelAndView {
        log.error("Handling number format exception")
        log.error(ex.message)
        val modelAndView = ModelAndView()
        modelAndView.viewName = "400error"
        modelAndView.addObject("exception", ex)
        return modelAndView
    }
}