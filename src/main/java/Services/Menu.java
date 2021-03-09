package Services;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Menu {

    public String startMessage() {
        List<String> menusList = Arrays.asList("1", "2", "3", "4");
        System.out.println("Работа с файлами");
        System.out.println("Выбирите действие:");
        System.out.println("1. Информация о папке.");
        System.out.println("2. Удалить/Создать фаил.");
        System.out.println("3. Копирование файла");
        System.out.println("4. Выход.");
        System.out.println("Выберете пункт меню:");
        String menu = new Scanner(System.in).nextLine();
        System.out.println();
        if (!menusList.contains(menu)) {
            System.out.println("Ввидите верное значение от 1 до 4");
            startMessage();
        }
        return menu;
    }

    public String dirMenu() {
        List<String> menusList = Arrays.asList("1", "2", "3");
        System.out.println("Информация о папке.");
        System.out.println("Выбирите действие:");
        System.out.println("1. dir.");
        System.out.println("2. Статистика.");
        System.out.println("3. Выход.");
        System.out.println("Выберете пункт меню:");
        String menu = new Scanner(System.in).nextLine();
        System.out.println();
        if (!menusList.contains(menu)) {
            System.out.println("Ввидите верное значение от 1 до 3");
            dirMenu();
        }
        return menu;
    }

    public String fileMenu() {
        List<String> menusList = Arrays.asList("1", "2", "3");
        System.out.println("Удалить/Создать фаил.");
        System.out.println("Выбирите действие:");
        System.out.println("1. Удалить файл.");
        System.out.println("2. Создать файл.");
        System.out.println("3. Выход.");
        System.out.println("Выберете пункт меню:");
        String menu = new Scanner(System.in).nextLine();
        System.out.println();
        if (!menusList.contains(menu)) {
            System.out.println("Ввидите верное значение от 1 до 3");
            fileMenu();
        }
        return menu;
    }

    public String enterFilePath(boolean exist) {
        System.out.println("Введите путь до файла:");
        String path = new Scanner(System.in).nextLine();
        File file = new File(path);
        if (exist) {
            if (file.isFile()) {
                return path;
            } else {
                System.out.println("Путь до файла не верен (файла возможно не сушествует).");
                enterFilePath(true);
            }
        } else {
            if (!file.isFile()) {
                if (Services.getFileExtension(file).length() > 0) {
                    return path;
                } else {
                    System.out.println("Задайте верное раширение файла");
                    enterFilePath(false);
                }
            } else {
                System.out.println("Данный файл уже сушествует");
                enterFilePath(false);
            }
        }
        return null;
    }

    public String enterDirPath() {
        System.out.println("Введите путь до папки:");
        String path = new Scanner(System.in).nextLine();
        File file = new File(path);
        if (file.isDirectory()) {
            return path;
        } else {
            System.out.println("Введеный путь не сушествует");
            enterDirPath();
        }
        return null;
    }

}
