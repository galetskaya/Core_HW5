package javacore.lesson5.Task2;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.FileOutputStream;

public class GameField {
    private static void saveField(int[] numbers, String fileName) {
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            for (int i = 0; i < numbers.length; i += 3) {
                byte b = (byte) ((numbers[i] << 4) | (numbers[i + 1] << 2) | numbers[i + 2]);
                fos.write(b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int[] loadField(String fileName) {
        int[] numbers = new int[9];
        try {
            byte[] array = Files.readAllBytes(Paths.get(fileName));

            int j = 0;
            for (int i = 0; i < numbers.length; i += 3) {
                numbers[i] = array[j] >> 4 & 0x3;
                numbers[i + 1] = array[j] >> 2 & 0x3;
                numbers[i + 2] = array[j] & 0x3;
                j++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return numbers;
    }


    private static int[][] convertTo2D(int[] numbers, int size) {
        int[][] result = new int[size][size];

        int i = 0;
        for (int j = 0; j < result.length; j++) {
            for (int k = 0; k < result[0].length; k++) {
                result[j][k] = numbers[i++];
            }
        }
        return result;
    }

    private static int[] convert2DTo1D(int[][] numbers) {
        int[] result = new int[numbers.length * numbers[0].length];

        int i = 0;
        for (int j = 0; j < numbers.length; j++) {
            for (int k = 0; k < numbers[0].length; k++) {
                result[i++] = numbers[j][k];
            }
        }
        return result;
    }

    public static void convertSave(String fileName, int[][] numbers) {
        int[] array = convert2DTo1D(numbers);
//        System.out.println(Arrays.toString(array));
        saveField(array, fileName);
    }

    public static int[][] convertLoad(String fileName, int size) {
        int[] array = loadField(fileName);
        return convertTo2D(array, size);
    }

}