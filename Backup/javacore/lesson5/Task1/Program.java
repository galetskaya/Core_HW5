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

    /**
     * Данная функция создает временную папку в заданной директории и с заданным
     * префиксом имени. Возвращает объект, ссылающийся на созданную папку.
     *
     * @param root   Папка, в которой будет создавать временная папка
     * @param prefix Префикс для имени папки
     * @return Объект, ссылающийся на созданную папку.
     */
    public static Path createTmpDir(Path root, String prefix) throws IOException {
        Path dir = Files.createTempDirectory(root, prefix);
        System.out.println("Временная папка " + dir + " создана");
        return dir;
    }

    /**
     * Функция для удаления временной папки
     *
     * @param dir Путь до папки
     */
    public static void deleteTmpDir(Path dir) throws IOException {
        System.out.println("Удаление папки " + dir);
        Files.deleteIfExists(dir);
    }

    /**
     * Функция для копирования некоторых файлов, начиная с позиции begin, и
     * заканчивая позицией end - 1 в папку to.
     * Возвращает массив путей скопированных файлов.
     *
     * @param files Массив файлов, которые нужно скопировать
     * @param to    Путь до папки, в которую будут копироваться файлы
     * @return Возвращает массив путей скопированных файлов
     */
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


    /**
     * Функция для вывода списка файлов в заданной директории
     *
     * @param dir Путь до директории
     */
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


