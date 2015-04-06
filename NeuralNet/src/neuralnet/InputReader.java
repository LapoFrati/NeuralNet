package neuralnet;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class InputReader {
	public int numberOfInputNeurons, numberOfOutputNeurons, inputLength, numberOfLayers;
	public int[] layers;
	private Path fFilePath, optionsFilePath;
	private final static Charset ENCODING = StandardCharsets.UTF_8;
	public ArrayList<InputPair> input;
	public int counter = 0;
	public int inputSize;
	public int batchSize;
	public double learningRate;
	public int epochs;
	public InputReader(){	
		input = new ArrayList<InputPair>();
	}
	
	public InputPair getPair(){
		return input.get((counter++)%inputSize);
	}
	
	public int getSize(){
		return inputSize;
	}
	
	/* Reads the file optionFile, then parses the inputFile repeatedly calling processElement
	 * The input file should be formatted as follows:
	 * INPUTLINE-1
	 * EXPECTEDRESULT-1
	 * INPUTLINE-2
	 * EXPECTEDRESLT-2
	 * ...
	*/
	public void readFiles(String optionsFile, String inputFile) throws IOException {
		readOptions(optionsFile);
		fFilePath = Paths.get(inputFile);
		try (Scanner scanner =  new Scanner(fFilePath, ENCODING.name())){
			while (scanner.hasNextLine()){
				processElement(scanner.nextLine(), scanner.nextLine());
			} 
		}
		
		inputSize = input.size();
		
		for(InputPair p : input){
			double[] input = p.getInput();
			double[] expected = p.getExpectedOutput();
			for(int i = 0; i< input.length; i++)
				System.out.println(input[i] + " ");
			for(int i = 0; i <expected.length; i++)
				System.out.println(expected[i] + " ");
			System.out.println("---");
		}
	}
	
	/* Expected format:
	 * 
	 * NInputNeurons: 15
	 * NOutputNeurons: 1
	 * BatchSize: 10
	 * LearningRate: 0.0001
	 * Epochs: 500
	 * 
	 */
	public void readOptions(String OptionFile) throws IOException{
		String option;
		optionsFilePath = Paths.get(OptionFile);
		try (Scanner scanner =  new Scanner(optionsFilePath, ENCODING.name())){
			
			option = scanner.nextLine();
			
			/*
			if(option.matches("NHiddenLayers: [1-9][0-9]*")){
				numberOfLayers = Integer.parseInt(option.split(" ")[1]);
				System.out.println("NHiddenLayers: "+numberOfLayers);
				if(scanner.hasNextLine())
					option = scanner.nextLine();
				else{
					System.out.println("Format Error");
					return;
				}
			}
			*/
			
			/*
			if(option.matches("Layers: [0-9 ]*")){
				System.out.println("Layers: ");
				String[] stringLayers = option.split(" ");
				layers = new int[stringLayers.length - 1];
				for(int i = 1; i < stringLayers.length; i++){
					layers[i-1] = Integer.parseInt(stringLayers[i]);
					//System.out.print(layers[i-1]+" ");
				}
				System.out.println();
				if(scanner.hasNextLine())
					option = scanner.nextLine();
				else{
					System.out.println("Format Error");
					return;
				}
			}
			*/
			
			if(option.matches("NInputNeurons: [0-9\t\r]*")){
				numberOfInputNeurons = Integer.parseInt(option.split(" ")[1]);
				System.out.println("NInputNeurons: "+numberOfInputNeurons);
				if(scanner.hasNextLine())
					option = scanner.nextLine();
				else{
					System.out.println("Format Error");
					return;
				}
			}
			
			if(option.matches("NOutputNeurons: [0-9\t\r]*")){
				numberOfOutputNeurons = Integer.parseInt(option.split(" ")[1]);
				System.out.println("NOutputNeurons: "+numberOfOutputNeurons);
				if(scanner.hasNextLine())
					option = scanner.nextLine();
				else{
					System.out.println("Format Error");
					return;
				}
			}
			
			if(option.matches("BatchSize: [0-9\t\r]*")){
				batchSize = Integer.parseInt(option.split(" ")[1]);
				System.out.println("BathcSize: "+batchSize);
				if(scanner.hasNextLine())
					option = scanner.nextLine();
				else{
					System.out.println("Format Error");
					return;
				}
			}
			
			if(option.matches("LearningRate: [.0-9\t\r]*")){
				learningRate = Double.parseDouble(option.split(" ")[1]);
				System.out.println("LearningRate: "+learningRate);
				if(scanner.hasNextLine())
					option = scanner.nextLine();
				else{
					System.out.println("Format Error");
					return;
				}
			}
			
			if(option.matches("Epochs: [0-9\t\r]*")){
				epochs = Integer.parseInt(option.split(" ")[1]);
				System.out.println("Epochs: "+epochs);
				if(scanner.hasNextLine()){
					System.out.println("Format Error");
					return;
				}
			}
			
			 
		}
	}
	
	//Each line should consist in a series of space-separated numbers of length numberOfInputNeurons, and one 
	// of length numberOfOutputNeurons, stores the inputs processed in the arrayList "input"
	protected void processElement(String elementLine, String testLine){ 
		double[] tmpInput = new double[numberOfInputNeurons];
		double[] tmpExpected = new double[numberOfOutputNeurons];
		String[] scannedInput, scannedExpectedOutuput;
		
		try(Scanner scanner = new Scanner(elementLine)){
			scannedInput = scanner.nextLine().split(" ");
			if(scannedInput.length != numberOfInputNeurons)
				System.out.println("ERROR: size mismatch while reading elementLine: expected "+numberOfInputNeurons+", got: "+scannedInput.length);
			else
				for(int i = 0; i<scannedInput.length; i++)
					tmpInput[i] = Double.parseDouble(scannedInput[i]);
		}
		
		try(Scanner scanner = new Scanner(testLine)){
			scannedExpectedOutuput = scanner.nextLine().split(" ");
			if(scannedExpectedOutuput.length != numberOfOutputNeurons)
				System.out.println("ERROR: size mismatch while reading testLine: expected "+numberOfInputNeurons+", got: "+scannedExpectedOutuput.length);
			else
				for(int i = 0; i<scannedExpectedOutuput.length; i++)
					tmpExpected[i] = Double.parseDouble(scannedExpectedOutuput[i]);			
		}
		
		input.add(new InputPair(tmpInput, tmpExpected));
	}
}	 
