package sample.logic.services;

import javafx.collections.ObservableList;
import sample.logic.PersonaException;
import sample.logic.entities.Persona;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface IPersonaServices
{
    List<Persona> getAll();
    Persona getById(UUID id);
    Persona insert(Persona persona);
    void delete (List<Persona> personas);
    void export() throws Exception;
    List<Persona> importPersonas(File file) throws IOException, PersonaException;
}
