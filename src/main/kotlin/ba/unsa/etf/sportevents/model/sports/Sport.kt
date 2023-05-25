package ba.unsa.etf.sportevents.model.sports

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "sports")
open class Sport(

    @Id
    val id: String?,
    val name: String

)