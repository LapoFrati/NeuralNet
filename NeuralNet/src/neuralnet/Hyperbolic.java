package neuralnet;

public class Hyperbolic {
	public static double activation(double d) {
		return Math.tanh(d);
	}
	public static double derivative(double d) {
		return 1.0 / Math.pow(Math.cosh(d), 2.0);
	}
}
