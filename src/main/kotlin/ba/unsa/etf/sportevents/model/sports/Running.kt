package ba.unsa.etf.sportevents.model.sports

import ba.unsa.etf.sportevents.model.Location
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Duration
import java.time.LocalDateTime

@Document(collection = "sports")
class Running(
    name: String,
    var startTime: LocalDateTime,
    var endTime: LocalDateTime,
    var runDuration: Duration,
    var startLocation: Location,
    var endLocation: Location,
    var distance: Double


    ): Sport(name){

    fun calculateDuration(){
        runDuration =  Duration.between(this.startTime, this.endTime)
    }

    fun calculateDistane(){
        distance = startLocation.distanceTo(endLocation)
    }
}