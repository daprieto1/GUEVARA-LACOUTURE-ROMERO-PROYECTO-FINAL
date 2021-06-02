package sample.logic.persistence.Implementacion;

import sample.logic.entities.Persona;
import sample.logic.persistence.IPersonaPersistance;

import java.io.*;
import java.rmi.server.ExportException;
import java.util.ArrayList;
import java.util.List;

public class PersonaPersistance implements IPersonaPersistance {

    private static final String PERSONAS_FILE_PATH ="personas.sabana";

    public PersonaPersistance() throws IOException {
        File file = new File(PERSONAS_FILE_PATH);
        if (file.createNewFile())
        {
            System.out.println("The file" + file.getName() + "was created");
        }
    }

    @Override
    public void save(Persona persona) throws IOException
    {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("personas.sabana",true));
        out.writeObject(persona);
        out.close();
    }

    @Override
    public List<Persona> read() throws IOException, ClassNotFoundException {

        List<Persona> result = new ArrayList<>();
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("personas.sabana"));

        Persona persona = (Persona) in.readObject();
        while(persona != null)
        {
            result.add(persona);
            persona = (Persona) in.readObject();
        }
        result.add((Persona) in.readObject());

        in.close();

        return result;
    }

    @Override
    public List<String> importPersonas(File file) throws IOException {

        if (!file.getName().split(",")[1].equals(Exportable.CSV))
        {
            throw new Exception("The file has not the right extention");
        }

        List<String> personas = new ArrayList<>();
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        br.readLine();
        String line = br.readLine();

        while (line != null)
        {
            personas.add(line);
            line = br.readLine();
        }

        br.close();
        return null;
    }



}
