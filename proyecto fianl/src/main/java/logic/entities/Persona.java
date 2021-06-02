package logic.entities;

import logic.PersonaException;

public class Persona {

    private String name;
    private String lastName;
    private int age;

    private boolean isVictim;
    private Enum aggressionType;
    private Enum side;
    private String fullName = getName()+" "+getLastName();


    public Persona(String name, String lastName, int age, boolean isVictim, Enum aggressionType, Enum side) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.isVictim = isVictim;
        this.aggressionType = aggressionType;
        this.side = side;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public boolean isVictim() {
        return isVictim;
    }

    public Enum getAggressionType() {
        return aggressionType;
    }

    public Enum getSide() {
        return side;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(String ageInput) throws PersonaException {
        try {
            int age = Integer.parseInt(ageInput);

            if (age < 0) throw new PersonaException(PersonaException.BAD_AGE_LOWER);
            if (age > 120) throw new PersonaException(PersonaException.BAD_AGE_UPPER);

            this.age = age;
        } catch (NumberFormatException er) {
            throw new PersonaException(PersonaException.BAD_AGE + " : " + er.getMessage());
        }
    }

    public void setVictim(boolean victim) {
        isVictim = victim;
    }

    public void setAggressionType(Enum aggressionType) {
        this.aggressionType = aggressionType;
    }

    public void setSide(Enum side) {
        this.side = side;
    }
}
