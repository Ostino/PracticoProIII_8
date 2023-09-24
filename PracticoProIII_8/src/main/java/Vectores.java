import java.util.Arrays;

public class Vectores {
    private double[] vector;

    public Vectores(double x, double y) {
        vector = new double[2];
        vector[0] = x;
        vector[1] = y;
    }

    public double[] getVector() {
        return vector;
    }

    public double getX() {
        return vector[0];
    }

    public double getY() {
        return vector[1];
    }

    @Override
    public String toString() {
        return "Vector2{" +
                "vector=" + Arrays.toString(vector) +
                '}';
    }
}