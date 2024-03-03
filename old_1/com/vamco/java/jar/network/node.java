package com.vamco.java.jar.network;

import java.util.Random;

public class node {
    public double[] input_a; //输入权重
    public double w; //偏置
    public double[] output_a; //输出权重
    public activationFunctionType functionType;

    public node(int inputLayers,int outputLayer,activationFunctionType functionType) {
        this.input_a = new double[inputLayers];
        this.output_a = new double[outputLayer];
        this.w = new Random().nextDouble(-5,5);
        this.functionType = functionType;
    }

  public double[] slove(double[] inputList) {
        double[] out = new double[output_a.length];
        double input = 0;
        for (int a = 0;a < inputList.length;a++){
            input += inputList[a] * this.input_a[a];
        }
        input += this.w;
        for (int a = 0;a < out.length;a++){
            out[a] = input * this.output_a[a];
            out[a] = activationFunction.functionOutput(out[a],this.functionType);
        }
        return out;
  }
}
