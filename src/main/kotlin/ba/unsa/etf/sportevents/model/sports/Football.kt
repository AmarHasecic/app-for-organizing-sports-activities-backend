package ba.unsa.etf.sportevents.model.sports

import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "sports")
class Football(
    id: String?,
    name: String,
    val playersPerTeam: Int = 11,
    val gameDuration: Int = 90
) : Sport(id,name)