package main;

public abstract class Console {

    public static void clear() {
        System.out.println(new String(new char[50]).replace("\0", "\r\n"));
    }
}