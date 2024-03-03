package com.vamco.java.jar.network.network;

import com.vamco.java.jar.network.activationFunction;
import com.vamco.java.jar.network.activationFunctionType;
import com.vamco.java.jar.network.node;

public class network {
    public node[][] nodes;
    public double learn;
    public goal goal;
    private final int input;
    private final int output;
    private final double[][][] testNodeData;
    private final int ELN;
    private final activationFunctionType type;
    private final int layer;
    public network(int input,int layer,int everyLayerNodes,int output,double learn,activationFunctionType type){
        this.goal = new goal(input,output);
        this.learn = learn;
        this.input = input;
        this.output = output;
        this.ELN = everyLayerNodes;
        this.type = type;
        this.layer = layer;
        this.testNodeData = new double[layer][everyLayerNodes][0];
        this.nodes = new node[layer][everyLayerNodes];
        for (int x = 0;x < layer;x++){
            for (int y = 0;y < everyLayerNodes;y++){
                if (x == 0){
                    this.nodes[x][y] = new node(input,everyLayerNodes,type);
                }else if (x == layer--){
                    this.nodes[x][y] = new node(everyLayerNodes,output,type);
                }else {
                    this.nodes[x][y] = new node(everyLayerNodes,everyLayerNodes,type);
                }
            }
        }
    }

    public void addGoal(double[] input,double[] output){
        this.goal.addGoal(input,output);
    }

    public void addGoal(double[][] input,double[][] output){
        for (int a = 0;a < input.length;a++){
            this.goal.addGoal(input[a],output[a]);
        }
    }

    public void train(int step){
        int l = 0;
        double[][] testData;
        while (l < step){
            testData = this.goal.getRandomData();
            double[] thisOutput = test(testData[0]);
            int trueData = (int) testData[1][0];
            double[][] miss = new double[this.layer][this.ELN];
            double returnMiss = trueData - thisOutput[trueData];
            //计算损失
            //输出层
            for (int a = this.nodes.length - 1;a < this.nodes.length;a++){
                for (int b = 0;b < this.output;b++){
                    miss[a][b] = this.nodes[a][b].output_a[trueData] * returnMiss;
                }
            }
            //隐层
            
            l++;
        }
    }

    public double[] test(double[] input){
        double[][][] thisTest = this.testNodeData.clone();
        double[] output = new double[this.output];
        //输入层
        for (int a = 0;a < this.input;a++){
            thisTest[0][a] = this.nodes[0][a].slove(input);
        }
        //隐层
        for (int a = 1; a < this.nodes.length - 1;a++){
            for (int b = 0;b < this.nodes[a].length;b++){
                double[] inputHere = new double[this.ELN];
                for (int x = 0;x < thisTest[a - 1].length;x++){
                    inputHere[x] = thisTest[a - 1][x][b];
                }
                thisTest[a][b] = this.nodes[a][b].slove(inputHere);
            }
        }
        //输出层
        for (int a = this.nodes.length - 2;a < this.nodes.length;a++){
            for (int b = 0;b < this.nodes[a].length;b++){
                double[] inputHere = new double[this.ELN];
                for (int x = 0;x < thisTest[a - 1].length;x++){
                    inputHere[x] = thisTest[a - 1][x][b];
                }
                thisTest[a][b] = this.nodes[a][b].slove(inputHere);
            }
        }
        //输出处理
        for (int a = thisTest.length - 1;a < thisTest.length;a++){
            for (int b = 0;b < this.nodes[a].length;b++){
                for (int i = 0;i < thisTest[a][b].length;i++){
                    output[i] += thisTest[a][b][i];
                }
            }
        }
        //输出项激活运算
        for (int a = 0;a < output.length;a++){
            output[a] = activationFunction.functionOutput(output[a],this.type);
        }
        return output;
    }
}
