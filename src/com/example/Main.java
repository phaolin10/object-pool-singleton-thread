package com.example;

import com.example.pool.StadiumDemo;

public class Main {

    public static void main(String[] args) {
        StadiumDemo stadium = new StadiumDemo();
        stadium.setUp();
        stadium.tearDown();
        stadium.testStadium();
    }
}
