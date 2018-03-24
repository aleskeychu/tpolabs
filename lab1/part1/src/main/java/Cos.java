class Cos {


    private static double canonicalize(double val) {
        double delta = 2 * Math.PI;
        if (val < 0) {
            delta = -delta;
        }
        while (Math.abs(val) >= 2 * Math.PI) {
            val -= delta;
        }
        System.out.println(val);
        return val;
    }

    public static double calc(double x) {
        x = canonicalize(x);
        double sum = 1;
        double xn = 1;
        for (int i = 2; i < 20; i += 2 ) {
            xn = Math.pow(-1, i / 2) * Math.pow(x, i) / factorial(i);
            sum += xn;
            System.out.println("xn: " + xn + " sum: " + sum);
        }
        return sum;
    }

    private static long factorial(int n) {
        long base = 1;
        for (int i = 2; i <= n; i++) {
            base *= i;
        }
        return base;
    }

    public static void main(String[] args) {
        System.out.println(Cos.calc(-2*Math.PI + 0.001));
    }
}