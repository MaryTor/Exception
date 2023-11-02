import MyExceptions.CountDataException;
import MyExceptions.TypeDataException;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class main {

    private static final int countData = 6;
    private static int counError = 0;

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args){
        String personData = getData();
        String[] data = personData.split(" ");
        try {
            validateCount(data);
        }
        catch (CountDataException e) {
            System.out.println(e.getMessage() + ": Требуется " +countData+" полей, а получаем " +data.length);
            return;
        }
        parseData(data);
        if (counError != 0) {
            System.out.println("Введенные данные не валидны и не могут быть сохранены");
            return;
        }
        saveData(data);

    }

    public static void parseData(String[] data){
        for (int i = 0; i < 3; i++) {
            try {
                validateString(data[i]);
            }
            catch (TypeDataException e){
                System.out.println(e.getMessage() + " в " +(i+1)+" пункте введенных данных. Требуется " +e.getType());
                counError += 1;
            }
        }
        try {
            validateBirth(data[3]);
        } catch (TypeDataException e){
            System.out.println(e.getMessage() + " дату нужно вводить в формате " + e.getType());
            counError += 1;
        }
        try {
            validatePhone(data[4]);
        }catch (TypeDataException e){
            System.out.println(e.getMessage() + e.getType());
            counError += 1;
        }
        try {
            validateGender(data[5]);
        }catch (TypeDataException e){
            System.out.println(e.getMessage() + e.getType());
            counError += 1;
        }

        try (FileWriter fileWriter = new FileWriter(data[0], true)) {
            String output = data + "\n";
            fileWriter.write(output);
            fileWriter.flush();
        } catch (IOException e) {
            System.out.println("Ошибка при записи данных в файл: " + e.getMessage());
            e.printStackTrace();
            counError += 1;
        }

    }

    public static String getData(){
        System.out.println("Введите данные через пробел (Фамилия, Имя, Отчество, дата рождения, номер телефона, пол):");
        return scanner.nextLine();
    }

    public static void validateCount(String[] inputData) throws CountDataException {

        if (inputData.length != countData) {
            throw new CountDataException("Некорректное число данных", inputData.length, countData);
        }
    }

    public static void validateString(String data) throws TypeDataException {
        String alphabet = "^[а-яА-Яa-zA-Z]+$";
        if (!data.matches(alphabet)){
            throw new TypeDataException("Некорректный тип данных", "String");
        }
    }

    public static void validateBirth(String date) throws TypeDataException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.mm.yyyy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(date);
        }catch (ParseException e){
            throw new TypeDataException("Неверный формат даты:", "dd.mm.yyyy");

        }
    }

    public static void validatePhone(String date) throws TypeDataException {
        String number = "\\d{11}";
        if (!date.matches(number)){
            throw new TypeDataException("Неверный номер телефона: ", "Только цифры, без знаков и пробелов");
        }
    }

    public static void validateGender(String date) throws TypeDataException {
        String type = "m|f";
        if (!date.matches(type)){
            throw new TypeDataException("Неверное обозначение пола: ", "Только m или f");
        }
    }
    public static void saveData(String[] data){
        try (FileWriter fileWriter = new FileWriter(data[0], true)) {
            for (int i = 0; i < data.length; i++) {
                String output =data[i] + " ";
                fileWriter.write(output);
            }
            String output =  "\n";
            fileWriter.write(output);
            fileWriter.flush();
        } catch (IOException e) {
            System.out.println("Ошибка при записи данных в файл: " + e.getMessage());
            e.printStackTrace();
        }

    }

}
