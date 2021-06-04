package logic.persistence.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import logic.PersonaException;
import logic.entities.AggressionType;
import logic.entities.Persona;
import logic.entities.Side;
import logic.persistence.IPersonaPersistence;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersonaPersistence implements IPersonaPersistence {
    private static final String PERSONAS_FILE_PATH = "personas.sabana";
    private static final String PERSONAS_FILE_EXTENSION = "sabana";

    public PersonaPersistence() throws IOException {
        File file = new File(PERSONAS_FILE_PATH);
        if (file.createNewFile()) {
            System.out.println("The file " + file.getName() + " was created");
        }
    }

    @Override
    public void save(Persona persona) throws IOException {

        FileOutputStream fileOutputStream = new FileOutputStream(PERSONAS_FILE_PATH, true);
        ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
        out.writeObject(persona);
        out.close();

    }

    @Override
    public List<Persona> read(String path) throws IOException, ClassNotFoundException {

        FileInputStream fis = new FileInputStream(path);
        ObjectInputStream in = new ObjectInputStream(fis);
        return readPersonasWithSabanaExtension(in);
    }

    @Override
    public List<String> importPersonas(File file) throws Exception {


        List<String> personas = new ArrayList<>();
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        br.readLine(); // ignore header
        String line = br.readLine();
        while (line != null) {
            personas.add(line);
            line = br.readLine();
        }

        br.close();
        return personas;
    }

    private List<Persona> readPersonasWithSabanaExtension(ObjectInputStream in) throws IOException, ClassNotFoundException {

        List<Persona> result = FXCollections.observableArrayList();

        try {
            while (in.available()>0) {
                result.add((Persona) in.readObject());
            }

        } catch (EOFException | NullPointerException e) {
            System.out.println("Reached end of file");
        } finally {
            in.close();
        }



        return result;
    }
}