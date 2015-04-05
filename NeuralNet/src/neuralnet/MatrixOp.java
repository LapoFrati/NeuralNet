package neuralnet;

public class MatrixOp {
	
	public static double[] applyWeights(double[] inputs, double[][] weights){
		double[] output = new double[weights[0].length];
		
		if(inputs.length != weights.length)
			throw new RuntimeException("Input size and weights' size doesn't match.");
		
		for(int i = 0; i<weights[0].length; i++){
			for(int j = 0; j<weights.length; j++){
				output[i] += inputs[j]*weights[j][i];
			}
		}
		return output;
	}
	
	public static double[][] multiplyByDouble(double[][] matrix, double coefficient){
		double[][] increaseMatrix = new double[matrix.length][matrix[0].length];
		for(int i = 0; i < matrix.length; i++){
			for(int j = 0; j < matrix[0].length; j++){
				increaseMatrix[i][j] = matrix[i][j]*coefficient;
			}
		}
		return increaseMatrix;
	}
	
	public static double[][] sumMatrix(double[][] matrixA, double[][] matrixB){
		double result[][] = new double[matrixA.length][matrixA[0].length];
		
		if(matrixA.length != matrixB.length || matrixA[0].length != matrixB[0].length)
			throw new RuntimeException("Size mismatch while summing two matrix.");
		
		for(int i = 0; i < matrixA.length; i++){
			for(int j = 0; j < matrixA[0].length; j++){
				result[i][j] = matrixA[i][j]+matrixB[i][j];
			}
		}
		return result;
	}
}
