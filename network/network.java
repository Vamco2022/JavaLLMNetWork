package com.vamco.java.jar.network;

import com.vamco.java.jar.network.base.node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static com.vamco.java.jar.network.loss.getOutputLoss;

public class network {
    public node[][] layers;
    public double learn;
    public ArrayList<double[]> inputGoalList;
    public ArrayList<double[]> outputGoalList;
    private int npl;
    private int ipn;
    private int opn;
    private final Random random;

    public network(int input, int output, int layer, int nodePerLayer, double learn){
        this.layers = new node[layer + input + output][];
        this.learn = learn;
        this.inputGoalList = new ArrayList<>();
        this.outputGoalList = new ArrayList<>();
        this.npl = nodePerLayer;
        this.ipn = input;
        this.opn = output;
        this.random = new Random();
    }

    public void initNetwork(){
        //inputLayer
        this.layers[0] = new node[this.ipn];
        for (int i = 0;i < this.layers[0].length;i++){
            this.layers[0][i] = new node(0);
        }
        //input to hind
        this.layers[1] = new node[this.npl];
        for (int i = 0;i < this.layers[1].length;i++){
            this.layers[1][i] = new node(this.ipn);
        }
        //node in layer
        for (int x = 2;x < this.layers.length - 1;x++){
            this.layers[x] = new node[this.npl];
            for (int y = 0;y < this.layers[x].length;y++){
                this.layers[x][y] = new node(this.npl);
            }
        }
        //outputLayer
        this.layers[this.layers.length - 1] = new node[this.opn];
        for (int i = 0;i < this.opn;i++){
            this.layers[this.layers.length - 1][i] = new node(this.npl);
        }
    }

    public void addGoal(double[] input, double[] output){
        this.inputGoalList.add(input);
        this.outputGoalList.add(output);
    }

    public double[] run(double[] input){
        //set input
        for (int i = 0;i < input.length;i++){
            this.layers[0][i].nodeData = input[i];
        }
        //input to hind
        for (int i = 0;i < this.layers[1].length;i++){
            this.layers[1][i].solveInput(getNodeInputList(this.layers[0]));
        }
        //hind to hind
        for (int x = 2;x < this.layers.length - 1;x++){
            for (int y = 0;y < this.layers[x].length;y++){
                this.layers[x][y].solveInput(getNodeInputList(this.layers[x - 1]));
            }
        }
        //hind to output
        for (int i = 0;i < this.layers[this.layers.length - 1].length;i++){
            this.layers[this.layers.length - 1][i].solveInput(getNodeInputList(this.layers[this.layers.length - 2]));
        }
        double[] out = new double[this.opn];
        for (int i = 0;i < this.layers[this.layers.length - 1].length;i++){
            out[i] = this.layers[this.layers.length - 1][i].nodeData;
        }
        return out;
    }

    public void train(int step){
        for (int i = 0;i < step;i++){
            long st = System.currentTimeMillis();
            System.out.println("Train: step " + i + ":");
            int randomDataID = random.nextInt(0,this.inputGoalList.size());
            double[] testInput = this.inputGoalList.get(randomDataID);
            double[] testTrueOutput = this.outputGoalList.get(randomDataID);
            double[] testOutput = run(testInput);
            double[] loss = getLoss(testTrueOutput, testOutput);
            double lossAll = networkMath.getAVG(loss);
            bf(testTrueOutput, testOutput);
            long et = System.currentTimeMillis();
            long speed;
            if (et == st) speed = 100000;
            else speed = (1000 / (et - st));
            System.out.println("testData: " + Arrays.toString(testInput) + ", trueOutput: " + Arrays.toString(testTrueOutput) + ", testOutput: " + Arrays.toString(testOutput));
            System.out.println("Loss: " + lossAll + ", use time: " + (et - st) + "ms, speed: " + speed + "t/s");
            System.out.println("-----------------------------------------------");
            System.out.println();
        }
    }

    public void trainSTA(int step){
        double lossAll = 0;
        long st,et,speed;
        for (int i = 0;i < step;i++){
            lossAll = 0;
            System.out.println("Train: step " + i + ":");
            st = System.currentTimeMillis();
            for (int a = 0;a < this.inputGoalList.size();a++) {
                double[] testInput = this.inputGoalList.get(a);
                double[] testTrueOutput = this.outputGoalList.get(a);
                double[] testOutput = run(testInput);
                double[] loss = getLoss(testTrueOutput, testOutput);
                lossAll += networkMath.getAVG(loss);
                bf(testTrueOutput, testOutput);
            }
            et = System.currentTimeMillis();
            if (et == st) speed = 100000;
            else speed = (1000 / (et - st));
            lossAll = lossAll / this.inputGoalList.size();
            System.out.println("Loss: " + lossAll + ", use time: " + (et - st) + "ms, speed: " + speed + "t/s");
            System.out.println("-----------------------------------------------");
            System.out.println();
        }
    }

    public double[] getLoss(double[] trueOutput, double[] output){
        //step 1, get loss of the all nodes
        //output loss
        //since some code need changes the all code, so we skip it
        //write it into step 2
        for (int i = 0;i < this.layers[this.layers.length - 1].length;i++){
            this.layers[this.layers.length - 1][i].loss = getOutputLoss(output[i],trueOutput[i]);
        }
        double[] out = new double[this.opn];
        for (int i = 0;i < out.length;i++) {
            out[i] = this.layers[this.layers.length - 1][i].loss;
        }
        return out;
    }

    public void bf(double[] trueOutput, double[] output){
        //step 2, update the w
        //output w
        for (int i = 0;i < this.layers[this.layers.length - 1].length;i++){
            for (int wi = 0;wi < this.layers[this.layers.length - 1][i].inputW.length;wi++){
                double newW = this.layers[this.layers.length - 1][i].inputW[wi];
                newW -= this.learn * (output[i] - trueOutput[i]) * output[i] * (1 - output[i]) * this.layers[this.layers.length - 2][wi].nodeData;
                this.layers[this.layers.length - 1][i].inputW[wi] = newW;
            }
        }
        //hind w
        for (int x = this.layers.length - 2;x > 0;x--){
            for (int y = 0;y < this.layers[x].length;y++){
                for (int wi = 0;wi < this.layers[x][y].inputW.length;wi++){
                    double newW = this.layers[x][y].inputW[wi];
                    double addAllLoss = 0;
                    for (int outL = 0; outL < this.opn;outL++){
                        addAllLoss += (output[outL] - trueOutput[outL]) * output[outL] * (1 - output[outL]) * this.layers[x - 1][wi].nodeData;
                    }
                    newW -= this.learn * addAllLoss;
                }
            }
        }
    }

    private double[] getNodeInputList(node[] i){
        double[] out = new double[i.length];
        for (int a = 0;a < i.length;a++){
            out[a] = i[a].nodeData;
        }
        return out;
    }
}
