package exceptions;

@SuppressWarnings("serial")
public class MyException extends Exception{
	
	private String errorText;
	private int errorCode;
	
	public MyException(String pErrorText, int pErrorCode){
		
		super(pErrorText);
		this.errorText = pErrorText;
		this.errorCode = pErrorCode;
	}
	
	public String getMessage() {
		return this.errorText;	
		}
	
	public int getErrorCode() {
		return this.errorCode;
	}

}
