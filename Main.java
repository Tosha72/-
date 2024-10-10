import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Выберите режим работы:");
            System.out.println("1. Шифрование текста");
            System.out.println("2. Расшифровка текста");
            System.out.println("3. Выход");
            choice = scanner.nextInt();
            scanner.nextLine();  // очистка буфера

            switch (choice) {
                case 1:
                    encryptFile(scanner);
                    break;
                case 2:
                    decryptFile(scanner);
                    break;
                case 3:
                    System.out.println("Выход из программы.");
                    break;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        } while (choice != 3);

        scanner.close();
    }

    private static void encryptFile(Scanner scanner) {
        System.out.print("Введите путь к файлу для шифрования: ");
        String inputFilePath = scanner.nextLine();
        System.out.print("Введите путь для сохранения зашифрованного файла: ");
        String outputFilePath = scanner.nextLine();
        System.out.print("Введите ключ (целое число от 1 до 25): ");
        int key = scanner.nextInt();
        scanner.nextLine(); // очистка буфера

        if (!isValidKey(key)) {
            System.out.println("Недопустимый ключ. Ключ должен быть от 1 до 25.");
            return;
        }

        try {
            String text = readFile(inputFilePath);
            String encryptedText = encrypt(text, key);
            writeFile(outputFilePath, encryptedText);
            System.out.println("Шифрование завершено. Зашифрованный файл сохранен по пути: " + outputFilePath);
        } catch (IOException e) {
            System.out.println("Ошибка при обработке файла: " + e.getMessage());
        }
    }

    private static void decryptFile(Scanner scanner) {
        System.out.print("Введите путь к файлу для расшифровки: ");
        String inputFilePath = scanner.nextLine();
        System.out.print("Введите ключ (целое число от 1 до 25): ");
        int key = scanner.nextInt();
        scanner.nextLine(); // очистка буфера

        if (!isValidKey(key)) {
            System.out.println("Недопустимый ключ. Ключ должен быть от 1 до 25.");
            return;
        }

        try {
            String text = readFile(inputFilePath);
            String decryptedText = decrypt(text, key);
            System.out.println("Расшифрованный текст: ");
            System.out.println(decryptedText);
        } catch (IOException e) {
            System.out.println("Ошибка при обработке файла: " + e.getMessage());
        }
    }

    private static String readFile(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    private static void writeFile(String filePath, String text) throws IOException {
        Files.write(Paths.get(filePath), text.getBytes());
    }

    private static String encrypt(String text, int key) {
        StringBuilder encrypted = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? 'a' : 'A';
                encrypted.append((char) ((c - base + key) % 26 + base));
            } else {
                encrypted.append(c); // оставить символы без изменений
            }
        }
        return encrypted.toString();
    }

    private static String decrypt(String text, int key) {
        return encrypt(text, 26 - key); // расшифровка с помощью обратного сдвига
    }

    private static boolean isValidKey(int key) {
        return key >= 1 && key <= 25;
    }
}
