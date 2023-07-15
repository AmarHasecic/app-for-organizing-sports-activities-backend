package ba.unsa.etf.sportevents.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
class SportActivity(


    @Id
    var id: String,
    var host: User,
    var title: String,
    var sport: String,
    var description: String,
    var location: Location,
    var startTime: LocalDateTime,
    var date: LocalDateTime,
    var numberOfParticipants: Int,
    var maxNumberOfParticipants: Int,
    var participants: List<User>

)