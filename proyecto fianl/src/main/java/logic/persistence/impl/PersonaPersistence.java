package logic.persistence.impl;

import logic.PersonaException;
import logic.entities.AggressionType;
import logic.entities.Persona;
import logic.entities.Side;
import logic.persistence.IPersonaPersistence;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

public class PersonaPersistence implements IPersonaPersistence {
    @Override
    public void save(Persona persona) throws IOException {
        FileOutputStream fos = new FileOutputStream("project.sabana");
        ObjectOutputStream out = new ObjectOutputStream(fos);

        Persona p = null;
        try {
            p = new Persona("Mia","Lacouture",18,true, AggressionType.VIOLENCIA_HOMICIDA_CON_ARMAS, Side.CIVILIAN);
        } catch (PersonaException e) {
            e.printStackTrace();
        }

        out.writeObject(p);

        out.flush();
        out.close();

    }

    @Override
    public List<Persona> read(String path) throws IOException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<String> importPersonas(File file) throws Exception {
        return null;
    }
}
