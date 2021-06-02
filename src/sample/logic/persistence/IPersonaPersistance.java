package sample.logic.persistence;

import sample.logic.entities.Persona;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface IPersonaPersistance {

    void save(Persona persona) throws IOException;

    List<Persona> read() throws IOException, ClassNotFoundException;

    List<String> importPersonas(File file) throws IOException;

}
