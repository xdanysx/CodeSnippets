package zugriffe;

public final class TestClassFinal {
	
	/* Der "Final" Zugriffsmodifizierer erlaubt es nicht von anderen Klassen geerbt zu werden.  
	 Er ist aber durch "public" von anderen klassen sichtbar*/
	private int Zahl = 5;
	
	public int NutzeMethode() {
		return Zahl;
	}
	
	// "abstract int GetZahl();" wurde nicht klappen da die klasse final, nicht abstract ist. (beides geht nicht) 
}
