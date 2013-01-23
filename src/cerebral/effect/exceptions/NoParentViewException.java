package cerebral.effect.exceptions;

public class NoParentViewException extends Exception{
	private static final long serialVersionUID = 1L;
	
	public NoParentViewException(String message){
		super(message);
	}
}
