package besondereklassen;

public class Generic<T> { // Generische Klasse
	
	private T content;
	
	public Generic(T content) { // Set Methode beim Instanzieren (Konstruktor)
		this.content = content;
	}
	
	public T getContent() { // Get Methode fuer private variablen
		return content;
	}

}
