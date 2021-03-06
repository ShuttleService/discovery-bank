package tech.ioco.discovery.bank.client

import spock.lang.Specification
import spock.lang.Unroll
import tech.ioco.discovery.bank.SpecFactory

import javax.validation.Validation

class ClientSpec extends Specification {

    final static Date date = new Date()

    def 'a client with all valid fields will be valid'() {
        expect:
        Validation.buildDefaultValidatorFactory().validator.validate(SpecFactory.client).empty
    }

    @Unroll('A client with title, username #username, password #password, #title, name #name, surname #surname, dob #dob, clientSubType #clientSubType is invalid')
    def 'A client is valid when all the required fields are valid'() {
        when: 'validating a client'
        boolean valid = Validation.buildDefaultValidatorFactory().validator.validate(new Client(username, password, title, name, surname, dob, clientSubType, role)).empty
        then: 'the client is invalid'
        !valid
        where:
        username        | password        | title      | name        | surname        | dob  | clientSubType             | role
        null            | null            | null       | null        | null           | null | null                      | null
        null            | 'some password' | Title.MISS | 'some name' | 'some surname' | date | SpecFactory.clientSubType | Role.CLIENT
        'some username' | null            | Title.MISS | 'some name' | 'some surname' | date | SpecFactory.clientSubType | Role.CLIENT
        'some username' | 'some password' | null       | 'some name' | 'some surname' | date | SpecFactory.clientSubType | Role.CLIENT
        'some username' | 'some password' | Title.MISS | null        | 'some surname' | date | SpecFactory.clientSubType | Role.CLIENT
        'some username' | 'some password' | Title.MISS | 'some name' | null           | date | SpecFactory.clientSubType | Role.CLIENT
        'some username' | 'some password' | Title.MISS | 'some name' | 'some surname' | null | SpecFactory.clientSubType | Role.CLIENT
        'some username' | 'some password' | Title.MISS | 'some name' | 'some surname' | date | null                      | Role.CLIENT
        'some username' | 'some password' | Title.MISS | 'some name' | 'some surname' | date | SpecFactory.clientSubType | null
    }
}
