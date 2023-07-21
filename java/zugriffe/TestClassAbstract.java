package zugriffe;

abstract class TestClassAbstract { // Mit "abstract" suggeriert man dass diese Klasse von einer anderen klasse erben wird
	// Wichtig!: Ohne das Wort "abstract" vor "class TestClassAbstract" kann man keine abstract methoden definieren.
	
	// Variablen: (Zugriffsmodifizierer wie public,private,protected,package, immer hinschreiben)
	private int hoechstGeschwindigkeit = 100; 
	public String autoModell = "Fiat";
	private String motorradModell = "Honda";
	protected int ganzZahl = 42069;
	
	/* Konkrete Methode die nicht abstract ist, kann auch in einer Abstrakten Klasse vorkommen.
	 sie muss dann aber auch definiert werden.*/
	String SageAuto() { // Methoden ohne Zugriffsmodifizierer sind automatisch "public"
		return "Ich bin ein " + this.autoModell + " und Fahre " + this.hoechstGeschwindigkeit + "km/h schnell!"; 
	}
	
	protected String SageMotorrad() { // Methoden mit Zugriffsmodifizierer "protected" lassen sich nur von klassen und subklassen sehen
		return "Ich bin ein " + this.motorradModell + " und Fahre " + this.hoechstGeschwindigkeit + "km/h schnell!"; 
	}
	
	/* Abstract Methoden sind immer public da sie von einer anderen Klasse implementiert werden müssen
	 (Wenn "public" aber steht muss in allen anderen klassen bei dieser Methode auch "public" stehen) */
	public abstract void SageHallo(); 
	
	// Abstract ohne public, ist automatisch public. 
	abstract void SageEtwas(String pEtwas); 
	
	// "private" und "abstract" gleichzeitig geht nicht da sonst andere klassen nicht zugreifen bzw. implementieren koennen
	abstract void SageGanzZahl(); 
}
