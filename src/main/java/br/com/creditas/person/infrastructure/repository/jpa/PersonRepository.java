package br.com.creditas.person.infrastructure.repository.jpa;

import br.com.creditas.person.domain.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface PersonRepository extends JpaRepository<Person, UUID> {

    Optional<Person> findByCpf(String cpf);

    @Query("FROM Person WHERE nome like %?1%")
    Optional<Person> findByNome(String nome);

}
