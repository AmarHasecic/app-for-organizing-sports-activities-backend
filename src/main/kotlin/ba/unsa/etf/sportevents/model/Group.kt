package ba.unsa.etf.sportevents.model

import org.springframework.data.mongodb.core.mapping.Document

@Document
class Group(

    var id: String,
    var name: String,
    var users: List<User>,
    var activities: List<SportActivity>

)