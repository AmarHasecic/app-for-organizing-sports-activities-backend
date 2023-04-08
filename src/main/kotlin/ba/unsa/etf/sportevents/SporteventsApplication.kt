package ba.unsa.etf.sportevents

import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.util.*

@SpringBootApplication
class SporteventsApplication

fun main(args: Array<String>) {

	runApplication<SporteventsApplication>(*args)
}
