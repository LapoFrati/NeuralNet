package neuralnet;

import java.io.IOException;

public class InputTest {

	public static void main(String[] args) throws IOException {
		InputReader in = new InputReader();
		
		//in.readOptions("Options.txt");
		in.readFiles("Options.txt", "Input.txt");
		
	}

}
