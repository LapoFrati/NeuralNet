package neuralnet;

import java.io.IOException;

public class MultiLayerPerceptron{
	InputReader input;
	Layer lowerLayer, upperLayer;
	int inputSize;
	InputPair currentPair;
	public final static int MAX_EPOCH = 500;
	public final static int BATCH = 10;
	public final static double LEARNING_RATE = 0.0001;
	double error;
	
	public MultiLayerPerceptron(){
		input = new InputReader();
	}
	
	public void buildNetwork(String options, String inputFile) throws IOException{
		input.readFiles(options, inputFile);
		inputSize = input.getSize();
		int hiddenSize = (input.numberOfInputNeurons + input.numberOfOutputNeurons)/2;
		lowerLayer = new Layer(input.numberOfInputNeurons, hiddenSize, LEARNING_RATE);
		upperLayer = new Layer(hiddenSize, input.numberOfOutputNeurons, LEARNING_RATE);
	}
	
	public void train(){
		for(int i = 0; i<MAX_EPOCH; i++){
			error = 0;
			for(int j=0; j<inputSize; j++){
				currentPair = input.getPair();
				
				lowerLayer.forward(currentPair.getInput());
				upperLayer.forward(lowerLayer.output);
				
				error += upperLayer.backward(currentPair.getExpectedOutput());
				error += lowerLayer.backward(upperLayer.deltas);
				
				if(j%BATCH == 0){
					lowerLayer.updateWeights();
					upperLayer.updateWeights();
				}
			}
		}
	}
}
