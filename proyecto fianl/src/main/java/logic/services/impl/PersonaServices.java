package logic.services.impl;

import javafx.collections.FXCollections;

import logic.PersonaException;
import logic.entities.AggressionType;
import logic.entities.Persona;
import logic.entities.Side;


import java.io.File;
import java.io.IOException;
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
        getAllVictims(persona);


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

        personas.add(pee);
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

       victims.remove(persona);

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
