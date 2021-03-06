package neuralnet;

import java.io.IOException;

import static org.junit.Assert.*;
import org.junit.Test;

public class InputUnitTest {
	double precision = 0.000001;
	
	@Test
	public void optionsReader_OK(){
		InputReader in = new InputReader();
		
		try{
			in.readOptions("TestInput/Options.txt");
		}catch(IOException e){}
		
		assertEquals(in.batchSize, 10);
		assertEquals(in.epochs, 500);
		assertEquals(in.learningRate, 0.0001, precision);
		assertEquals(in.numberOfInputNeurons, 15);
		assertEquals(in.numberOfOutputNeurons, 1);
	}
	
	@Test(expected=RuntimeException.class)
	public void inputReader_SIZEMISMATCH() {
		InputReader in = new InputReader();
		try{
			in.readOptions("TestInput/Options.txt");
			in.readTrainInput("TestInput/WrongInput.txt");
		}catch(IOException e){}
	}
	
	@Test
	public void inputReader_OK() {
		InputReader in = new InputReader();
		try{
			in.readOptions("TestInput/Options.txt");
			in.readTrainInput("TestInput/Input.txt");
		}catch(IOException e){}
		
		double[] firstInput = in.getTrainPair().getInput();
		assertEquals(firstInput.length, 15);
		for(int i = 0; i < 15; i++){
			assertEquals(firstInput[i], (double)(i+1), precision);
		}
	}

}
