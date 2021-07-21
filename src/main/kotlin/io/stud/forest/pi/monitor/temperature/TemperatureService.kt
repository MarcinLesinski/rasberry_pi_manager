package io.stud.forest.pi.monitor.temperature

import io.stud.forest.exceptions.Failure
import io.stud.forest.exceptions.Success
import io.stud.forest.exceptions.Try
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.*

@Service
class TemperatureService(
        @Value("\${temperature.source_file}") private val dataFileName: String
){

    fun measure(): Celsius {
        val result = Try { readTemperature(dataFileName) }

        return when (result) {
            is Success -> Celsius(result.value)
            is Failure -> throw ReadTemperatureException(result.error)
        }
    }

    fun readTemperature(fileName: String): Float =
            File(fileName)
                    .readLines()[0]
                    .toInt()
                    .div(1000f)
}

class ReadTemperatureException(cause: Throwable) : Throwable(cause)