import com.vamco.java.jar.network.network;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //network to virtual not
        network and = new network(
                2,
                2,
                10,
                5,
                0.1
        );
        and.initNetwork();
        and.addGoal(
                new double[]{0,0},
                new double[]{0,0}
        );
        and.addGoal(
                new double[]{1,0},
                new double[]{0,1}
        );
        and.addGoal(
                new double[]{0,1},
                new double[]{0,1}
        );
        and.addGoal(
                new double[]{1,1},
                new double[]{1,0}
        );
        and.trainSTD(5000,2048);
        Scanner get = new Scanner(System.in);
        while (true){
            System.out.print("Test data:");
            double[] in = new double[2];
            in[0] = get.nextDouble();
            in[1] = get.nextDouble();
            double[] out = and.run(in);
            System.out.println("Return " + Arrays.toString(out));
        }
    }
}
