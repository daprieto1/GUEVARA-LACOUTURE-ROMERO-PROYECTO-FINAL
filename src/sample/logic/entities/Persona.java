package sample.logic.entities;

import sample.logic.PersonaException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Persona extends Exportable implements Serializable {
    private UUID id;
    private String name;
    private String last;
    private int age;

    public Persona(String name, String lastName, String age) throws PersonaException {
        this.name = name;
        this.last = lastName;
        this.setAge(age);
        this.id = UUID.randomUUID();
    }

    public void setAge(String ageInput) throws PersonaException {
        try {
            int age = Integer.parseInt(ageInput);

            if (age < 1)
                throw new PersonaException(PersonaException.BAD_AGE_LOWER);
            if (age > 120)
                throw new PersonaException(PersonaException.BAD_AGE_UPPER);

            this.age = age;
        } catch (NumberFormatException er) {
            throw new PersonaException(PersonaException.BAD_AGE + er.getMessage());
        }
    }

    public int getAge() {
        return age;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLast() {
        return last;
    }
    @Override
    public List<String> toListString() {
        List<String> result = new ArrayList<>();
        result.add(this.name);
        result.add(this.last);
        return result;
    }

    @Override
    public String toString(){
        return String.format("Name = %s, LastName = %s, Age = %s", this.name,this.last,this.age);
    }
}
