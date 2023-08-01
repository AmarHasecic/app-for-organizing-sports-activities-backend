package ba.unsa.etf.sportevents

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication


@SpringBootApplication
class SporteventsApplication {
	companion object {
		@JvmStatic
		fun main(args: Array<String>) {


			val port: String? = System.getenv("PORT")
			if (port != null) {
				System.setProperty("server.port", port)
			}

			SpringApplication.run(SporteventsApplication::class.java, *args)
		}
	}
}