package neuralnet;

public class MatrixOp {
	
	public static double[] applyWeights(double[] inputs, double[][] weights){
		double[] output = new double[weights[0].length];
		
		if(inputs.length != weights.length)
			System.out.println("ERROR size mismatch when applying weights: "+inputs.length+" "+weights.length);
		/* 
		 * 	|a,b,c|*|d,e| = |ad+bf+ch,ae+bg+ci|
		 *   1x3	|f,g|	 1x2
		 * 			|h,i|
		 * 			 3x2
		 * */
		for(int i = 0; i<output.length; i++){
			for(int j = 0; j < inputs.length; j++){
					output[i] += inputs[j]*weights[j][i];
			}
		}		
		return output;
	}
	
	public static double[][] sumMatrix(double[][] matrixA, double[][] matrixB){
		double result[][] = new double[matrixA.length][matrixA[0].length];
		
		if(matrixA.length != matrixB.length || matrixA[0].length != matrixB[0].length)
			throw new RuntimeException("Size mismatch while summing two matrix.");
		
		for(int i = 0; i < matrixA.length; i++)
			for(int j = 0; j < matrixA[0].length; j++)
				result[i][j] = matrixA[i][j]+matrixB[i][j];
		return result;
	}
	
	public static double[] applySigmoid(double[] temp){
		double[] activatedNeurons = new double[temp.length];
		for(int i = 0; i < activatedNeurons.length; i++)
			activatedNeurons[i] = Sigmoid.activation(temp[i]);
		return activatedNeurons;
	}
	
	public static double[] applyDerivative(double[] temp){
		double[] activatedNeurons = new double[temp.length];
		for(int i = 0; i < activatedNeurons.length; i++)
			activatedNeurons[i] = Sigmoid.derivative(temp[i]);
		return activatedNeurons;
	}
}
