package MyExceptions;

public class TypeDataException extends Exception {

    private final String type;



    public String getType(){
        return type;
    }

    public TypeDataException(String message, String type) {
        super(message);
        this.type = type;
    }
}
