package neuralnet;

import java.io.IOException;

public class TrainXOR {

	public static void main(String[] args) throws IOException {
		MLP mlp = new MLP(true, false);
		
		mlp.buildNetwork("XOR/Options.txt","XOR/TrainInput.txt");
		mlp.train();

	}
}
