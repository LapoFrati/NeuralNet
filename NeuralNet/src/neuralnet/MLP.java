package neuralnet;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

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
				tempdWlower,
				tempdWupper;
	Random rnd;
	long seed;
	
	public MLP(){
		input = new InputReader();
	}
	
	public void buildNetwork(String optionsFile, String inputFile) throws IOException{
		input.readOptions(optionsFile);
		input.readInput(inputFile);
		epochs = input.epochs;
		System.out.println("Epochs: "+epochs);
		batch = input.batchSize;
		System.out.println("Batch: "+batch);
		learningRate = input.learningRate;
		System.out.println("learningRate: "+ learningRate);
		numberExamples = input.numberExamples;
		System.out.println("NumberOfExamples: "+numberExamples);
		numberInputNeurons = input.numberOfInputNeurons;
		System.out.println("NumberOfInputNeurons: "+numberInputNeurons);
		numberOutputNeurons = input.numberOfOutputNeurons;
		System.out.println("NumberOfOutputNeurons: "+numberOutputNeurons);
		numberHiddenNeurons = input.numberOfHiddenNeurons;
		System.out.println("NumberOfHiddenNeurons: "+numberHiddenNeurons);
		seed = input.seed;
		System.out.println("Seed: "+seed);
		
		hiddenNeuronsValues = new double[numberHiddenNeurons];
		lowerActivations = new double[numberHiddenNeurons];
		upperActivations = new double[numberOutputNeurons];
		actualInput = new double[numberInputNeurons];
		
		lowerWeights = new double[numberInputNeurons+1][numberHiddenNeurons]; //add one row for the bias
		System.out.println("LowerWeights: "+lowerWeights.length+"x"+lowerWeights[0].length);
		dWlower= new double[numberInputNeurons+1][numberHiddenNeurons];
		tempdWlower = new double[numberInputNeurons+1][numberHiddenNeurons];
		
		upperWeights = new double[numberHiddenNeurons+1][numberOutputNeurons]; //add one row for the bias
		System.out.println("UpperWeights: "+upperWeights.length+"x"+upperWeights[0].length);
		dWupper = new double[numberHiddenNeurons+1][numberOutputNeurons];
		tempdWupper = new double[numberHiddenNeurons+1][numberOutputNeurons];
		
		rnd = new Random(seed);
		initializeWeights();
		
		upperDeltas = new double[numberOutputNeurons];
		//System.out.println("UpperDeltas: "+upperDeltas.length);
		lowerDeltas = new double[numberHiddenNeurons];
		//System.out.println("LowerDeltas: "+lowerDeltas.length);
	}
	
	public void initializeWeights(){
		System.out.println("weightRange: +-"+0.2/Math.sqrt(numberExamples));
		for(int i = 0; i<upperWeights.length; i++){
			for(int j = 0; j<upperWeights[0].length; j++){
				upperWeights[i][j] = (rnd.nextDouble()*(0.2)-0.1)/Math.sqrt(numberExamples);
			}
		}
				
		for(int i = 0; i<lowerWeights.length; i++){
			for(int j = 0; j<lowerWeights[0].length; j++){
				lowerWeights[i][j]=(rnd.nextDouble()*(0.2)-0.1)/Math.sqrt(numberExamples);
			}
		}
	}
	
	public double[] addBias(double[] vec){
		double[] biasedVec = new double[vec.length+1];
		for(int i = 0; i < vec.length; i++)
			biasedVec[i] = vec[i];
		biasedVec[vec.length] = 1.0;
		return biasedVec;
	}
	
	public void forward(){
		lowerActivations = MatrixOp.applyWeights(addBias(actualInput), lowerWeights);
		hiddenNeuronsValues = MatrixOp.applySigmoid(lowerActivations);
		upperActivations = MatrixOp.applyWeights(addBias(hiddenNeuronsValues), upperWeights);
		actualOutput = MatrixOp.applySigmoid(upperActivations);
	}
	
	public void calculateUpperDeltas(){
		for(int i = 0; i< expectedOutput.length; i++){
			System.out.println("Expected: "+expectedOutput[i]+" - Actual: "+actualOutput[i]);
			upperDeltas[i]=(expectedOutput[i]-actualOutput[i])*Sigmoid.derivative(upperActivations[i]);
		}
	}
	
	public void calculateUpperDW(){
		for(int i = 0; i<upperWeights.length; i++){
			for(int j = 0; j<upperWeights[0].length; j++){
				if(i == upperWeights.length-1)//last neuron is the bias with value 1.0
					tempdWupper[i][j] = -learningRate*upperDeltas[j];
				else
					tempdWupper[i][j] = -learningRate*hiddenNeuronsValues[i]*upperDeltas[j];
			}
		}
		
		dWupper = MatrixOp.sumMatrix(tempdWupper, dWupper);
	}
	
	public void calculateLowerDeltas(){
		for(int j = 0; j<(upperWeights.length-1); j++){//ignore the bias neuron
			for(int i = 0; i<upperDeltas.length; i++){
				lowerDeltas[j] = upperDeltas[i]*dWupper[j][i];
			}
			lowerDeltas[j] = lowerDeltas[j]*Sigmoid.derivative(hiddenNeuronsValues[j]);
		}
	}
	
	public void calculateLowerDW(){
		for(int i = 0; i<lowerWeights.length; i++){
			for(int j = 0; j<lowerWeights[0].length; j++){
				if(i==lowerWeights.length-1)//last neuron is the bias with value 1.0
					tempdWlower[i][j] = -learningRate*lowerDeltas[j];
				else	
					tempdWlower[i][j] = -learningRate*actualInput[i]*lowerDeltas[j];
			}
		}
		dWlower = MatrixOp.sumMatrix(tempdWlower, dWlower);
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
		
		lowerWeights = MatrixOp.sumMatrix(lowerWeights, dWlower);
		upperWeights = MatrixOp.sumMatrix(upperWeights, dWupper);
		
		for(int i = 0; i<dWlower.length; i++){ //reset dWlower
			for(int j = 0; j<dWlower[0].length; j++)
				dWlower[i][j] = 0.0;
		}
		for(int i = 0; i<dWupper.length; i++){ //reset dWupper
			for(int j = 0; j<dWupper[0].length; j++)
				dWupper[i][j] = 0.0;
		}
	}
	
	public void train(){
		double[] results = new double[epochs];
		for(int i = 0; i<epochs; i++){
			error = 0;
			for(int j=0; j<numberExamples; j++){
				currentPair = input.getPair();
				actualInput = currentPair.getInput();
				expectedOutput = currentPair.getExpectedOutput();
				
				forward();
				error += backward(currentPair.getExpectedOutput());
				if(j%batch == 0){
					//System.out.println(Arrays.deepToString(dWupper));
					//System.out.println(Arrays.deepToString(dWlower));
					updateWeights();
				}
			}
			results[i] = error;
			System.out.println("Epoch: "+i+" Error: "+error);
			if(i>0){
				if(results[i] > results[i-1])
					System.out.println("+"+(results[i]-results[i-1]));
				else
					System.out.println(results[i]-results[i-1]);
			}
		}
	}
}
