package ba.unsa.etf.sportevents.repository

import ba.unsa.etf.sportevents.model.Group
import org.springframework.data.mongodb.repository.MongoRepository

interface GroupRepository: MongoRepository<Group, String> {
}