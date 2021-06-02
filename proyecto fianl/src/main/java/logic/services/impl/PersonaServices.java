package logic.services.impl;

import javafx.collections.FXCollections;
import logic.entities.Persona;

import java.io.File;
import java.io.IOException;
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
    public void export() throws Exception {

    }

    @Override
    public List<Persona> importPersonas(File file) throws Exception {
        return null;
    }
}
