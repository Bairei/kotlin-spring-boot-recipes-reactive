package com.bairei.springrecipes.controllers

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.support.WebExchangeBindException

@ControllerAdvice
class ControllerExceptionHandler {

    val log: Logger = LoggerFactory.getLogger(ControllerExceptionHandler::class.java)

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WebExchangeBindException::class, NumberFormatException::class)
    fun numberFormatExceptionHandler(ex: Exception, model: Model) : String {
        log.error("Handling number format exception")
        log.error(ex.message)
        model.addAttribute("exception", ex)
        return "400error"
    }
}