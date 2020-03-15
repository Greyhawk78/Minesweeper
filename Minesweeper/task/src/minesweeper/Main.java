package minesweeper;

import java.util.Random;
import java.util.Scanner;

public class Main {

    final static Scanner scanner = new Scanner(System.in);
    static int fieldSize=9;


    public static String[][] generateField (int quantityOfMines, int firstX, int firstY) {
        String[][] field = new String[fieldSize][fieldSize];
        int count = 0;

        for (int y = 0; y < fieldSize; y++) {
            for (int x = 0; x < fieldSize; x++) {
                field[y][x] = "/";
            }
        }

        while (count < quantityOfMines) {
            Random random = new Random();
            int tryx = random.nextInt(fieldSize);
            int tryy = random.nextInt(fieldSize);
            if (!field[tryy][tryx].equals("X")||!(tryx==firstX&&tryy==firstY)) {
                field[tryy][tryx] = "X";
                count++;
            }
        }

        int countOfMines=0;
        for (int y = 0; y < fieldSize; y++) {
            for (int x = 0; x < fieldSize; x++) {
                int ycheckStart = y-1;
                if (ycheckStart<0) ycheckStart=0;
                int xcheckStart = x-1;
                if (xcheckStart<0) xcheckStart=0;
                int ycheckFinish = y+1;
                if (ycheckFinish==fieldSize) ycheckFinish=fieldSize-1;
                int xcheckFinish = x+1;
                if (xcheckFinish==fieldSize) xcheckFinish=fieldSize-1;
                for (int ytemp=ycheckStart;ytemp<=ycheckFinish;ytemp++) {
                    for (int xtemp=xcheckStart;xtemp<=xcheckFinish;xtemp++) {
                        if (field[ytemp][xtemp].equals("X") &&(ytemp!=y||xtemp!=x)) {
                            countOfMines++;
                        }
                    }
                }
                if (countOfMines>0&& !field[y][x].equals("X")) field [y] [x] = String.valueOf(countOfMines);
                countOfMines=0;
            }
        }


        return field;
    }

 /*   public static void checkField (String[][] field) {
        int countOfMines=0;
        for (int y = 0; y < fieldSize; y++) {
            for (int x = 0; x < fieldSize; x++) {
                int ycheckStart = y-1;
                if (ycheckStart<0) ycheckStart=0;
                int xcheckStart = x-1;
                if (xcheckStart<0) xcheckStart=0;
                int ycheckFinish = y+1;
                if (ycheckFinish==fieldSize) ycheckFinish=fieldSize-1;
                int xcheckFinish = x+1;
                if (xcheckFinish==fieldSize) xcheckFinish=fieldSize-1;
                for (int ytemp=ycheckStart;ytemp<=ycheckFinish;ytemp++) {
                    for (int xtemp=xcheckStart;xtemp<=xcheckFinish;xtemp++) {
                        if (field[ytemp][xtemp].equals("X") &&(ytemp!=y||xtemp!=x)) {
                            countOfMines++;
                        }
                    }
                }
                if (countOfMines>0&& !field[y][x].equals("X")) field [y] [x] = String.valueOf(countOfMines);
                countOfMines=0;
            }
        }
    }

*/
    public static void printField (String[][] field) {


        System.out.println(" |123456789|");
        System.out.println("-|--------");
        for (int y = 0; y < fieldSize; y++) {
            System.out.print(y+1 + "|");
            for (int x = 0; x < fieldSize; x++) {
                System.out.print(field[y] [x]);
            }
            System.out.println("|");
        }
        System.out.println("-|-----------");
    }


    public static void checkWin (String [] [] secretField, String [] [] openField) {
        boolean win = true;
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                if (secretField[i][j].equals("X") && !openField[i][j].equals("*") ||
                        openField[i][j].equals("*") && !secretField[i][j].equals("X")) {
                    win = false;
                    break;
                }
            }
        }
        if (win) {
            System.out.println("Congratulations! You found all mines!");
            return;
        }
    }


    public static void gameLoop (String [] [] secretField, int firstX, int firstY) {

        String [] [] openField = new String[fieldSize][fieldSize];

        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                 openField[i] [j] = ".";
            }
        }


        printField(openField);

    while (true) {
    System.out.println("Set/unset mines marks or claim a cell as free:");
    int x = scanner.nextInt();
    int y = scanner.nextInt();
    String operation = scanner.next();
switch (operation) {
    case "mark":
    switch (openField[y - 1][x - 1]) {
        case ".":
            openField[y - 1][x - 1] = "*";
            printField(openField);
            break;
        case "*":
            openField[y - 1][x - 1] = ".";
            printField(openField);
            break;
        default:
            System.out.println("There is a number here!");
            break;
    }
    case "free":
        switch (secretField[y-1][x-1]) {
            case "X":
                printField(secretField);
                System.out.println("You stepped on a mine and failed!");
                return;
            case "/":
                openField [y-1] [x-1] = "/";
                printField(openField);
                break;
            default:
                openField [y-1] [x-1] = secretField [y-1] [x-1];
                printField(openField);
                break;
        }
}
         checkWin(secretField,openField);
}
    }


    public static void main(String[] args) {
        System.out.println("How many mines do you want on the field?");
        int quantityOfMines = scanner.nextInt();

        System.out.println(" |123456789|");
        System.out.println("-|--------");
        for (int y = 0; y < fieldSize; y++) {
            System.out.print(y+1 + "|");
            for (int x = 0; x < fieldSize; x++) {
                System.out.print(".");
            }
            System.out.println("|");
        }
        System.out.println("-|-----------");
        System.out.println("Set/unset mines marks or claim a cell as free:");
        int firstX=scanner.nextInt();
        int firstY=scanner.nextInt();
        String operation = scanner.next();

        String[][] field = generateField(quantityOfMines,firstX,firstY);
//        checkField(field);
        gameLoop(field,firstX,firstY);
    }
    }

