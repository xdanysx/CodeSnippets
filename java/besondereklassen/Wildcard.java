package besondereklassen;

import java.util.List;

public class Wildcard {

	public static void printList(List<? extends Number> list) { // Obere Begrenzungswildcard
		for(Number element : list) {
			System.out.println(element);
		}
		
		/* In diesem Fall kann die Methode printList eine Liste akzeptieren die 
		 * entweder den generischen Typ Number selbst oder eine Subklasse von Number enthaelt
		 */
	}
	
	public static void addNumbers(List<? super Integer> list) { // Untere Begrenzungswildcard
		list.add(1);
		list.add(2);
		list.add(3);
		
		/* In diesem Fall kann die Methode addNumbers eine Liste akzeptieren die 
		 * entweder den generischen Typ Integer selbst oder einen Supertyp von Integer enthaehlt
		 */
	}
	
}
