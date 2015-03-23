package neuralnet;

import static org.junit.Assert.*;
import neuralnet.Matrix;
import neuralnet.MatrixError;

import org.junit.Test;

public class MatrixUnitTest {
	public static final double DELTA = 0.001;
	
	@Test
	public void testRowsAndCols() throws Throwable
	{
		Matrix matrix = new Matrix(6,3);
		assertEquals(matrix.getRows(), 6);
		assertEquals(matrix.getCols(), 3);
		
		matrix.set(1,2, 1.5);
		assertEquals(matrix.get(1,2), 1.5, DELTA);
	}
	
	@Test
	public void testRowAndColRangeUnder() throws Throwable
	{
		Matrix matrix = new Matrix(6,3);
		
		// make sure set registers error on under-bound row
		try {
			matrix.set(-1, 0, 1);
			assertTrue(false); // should have thrown an exception
		}
		catch(MatrixError e)
		{
		}
		
		// make sure set registers error on under-bound col
		try {
			matrix.set(0, -1, 1);
			assertTrue(false); // should have thrown an exception
		}
		catch(MatrixError e)
		{
		}
		
		// make sure get registers error on under-bound row
		try {
			matrix.get(-1, 0 );
			assertTrue(false); // should have thrown an exception
		}
		catch(MatrixError e)
		{
		}
		
		// make sure set registers error on under-bound col
		try {
			matrix.get(0, -1 );
			assertTrue(false); // should have thrown an exception
		}
		catch(MatrixError e)
		{
		}
	}
	
	@Test
	public void testRowAndColRangeOver() throws Throwable
	{
		Matrix matrix = new Matrix(6,3);
		
		// make sure set registers error on under-bound row
		try {
			matrix.set(6, 0, 1);
			assertTrue(false); // should have thrown an exception
		}
		catch(MatrixError e)
		{
		}
		
		// make sure set registers error on under-bound col
		try {
			matrix.set(0, 3, 1);
			assertTrue(false); // should have thrown an exception
		}
		catch(MatrixError e)
		{
		}
		
		// make sure get registers error on under-bound row
		try {
			matrix.get(6, 0 );
			assertTrue(false); // should have thrown an exception
		}
		catch(MatrixError e)
		{
		}
		
		// make sure set registers error on under-bound col
		try {
			matrix.get(0, 3 );
			assertTrue(false); // should have thrown an exception
		}
		catch(MatrixError e)
		{
		}
	}
	
	@Test
	public void testMatrixConstruct() throws Throwable
	{
		double m[][] = {
				{1,2,3,4},
				{5,6,7,8},
				{9,10,11,12},
				{13,14,15,16} };
		Matrix matrix = new Matrix(m);
		assertEquals(matrix.getRows(), 4);
		assertEquals(matrix.getCols(), 4);
	}
	
	@Test
	public void testMatrixEquals() throws Throwable
	{
		double m1[][] = {
				{1,2},
				{3,4} };
		
		double m2[][] = {
				{0,2},
				{3,4} };	
	
		Matrix matrix1 = new Matrix(m1);
		Matrix matrix2 = new Matrix(m1);
		
		assertTrue(matrix1.equals(matrix2));
		
		matrix2 = new Matrix(m2);
		
		assertFalse(matrix1.equals(matrix2));
	}
	
	@Test
	public void testMatrixEqualsPrecision() throws Throwable
	{
		double m1[][] = {
				{1.1234,2.123},
				{3.123,4.123} };
		
		double m2[][] = {
				{1.123,2.123},
				{3.123,4.123} };
		
		Matrix matrix1 = new Matrix(m1);
		Matrix matrix2 = new Matrix(m2);
		
		assertTrue(matrix1.equals(matrix2,3));
		assertFalse(matrix1.equals(matrix2,4));
		
		double m3[][] = {
				{1.1,2.1},
				{3.1,4.1} };
		
		double m4[][] = {
				{1.2,2.1},
				{3.1,4.1} };
		
		Matrix matrix3 = new Matrix(m3);
		Matrix matrix4 = new Matrix(m4);
		assertTrue(matrix3.equals(matrix4,0));
		assertFalse(matrix3.equals(matrix4,1));
		
		try
		{
			matrix3.equals(matrix4,-1);
			assertTrue( false);
		}
		catch(MatrixError e)
		{
			
		}
		
		try
		{
			matrix3.equals(matrix4,19);
			assertTrue( false);
		}
		catch(MatrixError e)
		{
			
		}
		
	}
	
	@Test
	public void testMatrixMultiply() throws Throwable
	{
		double a[][] = {
				{1,0,2},
				{-1,3,1}
		};
		
		double b[][] = {
				{3,1},
				{2,1},
				{1,0}
		};
		
		double c[][] = {
				{5,1},
				{4,2}
		};
		
		Matrix matrixA = new Matrix(a);
		Matrix matrixB = new Matrix(b);
		Matrix matrixC = new Matrix(c);
		
		Matrix result = matrixA.clone();
		result = MatrixMath.multiply(matrixA,matrixB); 

		assertTrue(result.equals(matrixC));
		
		double a2[][] = {
				{1,2,3,4},
				{5,6,7,8}
		};
		
		double b2[][] = {
				{1,2,3},
				{4,5,6},
				{7,8,9},
				{10,11,12}
		};
		
		double c2[][] = {
				{70,80,90},
				{158,184,210}
		};
		
		matrixA = new Matrix(a2);
		matrixB = new Matrix(b2);
		matrixC = new Matrix(c2);
		
		result = MatrixMath.multiply(matrixA, matrixB);
		assertTrue(result.equals(matrixC));
		
		result = matrixB.clone();
		try
		{
			MatrixMath.multiply(matrixB,matrixA);
			assertTrue(false);
		}
		catch(MatrixError e)
		{
			
		}	
	}
	
	@Test
	public void testBoolean() throws Throwable
	{
		boolean matrixDataBoolean[][] = { 
				{true,false},
				{false,true}
		};
		
		double matrixDataDouble[][] = {
				{1.0,-1.0},
				{-1.0,1.0},
		};
		
		Matrix matrixBoolean = new Matrix(matrixDataBoolean);
		Matrix matrixDouble = new Matrix(matrixDataDouble);
		
		assertTrue(matrixBoolean.equals(matrixDouble));
	}
	
	@Test
	public void testGetRow() throws Throwable
	{
		double matrixData1[][] = {
				{1.0,2.0},
				{3.0,4.0}
		};
		double matrixData2[][] = {
				{3.0,4.0}
		};
		
		Matrix matrix1 = new Matrix(matrixData1);
		Matrix matrix2 = new Matrix(matrixData2);
		
		Matrix matrixRow = matrix1.getRow(1);
		assertTrue(matrixRow.equals(matrix2));
		
		try
		{
			matrix1.getRow(3);
			assertTrue(false);
		}
		catch(MatrixError e)
		{
			assertTrue(true);
		}
	}
	
	@Test
	public void testGetCol() throws Throwable
	{
		double matrixData1[][] = {
				{1.0,2.0},
				{3.0,4.0}
		};
		double matrixData2[][] = {
				{2.0},
				{4.0}
		};
		
		Matrix matrix1 = new Matrix(matrixData1);
		Matrix matrix2 = new Matrix(matrixData2);
		
		Matrix matrixCol = matrix1.getCol(1);
		assertTrue(matrixCol.equals(matrix2));
		
		try
		{
			matrix1.getCol(3);
			assertTrue(false);
		}
		catch(MatrixError e)
		{
			assertTrue(true);
		}
	}
	
	@Test
	public void testZero() throws Throwable
	{
		double doubleData[][] = { {0,0}, {0,0} };
		Matrix matrix = new Matrix(doubleData);
		assertTrue(matrix.isZero());
	}
	
	@Test
	public void testSum() throws Throwable
	{
		double doubleData[][] = { {1,2}, {3,4} };
		Matrix matrix = new Matrix(doubleData);
		assertEquals((int)matrix.sum(), 1+2+3+4);
	}
	
	@Test
	public void testRowMatrix() throws Throwable
	{
		double matrixData[] = {1.0,2.0,3.0,4.0};
		Matrix matrix = Matrix.createRowMatrix(matrixData);
		assertEquals(matrix.get(0,0), 1.0, DELTA);
		assertEquals(matrix.get(0,1), 2.0, DELTA);
		assertEquals(matrix.get(0,2), 3.0, DELTA);
		assertEquals(matrix.get(0,3), 4.0, DELTA);
	}
	
	@Test
	public void testColumnMatrix() throws Throwable
	{
		double matrixData[] = {1.0,2.0,3.0,4.0};
		Matrix matrix = Matrix.createColumnMatrix(matrixData);
		assertEquals(matrix.get(0,0), 1.0, DELTA);
		assertEquals(matrix.get(1,0), 2.0, DELTA);
		assertEquals(matrix.get(2,0), 3.0, DELTA);
		assertEquals(matrix.get(3,0), 4.0, DELTA);
	}
	
	@Test
	public void testAdd() throws Throwable
	{
		double matrixData[] = {1.0,2.0,3.0,4.0};
		Matrix matrix = Matrix.createColumnMatrix(matrixData);
		matrix.add(0, 0, 1);
		assertEquals(matrix.get(0, 0), 2.0, DELTA);
	}
	
	@Test
	public void testClear() throws Throwable
	{
		double matrixData[] = {1.0,2.0,3.0,4.0};
		Matrix matrix = Matrix.createColumnMatrix(matrixData);
		matrix.clear();
		assertEquals(matrix.get(0, 0), 0.0, DELTA);
		assertEquals(matrix.get(1, 0), 0.0, DELTA);
		assertEquals(matrix.get(2, 0), 0.0, DELTA);
		assertEquals(matrix.get(3, 0), 0.0, DELTA);
	}
	
	public void testIsVector() throws Throwable
	{
		double matrixData[] = {1.0,2.0,3.0,4.0};
		Matrix matrixCol = Matrix.createColumnMatrix(matrixData);
		Matrix matrixRow = Matrix.createRowMatrix(matrixData);
		assertTrue(matrixCol.isVector());
		assertTrue(matrixRow.isVector());
		double matrixData2[][] = {{1.0,2.0},{3.0,4.0}};
		Matrix matrix = new Matrix(matrixData2);
		assertFalse(matrix.isVector());
	}
	
	@Test
	public void testIsZero() throws Throwable
	{
		double matrixData[] = {1.0,2.0,3.0,4.0};
		Matrix matrix = Matrix.createColumnMatrix(matrixData);
		assertFalse(matrix.isZero());
		double matrixData2[] = {0.0,0.0,0.0,0.0};
		Matrix matrix2 = Matrix.createColumnMatrix(matrixData2);
		assertTrue(matrix2.isZero());

	}
	
	@Test
	public void testPackedArray() throws Throwable
	{
		double matrixData[][] = {{1.0,2.0},{3.0,4.0}};
		Matrix matrix = new Matrix(matrixData);
		Double matrixData2[] = matrix.toPackedArray();
		assertEquals(4, matrixData2.length);
		assertEquals(1.0,matrix.get(0, 0), DELTA);
		assertEquals(2.0,matrix.get(0, 1), DELTA);
		assertEquals(3.0,matrix.get(1, 0), DELTA);
		assertEquals(4.0,matrix.get(1, 1), DELTA);
		
		Matrix matrix2 = new Matrix(2,2);
		matrix2.fromPackedArray(matrixData2, 0);
		assertTrue(matrix.equals(matrix2));
	}
	
	@Test
	public void testPackedArray2() throws Throwable
	{
		Double data[] = {1.0,2.0,3.0,4.0};
		Matrix matrix = new Matrix(1,4);
		matrix.fromPackedArray(data, 0);
		assertEquals(1.0, matrix.get(0, 0),0.01);
		assertEquals(2.0, matrix.get(0, 1),0.01);
		assertEquals(3.0, matrix.get(0, 2),0.01);
	}
	
	public void testSize() throws Throwable
	{
		double data[][] = {{1.0,2.0},{3.0,4.0}};
		Matrix matrix = new Matrix(data);
		assertEquals(4, matrix.size());
	}
	
	@Test
	public void testRandomize() throws Throwable
	{
		final double MIN = 1.0;
		final double MAX = 10.0;
		Matrix matrix = new Matrix(10,10);
		matrix.ramdomize(MIN,MAX);
		Double array[] = matrix.toPackedArray();
		for(int i=0;i<array.length;i++)
		{
			if( array[i]<MIN || array[i]>MAX )
				assertFalse(true);
		}
	}
	
	@Test
	public void testVectorLength() throws Throwable
	{
		double vectorData[] = {1.0,2.0,3.0,4.0};
		Matrix vector = Matrix.createRowMatrix(vectorData);
		assertEquals(5, (int)MatrixMath.vectorLength(vector));
		
		Matrix nonVector = new Matrix(2,2);
		try
		{
			MatrixMath.vectorLength(nonVector);
			assertTrue(false);
		}
		catch(MatrixError e)
		{
			
		}
	}

}
