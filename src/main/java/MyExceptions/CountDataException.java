package MyExceptions;

public class CountDataException extends Exception{

    private final int inputData;
    private final int countData;

    public int getInputData(){

        return getInputData();
    }
    public int getCountData(){
        return getInputData();
    }

    public CountDataException(String message, int inputData, int countData) {
        super(message);
        this.inputData = inputData;
        this.countData = countData;
    }
}
