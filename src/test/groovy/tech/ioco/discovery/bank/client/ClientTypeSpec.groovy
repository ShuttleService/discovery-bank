package tech.ioco.discovery.bank.client

import spock.lang.Specification
import spock.lang.Unroll
import tech.ioco.discovery.bank.SpecFactory

import javax.validation.Validation

class ClientTypeSpec extends Specification {
    def 'a client type is valid when it has type with 2 characters and a description'() {
        expect:
        Validation.buildDefaultValidatorFactory().validator.validate(SpecFactory.clientType).empty
    }

    @Unroll('Client type with type #type, description #description is invalid')
    def 'Client type with incorrect number of characters for client type and description is invalid'() {
        when: 'checking the validity of a client type'
        ClientType clientType = new ClientType(type, description)
        then:
        !Validation.buildDefaultValidatorFactory().validator.validate(clientType).empty
        where:
        type  | description
        null  | null
        ''    | 'valid'
        'icd' | 'valid'
        null  | 'valid'
        'ic'  | null
        'ic'  | ''
    }
}
