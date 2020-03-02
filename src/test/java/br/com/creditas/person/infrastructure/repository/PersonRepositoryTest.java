package br.com.creditas.person.infrastructure.repository;

import br.com.creditas.person.domain.entity.Person;
import br.com.creditas.person.infrastructure.repository.jpa.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @BeforeEach
    public void setUp() {
        var person = new Person();
        person.setCpf("1234567890");
        person.setNome("Teste");

        personRepository.deleteAll();

        personRepository.save(person);
    }

    @Test
    public void findByNome() {
        var person = personRepository.findByNome("Tes");

        assertTrue(person.isPresent());
        assertEquals(person.get().getNome(), "Teste");
        assertEquals(person.get().getCpf(), "1234567890");
    }

}