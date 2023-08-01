package ba.unsa.etf.sportevents

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication


@SpringBootApplication
class SporteventsApplication {
	companion object {
		@JvmStatic
		fun main(args: Array<String>) {
			SpringApplication.run(SporteventsApplication::class.java, *args)
		}
	}
}