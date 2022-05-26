package com.kt.monitoring;

public class Main {
    public static void main(String args[]) {
        String nodename="192.168.0.211:/mnt/nfs";
        String ipAddr = nodename.substring(0, nodename.indexOf(":"));
        System.out.println(ipAddr);
//        String a = "/dev/sda2      123327040 27966620  89052736  24% /";
//        String[] arr=a.split(" ");
//
//        System.out.println( arr.length );
//
//        for (int i = 0; i < arr.length ; i++) {
//            if(arr[i].length() >0 ) {
//                System.out.println(arr[i].trim());
//            }
//        }
    }

}
