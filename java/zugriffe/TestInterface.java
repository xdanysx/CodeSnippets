package zugriffe;

import java.util.Random;

public interface TestInterface {
	
	/* Wichtig!: alle Variablen werden in Interfaces automatisch als "public static final" behandelt
	 Variablen duerfen nicht den Zugriffsmodifizierer private haben, da sonst 
	 die Klassen die dieses Interface implementieren nichts davon haben. */
	
	String Name = "Daniel";
	int Zahl = 20; // Aquivalent zu "public static final int Zahl = 20;"
	final float PI = 3.141f; 
	
	//Methoden im Interface werden automatisch als "public abstract" gesehen
	static void SageZufaelligeZahl() {//Diese Methode kann nicht veraendert werden da sie statisch ist
		System.out.println(new Random().nextInt(Zahl));
	}
	
	default void InterfaceMethodeDefault() {
		System.out.println("Die Methode ist so als standard definiert"); 
		//Kann aber von der Klasse die dieses Interface implementiert veraendert werden, wegen dem "default"
	};
	
	void InterfaceMethode(); /*Diese Methode wurde noch nicht definiert aber sie gibt es schonmal
	die Klasse die dieses Interface implementiert muss diese noch definieren, da sie abstrakt ist*/
}
