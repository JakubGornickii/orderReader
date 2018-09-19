package main;

import java.io.IOException;

public abstract class Console {

    public static void clear() {
        try {
       if (System.getProperty("os.name").contains("Windows")) {
           new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
       }else {

               Runtime.getRuntime().exec("clear");
           }} catch (IOException | InterruptedException e) {
               e.printStackTrace();
           }
    }
      /*  System.out.println(new String(new char[50]).replace("\0", "\r\n"));*/
    }
