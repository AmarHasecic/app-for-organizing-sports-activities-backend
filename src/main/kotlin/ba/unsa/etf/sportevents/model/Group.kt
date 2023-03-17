package ba.unsa.etf.sportevents.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class Group(

    var name: String,
    var users: List<User>,
    var activities: List<SportActivity>

)