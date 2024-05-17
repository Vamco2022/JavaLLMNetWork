package com.vamco.java.jar.network;

public class activeFunction {
    public static double sigmoid(double f){
        return 1 / (1 + (Math.pow(Math.E, -f)));
    }

    public static double tanH(double f){
        return ((Math.pow(Math.E,2 * f) - 1) / (Math.pow(Math.E,2 * f) + 1));
    }

    public static double ReLU(double f){
        return Math.max(0,f);
    }
}
