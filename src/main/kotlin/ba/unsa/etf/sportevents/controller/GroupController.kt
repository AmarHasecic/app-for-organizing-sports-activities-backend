package ba.unsa.etf.sportevents.controller

import ba.unsa.etf.sportevents.model.Group
import ba.unsa.etf.sportevents.repository.GroupRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
    @RequestMapping("/groups")
    class GroupController(private val groupRepository: GroupRepository) {

        private fun generateId(): String{
            var id = UUID.randomUUID().toString().replace("-", "")

            // Check if ID already exists in database
            var existingItem = this.groupRepository.findById(id)
            while (existingItem.isPresent) {
                // If ID already exists, recursively call this function to generate a new ID
                id = UUID.randomUUID().toString().replace("-", "")
                existingItem = this.groupRepository.findById(id)
            }

            return id;
        }


        @GetMapping("")
        fun getGroups(): ResponseEntity<List<Group>> {

            return ResponseEntity.ok(this.groupRepository.findAll())
        }


        @PostMapping("")
        fun createGroup(@RequestBody group: Group): ResponseEntity<Group> {

            group.id = generateId();
            return ResponseEntity.ok(this.groupRepository.save(group))

        }

        @PutMapping("/{id}")
        fun updateGroup(@PathVariable id: String, @RequestBody group: Group): ResponseEntity<Group> {

            val oldGroup = this.groupRepository.findById(id).orElse(null)
            group.id = oldGroup.id
            this.groupRepository.deleteById(id)
            return ResponseEntity.ok(groupRepository.save(group))
        }

        @DeleteMapping("/{id}")
        fun deleteGroup(@PathVariable id: String): ResponseEntity<String> {
            this.groupRepository.deleteById(id)
            return ResponseEntity.ok("Group successfully deleted.")
        }

    }