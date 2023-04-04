package ba.unsa.etf.sportevents.model

import ba.unsa.etf.sportevents.model.sports.Sport
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Duration
import java.time.LocalDateTime

@Document
class SportActivity(


    @Id
    var id: String,
    var host: User,
    var title: String,
    var sport: Sport,
    var description: String,
    var location: Location,
    var startTime: LocalDateTime,
    var endTime: LocalDateTime,
    var numberOfParticipants: Int = 0,
    var maxNumberOfParticipants: Int,
    var participants: List<User>

) {
    fun calculateDuration(start: LocalDateTime, end: LocalDateTime): Duration {
        return Duration.between(start, end)
    }

}