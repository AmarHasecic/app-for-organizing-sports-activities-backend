package ba.unsa.etf.sportevents.model

import ba.unsa.etf.sportevents.model.sports.Sport
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Document("users")
data class User(

        @Id
        var id: String,

        var username: String,
        var fullName: String,
        var email: String,
        var password: String,
        var dateOfBirth: LocalDate,
        var sports: List<String>,
        var activities: List<SportActivity>
)
        