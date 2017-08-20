package nl.cc

import java.time.LocalDate

/**
 * This class is always created from stored entities, so their ID will never be null.
 * Since date of birth is optional, the age property can be null
 */
data class CustomerDTO(val id: Long, val name: String, val age: Int?)

/**
 * IDs are assigned by the persistence mechanism, so they have no place in a creation request
 */
data class CreateCustomerDTO(val name: String, val dateOfBirth: LocalDate? = null)

/**
 * In an update request we need the ID to look up the stored entity
 * All UpdateField properties are mandatory, but they have default values for convenience
 */
data class UpdateCustomerDTO(val id: Long,
                             val name: UpdateField<String> = UpdateField.ignore(),
                             val dateOfBirth: UpdateField<LocalDate> = UpdateField.ignore())