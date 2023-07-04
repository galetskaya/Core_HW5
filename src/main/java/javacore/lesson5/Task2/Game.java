package javacore.lesson5.Task2;

import java.util.Random;
import java.util.Scanner;

import static javacore.lesson5.Task2.GameField.convertSave;

public class Game {
    private static final int DOT_HUMAN = 1;
    private static final int DOT_AI = 2;
    private static final int DOT_EMPTY = 0;
    private static final char DOT_HUMAN_UI = 'X';
    private static final char DOT_AI_UI = 'O';
    private static final char DOT_EMPTY_UI = '.';
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final String GAME_NAME = "*** - Крестики-нолики - ***";

//    private static int[][] field; // двумерный массив хранит текущее состояние игрового поля

    private static final Random random = new Random();

    private static int fieldSizeX; // размерность игрового поля
    private static int fieldSizeY; // размерность игрового поля
    private static String fileStorage; // размерность игрового поля


// Инициализация игрового поля

    private static void initialize(String fileName) {
        int[][] field;
        // Установим размерность игрового поля
        fieldSizeX = 3;
        fieldSizeY = 3;

        fileStorage = fileName;

        field = new int[fieldSizeX][fieldSizeY];
        // пройдем по всем элементам массива
        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y < fieldSizeY; y++) {
                // проинициализируем все элементы массива DOT_EMPTY (признак пустого поля)
                field[x][y] = DOT_EMPTY;
            }
        }
        convertSave(fileStorage, field);
    }

// отрисовывание поля
    private static void printField() {
        System.out.print("+");
        for (int i = 0; i < fieldSizeX * 2 + 1; i++) {
            System.out.print((i % 2 == 0) ? "-" : i / 2 + 1);
        }
        System.out.println();

        for (int i = 0; i < fieldSizeX; i++) {
            System.out.print(i + 1 + "|");

            for (int j = 0; j < fieldSizeY; j++) {
                int[][] field = GameField.convertLoad(fileStorage, fieldSizeX);
                System.out.print(convertUI(field[i][j]) + "|");
            }
            System.out.println();
        }

        for (int i = 0; i < fieldSizeX * 2 + 2; i++) {
            System.out.print("-");
        }
        System.out.println();
    }

 // ход игрока
    private static void humanTurn() {
        int x, y;
        do {
            System.out.print("Введите координаты хода X и Y (от 1 до 3) через пробел >>> ");
            x = SCANNER.nextInt() - 1;
            y = SCANNER.nextInt() - 1;
        }
        while (!isCellValid(x, y) || !isCellEmpty(x, y));
        int[][] field = GameField.convertLoad(fileStorage, fieldSizeX);
        field[x][y] = DOT_HUMAN;
        convertSave(fileStorage, field);
    }

// проверка на пустоту ячейки
    static boolean isCellEmpty(int x, int y) {
        int[][] field = GameField.convertLoad(fileStorage, fieldSizeX);
        return field[x][y] == 0;
    }

// валидность координат
    static boolean isCellValid(int x, int y) {
        return x >= 0 && x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }

// II ход
    private static void aiTurn() {
        int x, y;
        do {
            x = random.nextInt(fieldSizeX);
            y = random.nextInt(fieldSizeY);
        }
        while (!isCellEmpty(x, y));
        int[][] field = GameField.convertLoad(fileStorage, fieldSizeX);
        field[x][y] = DOT_AI;
        convertSave(fileStorage, field);
    }

// Проверка победы
    static boolean checkWin(int c) {
        int[][] field = GameField.convertLoad(fileStorage, fieldSizeX);
        // Проверка по трем горизонталям
        if (field[0][0] == c && field[0][1] == c && field[0][2] == c) return true;
        if (field[1][0] == c && field[1][1] == c && field[1][2] == c) return true;
        if (field[2][0] == c && field[2][1] == c && field[2][2] == c) return true;

        // Проверка по диагоналям
        if (field[0][0] == c && field[1][1] == c && field[2][2] == c) return true;
        if (field[0][2] == c && field[1][1] == c && field[2][0] == c) return true;

        // Проверка по трем вертикалям
        if (field[0][0] == c && field[1][0] == c && field[2][0] == c) return true;
        if (field[0][1] == c && field[1][1] == c && field[2][1] == c) return true;
        if (field[0][2] == c && field[1][2] == c && field[2][2] == c) return true;

        return false;
    }

// проверка на ничью
    static boolean checkDraw() {
        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y < fieldSizeY; y++) {
                if (isCellEmpty(x, y)) return false;
            }
        }
        return true;
    }

// проверка игры

    static boolean gameCheck(int c, String str) {
        if (checkWin(c)) {
            System.out.print("\033[H\033[J");
            System.out.println(GAME_NAME);
            printField();
            System.out.println(str);
            return true;
        }
        if (checkDraw()) {
            System.out.print("\033[H\033[J");
            System.out.println(GAME_NAME);
            printField();
            System.out.println("Ничья!");
            return true;
        }
        return false; // Игра продолжается
    }

    static void gameStart(String fileName) {
        do {
            System.out.print("\033[H\033[J");
            System.out.println(GAME_NAME);
            initialize(fileName);
            printField();
            while (true) {
                humanTurn();
                System.out.print("\033[H\033[J");
                printField();
                if (gameCheck(DOT_HUMAN, "Вы победили"))
                    break;
                aiTurn();
                System.out.print("\033[H\033[J");
                System.out.println(GAME_NAME);
                printField();
                if (gameCheck(DOT_AI, "Компьютер победил"))
                    break;
            }
            System.out.println("Сыграть еще раз? (Y - да N - нет)");
        } while (SCANNER.next().equalsIgnoreCase("Y"));
    }

    private static char convertUI(int value) {
        return switch (value) {
            case 1 -> DOT_HUMAN_UI;
            case 2 -> DOT_AI_UI;
            default -> DOT_EMPTY_UI;
        };
    }

}