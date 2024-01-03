package org.example;

public class Main {
    public static void main(String[] args) {
        String id = "@123";
        Long aLong = Long.valueOf(id.substring(1, id.length()));
        System.out.println(aLong);
    }
}
