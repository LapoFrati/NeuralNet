package neuralnet;

import java.io.IOException;

public class TrainLetters {
	public static void main(String[] args) throws IOException {
		MLP mlp = new MLP(true, true);
		
		mlp.buildNetwork("Letters/Options.txt","Letters/TrainInputProcessed.txt","Letters/TestInputProcessed.txt");
		mlp.train();
		
		/*
		 	NumberOfInputNeurons: 16
			NumberOfOutputNeurons: 26
			NumberOfHiddenNeurons: 15
			Batch: 1600
			Seed: 42
			learningRate: 0.01
			Momentum: 0.3
			Target Error: 1.0E-5
			Epochs: 1000000
			NumberOfExamples: 16000
			LowerWeights: 17x15
			UpperWeights: 16x26
			weightRange: +-0.0015811388300841897
			
			Keeps oscillating, maybe learning rate too high
		*/

	}
}
