package util;

import java.util.Scanner;

public class InputUtil {
    private static final Scanner scanner = new Scanner(System.in);

    public static String inputString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public static int inputInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Masukkan angka yang valid!");
            }
        }
    }
}