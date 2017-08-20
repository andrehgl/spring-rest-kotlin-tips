package nl.cc

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.time.LocalDate

@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(Application::class), webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class IntegrationTest {

    @Autowired
    private lateinit var endpoint: CustomerEndpoint


    val aLongTimeAgo = LocalDate.now().minusYears(30)

    @Test
    fun testCreation() {
        val jane = endpoint.createCustomer(CreateCustomerDTO(name = "Jane"))
        assertThat(endpoint.getCustomerById(jane.id).id).isEqualTo(jane.id)
    }

    @Test
    fun testAge() {
        val john = endpoint.createCustomer(CreateCustomerDTO(name = "John", dateOfBirth = aLongTimeAgo))
        assertThat(endpoint.getCustomerById(john.id).age).isEqualTo(30)
    }

    @Test
    fun testUpdateMandatoryField() {
        val john = endpoint.createCustomer(CreateCustomerDTO(name = "John", dateOfBirth = aLongTimeAgo))
        endpoint.updateCustomer(UpdateCustomerDTO(id = john.id, name = UpdateField.of("Jan")))
        val updated = endpoint.getCustomerById(john.id)
        assertThat(updated.name).isEqualTo("Jan")
        assertThat(updated.age).isEqualTo(30)
    }

    @Test
    fun testUpdateMandatoryFieldToNullThrows() {
        val john = endpoint.createCustomer(CreateCustomerDTO(name = "John", dateOfBirth = aLongTimeAgo))
        val updateRequest = UpdateCustomerDTO(id = john.id, name = UpdateField.setNull())
        assertThatThrownBy { endpoint.updateCustomer(updateRequest) }.hasMessage("value cannot be null.")
    }

    @Test
    fun testDeleteEntity() {
        val jill = endpoint.createCustomer(CreateCustomerDTO(name = "Jill", dateOfBirth = aLongTimeAgo))
        endpoint.deleteCustomerById(jill.id)
        assertThatThrownBy { endpoint.getCustomerById(jill.id) }.hasMessage("No customer with id ${jill.id}")
    }

}
