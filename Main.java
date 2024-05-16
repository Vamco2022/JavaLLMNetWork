import com.vamco.java.jar.network.network;

public class Main {
    public static void main(String[] args) {
        //network to virtual not
        network not = new network(
                1,
                1,
                2,
                2,
                0.3
        );
        not.initNetwork();
        not.addGoal(
                new double[]{0},
                new double[]{1}
        );
        not.addGoal(
                new double[]{0},
                new double[]{1}
        );
        not.addGoal(
                new double[]{0},
                new double[]{1}
        );
        not.addGoal(
                new double[]{1},
                new double[]{0}
        );
        not.addGoal(
                new double[]{1},
                new double[]{0}
        );
        not.addGoal(
                new double[]{1},
                new double[]{0}
        );
        not.trainSTA(100);
    }
}
