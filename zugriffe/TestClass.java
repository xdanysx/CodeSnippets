package zugriffe;

public class TestClass extends TestClassAbstract implements TestInterface{

	int ganzZahl = 123; //Wenn Variablen kein Zugriffsmodifizierer besitzen, sind sie automatisch "package-private"
	private static int counter = 0;
	private char geheimZeichen = 'c'; //Wenn Variablen "private" sind, kann man diese auch nur innerhalb von einer Klasse sehen
	public int oeffentlicheZahl = 33; // Jede klasse kann auf die Variable zugreifen
    final float KOMMAZAHL = 2.3f;  //Finale variablen koennen nicht "public" sein
    final int KONSTANTE; // Finale Variablen die instanziert aber nicht definiert werden, muessen im konstruktor definiert werden
  
	public byte zusatzZahl;
	
	// Konstruktoren werden bei Objektinstanziierungen ausgefuehrt
	// Konstruktor:
	TestClass(int pKonstante){
		this.KONSTANTE = pKonstante;
		InterfaceMethodeDefault();
		InterfaceMethode();
		SageAuto(); //Schon definierte Methode von "TestClassAbstract". Diese Methode besitzt kein public/abstract.
	}
	
	// Mit "public" Methoden kann man auf private Variablen zugreifen
	public char GetSecretChar() { 
		return geheimZeichen;
	}
	
	public int GetCountNumber() {
		return counter;
	};
	
	// Ueberlagerte Methoden
	// Wenn es zwei Methoden gibt allerdings mit verschiedenen Parametern, nennt man das überlagerte Methoden
	public void ErhoeheCounter() {// Erhoeht counter um 1
		++counter;
	}
	public void ErhoeheCounter(int pMenge) {// Erhoeht counter um pMenge
		counter += pMenge;
	}
	//
	
	public static void ErhoeheCounterAberStatic(int pMenge) {
		counter += pMenge;
	}
	
	
	
	
	/* Hier sind abstracte methoden die von TestClassAbstract kommen
	 Es müssen daber nicht alle Abstrakte Methoden benutzt werden */
	
	/* "@Override" ist hierbei nicht unbedingt notwendig zu schreiben aber dient der uebersichtlichkeit
	 damit man weiss dass man eine Methode ueberschreibt*/
	@Override 
	public void SageHallo() {
		// TODO Auto-generated method stub
		System.out.println("Ich sage Hallo");
	}

	@Override 
	void SageEtwas(String pEtwas) {
		// TODO Auto-generated method stub
		System.out.println(pEtwas);
	}

	@Override
	void SageGanzZahl() {
		// TODO Auto-generated method stub
		System.out.println(this.ganzZahl); // Variable von der aktuellen Klasse
		System.out.println(super.ganzZahl); // Variable von der Super Klasse
		
	}

	
	//Wegen dem interface "TestInterface"
	
	/* Die Methode "InterfaceMethode(){}" ist eine optionale Methode die man veraendern kann, sie besitzt 
	 * naemlich schon im Interface eine Definition, wenn man diese Ausklammert erhaelt man das standard printf
	 */
	@Override
	public void InterfaceMethodeDefault() {
		// TODO Auto-generated method stub
		System.out.println("Die InterfaceMethode() wurde geaendert, die Konstante Zahl ist: " + KONSTANTE);
	}
	 
	
	@Override
	public void InterfaceMethode() {
		// TODO Auto-generated method stub
		System.out.println("Die InterfaceMethodeAbstract() wurde definiert");
	}

	
	
}
