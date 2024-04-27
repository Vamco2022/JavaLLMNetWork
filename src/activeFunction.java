public class activeFunction {
    public static double sigmoid(double f){
        return 1 / (1 + (Math.pow(Math.E, -f)));
    }

    public static double sigmoidDerivative(double f){
        return sigmoid(f) * (1 - sigmoid(f));
    }
}
