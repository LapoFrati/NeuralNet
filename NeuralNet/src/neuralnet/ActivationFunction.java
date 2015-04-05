package neuralnet;

public interface ActivationFunction {
	public double activation(double d);
	public double derivative(double d);
}
