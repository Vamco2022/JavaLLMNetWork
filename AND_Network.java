import java.util.HashMap;
import java.util.Map;
import java.util.Random;

class network{
    //神经核数
    int[] net;
    //权重值(核的)
    double[][][] a;
    //偏置值
    double[][] b;
    //学习率
    double l;
    Random random = new Random();
    //目标
    int[][][] goal;
    //训练数据格式 [[[输入],[输出]]]
    int input;
    int output;
    String[] outputTag;
    //输出权重
    double [][] outputA;
    //输出偏置
    double[] outputB;

    long year = 0; //迭代次数

    public network(int[] net,double learn,int input,int output,String[] outputTag){
        this.net = net;
        this.l = learn;
        this.a = new double[net.length][][];
        this.b = new double[net.length][];
        this.outputA = new double[output][net[net.length - 1] * output];
        this.outputB = new double[output];
        this.input = input;
        this.output = output;
        this.outputTag = outputTag;
        this.a[0] = new double[net[0]][input];
        this.b[0] = new double[net[0]];
        for (int r = 1;r < net.length;r++){
            this.a[r] = new double[net[r]][net[r - 1]];
            this.b[r] = new double[net[r]];
        }
        this.year = 0;
    }

    public void init(){
        for (int x = 0;x < this.a.length;x++){
            for (int y = 0;y < this.a[x].length;y++){
                for (int z = 0;z < this.a[x][y].length;z++) {
                    this.a[x][y][z] = random.nextDouble(-2, 2);
                }
                this.b[x][y] = random.nextDouble(-3,3);
            }
        }

        //专门的处理输出
        for (int x = 0;x < this.outputA.length;x++){
            for (int y = 0;y < this.outputA[x].length;y++){
                outputA[x][y] = random.nextDouble(-2,2);
            }
            outputB[x] = random.nextDouble(-3,3);
        }
    }

    public void setGoal(int[][][] input){
        this.goal = input;
    }

    public void learn(int step){
        if (this.year == 0){
            //若为第一代
            //生成第一代母体
            network best = null;
            for (int i = 0;i < 999;i++){
                network frist = new network(
                        this.net,
                        this.l,
                        this.input,
                        this.output,
                        this.outputTag
                );
                frist.init();
                int choiceData = random.nextInt(0,this.goal.length);
                double[] out = frist.test(this.goal[choiceData]);
                int[] choiceGoal = this.goal[choiceData][1];
                if ()
            }
            this.year++;
        }
    }

//    public void learn(int step){
//        int[][] testData;
//        double[][] tmpNet;
//        double tQ = 0;
//        double tQo = 0;
//        long st;
//        long et;
//        double g = 1;
//        for (int i = 0;i < step;i++){
//            testData = this.goal[random.nextInt(0,this.goal.length)];
//            tmpNet = initTmpNet();
//            st = System.currentTimeMillis();
//            //先训练第一层，及于输入相连接的层
////            for (int node = 0;node < this.net[0];node++){
////                tmpNet[0][node] = mathInNode(
////                        testData[0],
////                        this.a[0][node],
////                        this.b[0][node]
////                );
////            }
////            //然后是中间的隐层
////            if (this.net.length > 2) {
////                for (int layer = 1; layer < tmpNet.length; layer++) {
////                    for (int node = 0; node < tmpNet[layer].length; node++) {
////                        tmpNet[layer][node] = mathInNode(
////                                tmpNet[layer - 1],
////                                this.a[layer][node],
////                                this.b[layer][node]
////                        );
////                    }
////                }
////            }
//////            //最后是输出
//////            for (int goalTag = 0;goalTag < this.output;goalTag++){
//////                outputNet[goalTag] = mathInNode(
//////                        tmpNet[tmpNet.length - 1],
//////                        this.outputA[goalTag],
//////                        this.outputB[goalTag]
//////                );
//////            }
////            //判断输出结果
////            //进行100次网络测试
////            int t = 0;
////            for (int ts = 0;ts < 100;ts++){
////                int[][] testStepData = this.goal[random.nextInt(0,this.goal.length)];
////                double[] out = test(testStepData);
////                int max = 0;
////                for (int r = 0;r < out.length;r++){
////                    if (out[max] < out[r]){
////                        max = r;
////                    }
////                }
////                if (max == testStepData[1][0]){
////                    t++;
////                }
////            }
////            if (i >= 1){
////                tQo = tQ;
////            }
////            tQ = (double) t / 100;
////            if (tQ < tQo){
////                this.way = -this.way;
////                g = g / 2;
////            }else{
////                g = g / 2;
////            }
////            //更新网络并修正，进入下一次学习
////            for (int x = 0;x<this.a.length;x++){
////                for (int y = 0;y < this.a[x].length;y++){
////                    for (int z = 0;z < this.a[x][y].length;z++){
////                        this.a[x][y][z] = getNewA(
////                                this.a[x][y][z],
////                                this.way,
////                                g,
////                                this.l
////                        );
////                    }
////                    this.b[x][y] = getNewB(
////                            this.b[x][y],
////                            this.way,
////                            g,
////                            this.l
////                    );
////                }
////            }
////            for (int x = 0;x<outputA.length;x++){
////                for (int y = 0;y < outputA[x].length;y++){
////                    this.outputA[x][y] = getNewA(
////                            this.outputA[x][y],
////                            this.way,
////                            g,
////                            this.l
////                    );
////                }
////                this.outputB[x] = getNewB(
////                        this.outputB[x],
////                        this.way,
////                        g,
////                        this.l
////                );
////            }
////            et = System.currentTimeMillis();
////            System.out.println("The Network learn: year" + i + ",use time:" + (et - st) + "ms," + "True:" + tQ * 100 + "%");
//        }
//    }

    public double[] test(int[][] tD){
        double[][] tmpNet = initTmpNet();
        double[] outputNet = new double[this.output];
        //输入处理
        for (int node = 0;node < this.net[0];node++){
            tmpNet[0][node] = mathInNode(
                    tD[0],
                    this.a[0][node],
                    this.b[0][node]
            );
        }
        //然后是中间的隐层
        if (this.net.length > 2) {
            for (int layer = 1; layer < tmpNet.length; layer++) {
                for (int node = 0; node < tmpNet[layer].length; node++) {
                    tmpNet[layer][node] = mathInNode(
                            tmpNet[layer - 1],
                            this.a[layer][node],
                            this.b[layer][node]
                    );
                }
            }
        }
        //最后是输出
        for (int goalTag = 0;goalTag < this.output;goalTag++){
            outputNet[goalTag] = mathInNode(
                    tmpNet[tmpNet.length - 1],
                    this.outputA[goalTag],
                    this.outputB[goalTag]
            );
        }
        //输出对输入的结果置信度
        return outputNet;
    }

    //训练初始化
    private double[][] initTmpNet(){
        double[][] q = new double[this.net.length][];
        for (int i = 0;i < this.net.length;i++){
            q[i] = new double[this.net[i]];
        }
        return q;
    }
    //单核计算
    private double mathInNode(double[] list,double[] A,double B){
        double i = 0;
        for (int a = 0;a < list.length;a++){
            i+= list[a] * A[a];
        }
        i += B;
        i = tanH(i);
        return i;
    }

    private double mathInNode(int[] list,double[] A,double B){
        double i = 0;
        for (int a = 0;a < list.length;a++){
            i+= list[a] * A[a];
        }
        i += B;
        i = tanH(i);
        return i;
    }

    //激活函数
    private double tanH(double r){
        return ((Math.pow(Math.E,r) - (Math.pow(Math.E,-r))
        / (Math.pow(Math.E,r)) + (Math.pow(Math.E,-r))));
    }

//    //微分
//    private double wf(double x){
//        return 0.5 * x;
//    }
//    //梯度下降算法
//    private double txm(double last,double learn){
//        return last - (learn * wf(last));
//    }

    //权重更新算法
//    public double getNewA(double old, int way,double g,double learn){
//        return old + (way * (learn * g));
//    }
//
//    //偏置更新算法
//    public double getNewB(double old,int way,double g,double learn){
//        return old + (way * (learn * g));
//    }
}

public class AND_Network {
    public static void main(String[] args) {
        network t = new network(
                new int[]{3,3,3},
                0.03,
                2,
                2,
                new String[]{"F","T"}
        );
        t.setGoal(
                new int[][][]{
                        {{0,0},{0}},
                        {{0,1},{0}},
                        {{1,0},{0}},
                        {{1,1},{1}}
                }
        );
        t.init();
        t.learn(5000);
    }
}
