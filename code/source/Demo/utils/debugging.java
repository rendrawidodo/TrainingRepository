package Demo.utils;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
// --- <<IS-END-IMPORTS>> ---

public final class debugging

{
	// ---( internal utility methods )---

	final static debugging _instance = new debugging();

	static debugging _newInstance() { return new debugging(); }

	static debugging _cast(Object o) { return (debugging)o; }

	// ---( server methods )---




	public static final void calculator (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(calculator)>> ---
		// @sigtype java 3.5
		// [i] field:0:required num1
		// [i] field:0:required num2
		// [i] field:0:required operator
		// [o] field:0:required result
		// [o] field:0:required status
		IDataCursor pipelineCursor = pipeline.getCursor();
		String num1Str = IDataUtil.getString(pipelineCursor, "num1");
		String num2Str = IDataUtil.getString(pipelineCursor, "num2");
		String operator = IDataUtil.getString(pipelineCursor, "operator");
		
		try {
		    if (num1Str == null || num2Str == null || operator == null) {
		        IDataUtil.put(pipelineCursor, "status", "ERROR: Parameter num1, num2, dan operator wajib diisi.");
		        return;
		    }
		
		    double n1 = Double.parseDouble(num1Str.trim());
		    double n2 = Double.parseDouble(num2Str.trim());
		    double res = 0;
		
		    switch (operator.trim()) {
		        case "+":
		        case "ADD":
		            res = n1 + n2;
		            break;
		        case "-":
		        case "SUBTRACT":
		            res = n1 - n2;
		            break;
		        case "*":
		        case "MULTIPLY":
		            res = n1 * n2;
		            break;
		        case "/":
		        case "DIVIDE":
		            if (n2 == 0) {
		                IDataUtil.put(pipelineCursor, "status", "ERROR: Division by zero.");
		                return;
		            }
		            res = n1 / n2;
		            break;
		        default:
		            IDataUtil.put(pipelineCursor, "status", "ERROR: Operator tidak valid. Gunakan +, -, *, atau /");
		            return;
		    }
		
		    IDataUtil.put(pipelineCursor, "result", String.valueOf(res));
		    IDataUtil.put(pipelineCursor, "status", "SUCCESS");
		
		} catch (NumberFormatException e) {
		    IDataUtil.put(pipelineCursor, "status", "ERROR: Input bukan angka yang valid.");
		} finally {
		    pipelineCursor.destroy();
		}
		// --- <<IS-END>> ---

                
	}
}

