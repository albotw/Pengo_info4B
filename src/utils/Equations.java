package utils;

public class Equations {

    public static int RandomizedInt(int a, int b) {
        if (a < b) {
            b++;
            return (int) (a + (b - a) * Math.random());
        } else {
            a++;
            return (int) (b + (a - b) * Math.random());
        }
    }

    public static double VectorialDistance(int x1, int x2, int y1, int y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2.0) + Math.pow(y2 - y1, 2.0));
    }
}
