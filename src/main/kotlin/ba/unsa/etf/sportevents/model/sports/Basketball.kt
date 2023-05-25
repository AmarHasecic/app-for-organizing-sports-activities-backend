package ba.unsa.etf.sportevents.model.sports

import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "sports")
class Basketball(
      id: String?,
      name: String,
      val playersPerTeam: Int = 5,
      val gameDuration: Int = 48,

): Sport(id,name)