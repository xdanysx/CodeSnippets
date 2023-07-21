package streams;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class StreamIO {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// VON QUELLDATEI LESEN ========================================================================
		String quellenPfad = "src/streams/quelle.txt"; // QuellenStream/QuellPfad
		String quellText; // Hier wird nachher das eingetragen was in der QuellDatei steht
		
		// In TRY-CATCH Block schreiben damit am ende des lesevorgangs der Filereader geschlossen wird
		try(BufferedReader br = new BufferedReader(new FileReader(quellenPfad))) { // BufferedReader wird gebraucht um mehrere Zeilen zu lesen, Filereader liest einzelne Zeichen
			while((quellText = br.readLine())!= null) { //Solange gelesene Zeile nicht "null" bzw. leer ist 
				System.out.println(quellText); // Gebe Zeile aus
			}
		}catch(IOException e) {
			e.printStackTrace(); //Gebe ausnahme e aus
		}
		
		
		
		
		// IN ZIELDATEI EINTRAGEN =======================================================================
		String zielPfad = "src/streams/ziel.txt"; // ZielStream/Zielpfad
		String zielText = "Hallo wie geht es dir?"; // Hier wird eingetragen was in die Zieldatei reinkommen soll
		
		// In TRY-CATCH Block schreiben damit am ende des schreibvorgangs der Filewriter geschlossen wird
		try(FileWriter fw = new FileWriter(zielPfad)){ // Achtung!: Filewriter schreibt genau das um was im "zielText" steht, er haengt nichts dran sondern schreibt neu
			fw.write(zielText); //Schreibe "zielText" in "zielPfad"
		}catch(IOException e) {
			e.printStackTrace(); //Gebe ausnahme e aus
		}
		
		
		
	}

}
