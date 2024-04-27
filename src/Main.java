import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        //try to let network virtual as the "and" system
        network n = new network(2,2,2,2,0.3);
        //init network
        n.addTrainGoal(new double[]{0,0},new double[]{0,1});
        n.addTrainGoal(new double[]{0,1},new double[]{0,1});
        n.addTrainGoal(new double[]{1,0},new double[]{0,1});
        n.addTrainGoal(new double[]{1,1},new double[]{1,0});
        //add network goal - virtual and system (o[0] = 1,o[1] = 0)
        n.initNetwork();
        //initNetwork
        for (int step = 0;step < 10000;step++){
            //train network by 100 step
            double[][] w = n.train();
            System.out.println("Step" + step + ": return " + Arrays.toString(w[1]) + "for true return " + Arrays.toString(w[0]));
        }
    }
}
