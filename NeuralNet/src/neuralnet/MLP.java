package neuralnet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class MLP{
	InputReader input;
	InputPair currentPair;
	public int 	epochs, 
				batch, 
				numberTrainExamples,
				numberTestExamples,
				hiddenSize,
				numberInputNeurons, 
				numberOutputNeurons, 
				numberHiddenNeurons;
	
	public double 	learningRate, 
					error,
					momentum,
					targetError;
	
	double[] 	actualInput,
				actualOutput,
				expectedOutput, 
				lowerDeltas, 
				upperDeltas, 
				lowerActivations, 
				upperActivations, 
				lowerOutput;
	
	double[][] 	lowerWeights, 
				upperWeights, 
				dWlower, 
				dWupper;
	Random rnd;
	long seed;
	boolean useSigmoid,
			hasTest = false,
			multiClass = false;
	String outputFolder;
	
	public MLP(boolean useSigmoid, boolean multiClass) {
		input = new InputReader();
		this.useSigmoid = useSigmoid;
		this.multiClass = multiClass;
	}
	
	public void buildNetwork(String optionsFile, String inputTrainFile, String inputTestFile) throws IOException{
		buildNetwork(optionsFile, inputTrainFile);
		input.readTestInput(inputTestFile);
		numberTestExamples = input.numberTestExamples;
		hasTest = true;
		
	}
	
	public void buildNetwork(String optionsFile, String inputFile) throws IOException{
		input.readOptions(optionsFile);
		input.readTrainInput(inputFile);
		
		outputFolder = optionsFile.split("/")[0];
		FileOutputStream file = new FileOutputStream(outputFolder+"/OutputLog.txt");
	    TeePrintStream tee = new TeePrintStream(file, System.out);
	    System.setOut(tee);
		
		numberInputNeurons = input.numberOfInputNeurons;
		System.out.println("NumberOfInputNeurons: "+numberInputNeurons);
		numberOutputNeurons = input.numberOfOutputNeurons;
		System.out.println("NumberOfOutputNeurons: "+numberOutputNeurons);
		numberHiddenNeurons = input.numberOfHiddenNeurons;
		System.out.println("NumberOfHiddenNeurons: "+numberHiddenNeurons);
		batch = input.batchSize;
		System.out.println("Batch: "+batch);
		seed = input.seed;
		System.out.println("Seed: "+seed);
		learningRate = input.learningRate;
		System.out.println("learningRate: "+ learningRate);
		momentum = input.momentum;
		System.out.println("Momentum: "+ momentum);
		targetError = input.targetError;
		System.out.println("Target Error: "+targetError);
		epochs = input.epochs;
		System.out.println("Epochs: "+epochs);
		
		numberTrainExamples = input.numberTrainExamples;
		System.out.println("NumberOfExamples: "+numberTrainExamples);

		lowerWeights = new double[numberInputNeurons+1][numberHiddenNeurons]; //add one row for the bias
		System.out.println("LowerWeights: "+lowerWeights.length+"x"+lowerWeights[0].length);
		dWlower= new double[numberInputNeurons+1][numberHiddenNeurons];
		
		upperWeights = new double[numberHiddenNeurons+1][numberOutputNeurons]; //add one row for the bias
		System.out.println("UpperWeights: "+upperWeights.length+"x"+upperWeights[0].length);
		dWupper = new double[numberHiddenNeurons+1][numberOutputNeurons];
		
		rnd = new Random(seed);
		initializeWeights();
		
		upperDeltas = new double[numberOutputNeurons];
		//System.out.println("UpperDeltas: "+upperDeltas.length);
		lowerDeltas = new double[numberHiddenNeurons];
		//System.out.println("LowerDeltas: "+lowerDeltas.length);
		
	}
	
	public void initializeWeights(){
		System.out.println("weightRange: +-"+0.2/Math.sqrt(numberTrainExamples));
		for(int i = 0; i<upperWeights.length; i++){
			for(int j = 0; j<upperWeights[0].length; j++){
				upperWeights[i][j] = (rnd.nextDouble()*(0.2)-0.1)/Math.sqrt(numberTrainExamples);
			}
		}
		
		for(int i = 0; i<lowerWeights.length; i++){
			for(int j = 0; j<lowerWeights[0].length; j++){
				lowerWeights[i][j] = (rnd.nextDouble()*(0.2)-0.1)/Math.sqrt(numberTrainExamples);
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
		lowerActivations = MatrixOp.applyWeights(actualInput, lowerWeights);
		
		if(useSigmoid)
			lowerOutput = addBias(MatrixOp.applySigmoid(lowerActivations));
		else
			lowerOutput = addBias(MatrixOp.applyTanh(lowerActivations));
		
		upperActivations = MatrixOp.applyWeights(lowerOutput, upperWeights);
		
		if(useSigmoid)
			actualOutput = MatrixOp.applySigmoid(upperActivations);
		else
			actualOutput = MatrixOp.applyTanh(upperActivations);
	}
	
	public void calculateUpperDeltas(){
		for(int i = 0; i< expectedOutput.length; i++){
			//System.out.println("Expected: "+expectedOutput[i]+" - Actual: "+actualOutput[i]);
			if(useSigmoid)
				upperDeltas[i]=(expectedOutput[i]-actualOutput[i])*Sigmoid.derivative(upperActivations[i]);
			else
				upperDeltas[i]=(expectedOutput[i]-actualOutput[i])*Hyperbolic.derivative(upperActivations[i]);
		}
	}
	
	public void calculateUpperDW(){
		for(int i = 0; i<upperWeights.length; i++){
			for(int j = 0; j<upperWeights[0].length; j++){
					dWupper[i][j] += learningRate*upperDeltas[j]*lowerOutput[i];
			}
		}
	}
	
	public void calculateLowerDeltas(){
		for(int i = 0; i<lowerDeltas.length; i++){
			lowerDeltas[i] = 0;
			for(int j = 0; j<upperWeights[0].length; j++){
				lowerDeltas[i] += upperDeltas[j]*upperWeights[i][j];
			}
			if(useSigmoid)
				lowerDeltas[i] = lowerDeltas[i]*Sigmoid.derivative(lowerActivations[i]);
			else
				lowerDeltas[i] = lowerDeltas[i]*Hyperbolic.derivative(lowerActivations[i]);
		}
	}
	
	public void calculateLowerDW(){
		for(int i = 0; i<lowerWeights.length; i++){
			for(int j = 0; j<lowerWeights[0].length; j++){	
					dWlower[i][j] += learningRate*lowerDeltas[j]*actualInput[i];
			}
		}
	}
	
	public double backward(double[] expectedOutput){
		calculateUpperDeltas();
		calculateUpperDW();
		
		calculateLowerDeltas();
		calculateLowerDW();
		
		double error = 0.0;
		
		if(multiClass){ // hard-max
			double maxVal = 0.0;
			int maxPos = 0;
			for(int i = 0; i<actualOutput.length;i++){
				if(actualOutput[i] > maxVal){
					maxVal = actualOutput[i];
					maxPos = i;
				}
			}
			if(expectedOutput[maxPos] < 1.0)
				error = 1.0;
		}else{ // squared-error
			for(int i = 0; i<expectedOutput.length;i++){
				error += Math.pow(expectedOutput[i]-actualOutput[i],2.0)/2;
			}
		}
		
		return error;
	}
	
	public void updateWeights(){
		
		lowerWeights = MatrixOp.sumMatrix(lowerWeights, dWlower);
		upperWeights = MatrixOp.sumMatrix(upperWeights, dWupper);
		
		for(int i = 0; i<dWlower.length; i++){
			for(int j = 0; j<dWlower[0].length; j++)
				dWlower[i][j] = dWlower[i][j]*momentum;
		}
		for(int i = 0; i<dWupper.length; i++){
			for(int j = 0; j<dWupper[0].length; j++)
				dWupper[i][j] = dWupper[i][j]*momentum;
		}
	}
	
	public void train(){
		double minTestErrorValue = Double.MAX_VALUE;
		int minTestErrorEpoch = 0;
		
		boolean errorTooBig = true;
		for(int i = 0; i<epochs && errorTooBig; i++){
			System.out.println("Epoch: "+i);
			error = 0;
			for(int j=0; j<numberTrainExamples; j++){
				currentPair = input.getTrainPair();
				actualInput = addBias(currentPair.getInput());
				expectedOutput = currentPair.getExpectedOutput();
				forward();
				error += backward(expectedOutput);
				if((j+1)%batch == 0){
					updateWeights();
				}
			}
			
			error /= numberTrainExamples;
			
			System.out.println("Train error: "+error);
			
			if(error < targetError)
				errorTooBig = false;
			
			if(hasTest){
				error = 0;
				for(int h=0; h<numberTestExamples; h++){
					currentPair = input.getTestPair();
					actualInput = addBias(currentPair.getInput());
					expectedOutput = currentPair.getExpectedOutput();
					forward();
					
					if(multiClass){ // hard-max
						double maxVal = 0.0;
						int maxPos = 0;
						for(int j = 0; j<actualOutput.length;j++){ 
							if(actualOutput[j] > maxVal){
								maxVal = actualOutput[j];
								maxPos = j;
							}
						}
						if(expectedOutput[maxPos] < 1.0)
							error += 1.0;
					}else{ // squared-error
						for(int k = 0; k<expectedOutput.length;k++){
							error += Math.pow(expectedOutput[k]-actualOutput[k],2.0)/2;
						}
					}
				}
				error /= numberTestExamples;
				if(error < minTestErrorValue){
					minTestErrorValue = error;
					minTestErrorEpoch = i;
				}
				System.out.println("Test error:  "+error);
			}
		}
		
		//after training has finished
		if(hasTest){
			for(int h=0; h<numberTestExamples; h++){
				currentPair = input.getTestPair();
				actualInput = addBias(currentPair.getInput());
				expectedOutput = currentPair.getExpectedOutput();
				forward();
				for(int i = 0; i< expectedOutput.length; i++){
					System.out.println("Expected: "+expectedOutput[i]+" - Actual: "+actualOutput[i]);
				}
			}
			System.out.println("Minimun Test Error: "+minTestErrorValue+" at epoch: "+minTestErrorEpoch);
		}
		else
			for(int j=0; j<numberTrainExamples; j++){
				currentPair = input.getTrainPair();
				actualInput = addBias(currentPair.getInput());
				expectedOutput = currentPair.getExpectedOutput();
				forward();
				for(int i = 0; i< expectedOutput.length; i++){
					System.out.println("Expected: "+expectedOutput[i]+" - Actual: "+actualOutput[i]);
				}
			}
	}
}
