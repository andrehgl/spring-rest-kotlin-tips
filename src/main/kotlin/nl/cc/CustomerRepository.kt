package nl.cc

import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.Period

/**
 * Simple in-memory storage
 */
@Service
class CustomerRepository {

    private var sequence = 1L
    private val cache = mutableMapOf<Long, CustomerEntity>()


    fun getAll(): List<CustomerDTO> = cache.values.map { createDTO(it) }

    fun getById(id: Long): CustomerDTO = createDTO(cache[id] ?: throw IllegalArgumentException("No customer with id $id"))

    fun save(create: CreateCustomerDTO): CustomerDTO {
        val entity = CustomerEntity(name = create.name, dateOfBirth = create.dateOfBirth)
        //in a JPA setting, a unique would be assigned after construction, so the ID is initially null and must be a Long?
        val id = nextId()
        entity.id = id
        cache.put(id, entity)
        return createDTO(entity)
    }

    fun save(customerUpdate: UpdateCustomerDTO): CustomerDTO {
        val entity: CustomerEntity = cache[customerUpdate.id] ?: throw IllegalArgumentException("No customer with id ${customerUpdate.id}")
        if (!customerUpdate.name.ignored)
            entity.name = customerUpdate.name.get()
        if (!customerUpdate.dateOfBirth.ignored)
            entity.dateOfBirth = customerUpdate.dateOfBirth.getOrNull()
        cache.put(customerUpdate.id, entity)
        return createDTO(entity)
    }

    private fun createDTO(entity: CustomerEntity): CustomerDTO {
        val age = if (entity.dateOfBirth == null) null else Period.between(entity.dateOfBirth, LocalDate.now()).years
        return CustomerDTO(id = entity.id!!, name = entity.name, age = age)
    }

    fun removeById(id: Long): CustomerDTO {
        val toDelete = getById(id)
        cache.remove(id)
        return toDelete
    }

    private fun nextId() = ++sequence

}