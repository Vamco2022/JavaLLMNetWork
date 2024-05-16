package com.vamco.java.jar.network;

public class loss {
    public static double getOutputLoss(double output,  double trueOutput){
        return (0.5) * Math.pow((output - trueOutput),2);
    }
}
