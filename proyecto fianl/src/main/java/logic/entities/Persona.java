package logic.entities;

import logic.PersonaException;

public class Persona {

    private String name;
    private String lastName;
    private int age;

    private boolean isVictim;
    private Enum aggressionType;
    private Enum side;
    private String full;


    public Persona(String name, String lastName, int age, boolean isVictim, Enum aggressionType, Enum side) throws PersonaException {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.isVictim = isVictim;
        this.aggressionType = aggressionType;
        this.side = side;
        this.full = this.name + " " + this.lastName;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAge() {
        return String.valueOf(age);
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

    public void setAge(int age) {
        this.age = age;
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
    public void setFull(String name, String lastName) {
        this.full = name + " "+ lastName;
    }

    public String getFull() {
        return full;
    }
    public void prueba()
    {
        System.out.println("Hola");
    }

}
