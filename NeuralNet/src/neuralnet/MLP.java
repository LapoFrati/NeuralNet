package neuralnet;

import java.io.IOException;

public class MLP{
	InputReader input;
	InputPair currentPair;
	public int 	epochs, 
				batch, 
				numberExamples, 
				hiddenSize,
				numberInputNeurons, 
				numberOutputNeurons, 
				numberHiddenNeurons;
	
	public double 	learningRate, 
					error;
	
	double[] 	biasedLowerInput, 
				biasedUpperInput, 
				actualInput,
				actualOutput,
				expectedOutput, 
				lowerDeltas, 
				upperDeltas, 
				lowerActivations, 
				upperActivations, 
				hiddenNeuronsValues;
	
	double[][] 	lowerWeights, 
				upperWeights, 
				dWlower, 
				dWupper, 
				temp;
	
	public MLP(){
		input = new InputReader();
	}
	
	public void buildNetwork(String optionsFile, String inputFile) throws IOException{
		input.readOptions(optionsFile);
		input.readInput(inputFile);
		epochs = input.epochs;
		batch = input.batchSize;
		learningRate = input.learningRate;
		numberExamples = input.numberExamples;
		numberInputNeurons = input.numberOfInputNeurons;
		numberOutputNeurons = input.numberOfOutputNeurons;
		hiddenSize = (numberInputNeurons + numberOutputNeurons)/2;
		lowerWeights = new double[numberInputNeurons+1][hiddenSize]; //add one row for the bias
		upperWeights = new double[hiddenSize+1][numberOutputNeurons]; //add one row for the bias
	}
	
	public void intializeWeights(double inputSize){
		
	}
	
	public double[] addBias(double[] vec){
		double[] biasedVec = new double[vec.length+1];
		for(int i = 0; i < vec.length; i++)
			biasedVec[i] = vec[i];
		biasedVec[vec.length] = 1.0;
		return biasedVec;
	}
	
	public void forward(){
		lowerActivations = MatrixOp.applyWeights(actualInput, lowerWeights);
		hiddenNeuronsValues = MatrixOp.applySigmoid(addBias(lowerActivations));
		upperActivations = MatrixOp.applyWeights(hiddenNeuronsValues, upperWeights);
		actualOutput = MatrixOp.applySigmoid(upperActivations);
	}
	
	public void calculateUpperDeltas(){
		for(int i = 0; i< expectedOutput.length; i++){
			upperDeltas[i]=(expectedOutput[i]-actualOutput[i])*Sigmoid.derivative(upperActivations[i]);
		}
	}
	
	public void calculateUpperDW(){
		temp = new double[upperWeights.length][upperWeights[0].length];
		
		for(int i = 0; i<upperWeights.length; i++){
			for(int j = 0; j<upperWeights[0].length; j++){
				temp[i][j] = -learningRate*hiddenNeuronsValues[i]*upperDeltas[j];
			}
		}
		
		dWupper = MatrixOp.sumMatrix(temp, dWupper);
	}
	
	public void calculateLowerDeltas(){
		for(int j = 0; j<upperWeights.length; j++){
			for(int i = 0; i<upperDeltas.length; i++){
				lowerDeltas[j] = upperDeltas[i]*dWupper[j][i];
			}
			lowerDeltas[j] = lowerDeltas[j]*Sigmoid.derivative(hiddenNeuronsValues[j]);
		}
	}
	
	public void calculateLowerDW(){
		temp = new double[lowerWeights.length][lowerWeights[0].length];
		
		for(int i = 0; i<lowerWeights.length; i++){
			for(int j = 0; j<lowerWeights[0].length; j++){
				temp[i][j] = -learningRate*actualInput[i]*lowerDeltas[j];
			}
		}
		
		dWupper = MatrixOp.sumMatrix(temp, dWupper);
	}
	
	public double backward(double[] expectedOutput){
		calculateUpperDeltas();
		calculateUpperDW();
		
		calculateLowerDeltas();
		calculateLowerDW();
		
		double error = 0.0;
		for(int i = 0; i<expectedOutput.length;i++){
			error += Math.pow(expectedOutput[i]-actualOutput[i],2.0)/2;
		}
		return error;
	}
	
	public void updateWeights(){
		
		MatrixOp.sumMatrix(lowerWeights, dWlower);
		MatrixOp.sumMatrix(upperWeights, dWupper);
		
		for(int i = 0; i<dWlower.length; i++){ //reset dW
			for(int j = 0; j<dWlower[0].length; j++)
				dWlower[i][j] = 0.0;
		}
		for(int i = 0; i<dWupper.length; i++){ //reset dW
			for(int j = 0; j<dWupper[0].length; j++)
				dWupper[i][j] = 0.0;
		}
	}
	
	public void train(){
		for(int i = 0; i<epochs; i++){
			error = 0;
			for(int j=0; j<numberExamples; j++){
				currentPair = input.getPair();
				actualInput = currentPair.getInput();
				expectedOutput = currentPair.getExpectedOutput();
				
				forward();
				error += backward(currentPair.getExpectedOutput());
				
				if(j%batch == 0){
					updateWeights();
				}
			}
		}
	}
}
