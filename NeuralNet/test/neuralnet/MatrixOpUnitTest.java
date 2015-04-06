package neuralnet;

import static org.junit.Assert.*;

import org.junit.Test;

public class MatrixOpUnitTest {
	double precision = 0.000001;
	
	@Test
	public void applyWeightsTest_OK() {
		double[] inputs = {1.0, 2.0, 4.0};
		double[][] weights = {{1.0, 1.0},{2.0,0.5},{0.5,2.0}};
		double[] output = MatrixOp.applyWeights(inputs, weights);
		assertEquals(output.length, 2);
		assertEquals(output[0],7.0,precision);
		assertEquals(output[1], 10.0, precision);
	}
	
	@Test(expected=RuntimeException.class)
	public void applyWeightsTest_SIZEMISMATCH(){
		double[] inputs = {1.0, 2.0, 4.0};
		double[][] weights = {{1.0, 1.0},{2.0,0.5}};
		MatrixOp.applyWeights(inputs, weights);
	}
	
	@Test
	public void multiplyByDoubleTest(){
		double[][] weights = {{1.0, 1.0},{2.0,0.5}};
		double[][] result = MatrixOp.multiplyByDouble(weights, 0.5);
		assertEquals(result.length   , 2);
		assertEquals(result[0].length, 2);
		assertEquals(result[0][0], 0.5,  precision);
		assertEquals(result[0][1], 0.5,  precision);
		assertEquals(result[1][0], 1.0,  precision);
		assertEquals(result[1][1], 0.25, precision);
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
