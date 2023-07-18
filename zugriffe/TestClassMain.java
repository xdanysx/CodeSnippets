package zugriffe;

public final class TestClassMain {
	
	public static void main(String[] args) {
		System.out.println(TestInterface.Name); // Funktioniert da "TestInterface" public ist (package-private geht auch)
		System.out.println(TestClass.PI); // PI ist von TestInterface und wird in TestClass implementiert, daher moeglich 
		
		/* Methode "ErhoeheCounterAberStatic()" geht auch ohne ein object von TestClass
		  da die Methode Static ist: */
		TestClass.ErhoeheCounterAberStatic(20); // Erhoeht counter 0 -> 20
		
		TestClass tc1 = new TestClass(404); // Erstes Object von der Klasse "TestClass" namens "tc1" instanziert
		TestClass tc2 = new TestClass(23); // Zweites Object von der Klasse "TestClass" namens "tc2" instanziert
		tc1.ErhoeheCounter(); // "ErhoeheCounter()" geht aber nur wenn es ein Object von "TestClass" gibt. (20 -> 21)
		//System.out.println(tc2.counter); geht nicht da "counter" auf "private" gestellt ist
		System.out.println(tc2.GetCountNumber()); // ist moeglich da "GetCountNumber()" auf "public" gestellt ist
		tc2.ErhoeheCounter(32); // "ErhoeheCounter(32)" erhoeht dabei immer nur "counter" in "Testclass" da es "static" ist (21 -> 53)
		System.out.println(tc1.GetCountNumber()); //Trotz zwei instanzen teilen sich beide den statischen "counter"
		System.out.println(tc1.KOMMAZAHL); //"TestClass.KOMMAZAHL" wuerde nicht funktionieren da "KOMMAZAHL" nicht statisch ist
		System.out.println(tc2.ganzZahl);
		
		TestInterface.SageZufaelligeZahl(); // Ist moeglich da "SageZuaeflligeZahl()" statisch ist (und nicht veraenderbar)
	}

}
