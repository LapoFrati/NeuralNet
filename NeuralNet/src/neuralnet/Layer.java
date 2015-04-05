package neuralnet;

public class Layer {
	ActivationFunction fun;
	double[][] weights;
	double[] lastFired; //the most recent outputs of this layer
	double[] actualInput;
	double[] expectedOutput;
	
	public Layer(int inputNeurons, int outputNeurons, ActivationFunction fun){
		lastFired = new double[outputNeurons];
		weights = new double[inputNeurons][outputNeurons];
		this.fun = fun;
	}
	
	public void forward(double[] input){
	}
}
