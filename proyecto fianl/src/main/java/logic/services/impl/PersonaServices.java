package logic.services.impl;

import javafx.collections.FXCollections;

import logic.PersonaException;
import logic.entities.AggressionType;
import logic.entities.Exportable;
import logic.entities.Persona;
import logic.entities.Side;
import logic.persistence.IPersonaPersistence;
import logic.persistence.impl.Export;
import logic.persistence.impl.IExport;
import logic.persistence.impl.PersonaPersistence;


import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;


public class PersonaServices implements IPersonaServices {



    private List<Persona> personas;
    private List<Persona> victims = new ArrayList<>();
    private List<Persona> pViolenciaHomicida=new ArrayList<>();
    private List<Persona> pViolenciaConArmas=new ArrayList<>();
    private List<Persona> pViolenciaSexual=new ArrayList<>();
    private List<Persona> mViolenciaHomicida=new ArrayList<>();
    private List<Persona> mViolenciaConArmas=new ArrayList<>();
    private List<Persona> mViolenciaSexual=new ArrayList<>();

    private static IExport export = new Export();
    private IPersonaPersistence personaPersistence;

    public PersonaServices() throws IOException, ClassNotFoundException {

        this.personas = FXCollections.observableArrayList();

        try {
            this.personaPersistence = new PersonaPersistence();
            this.export = new Export();
            this.personas = personaPersistence.read("personas.sabana");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    @Override
    public List<Persona> getAll() {
        return this.personas;
    }

    @Override
    public Persona insert(Persona persona)
    {
        personas.add(persona);
        getAllVictims(persona);
        try {
            personaPersistence.save(persona);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        return persona;
    }

    @Override
    public void delete(Persona persona) {
        personas.remove(persona);
        getAllDeletedVictims(persona);


    }

    @Override
    public void export() throws Exception{
        List<Exportable> e = new ArrayList<>();
        this.personas.stream().forEach(p -> e.add(p));
        export.export(e,Exportable.CSV);

    }

    @Override
    public List<Persona> importPersonas(File file) throws Exception {
            List<Persona> importedPersonas = new ArrayList<>();
            List<String> read = this.personaPersistence.importPersonas(file);

            for (String line : read) {
                String[] tokens = line.split(Exportable.CSV.toString());
                Persona persona = new Persona(tokens[0], tokens[1], 25,true,AggressionType.VIOLENCIA_HOMICIDA_CON_ARMAS,Side.POLICE);
                importedPersonas.add(persona);
                this.insert(persona);
            }

            return importedPersonas;

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

        personas.add(pee);

        getAllVictims(pee);
        getAllDeletedVictims(a);
        return pee;
    }

    @Override
    public Persona getAllVictims(Persona persona) {
        if (persona.isVictim())
        {
            victims.add(persona);
            if (persona.getSide().equals(Side.POLICE)&&persona.getAggressionType().equals(AggressionType.VIOLENCIA_HOMICIDA_CON_ARMAS))
                pViolenciaHomicida.add(persona);
            if (persona.getSide().equals(Side.POLICE)&&persona.getAggressionType().equals(AggressionType.VIOLENCIA_CON_ARMAS))
                pViolenciaConArmas.add(persona);
            if (persona.getSide().equals(Side.POLICE)&&persona.getAggressionType().equals(AggressionType.VIOLENCIA_SEXUAL))
                pViolenciaSexual.add(persona);
            if (persona.getSide().equals(Side.CIVILIAN)&&persona.getAggressionType().equals(AggressionType.VIOLENCIA_HOMICIDA_CON_ARMAS))
                mViolenciaHomicida.add(persona);
            if (persona.getSide().equals(Side.CIVILIAN)&&persona.getAggressionType().equals(AggressionType.VIOLENCIA_CON_ARMAS))
                mViolenciaConArmas.add(persona);
            if (persona.getSide().equals(Side.CIVILIAN)&&persona.getAggressionType().equals(AggressionType.VIOLENCIA_SEXUAL))
                mViolenciaSexual.add(persona);
        }
        return persona;
    }

    @Override
    public void getAllDeletedVictims(Persona persona) {

        if (persona.isVictim())
        {
            victims.remove(persona);
            if (persona.getSide().equals(Side.POLICE)&&persona.getAggressionType().equals(AggressionType.VIOLENCIA_HOMICIDA_CON_ARMAS))
                pViolenciaHomicida.remove(persona);
            if (persona.getSide().equals(Side.POLICE)&&persona.getAggressionType().equals(AggressionType.VIOLENCIA_CON_ARMAS))
                pViolenciaConArmas.remove(persona);
            if (persona.getSide().equals(Side.POLICE)&&persona.getAggressionType().equals(AggressionType.VIOLENCIA_SEXUAL))
                pViolenciaSexual.remove(persona);
            if (persona.getSide().equals(Side.CIVILIAN)&&persona.getAggressionType().equals(AggressionType.VIOLENCIA_HOMICIDA_CON_ARMAS))
                mViolenciaHomicida.remove(persona);
            if (persona.getSide().equals(Side.CIVILIAN)&&persona.getAggressionType().equals(AggressionType.VIOLENCIA_CON_ARMAS))
                mViolenciaConArmas.remove(persona);
            if (persona.getSide().equals(Side.CIVILIAN)&&persona.getAggressionType().equals(AggressionType.VIOLENCIA_SEXUAL))
                mViolenciaSexual.remove(persona);
        }

    }

    public List<Persona> getpViolenciaHomicida() {
        return pViolenciaHomicida;
    }

    public List<Persona> getpViolenciaConArmas() {
        return pViolenciaConArmas;
    }

    public List<Persona> getpViolenciaSexual() {
        return pViolenciaSexual;
    }

    public List<Persona> getmViolenciaHomicida() {
        return mViolenciaHomicida;
    }

    public List<Persona> getmViolenciaConArmas() {
        return mViolenciaConArmas;
    }

    public List<Persona> getmViolenciaSexual() {
        return mViolenciaSexual;
    }

    public List<Persona> getVictims() {
        return victims;
    }
}
