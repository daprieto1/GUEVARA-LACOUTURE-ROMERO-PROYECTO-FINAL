package logic.services.impl;

import javafx.collections.FXCollections;

import logic.PersonaException;
import logic.entities.Persona;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class PersonaServices implements IPersonaServices {



    private List<Persona> personas;

    public PersonaServices() {
        this.personas = FXCollections.observableArrayList();
    }
    @Override
    public List<Persona> getAll() {
        return this.personas;
    }

    @Override
    public Persona insert(Persona persona)
    {
        personas.add(persona);
        return persona;
    }

    @Override
    public void delete(List<Persona> personasToDelete) {
        personasToDelete.forEach(this.personas::remove);
    }

    @Override
    public void export() throws Exception{

    }

    @Override
    public List<Persona> importPersonas(File file) throws Exception {
        return null;
    }

    @Override
    public Persona edit(String name, String lastName, int age, boolean isVictim, Enum aggressionType, Enum side,Persona a) {

        Persona pee = null;
        try {
            pee = new Persona(name,lastName,age,isVictim,aggressionType,side);
        } catch (PersonaException e) {
            e.printStackTrace();
        }
        personas.remove(a);

        /*
        pee.setName(name);
        pee.setLastName(lastName);
        pee.setAge(age);
        pee.setVictim(isVictim);
        pee.setAggressionType(aggressionType);
        pee.setSide(side);
         */

        personas.add(pee);
        return pee;
    }
}
