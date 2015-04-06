package neuralnet;

public class Sigmoid{
	public static double activation(double d) {
		return 1.0 / (1 + Math.exp(-1.0 * d));
	}
	public static double derivative(double d) {
		return d*(1.0-d);
	}
}
