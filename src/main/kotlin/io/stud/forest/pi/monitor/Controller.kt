package io.stud.forest.pi.monitor

import io.stud.forest.pi.monitor.temperature.Temperature
import io.stud.forest.pi.monitor.temperature.TemperatureService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("")
class Controller(
        private val temperatureService: TemperatureService
) {
    @GetMapping("/temperature")
    fun measureTemperature(): Temperature {
        return temperatureService.measure().toCelsius()
    }

    @GetMapping("/temperature")
    fun measureTemperature(@RequestParam("unit") unit: String): Temperature {
        val temp = temperatureService.measure()

        return temp.convertTo(unit)
    }
}