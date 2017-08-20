package nl.cc

import java.time.LocalDate

/**
 * ID is initially null, because assigned by means of a sequence
 * name is mandatory, dateOfBirth is optional
 */
data class CustomerEntity(var id: Long? = null, var name: String, var dateOfBirth: LocalDate?)