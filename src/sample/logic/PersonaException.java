package sample.logic;

public class PersonaException extends Exception{

    public static String BAD_AGE_LOWER = "The age must be greater than 0";
    public static String BAD_AGE_UPPER = "The age must be lower tan 120";
    public static String BAD_AGE = "This text is not an age";

    public PersonaException(String message)
    {
        super(message);
    }

}
