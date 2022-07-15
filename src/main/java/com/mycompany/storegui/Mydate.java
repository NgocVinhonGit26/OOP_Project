package com.mycompany.storegui;

import java.util.StringTokenizer;

public class Mydate {
	private int day;
    private int month;
    private int year;

    public Mydate(String Date) {
        StringTokenizer myTokens = new StringTokenizer(Date, "/");
        this.day = Integer.parseInt(myTokens.nextToken());
        this.month = Integer.parseInt(myTokens.nextToken());
        this.year = Integer.parseInt(myTokens.nextToken());
    }

    public void printDate() {
        System.out.println(day + "/" + month + "/" + year);
    }
}
