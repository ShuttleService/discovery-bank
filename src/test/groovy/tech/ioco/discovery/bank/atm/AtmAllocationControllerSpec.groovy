package tech.ioco.discovery.bank.atm

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification
import tech.ioco.discovery.bank.SpecFactory
import tech.ioco.discovery.bank.client.Client
import tech.ioco.discovery.bank.client.ClientRepository

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath

@SpringBootTest
class AtmAllocationControllerSpec extends Specification {
    @Autowired
    private AtmAllocationController controller
    @Autowired
    private WebApplicationContext context
    @Autowired
    private ClientRepository clientRepository
    private AtmAllocationService service = Mock()
    private MockMvc mockMvc
    private Client testClient
    String withdrawalJsonRequest

    def setup() {
        controller.service = service
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(SecurityMockMvcConfigurers.springSecurity()).alwaysDo(print()).build()
        testClient = clientRepository.findByUsername('test').get()
        withdrawalJsonRequest = new Gson().toJson(SpecFactory.withdrawalRequest)
    }

    @WithUserDetails('test')
    def 'making a withdrawal delegates to the service withdraw'() {
        given: 'a withdrawal request json'
        WithdrawalRequest withdrawalRequest = SpecFactory.withdrawalRequest
        String withdrawalJsonRequest = new ObjectMapper().writeValueAsString(withdrawalRequest)
        when: 'making a withdrawal'
        mockMvc.perform(put("/withdraw").contentType(MediaType.APPLICATION_JSON).content(withdrawalJsonRequest)).
                andExpect(jsonPath('$[0].denomination.value').value(SpecFactory.withdrawalAllocation.denomination.value)).
                andExpect(jsonPath('$[0].count').value(SpecFactory.withdrawalAllocation.count))
        then: 'delegate to the service'
        1 * service.withdraw(withdrawalRequest.atmId, {
            Client client -> client.clientId == SpecFactory.client.clientId
        }, withdrawalRequest.clientAccountNumber, withdrawalRequest.amount) >> [SpecFactory.withdrawalAllocation]
    }

    def 'Only an authenticated client can withdraw'() {
        when: 'withdrawing with a non authenticated user'
        mockMvc.perform(put('/withdraw').contentType(MediaType.APPLICATION_JSON).content(withdrawalJsonRequest))
        then: 'abort the withdrawal with no calls to withdraw'
        0 * service.withdraw(_, _, _, _)
    }
}
