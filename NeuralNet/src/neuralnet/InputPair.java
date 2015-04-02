package neuralnet;

public class InputPair {
	private double[] input, expectedOutput;
	
	public InputPair(double[] input, double[] expectedOutput) {
		this.input = input;
		this.expectedOutput = expectedOutput;
	}
	
	public double[] getInput(){
		return input;
	}
	
	public double[] getExpectedOutput(){
		return expectedOutput;
	}
}
