package besondereklassen;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// Alles was mit der "Generic" Class zu tun hat: =============
		Generic<String> genericString = new Generic<>("Hello");
		Generic<Integer> genericInteger = new Generic<>(42);
		
		String text = genericString.getContent();
		int nummer = genericInteger.getContent();
		
		System.out.println(text + nummer);
		
		// Alles was mit "ArrayUtils" Class zu tun hat: ==============
		String[] stringArray = {"Hello", "World"};
		Integer[] intArray = {1 , 2 , 3};
		
		ArrayUtils.printArray(stringArray);
		ArrayUtils.printArray(intArray);
		
		// Alles was mit "Wildcard" Class zu tun hat:  ===============
		// Obere Begrenzungswildcard
		List<Integer> integerList = Arrays.asList(1,2,3);
		List<Double> doubleList = Arrays.asList(1.1,2.2,3.3);
		
		Wildcard.printList(integerList);
		Wildcard.printList(doubleList);
		
		// Untere Begrenzungswildcard
		List<Number> numberList = new ArrayList<>();
		List<Object> objectList = new ArrayList<>();
		
		Wildcard.addNumbers(numberList);
		Wildcard.addNumbers(objectList);
	}
	
	
	
	
	
	

}
