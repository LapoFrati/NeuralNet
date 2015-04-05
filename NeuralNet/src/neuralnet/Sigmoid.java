package neuralnet;

public class Sigmoid {
	public double activation(final double d) {
		return 1.0 / (1 + Math.exp(-1.0 * d));
	}
	public double derivative(double d) {
		return d*(1.0-d);
	}
}
