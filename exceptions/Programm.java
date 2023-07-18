package exceptions;

public class Programm {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			throw new MyException("Test Ausnahme", 12345);
		}catch(MyException e) {
			System.out.println("Fehlernachricht: " + e.getMessage() + ", Fehlercode: " + e.getErrorCode());
		}
		
	}

}
