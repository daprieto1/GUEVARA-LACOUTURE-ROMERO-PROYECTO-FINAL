package sample.logic.services.implementación;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.logic.PersonaException;
import sample.logic.entities.Exportable;
import sample.logic.entities.Persona;
import sample.logic.persistence.IExport;
import sample.logic.persistence.IPersonaPersistance;
import sample.logic.persistence.Implementacion.PersonaPersistance;
import sample.logic.services.IPersonaServices;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class PersonaServices implements IPersonaServices {


    private IPersonaPersistance personaPersistance;
    private List<Persona> personasVisuales;
    private IExport export;

    public PersonaServices() {

        this.personasVisuales = FXCollections.observableArrayList();

        try {
            this.personaPersistance = new PersonaPersistance();
            //this.personasVisuales.addAll(this.personaPersistance.read());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Persona> getAll() {

        return this.personasVisuales;
    }

    @Override
    public Persona getById(UUID id) {
        return null;
    }

    @Override
    public Persona insert(Persona persona) {

        personasVisuales.add(persona);
        return persona;
    }

    @Override
    public void delete(List<Persona> personas) {

        personas.forEach(this.personasVisuales::remove);
    }

    @Override
    public void export() throws Exception{
        List<Exportable> e = new ArrayList<>();
        this.personasVisuales.stream().forEach(p -> e.add(p));
        this.export.export(e,Exportable.CSV);
    }

    @Override
    public List<Persona> importPersonas(File file) throws IOException, PersonaException {

        List<String> read = this.personaPersistance.importPersonas(file);

        List<Persona> importedPersonas = new ArrayList<>();
        for (String line : read)
        {
            String[] tokens = line.split(Exportable.CSV.toString());
            Persona persona = new Persona (tokens[0], tokens[1],"25");
            importedPersonas.add(persona);
            this.insert(persona);


        }
        return importedPersonas;
    }


}
