package neuralnet;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class CreateSine {
	
	public static double getRandomVal(){
		return (Math.random()*2.0)-1.0;
	}
	
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException{
		
		PrintWriter writer = new PrintWriter("Sine/TrainInput.txt", "UTF-8");
		double x1, x2, x3, x4;
		for(int i = 0; i<40; i++){
			x1 = getRandomVal();
			x2 = getRandomVal();
			x3 = getRandomVal();
			x4 = getRandomVal();
			writer.println(x1+" "+x2+" "+x3+" "+x4);
			writer.println(Math.sin(x1+x2+x3+x4));
		}
		writer.close();
		
		PrintWriter writer2 = new PrintWriter("Sine/TestInput.txt", "UTF-8");
		for(int i = 0; i<10; i++){
			x1 = getRandomVal();
			x2 = getRandomVal();
			x3 = getRandomVal();
			x4 = getRandomVal();
			writer2.println(x1+" "+x2+" "+x3+" "+x4);
			writer2.println(Math.sin(x1+x2+x3+x4));
		}
		writer2.close();
	}
}
