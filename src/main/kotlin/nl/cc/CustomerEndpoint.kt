package nl.cc

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RequestMapping("/customers")
@RestController
class CustomerEndpoint @Autowired constructor(private val repository: CustomerRepository) {

    @RequestMapping(method = arrayOf(RequestMethod.GET))
    fun getAllCustomers(): List<CustomerDTO> {
        return repository.getAll()
    }

    @RequestMapping(value = "{id}", method = arrayOf(RequestMethod.GET))
    fun getCustomerById(@PathVariable("id") id: Long): CustomerDTO {
        return repository.getById(id)
    }

    @RequestMapping(method = arrayOf(RequestMethod.POST))
    fun createCustomer(@RequestBody createRequest: CreateCustomerDTO): CustomerDTO {
        return repository.save(createRequest)
    }

    @RequestMapping(value = "{id}", method = arrayOf(RequestMethod.DELETE))
    fun deleteCustomerById(@PathVariable("id") id: Long): CustomerDTO {
        return repository.removeById(id)
    }

    @RequestMapping(method = arrayOf(RequestMethod.PUT))
    fun updateCustomer(@RequestBody updateRequest: UpdateCustomerDTO): CustomerDTO {
        return repository.save(updateRequest)
    }

}