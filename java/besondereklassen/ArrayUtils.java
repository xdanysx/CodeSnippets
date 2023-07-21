package besondereklassen;

public class ArrayUtils {

	public static <T> void printArray(T[]array) { // Generische Methode
		for(T element : array) {
			System.out.println(element);
		}
	}
	
}
