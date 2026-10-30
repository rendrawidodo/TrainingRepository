package Demo.utils.security;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.util.Date;
import javax.crypto.SecretKey;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
// --- <<IS-END-IMPORTS>> ---

public final class jwt

{
	// ---( internal utility methods )---

	final static jwt _instance = new jwt();

	static jwt _newInstance() { return new jwt(); }

	static jwt _cast(Object o) { return (jwt)o; }

	// ---( server methods )---




	public static final void generateJWT (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(generateJWT)>> ---
		// @sigtype java 3.5
		// [i] field:0:required username
		// [i] field:0:required secret
		// [i] field:0:required ttl
		// [o] field:0:required token
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		String username = IDataUtil.getString(pipelineCursor, "username");
		String secret = IDataUtil.getString(pipelineCursor, "secret");
		String ttlStr = IDataUtil.getString(pipelineCursor, "ttl");
		
		try {
		    if (secret == null || secret.trim().isEmpty()) {
		        throw new IllegalArgumentException("Secret key cannot be null or empty.");
		    }
		    if (username == null || username.trim().isEmpty()) {
		        throw new IllegalArgumentException("Username cannot be null or empty.");
		    }
		
		    long ttl = 3600L; // default 1 hours
		    if (ttlStr != null && !ttlStr.trim().isEmpty()) {
		        ttl = Long.parseLong(ttlStr.trim());
		    }
		
		    SecretKey key = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
		
		    long nowMillis = System.currentTimeMillis();
		    Date now = new Date(nowMillis);
		    Date exp = new Date(nowMillis + (ttl * 1000L));
		
		    // 4. Generate JWT
		    String token = Jwts.builder()
		            .subject(username)
		            .issuedAt(now)
		            .expiration(exp)
		            .signWith(key)
		            .compact();
		
		    IDataUtil.put(pipelineCursor, "token", token);
		    IDataUtil.put(pipelineCursor, "error", null);
		
		} catch (Exception e) {
		    IDataUtil.put(pipelineCursor, "token", "");
		    IDataUtil.put(pipelineCursor, "error", "Error generating token: " + e.getMessage());
		} finally {
		    pipelineCursor.destroy();
		}
		// --- <<IS-END>> ---

                
	}



	public static final void validateJWT (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(validateJWT)>> ---
		// @sigtype java 3.5
		// [i] field:0:required secret
		// [i] field:0:required token
		String secret = ValuesEmulator.getString(pipeline, "secret");
		String token = ValuesEmulator.getString(pipeline, "token");
		 
		SecretKey key = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
		
		String result=Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();
		
		ValuesEmulator.put(pipeline, "result", result);
		// --- <<IS-END>> ---

                
	}
}

