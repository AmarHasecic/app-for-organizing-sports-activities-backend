package ba.unsa.etf.sportevents.repository

import ba.unsa.etf.sportevents.model.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository


@Repository
interface UserRepository: MongoRepository<User, String> {
    fun findByUsername(username: String): User?
}