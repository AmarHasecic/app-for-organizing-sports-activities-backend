package ba.unsa.etf.sportevents.model
import org.springframework.data.mongodb.core.mapping.Document
import kotlin.math.*

@Document
class Location (
    val latitude: Double,
    val longitude: Double,
    val name: String
){

    fun distanceTo(other: Location): Double {
        val earthRadius = 6371 // Earth's radius in kilometers
        val lat1 = Math.toRadians(this.latitude)
        val lat2 = Math.toRadians(other.latitude)
        val deltaLat = Math.toRadians(other.latitude - this.latitude)
        val deltaLon = Math.toRadians(other.longitude - this.longitude)

        val a = sin(deltaLat / 2) * sin(deltaLat / 2) +
                cos(lat1) * cos(lat2) *
                sin(deltaLon / 2) * sin(deltaLon / 2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return earthRadius * c
    }
}