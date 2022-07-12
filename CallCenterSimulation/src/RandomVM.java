
import java.util.*;
/**
 * Random generator used for the project
 *
 */
public class RandomVM {
	private int lastZ;
	private Random random;
	
	public RandomVM() {
		random = new Random();
	}
	
	public RandomVM(int seed) {
		random = new Random();
		lastZ = seed;
	}
	
	private void repZ(int seed) {
		seed = seed * seed;
		try {
		    lastZ = Integer.parseInt(String.valueOf(seed).substring(2, 6));
		}
		catch(Exception e) {
			System.out.print("");
		}
		
	}
	
	public void printChart(int reps) {
		System.out.print("i  ");
		System.out.print("  Zi  ");
		System.out.print("  Ui  ");
		System.out.print("  Zi^2\n");
		
		for(int i = 0; i<reps; i++) {
			System.out.print(i + " ");
			System.out.print(" " + lastZ + " ");
			if(i != 0)
			    System.out.print(" " + "0." + lastZ + " ");
			else
				System.out.print("   " + "--" + "   ");
			
			System.out.print(" " + lastZ * lastZ + "\n");
			repZ(lastZ);
			
			
		}
	}
	
	public int nextInt() {
		return random.nextInt();
				
	}
	
	public int nextInt(int exclusiveUpperLimit) {
		return random.nextInt(exclusiveUpperLimit);
	}
	
	public int nextInt(int inclusiveLowerLimit, int exclusiveUpperLimit) {
		return random.nextInt(exclusiveUpperLimit - inclusiveLowerLimit) + inclusiveLowerLimit;
	}
	
	public double nextDouble() {
		return random.nextDouble();
	}
	
	public double nextDouble(int precision) {
		String format = String.format("%." + precision + "f", random.nextDouble());
		return Double.valueOf(format);
		
	}
	
	public double nextDouble(double exclusiveUpperLimit) {
		return exclusiveUpperLimit * random.nextDouble();
	}
	
	public double nextDouble(double exclusiveUpperLimit, int precision) {
		double generatedValue = nextDouble(exclusiveUpperLimit);
		String format = String.format("%." + precision + "f", generatedValue);
		
		return Double.valueOf(format);
	}

}
