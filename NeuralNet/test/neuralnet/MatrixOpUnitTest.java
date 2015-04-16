package neuralnet;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class MatrixOpUnitTest {
	double precision = 0.000001;
	
	@Test
	public void applyWeightsTest_OK() {
		double[] inputs = {1.0, 2.0, 4.0};
		double[][] weights = {{1.0, 1.0},{2.0,0.5},{0.5,2.0}};
		double[] output = MatrixOp.applyWeights(inputs, weights);
		assertEquals(output.length, 2);
		assertEquals(output[0], 7.0, precision);
		assertEquals(output[1], 10.0, precision);
	}
	@Test
	public void applyWeightsTest2_OK() {
		double[] inputs = {0.5, 0.5, 1.0};
		double[][] weights = {{2.0}, {2.0}, {1.0}};
		double[] output = MatrixOp.applyWeights(inputs, weights);
		assertEquals(output.length, 1);
		assertEquals(output[0],3.0,precision);
	}
	
	@Test(expected=RuntimeException.class)
	public void applyWeightsTest_SIZEMISMATCH(){
		double[] inputs = {1.0, 2.0, 4.0};
		System.out.println("Expect Size Mismatch:");
		double[][] weights = {{1.0, 1.0},{2.0,0.5}};
		MatrixOp.applyWeights(inputs, weights);
	}
	
	@Test
	public void applySigmoid(){
		double[] test = {0.14, 0.0,-1.0};
		System.out.println(Arrays.toString(MatrixOp.applySigmoid(test)));
	}
	
	@Test
	public void sumMatrix_OK(){
		double[][] matrixA = {{1.0, 1.0},{2.0,0.5}};
		double[][] matrixB = {{1.0, 1.0},{2.0,0.5}};
		double[][] result = MatrixOp.sumMatrix(matrixA, matrixB);
		assertEquals(result[0][0], 2.0,  precision);
		assertEquals(result[0][1], 2.0,  precision);
		assertEquals(result[1][0], 4.0,  precision);
		assertEquals(result[1][1], 1.0,  precision);
	}
	
	@Test(expected=RuntimeException.class)
	public void sumMatrix_SIZEMISMATCH(){
		double[][] matrixA = {{1.0, 1.0},{2.0,0.5}};
		double[][] matrixB = {{1.0, 1.0}};
		MatrixOp.sumMatrix(matrixA, matrixB);
	}

}
