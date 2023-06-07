import java.io.IOException;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        while(true){
            System.out.print("Введите выражение: ");
            String input = in.nextLine();
            System.out.println("Ответ: " + calc(input));
        }
    }
    static int counter = 0;
    public static String toRome(int number){ //преобразование арбаского числа в римское
        int dozens, units;
        String out = "";
        dozens = number / 10;
        units = number % 10;
        switch (dozens) { //десятки
            case 1 -> out += "X";
            case 2 -> out += "XX";
            case 3 -> out += "XXX";
            case 4 -> out += "XL";
            case 5 -> out += "L";
            case 6 -> out += "LX";
            case 7 -> out += "LXX";
            case 8 -> out += "LXXX";
            case 9 -> out += "XC";
            case 10 -> out += "C";
            default -> {
            }
        }
        switch (units) { //единицы
            case 1 -> out += "I";
            case 2 -> out += "II";
            case 3 -> out += "III";
            case 4 -> out += "IV";
            case 5 -> out += "V";
            case 6 -> out += "VI";
            case 7 -> out += "VII";
            case 8 -> out += "VIII";
            case 9 -> out += "IX";
            default -> {
            }
        }
        return out;
    }
    public static int tryToArabic(String rome){ //проверка на римское число -> преобразование в арабское
        int number;
        switch (rome) {
            case "I":
                counter++;
                return 1;
            case "II":
                counter++;
                return 2;
            case "III":
                counter++;
                return 3;
            case "IV":
                counter++;
                return 4;
            case "V":
                counter++;
                return 5;
            case "VI":
                counter++;
                return 6;
            case "VII":
                counter++;
                return 7;
            case "VIII":
                counter++;
                return 8;
            case "IX":
                counter++;
                return 9;
            case "X":
                counter++;
                return 10;
        }
        return Integer.parseInt(rome);
    }
    public static String calc(String input) throws IOException {
        int[] numbers = new int[2]; //числа a и b
        String[] parts = input.split(" "); //разделение строки на два числа и оператор

        String buf = parts[2]; //второе число и оператор меняются местами в массиве, для удобства вычисления в последующем цикле
        parts[2] = parts[1];
        parts[1] = buf;

        numbers[0] = tryToArabic(parts[0]);
        numbers[1] = tryToArabic(parts[1]);

        if(counter == 0) { //если нет римских чисел
            for (int i = 0; i < numbers.length; i++){
                try {
                    numbers[i] = Integer.parseInt(parts[i]); //преобразование числа в int, если это невозможно выдает исключение
                }
                catch(NumberFormatException e){
                    e.printStackTrace();
                }
                if(numbers[i] < 1 || numbers[i] > 10){ //проверка вводных чисел на соответствие диапазону
                    throw new NumberFormatException("Вводимое число(-а) вне диапазона от 1 до 10 включительно");
                }
            }
            switch (parts[2]) { //проверка символа на соответствие поддерживаемых операторов и расчет
                case "+" -> input = Integer.toString(numbers[0] + numbers[1]);
                case "-" -> input = Integer.toString(numbers[0] - numbers[1]);
                case "*" -> input = Integer.toString(numbers[0] * numbers[1]);
                case "/" -> input = Integer.toString(numbers[0] / numbers[1]);
                default -> throw new IOException("Формат математической операции не удовлетворяет требованиям");
            }
        }
        else if(counter == 2){ //если оба числа римские
            switch (parts[2]) {
                case "+" -> input = toRome((numbers[0]) + (numbers[1]));
                case "-" -> {
                    if (numbers[0] - numbers[1] < 1) {
                        throw new NumberFormatException("Разность чисел меньше единицы");
                    } else {
                        input = toRome((numbers[0]) - (numbers[1]));
                    }
                }
                case "*" -> input = toRome((numbers[0]) * (numbers[1]));
                case "/" -> {
                    if(numbers[0] / numbers[1] == 0)
                        throw new NumberFormatException("Результат деления римских чисел равен нулю");
                    else
                        input = toRome((numbers[0]) / (numbers[1]));
                }
                default -> throw new IOException("Формат математической операции не удовлетворяет требованиям");
            }
        }
        else{ //если формат чисел разный
            throw new IOException("Формат вводимых чисел неодинаков");
        }
        return input;
    }
}