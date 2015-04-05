package neuralnet;

public class Layer {
	ActivationFunction fun;
	Matrix weights;
	double[] lastFired; //the most recent outputs of this layer
	
	public Layer(int NNeurons, ActivationFunction fun){
		lastFired = new double[NNeurons];
		this.fun = fun;
	}
}
