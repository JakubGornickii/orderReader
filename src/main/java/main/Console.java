package main;

import java.io.IOException;

public abstract class Console {

    public static void clear() {
        try {
            System.out.println(System.getProperty("os.name"));
       if (System.getProperty("os.name").contains("Windows")) {
           new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
       }else {
           System.out.print("\033[H\033[2J");
           }} catch (IOException | InterruptedException e) {
               e.printStackTrace();
           }
    }
      /*  System.out.println(new String(new char[50]).replace("\0", "\r\n"));*/
    }
