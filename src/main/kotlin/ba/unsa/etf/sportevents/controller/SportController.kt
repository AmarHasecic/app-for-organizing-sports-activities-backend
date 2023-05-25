package ba.unsa.etf.sportevents.controller

import ba.unsa.etf.sportevents.model.sports.Sport
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/sports")
class SportController {

    private val sports: MutableList<Sport> = mutableListOf()

    @GetMapping
    fun getAllSports(): ResponseEntity<List<Sport>> {
        return ResponseEntity.ok(sports)
    }

    @GetMapping("/{id}")
    fun getSportById(@PathVariable id: String): ResponseEntity<Sport> {
        val sport = sports.find { it.id == id }
        return if (sport != null) {
            ResponseEntity.ok(sport)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun createSport(@RequestBody sport: Sport): ResponseEntity<Unit> {
        sports.add(sport)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @PutMapping("/{id}")
    fun updateSport(@PathVariable id: String, @RequestBody updatedSport: Sport): ResponseEntity<Unit> {
        val sport = sports.find { it.id == id }
        return if (sport != null) {
            sports.remove(sport)
            sports.add(updatedSport)
            ResponseEntity.ok().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteSport(@PathVariable id: String): ResponseEntity<Unit> {
        val sport = sports.find { it.id == id }
        return if (sport != null) {
            sports.remove(sport)
            ResponseEntity.ok().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
