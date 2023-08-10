package ru.dkalchenko.teatime.model

import lombok.Getter
import lombok.NoArgsConstructor
import lombok.RequiredArgsConstructor
import lombok.Setter
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Getter
@Setter
@Document
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
class Person {
    @Id
    private val id: String = null
    private val firstName: String = null
    private val lastName: String = null
    private val email: String = null
    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o == null || javaClass != o.javaClass) {
            return false
        }
        val person = o as Person
        return firstName == person.firstName && lastName == person.lastName && email == person.email
    }

    override fun hashCode(): Int {
        return Objects.hash(firstName, lastName, email)
    }
}
