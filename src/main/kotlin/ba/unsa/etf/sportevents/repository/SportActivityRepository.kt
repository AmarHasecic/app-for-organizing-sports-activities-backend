package ba.unsa.etf.sportevents.repository

import ba.unsa.etf.sportevents.model.SportActivity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface SportActivityRepository: MongoRepository<SportActivity, String> {
}