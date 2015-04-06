package neuralnet;

import java.io.IOException;

import static org.junit.Assert.*;
import org.junit.Test;

public class InputUnitTest {
	
	@Test
	public void optionsReader_OK(){
		InputReader in = new InputReader();
		
		try{
			in.readOptions("Options.txt");
		}catch(IOException e){}
		
		assertEquals(in.batchSize, 10);
		assertEquals(in.epochs, 500);
		assertEquals(in.learningRate, 0.0001, 0.0001);
		assertEquals(in.numberOfInputNeurons, 15);
		assertEquals(in.numberOfOutputNeurons, 1);
	}
	
	@Test(expected=RuntimeException.class)
	public void inputReader_SIZEMISMATCH() {
		InputReader in = new InputReader();
		try{
			in.readFiles("Options.txt", "WrongInput.txt");
		}catch(IOException e){}
	}

}
