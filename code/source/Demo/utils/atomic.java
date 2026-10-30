package Demo.utils;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
// --- <<IS-END-IMPORTS>> ---

public final class atomic

{
	// ---( internal utility methods )---

	final static atomic _instance = new atomic();

	static atomic _newInstance() { return new atomic(); }

	static atomic _cast(Object o) { return (atomic)o; }

	// ---( server methods )---




	public static final void passwordStrengthChecker (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(passwordStrengthChecker)>> ---
		// @sigtype java 3.5
		// [i] field:0:required password
		// [o] object:0:required score
		// [o] field:0:required strength
		// [o] field:0:required message
		IDataCursor cursor = pipeline.getCursor();
		
		try {
		    String password = IDataUtil.getString(cursor, "password");
		
		    if (password == null || password.trim().isEmpty()) {
		        throw new ServiceException("Password tidak boleh kosong.");
		    }
		
		    int length = password.length();
		
		    boolean hasUppercase = false;
		    boolean hasLowercase = false;
		    boolean hasDigit = false;
		    boolean hasSpecialCharacter = false;
		
		    for (int i = 0; i < password.length(); i++) {
		        char c = password.charAt(i);
		        if (Character.isUpperCase(c)) {
		            hasUppercase = true;
		        }
		        else if (Character.isLowerCase(c)) {
		            hasLowercase = true;
		        }
		        else if (Character.isDigit(c)) {
		            hasDigit = true;
		        }
		        else {
		            hasSpecialCharacter = true;
		        }
		    }
		
		    int score = 0;
		    if (length >= 8) {
		        score++;
		    }
		    if (hasUppercase) {
		        score++;
		    }
		    if (hasLowercase) {
		        score++;
		    }
		    if (hasDigit) {
		        score++;
		    }
		    if (hasSpecialCharacter) {
		        score++;
		    }
		
		    String strength;
		
		    if (score <= 2) {
		        strength = "WEAK";
		    }
		    else if (score <= 4) {
		        strength = "MEDIUM";
		    }
		    else {
		        strength = "STRONG";
		    }
		
		    String message = "Password Strength = " + strength;
		
		    IDataUtil.put(cursor, "score", Integer.valueOf(score));
		    IDataUtil.put(cursor, "strength", strength);
		    IDataUtil.put(cursor, "message", message);
		} catch (Exception e) {
		    throw new ServiceException(e);
		}
		finally {
		    cursor.destroy();
		}
		// --- <<IS-END>> ---

                
	}



	public static final void test (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(test)>> ---
		// @sigtype java 3.5
		// [i] field:0:required password
		IDataCursor cursor = pipeline.getCursor();
		String password = IDataUtil.getString(cursor, "password");
		
		try {
			//Business Logic
			if (password == null || password.trim().isEmpty()) {
				throw new ServiceException("Password tidak boleh kosong.");
			}
			int length = password.length();
			
			boolean hasUppercase = false;
			boolean hasLowercase = false;
			boolean hasDigit = false;
			boolean hasSpecialCharacter = false;
			
			for (int i = 0; i < password.length(); i++){
				char c = password.charAt(i);
				if (Character.isUpperCase(c)) {
					hasUppercase = true;
				}
				else if (Character.isLowerCase(c)){
					hasLowercase = true;
				}
				else if (Character.isDigit(c)){
					hasDigit = true;
				}
				else {
					hasSpecialCharacter = true;
				}
			}
			
			int score = 0;
			if (length >= 0){
				score++;
			}
			if (hasUppercase){
				score++;
			}
			if (hasLowercase){
				score++;
			}
			if (hasDigit) {
				score++;
			}
			if (hasSpecialCharacter){
				score++;
			}
			
			String strength;
			if (score <= 2) {
				strength = "WEAK";
			}
			else if (score <= 4) {
				strength = "MEDIUM";
			}
			else {
				strength = "STRONG";
			}
			
			String message = "Password Strength = " + strength;
			
			IDataUtil.put(cursor, "score", Integer.valueOf(score));
			IDataUtil.put(cursor, "strength", strength);
			IDataUtil.put(cursor, "message", message);
		}
		catch (Exception e){
			throw new ServiceException(e.getMessage());
		}
		finally{
			cursor.destroy();
		}
		// --- <<IS-END>> ---

                
	}
}

