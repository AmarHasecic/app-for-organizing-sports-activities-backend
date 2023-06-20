package ba.unsa.etf.sportevents.controller

import ba.unsa.etf.sportevents.model.Location
import ba.unsa.etf.sportevents.model.SportActivity
import ba.unsa.etf.sportevents.repository.SportActivityRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/activities")
class SportActivityController(private val activityRepository: SportActivityRepository) {

    private fun generateId(): String{
        var id = UUID.randomUUID().toString().replace("-", "")

        // Check if ID already exists in database
        var existingItem = this.activityRepository.findById(id)
        while (existingItem.isPresent) {
            // If ID already exists, recursively call this function to generate a new ID
            id = UUID.randomUUID().toString().replace("-", "")
            existingItem = this.activityRepository.findById(id)
        }

        return id;
    }


    @GetMapping("")
    fun getActivities(): ResponseEntity<List<SportActivity>> {

        return ResponseEntity.ok(this.activityRepository.findAll())
    }

    @GetMapping("/{title}")
    fun getActivitiesByTitle(@PathVariable title: String): ResponseEntity<List<SportActivity>> {

        val activitiesByTitle = this.activityRepository.findAll().filter { it.title == title }
        return ResponseEntity.ok(activitiesByTitle)
    }

    @PostMapping("")
    fun createActivity(@RequestBody activity: SportActivity): ResponseEntity<SportActivity> {

        activity.id = generateId();
        return ResponseEntity.ok(this.activityRepository.save(activity))
    }

    @PutMapping("/{id}")
    fun updateActivity(@PathVariable id: String, @RequestBody activity: SportActivity): ResponseEntity<SportActivity> {

        val oldActivity = this.activityRepository.findById(id).orElse(null)
        activity.id = oldActivity.id
        this.activityRepository.deleteById(id)
        return ResponseEntity.ok(activityRepository.save(activity))
    }

    @DeleteMapping("/{id}")
    fun deleteActivity(@PathVariable id: String): ResponseEntity<String> {
        this.activityRepository.deleteById(id)
        return ResponseEntity.ok("Activity successfully deleted.")
    }

    @GetMapping("/nearby")
    fun getActivitiesNearby(
        @RequestParam latitude: Double,
        @RequestParam longitude: Double
    ): ResponseEntity<List<SportActivity>> {

        val searchRadius = 10.0 // 10 kilometers

        val currentLocation = Location(latitude, longitude, "Current Location")

        val activitiesNearby = activityRepository.findAll().filter { activity ->
            val activityLocation = Location(activity.location.latitude, activity.location.longitude, activity.location.name)
            currentLocation.calculateDistance(activityLocation) <= searchRadius
        }

        return ResponseEntity.ok(activitiesNearby)
    }

}