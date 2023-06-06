package ru.dkalchenko.teatime.controller;

import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dkalchenko.teatime.assembler.PersonModelAssembler;
import ru.dkalchenko.teatime.exception.PersonNotFoundException;
import ru.dkalchenko.teatime.model.Person;
import ru.dkalchenko.teatime.service.PersonService;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/person")
@AllArgsConstructor
public class PersonController {

    private final PersonService personService;

    private final PersonModelAssembler assembler;

    @GetMapping()
    public CollectionModel<EntityModel<Person>> all() {
        List<EntityModel<Person>> persons = personService.findAll().stream() //
                .map(assembler::toModel) //
                .collect(Collectors.toList());
        return CollectionModel.of(persons, linkTo(methodOn(PersonController.class).all()).withSelfRel());
    }

    @PostMapping
    public ResponseEntity<?> newPerson(@RequestBody Person newPerson) {
        EntityModel<Person> entityModel = assembler.toModel(personService.save(newPerson));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/{id}")
    public EntityModel<Person> one(@PathVariable Long id) {
        Person person = personService.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
        return assembler.toModel(person);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> replacePerson(@RequestBody Person newPerson, @PathVariable Long id) {
        Person updatedPerson = personService.findById(id)
                .map(person -> {
                    person.setFirstName(newPerson.getFirstName());
                    person.setLastName(newPerson.getLastName());
                    person.setEmail(newPerson.getEmail());
                    return personService.update(person);
                })
                .orElseGet(() -> {
                    newPerson.setId(id);
                    return personService.save(newPerson);
                });
        EntityModel<Person> entityModel = assembler.toModel(updatedPerson);
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePerson(@PathVariable Long id) {
        personService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
