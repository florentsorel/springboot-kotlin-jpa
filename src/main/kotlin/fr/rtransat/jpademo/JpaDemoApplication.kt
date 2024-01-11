package fr.rtransat.jpademo

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.repository.CrudRepository
import java.time.Instant


@SpringBootApplication
class JpaDemoApplication {
    @Bean
    fun demo(actorRepository: ActorRepository) = CommandLineRunner {
        actorRepository.deleteAll()
        actorRepository.saveAll(
            listOf(
                Actor(name = "Bryan Cranston", creationDate = Instant.now()),
                Actor(name = "Margot Robbie", creationDate = Instant.now()),
            )
        )

        println("-------------------")
        println("All actors")
        actorRepository.findAll().forEach { println(it) }
        println("-------------------\n")

        println("-------------------")
        println("All actors containing Bryan as name")
        println(actorRepository.findAllByNameContaining("Bryan"))
        println("-------------------\n")

        println("-------------------")
        println("Find first actor containing Bry as name")
        println(actorRepository.findFirstByNameContaining("Bry"))
        println("-------------------\n")
    }
}

interface ActorRepository : CrudRepository<Actor, Long> {
    fun findAllByNameContaining(name: String): List<Actor>

    fun findFirstByNameContaining(name: String): Actor?
}

@Entity
data class Actor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idActor: Int? = null,
    val name: String,
    val creationDate: Instant, val lastUpdateDate: Instant? = null) {
    override fun toString() = "id: $idActor, name: $name, creationDate: $creationDate, lastUpdateDate: $lastUpdateDate"
}

fun main(args: Array<String>) {
    runApplication<JpaDemoApplication>(*args)
}