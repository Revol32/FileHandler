import Services.Menu;
import Services.Services;

import java.io.File;
import java.io.IOException;

public class Main {
    static Menu menu = new Menu();

    public static void main(String[] args) {
        Services services = new Services();
        boolean cycle = true;
        while (cycle) {
            String mainMenu = menu.startMessage();
            switch (mainMenu) {
                case "1" -> {
                    String dirMenu = menu.dirMenu();
                    switch (dirMenu) {
                        case "1" -> {
                            String dirDir = menu.enterDirPath();
                            services.getDirList(new File(dirDir));
                        }
                        case "2" -> {
                            String infoDir = menu.enterDirPath();
                            services.getDirInfo(new File(infoDir));
                        }
                        case "3" -> {
                        }
                    }
                }
                case "2" -> {
                    String fileMenu = menu.fileMenu();
                    switch (fileMenu) {
                        case "1" -> {
                            String delete = menu.enterFilePath(true);
                            System.out.println(new File(delete).delete() ? "OK" : "Ошибка");
                        }
                        case "2" -> {
                            String create = menu.enterDirPath();
                            try {
                                System.out.println(new File(create).createNewFile() ? "OK" : "Ошибка");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        case "3" -> {
                        }
                    }
                }
                case "3" -> {
                    System.out.println("Исходный фаил.");
                    String fromPath = menu.enterFilePath(true);
                    System.out.println("Расположение нового файла.");
                    String toPath = menu.enterFilePath(false);
                    try {
                        System.out.println(services.copyFiles(fromPath, toPath) ? "OK" : "Ошибка");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                case "4" -> cycle = false;
            }
        }
    }
}
