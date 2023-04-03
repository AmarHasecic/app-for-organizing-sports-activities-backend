package ba.unsa.etf.sportevents.model

import ba.unsa.etf.sportevents.model.sports.Sport
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Document
data class User(

        @Id
        var username: String,

        var firstName: String,
        var lastName: String,
        var email: String,
        var password: String,
        var dateOfBirth: LocalDate,
        var sports: List<Sport>,
        var activities: List<SportActivity>
)
