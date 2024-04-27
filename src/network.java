import java.util.ArrayList;
import java.util.Random;

public class network {
    public node[][] layers;
    public double[] input;
    public double[] output;
    public ArrayList<double[]> goalInput;
    public ArrayList<double[]> goalOutput;
    public double learn;

    public network(int input, int output, int layers,int perLayer,double learn){
        this.layers = new node[layers][perLayer];
        this.input = new double[input];
        this.output = new double[output];
        this.goalInput = new ArrayList<>();
        this.goalOutput = new ArrayList<>();
        this.learn = learn;
    }

    public void initNetwork(){
        //input - layer network
        for (int i = 0;i < this.layers[0].length;i++){
            this.layers[0][i] = new node(this.input.length, this.layers[1].length);
        }
        //layer - layer network
        for (int x = 1;x < this.layers.length - 1;x++){
            for (int y = 0;y < this.layers[x].length;y++){
                this.layers[x][y] = new node(this.layers[x].length, this.layers[x + 1].length);
            }
        }
        //layer - output network
        for (int i = 0;i < this.layers[this.layers.length - 1].length;i++){
            this.layers[this.layers.length - 1][i] = new node(this.layers[this.layers.length - 2].length, 1);
        }
    }

    public void addTrainGoal(double[] input, double[] output){
        this.goalInput.add(input);
        this.goalOutput.add(output);
    }

    public double[] testNetwork(double[] input){
        // input - layer
        for (int i = 0; i < this.layers[0].length;i++){
            this.layers[0][i].getNodeReturn(input);
        }
        //layer - layer
        for (int x = 1; x < this.layers.length - 1;x++){
            for (int y = 0;y < this.layers[x].length;y++){
                double[] nodeInput = new double[this.layers[x - 1].length];
                for (int i = 0;i < this.layers[x - 1].length;i++){
                    nodeInput[i] = this.layers[x - 1][i].outputValue[i * y];
                }
                this.layers[x][y].getNodeReturn(nodeInput);
            }
        }
        //layer - output
        for (int i = 0;i < this.layers[this.layers.length - 1].length;i++){
            double[] nodeInput = new double[this.layers[this.layers.length - 1].length];
            for (int a = 0;a < this.layers[this.layers.length - 2].length;a++){
                nodeInput[a] = this.layers[this.layers.length - 2][a].outputValue[a * i];
            }
            this.layers[this.layers.length - 1][i].getNodeReturn(nodeInput);
        }
//        //get output return list
//        double[] output = new double[this.output.length];
//        for (int i = 0;i < this.output.length;i++){
//            double outputSum = 0;
//            for (int a = 0; a < this.layers[this.layers.length - 1].length;a++){
//                outputSum += this.layers[this.layers.length - 1][a].outputValue[i];
//            }
//            output[i] = outputSum;
//        }
        double[] output = new double[this.output.length];
        for (int i = 0;i < this.output.length;i++){
            output[i] = this.layers[this.layers.length - 1][i].outputValue[0];
        }
        return output;
    }

    public double[][] train(){ // return the error of train in per step
        int i = new Random().nextInt(0, this.goalInput.size()); // get random data from goal list
        double[] randomInput = this.goalInput.get(i);
        double[] randomOutput = this.goalOutput.get(i);
        double[] testReturn = testNetwork(randomInput);
        double[] testError = new double[testReturn.length];
        for (int a = 0;a < testError.length;a++){
            testError[a] = randomOutput[a] - testReturn[a];
        }
        bfTrain(testError,randomInput);
        return new double[][]{randomOutput,testReturn};
    }

    private void bfTrain(double[] returnError,double[] testInput){
        double[][] errorPerNode = new double[this.layers.length][];
        for (int i = 0;i < this.layers.length;i++){
            errorPerNode[i] = new double[this.layers[i].length];
        }
        //output - layer error
        for (int i = 0; i < this.layers[this.layers.length - 1].length;i++){
            double errorSum = 0;
            for (int a = 0;a < returnError.length;a++){
                errorSum += returnError[a] * this.layers[this.layers.length - 2][i].outputW[a];
            }
            errorPerNode[this.layers.length - 1][i] = errorSum;
        }
        //layer - layer error (background)
        for (int x = this.layers.length - 2;x > 0;x--){
            for (int y = 0;y < this.layers[x].length;y++){
                double errorSum = 0;
                for (int a = 0; a < this.layers[x - 1].length;a++){
                    errorSum += errorPerNode[x + 1][y] * this.layers[x][y].outputW[a];
                }
                errorPerNode[x][y] = errorSum;
            }
        }
        //step 2 : upgrade the new weight of per node (front ground)
        //input - layer
        for (int i = 0;i < this.layers[0].length;i++){
            double error = errorPerNode[0][i];
            for (int a = 0;a < this.input.length;a++){
                this.layers[0][i].inputW[a] += (this.learn * error * activeFunction.sigmoidDerivative(testInput[a]));
            }
        }
        //layer - layer
        for (int x = 1;x < this.layers.length;x++){
            for (int y = 0;y <this.layers[x].length;y++){
                double error = errorPerNode[x][y];
                for (int a = 0;a < this.layers[x - 1].length;a++){
                    double output = this.layers[x - 1][y].outputValue[a];
                    this.layers[x][y].inputW[a] += (this.learn * error * activeFunction.sigmoidDerivative(output));
                }
            }
        }
    }
}
