package com.vamco.java.jar.network.base;

import com.vamco.java.jar.network.activeFunction;

import java.util.Random;

public class node {
    public double nodeData;
    public double loss;
    public double[] inputW;
    public node(int input){
        this.inputW = new double[input];
        initNode();
    }

    private void initNode(){
        Random random = new Random();
        for (int i = 0;i < this.inputW.length;i++){
            this.inputW[i] = random.nextDouble(-5,5);
        }
    }

    public void solveInput(double[] input){
        this.nodeData = 0;
        for (int i = 0;i < this.inputW.length;i++){
            this.nodeData += this.inputW[i] * input[i];
        }
        this.nodeData = activeFunction.sigmoid( this.nodeData);
    }
}
