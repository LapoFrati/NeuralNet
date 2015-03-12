package neuralnet;

public class InputPair {
	private int[] input, expectedOutput;
	
	public InputPair(int[] input, int[] expectedOutput) {
		this.input = input;
		this.expectedOutput = expectedOutput;
	}
	
	public int getNthInput(int n){
		return input[n];
	}
	
	public int getNthExpectedOutput(int n){
		return expectedOutput[n];
	}
}
