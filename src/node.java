import java.util.Random;

public class node {
    double[] inputW;
    double[] outputW;
    double[] outputValue;
    double p;
    public node(int input,int output){
        this.inputW = new double[input];
        this.outputW = new double[output];
        this.outputValue = new double[output];
        this.p = 0;
        initNode();
    }

    public void initNode(){
        Random random = new Random();
        for (int i = 0;i < this.inputW.length;i++){
            this.inputW[i] = random.nextDouble(-5,5);
        }
        for (int i = 0;i < this.outputW.length;i++){
            this.outputW[i] = random.nextDouble(-5,5);
        }
        this.p = random.nextDouble(-5,5);
    }

    public double[] getNodeReturn(double[] input){
        double inputSum = 0;
        for (int i = 0;i < input.length;i++){
            inputSum += input[i] * this.inputW[i];
        }
        double functionAfter = activeFunction.sigmoid(inputSum + this.p);
        for (int i = 0;i < this.outputValue.length;i++){
            this.outputValue[i] = functionAfter * this.outputW[i];
        }
        return this.outputValue;
    }
}
