package javacore.lesson5.Task1;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
/*
Написать функцию,
создающую резервную копию всех файлов в директории
(без поддиректорий) во вновь созданную папку ./backup
*/


public class Program {
    /*Задаем количество создаваемых файлов*/
    public static final int N = 4;


    public static Path createTmpDir(Path root, String prefix) throws IOException {
        Path dir = Files.createTempDirectory(root, prefix);
        System.out.println("Временная папка " + dir + " создана");
        return dir;
    }


    public static void deleteTmpDir(Path dir) throws IOException {
        System.out.println("Удаление папки " + dir);
        Files.deleteIfExists(dir);
    }


    public static Path[] copyFiles(Path[] files, Path to) throws IOException {
        Path[] newFiles = new Path[files.length];
//        System.out.println("Копирование файлов из папки " + files[begin].getParent() + " в папку " + to);
        for (int i = 0; i < files.length; i++) {
            newFiles[i] = Paths.get(to.toString() + "/" + files[i].getFileName());
            Files.copy(files[i], newFiles[i]);
            System.out.println("\tФайл " + files[i].getFileName() + " скопирован");
        }
        System.out.println();
        return newFiles;
    }


    public static void printFilesList(Path dir) {
        System.out.println("Список файлов в папке " + dir + ": ");
        File[] filesList = dir.toFile().listFiles();
        if (filesList.length == 0) {
            System.out.println("\tПапка пуста");
        } else {
            for (File file : filesList) {
                System.out.println("\t" + file);
            }
        }
        System.out.println();
    }


}


