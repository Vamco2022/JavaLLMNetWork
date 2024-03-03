package com.vamco.java.jar.network.network;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class goal {
    public double[][] inputData;
    public double[][] outputData;
    public int length;

    public goal(int i,int o){
        this.inputData = new double[0][i];
        this.outputData = new double[0][o];
        this.length = -1;
    }

    public void addGoal(double[] input,double[] output){
        this.inputData = installToList(this.inputData,input);
        this.outputData = installToList(this.outputData,output);
        this.length++;
    }

    public double[][] getRandomData(){
        if (this.length == -1){
            return null;
        }
        double[] rI = this.inputData[new Random().nextInt(0,this.length)];
        double[] rO = this.outputData[new Random().nextInt(0,this.length)];
        return new double[][]{
                rI,
                rO
        };
    }

    private double[][] installToList(double[][] list,double[] data){
        list = Arrays.copyOf(list, list.length + 1);
        list[list.length - 1] = data;
        return list;
    }
}
