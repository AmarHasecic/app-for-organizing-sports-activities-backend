package ba.unsa.etf.sportevents.controller

import ba.unsa.etf.sportevents.model.sports.Basketball
import ba.unsa.etf.sportevents.repository.SportRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/sports")
class SportController(private val sportRepository: SportRepository) {

    @PostMapping("/basketball")
    fun createBasketball(@RequestBody basketball: Basketball): ResponseEntity<Basketball> {

        val basketballFromDatabase = sportRepository.findById("Basketball").orElse(null) as? Basketball
        if(basketballFromDatabase != null){
            return ResponseEntity.status(HttpStatus.CONFLICT).build()
        }
        else {
            val createdBasketball = sportRepository.save(basketball)
            return ResponseEntity.status(HttpStatus.CREATED).body(createdBasketball)
        }
    }

    @GetMapping("/basketball")
    fun getBasketball(): ResponseEntity<Basketball> {
        val basketball = sportRepository.findById("Basketball").orElse(null) as? Basketball
        return if (basketball != null) {
            ResponseEntity.ok(basketball)
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
