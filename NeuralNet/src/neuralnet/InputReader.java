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
	
	/**
	 Constructor.
	 @param aFileName full name of an existing, readable file.
	*/
	public InputReader(){	
		input = new ArrayList<InputPair>();
	}	  
	
	/** Template method that calls {@link #processLine(String)}.  */
	public final void processElements(String optionsFile, String inputFile) throws IOException {
		readOptions(optionsFile);
		fFilePath = Paths.get(inputFile);
		try (Scanner scanner =  new Scanner(fFilePath, ENCODING.name())){
			while (scanner.hasNextLine()){
				processElement(scanner.nextLine(), scanner.nextLine());
			} 
		}
	}
	
	/*
	 * NHiddenLayers: 3
	 * Layers: 10 7 3
	 * NInputNeurons: 15
	 * NOutputNeurons: 1
	 * 
	 */
	public final void readOptions(String OptionFile) throws IOException{
		String option;
		optionsFilePath = Paths.get(OptionFile);
		try (Scanner scanner =  new Scanner(optionsFilePath, ENCODING.name())){
			while (scanner.hasNextLine()){
				option = scanner.nextLine();
				
				if(option.matches("NHiddenLayers: [1-9][0-9]*")){
					numberOfLayers = Integer.parseInt(option.split(" ")[1]);
					System.out.println("NHiddenLayers: "+numberOfLayers);
					continue;
				}
				
				if(option.matches("Layers: [0-9 ]*")){
					System.out.println("Layers: ");
					String[] stringLayers = option.split(" ");
					layers = new int[stringLayers.length - 1];
					for(int i = 1 /* ignore Layers: */; i < stringLayers.length; i++){
						layers[i-1] = Integer.parseInt(stringLayers[i]);
						System.out.print(layers[i-1]+" ");
					}
					System.out.println();
					continue;
				}
				
				if(option.matches("NInputNeurons: [0-9\t\r]*")){
					numberOfInputNeurons = Integer.parseInt(option.split(" ")[1]);
					System.out.println("NInputNeurons: "+numberOfInputNeurons);
					continue;
				}
				
				if(option.matches("NOutputNeurons: [0-9\t\r]*")){
					numberOfOutputNeurons = Integer.parseInt(option.split(" ")[1]);
					System.out.println("NOutputNeurons: "+numberOfOutputNeurons);
					continue;
				}
			} 
		}
	}
	 
	protected void processElement(String elementLine, String testLine){
	//use a second Scanner to parse the content of each line 
		int[] tmpInput = new int[numberOfInputNeurons];
		int[] tmpExpected = new int[numberOfInputNeurons];
		String[] scannedInput, scannedExpectedOutuput;
		
		try(Scanner scanner = new Scanner(elementLine)){
			scannedInput = scanner.nextLine().split(" ");
			if(scannedInput.length != numberOfInputNeurons)
				System.out.println("ERROR: size mismatch while reading elementLine: expected "+numberOfInputNeurons+", got: "+scannedInput.length);
			else
				for(int i = 0; i<scannedInput.length; i++)
					tmpInput[i] = Integer.parseInt(scannedInput[i]);
		}
		
		try(Scanner scanner = new Scanner(testLine)){
			scannedExpectedOutuput = scanner.nextLine().split(" ");
			if(scannedExpectedOutuput.length != numberOfInputNeurons)
				System.out.println("ERROR: size mismatch while reading testLine: expected "+numberOfInputNeurons+", got: "+scannedExpectedOutuput.length);
			else
				for(int i = 0; i<scannedExpectedOutuput.length; i++)
					tmpExpected[i] = Integer.parseInt(scannedExpectedOutuput[i]);			
		}
		
		input.add(new InputPair(tmpInput, tmpExpected));
	}
}	 
