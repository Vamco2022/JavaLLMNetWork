package com.vamco.java.jar.network;

public class activationFunction {
    public static double functionOutput(double a,activationFunctionType type){
        switch (type){
            case step -> { //阶跃函数
                return a > 0 ? 1 : 0;
            }
            case sigmoid -> { //Sigmoid函数
                double r = 1 + (Math.pow(Math.E,(-a)));
                return 1 / r;
            }
        }
        return 0;
    }
}
