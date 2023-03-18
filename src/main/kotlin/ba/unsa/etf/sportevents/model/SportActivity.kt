package ba.unsa.etf.sportevents.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
class SportActivity(


    @Id
    var id: String,
    var title: String,
    var sport: String,
    var description: String,
    var location: String,
    var startTime: LocalDateTime,
    var endTime: LocalDateTime,
    var numberOfParticipants: Int = 0,
    var maxNumberOfParticipants: Int,
    var participants: List<User>

)