package Demo.utils.atomic;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.util.concurrent.ConcurrentHashMap;
// --- <<IS-END-IMPORTS>> ---

public final class employee

{
	// ---( internal utility methods )---

	final static employee _instance = new employee();

	static employee _newInstance() { return new employee(); }

	static employee _cast(Object o) { return (employee)o; }

	// ---( server methods )---




	public static final void generateEmployeeId (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(generateEmployeeId)>> ---
		// @sigtype java 3.5
		// [i] field:0:required department
		// [i] field:0:required joiningYear
		// [o] field:0:required status
		// [o] field:0:required message
		// [o] field:0:required employeeId
		IDataCursor cursor = pipeline.getCursor();
		
		try {
		    String department = IDataUtil.getString(cursor, "department");
		    String joiningYear = IDataUtil.getString(cursor, "joiningYear");
		
		    if (department == null || department.trim().isEmpty()) {
		        throw new ServiceException("Department tidak boleh kosong.");
		    }
		
		    if (joiningYear == null || joiningYear.trim().isEmpty()) {
		        throw new ServiceException("Joining Year tidak boleh kosong.");
		    }
		
		    department = department.trim().toUpperCase();
		    joiningYear = joiningYear.trim();
		
		    IData reserveInput = IDataFactory.create();
		
		    IDataCursor reserveCursor = reserveInput.getCursor();
		    IDataUtil.put(reserveCursor, "department", department);
		    IDataUtil.put(reserveCursor, "joiningYear",joiningYear);
		    reserveCursor.destroy();
		
		    /** Invoke **/
		    IData reserveOutput = Service.doInvoke("Demo.utils.atomic.employee", "reserveEmployeeSequence", reserveInput);
		
		    IDataCursor outputCursor = reserveOutput.getCursor();
		    String batch = IDataUtil.getString(outputCursor, "batch");
		    String sequence = IDataUtil.getString(outputCursor, "sequence");
		    outputCursor.destroy();
		
		    String employeeId =
		            new StringBuilder()
		                    .append(department)
		                    .append("-")
		                    .append(joiningYear)
		                    .append("-")
		                    .append(formatBatch(
		                            Integer.parseInt(batch)))
		                    .append("-")
		                    .append(formatSequence(
		                            Integer.parseInt(sequence)))
		                    .toString();
		
		    IDataUtil.put(cursor, "employeeId", employeeId);
		    IDataUtil.put(cursor, "status", "SUCCESS");
		    IDataUtil.put(cursor, "message", "Employee ID berhasil dibuat.");
		
		}
		catch (Exception e) {
		    throw new ServiceException(null, "Failed to generate Employee ID.", null, e, null);
		}
		finally {
		    cursor.destroy();
		}
		// --- <<IS-END>> ---

                
	}



	public static final void reserveEmployeeSequence (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(reserveEmployeeSequence)>> ---
		// @sigtype java 3.5
		// [i] field:0:required department
		// [i] field:0:required joiningYear
		// [o] field:0:required batch
		// [o] field:0:required sequence
		// [o] field:0:required isNewBatch
		// [o] field:0:required status
		IDataCursor cursor = pipeline.getCursor();
		
		try {
		    String department = IDataUtil.getString(cursor, "department");
		    String joiningYear = IDataUtil.getString(cursor, "joiningYear");
		
		    if (department == null || department.trim().isEmpty()) {
		        throw new ServiceException("Department tidak boleh kosong.");
		    }
		
		    if (joiningYear == null || joiningYear.trim().isEmpty()) {
		        throw new ServiceException("Joining Year tidak boleh kosong.");
		    }
		
		    department = department.trim().toUpperCase();
		    joiningYear = joiningYear.trim();
		
		    SequenceData sequenceData = getSequenceData(department, joiningYear);
		
		    int currentBatch;
		    int currentSequence;
		
		    boolean isNewBatch = false;
		
		    synchronized (sequenceData) {
		        currentBatch = sequenceData.getBatch();
		        currentSequence = sequenceData.getSequence();
		        currentSequence++;
		
		        // Overflow
		        if (currentSequence > MAX_SEQUENCE) {
		            currentBatch++;
		            currentSequence = 1;
		            isNewBatch = true;
		        }
		
		        sequenceData.setBatch(currentBatch);
		        sequenceData.setSequence(currentSequence);
		    }
		
		    IDataUtil.put(cursor, "batch", String.valueOf(currentBatch));
		    IDataUtil.put(cursor, "sequence", String.valueOf(currentSequence));
		    IDataUtil.put(cursor, "isNewBatch", String.valueOf(isNewBatch));
		    IDataUtil.put(cursor, "status", "SUCCESS");
		} catch(Exception e){
		    throw new ServiceException(null, "Unexpected error while reserving employee sequence.", null, e, null);
		}
		finally {
		    cursor.destroy();
		}
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	 /**Maximum Sequence**/
	private static final int MAX_SEQUENCE = 5;
	
	/**Mock Database**/
	private static final ConcurrentHashMap<String, SequenceData>
	        SEQUENCE_STORE = new ConcurrentHashMap<>();
	
	
	/**Sequence Information**/
	private static class SequenceData {
	    private int batch;
	    private int sequence;
	    
	    SequenceData(int batch, int sequence) {
	        this.batch = batch;
	        this.sequence = sequence;
	    }
	
	    public int getBatch() {
	        return batch;
	    }
	
	    public void setBatch(int batch) {
	        this.batch = batch;
	    }
	
	    public int getSequence() {
	        return sequence;
	    }
	
	    public void setSequence(int sequence) {
	        this.sequence = sequence;
	    }
	}
	
	/**Generate Key**/
	private static String generateKey(String department, String year) {
	    return department + "-" + year;
	}
	
	/**Get Current Sequence**/
	private static SequenceData getSequenceData(String department, String year) {
	    String key = generateKey(department, year);
	    return SEQUENCE_STORE.computeIfAbsent(key, k -> new SequenceData(1, 0));
	}
	
	/**Format Sequence**/
	private static String formatSequence(int sequence) {
	    return String.format("%06d", sequence);
	}
	
	/**Format Batch**/
	private static String formatBatch(int batch) {
	    return String.format("%02d", batch);
	}
	// --- <<IS-END-SHARED>> ---
}

