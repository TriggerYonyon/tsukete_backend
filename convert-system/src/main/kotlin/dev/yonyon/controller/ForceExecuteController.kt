package dev.yonyon.controller

import dev.yonyon.srevice.SeatService
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Put
import io.micronaut.http.annotation.Status

@Controller("/api")
class ForceExecuteController(private val seatService: SeatService) {

    @Put("/execute")
    @Status(HttpStatus.OK)
    fun execute() {
        seatService.updateIsUsed()
    }

}
