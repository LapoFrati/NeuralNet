package neuralnet;

import java.io.IOException;

public class TrainLetters {
	public static void main(String[] args) throws IOException {
		MLP mlp = new MLP(true);
		
		mlp.buildNetwork("Letters/Options.txt","Letters/TrainInputProcessed.txt","Letters/TestInputProcessed.txt");
		mlp.train();

	}
}
