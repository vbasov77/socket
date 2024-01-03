package org.example;

import java.util.Arrays;
import java.util.Objects;

public class ClientService {
    public Long getIdClient(String str) {
        String arr[] = str.split(" ", 2);
        String c = String.valueOf(arr[0].charAt(0));
        Arrays.stream(arr).forEach(System.out::println);
        System.out.println(arr[0]);
        if (c.equals("@")){
            return Long.valueOf(arr[0].substring( 1, arr[0].length()));
        }
        return -1L;
    }
}
