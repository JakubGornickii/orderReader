package main;

import java.io.IOException;
import java.util.Scanner;

public abstract class Console {

    public static void clear() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        printLogo();
    }

    public static void pressEnter() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("\nWciśnij enter żeby kontynułować...");
        try {
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }if (System.getProperty("os.name").contains("Windows")) {
        scanner.nextLine();}
    }

    private static void printLogo() {
        System.out.println(" _____            _              ______                   _             \n" +
                "|  _  |          | |             | ___ \\                 | |            \n" +
                "| | | | _ __   __| |  ___  _ __  | |_/ /  ___   __ _   __| |  ___  _ __ \n" +
                "| | | || '__| / _` | / _ \\| '__| |    /  / _ \\ / _` | / _` | / _ \\| '__|\n" +
                "\\ \\_/ /| |   | (_| ||  __/| |    | |\\ \\ |  __/| (_| || (_| ||  __/| |   \n" +
                " \\___/ |_|    \\__,_| \\___||_|    \\_| \\_| \\___| \\__,_| \\__,_| \\___||_|   \n" +
                "                                                                        \n" +
                "                                                                        ");
    }
}


