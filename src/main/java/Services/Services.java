package Services;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Objects;

public class Services {

    public static String getFileExtension(File file) {
        String fileName = file.getName();
        String extension = "";
        int dot = fileName.lastIndexOf('.');
        int separator = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));
        if (dot > separator) {
            extension = fileName.substring(dot + 1);
        }
        return extension;
    }

    private String getFileMaxSizeInDir(String path) {
        File dir = new File(path);
        long size = 0;
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            if (file.isFile()) {
                if (file.length() > size) {
                    size = file.length();
                }
            }
        }
        return BigDecimal.valueOf((double) size / 1024).setScale(1, RoundingMode.HALF_UP) + "KB";
    }

    private String getFilesAVGSizeInDir(String path) {
        File dir = new File(path);
        long count = 0;
        long size = 0;
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            if (file.isFile()) {
                size += file.length();
                count++;
            }
        }
        return BigDecimal.valueOf((double) size / count / 1024).setScale(1, RoundingMode.HALF_UP) + "KB";
    }

    private long countFilesInDirectory(String path) {
        File dir = new File(path);
        long count = 0;
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            if (file.isFile()) {
                count++;
            }
        }
        return count;
    }

    private long countDirsInDirectory(String path) {
        File dir = new File(path);
        long count = 0;
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            if (file.isDirectory()) {
                count++;
            }
        }
        return count;
    }

    public void getDirList(File dirToOutput) {
        LinkedList<File> dirsList = new LinkedList<>();
        LinkedList<File> filesList = new LinkedList<>();
        for (File fileToType : Objects.requireNonNull(dirToOutput.listFiles())) {
            if (fileToType.isDirectory()) {
                dirsList.add(fileToType);
            }
            if (fileToType.isFile()) {
                filesList.add(fileToType);
            }
        }
        Comparator<File> comparator = Comparator.comparing(File::getName);
        dirsList.sort(comparator);
        filesList.sort(comparator);
        int i = 1;
        System.out.println("...");
        for (File dir : dirsList) {
            String fileName = formatToPrint(dir.getName(), 20);
            System.out.println(i + ". " + fileName + "dir");
            i++;
        }
        for (File file : filesList) {
            String fileName = formatToPrint(file.getName(), 20);
            BigDecimal bigDecimal = BigDecimal.valueOf((double) file.length() / 1024).setScale(1, RoundingMode.HALF_UP);
            String fileSize = formatToPrint(bigDecimal + "KB", 10);
            String extension = formatToPrint(getFileExtension(file), 7);
            System.out.println(i + ". " + fileName + extension + fileSize);
            i++;
        }

    }

    private String formatToPrint(String string, int minLength) {
        if (string.length() < minLength) {
            StringBuilder stringBuilder = new StringBuilder(string);
            while (stringBuilder.length() < minLength) {
                stringBuilder.append(" ");
            }
            string = stringBuilder.toString();
        }
        return string;
    }

    public void getDirInfo(File file) {
        String path = file.getPath();
        long countDirs = countDirsInDirectory(path);
        long countFiles = countFilesInDirectory(path);
        String filesAVGSize = getFilesAVGSizeInDir(path);
        String fileMaxSize = getFileMaxSizeInDir(path);
        System.out.println("Папка содержит:");
        System.out.println(countDirs + " папок");
        System.out.println(countFiles + " файлов");
        System.out.println("Самый большой файл: " + fileMaxSize);
        System.out.println("Средний размер файлов: " + filesAVGSize);
    }

    public boolean copyFiles(String from, String to, String mode) throws IOException {
        switch (mode) {
            case "Files":
                Files.copy(Path.of(from), Path.of(to));
                break;
            case "Channel":
                try (FileChannel inputChannel = new FileInputStream(from).getChannel(); FileChannel outputChannel = new FileOutputStream(to).getChannel()) {
                    outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
                }
                break;
            case "Stream":
                try (InputStream inputStream = new FileInputStream(from); OutputStream outputStream = new FileOutputStream(to)) {
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = inputStream.read(buffer)) > 0) {
                        outputStream.write(buffer, 0, length);
                    }
                }
                break;
        }
        File fromFile = new File(from);
        File toFile = new File(to);
        return fromFile.equals(toFile);
    }

    public boolean copyFiles(String from, String to) throws IOException {
        return copyFiles(from, to, "Files");
    }
}
