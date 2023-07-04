package javacore.lesson5.Task1;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/*
Написать функцию, создающую резервную
копию всех файлов в директории(без поддиректорий) во вновь созданную папку
./backup
*/
public class Main {
    public static void main(String[] args) {

        String sourceDirectoryLocation = "C:\\Users\\mega1\\Desktop\\GB Учебка\\JAVA CORE\\HW5\\src\\main\\java";
        String destinationDirectoryLocation = "C:\\Users\\mega1\\Desktop\\GB Учебка\\JAVA CORE\\HW5\\Backup";


        try {
            copyDirectory(sourceDirectoryLocation, destinationDirectoryLocation);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void copyDirectory(String sourceDirectoryLocation, String destinationDirectoryLocation)
            throws IOException {
        Files.walk(Paths.get(sourceDirectoryLocation))
                .forEach(source -> {
                    Path destination = Paths.get(destinationDirectoryLocation, source.toString()
                            .substring(sourceDirectoryLocation.length()));
                    try {
                        Files.copy(source, destination);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });


    }



}

