package neuralnet;

public class Layer {
	double[][] weights,dW;
	double[] output, //the most recent outputs of this layer
			 activations,
			 deltas;
	double learningRate;
	
	public Layer(int inputNeurons, int outputNeurons, double learningRate){
		output = new double[outputNeurons];
		weights = new double[inputNeurons][outputNeurons];
		dW = new double[inputNeurons][outputNeurons];
		this.learningRate = learningRate;
	}
	
	public void forward(double[] input){
		output = MatrixOp.applyWeights(input, weights);
		activations = MatrixOp.applySigmoid(output);
	}
	
	public double backward(double[] expectedOutput){
		return 0;
	}
	
	public void updateWeights(){
		MatrixOp.sumMatrix(weights, MatrixOp.multiplyByDouble(dW, learningRate));
		for(int i = 0; i<dW.length; i++){ //reset dW
			for(int j = 0; j<dW[0].length; j++)
				dW[i][j] = 0.0;
		}
	}
}
