package neuralnet;

import java.io.IOException;

public class TrainSine {
	public static void main(String[] args) throws IOException {
		MLP mlp = new MLP(false,false);
		
		mlp.buildNetwork("Sine/Options.txt","Sine/TrainInput.txt","Sine/TestInput.txt");
		mlp.train();
		
	}
}
