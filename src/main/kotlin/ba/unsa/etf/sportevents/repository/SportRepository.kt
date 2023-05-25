package ba.unsa.etf.sportevents.repository

import ba.unsa.etf.sportevents.model.sports.Sport
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface SportRepository : MongoRepository<Sport, String> {
    @Query("{'sportType': ?0}")
    fun findBySportType(sportType: String): Sport?
}