package com.vamco.java.jar.network;

public class networkMath {
    public static double getAVG(double[] v){
        double i = 0;
        for (double e : v){
            i += e;
        }
        i = i / v.length;
        return i;
    }

    public static double getSUM(double[] v){
        double i = 0;
        for (double e : v){
            i += e;
        }
        return i;
    }
}
