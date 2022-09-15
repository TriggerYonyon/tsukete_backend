package dev.yonyon.job

import dev.yonyon.srevice.SeatService
import io.micronaut.scheduling.annotation.Scheduled
import jakarta.inject.Singleton

@Singleton
class SeatUpdateJob(private val seatService: SeatService) {

    @Scheduled(fixedDelay = "10s", initialDelay = "60s")
    fun executeEverytime() {
        seatService.updateIsUsed()
    }

}
