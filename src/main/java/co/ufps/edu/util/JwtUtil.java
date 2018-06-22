package co.ufps.edu.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Clase que permite usar servicios para el manejo del JWT.
 * @author ufps
 *
 */
public class JwtUtil {

	// @Value("${jwt.secret}")
	private String secret = "secret";
	private static JwtUtil jwtUtil;
	
	public static JwtUtil getInstancia() {
	  if(jwtUtil == null) {
	    jwtUtil = new JwtUtil();
	  }
	  return jwtUtil;
	}
	
	/**
	 * Tries to parse specified String as a JWT token. If successful, returns User
	 * object with username, id and role prefilled (extracted from token). If
	 * unsuccessful (token is invalid or not containing all required user
	 * properties), simply returns null.
	 * 
	 * @param token
	 *            the JWT token to parse
	 * @return the User object extracted from specified token or null if a token is
	 *         invalid.
	 */

	public String parseToken(String token) {
		try {
			Claims body = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
			String codigo = body.get("correo").toString();
			return codigo;
		} catch (JwtException | ClassCastException e) {
			return "";
		} catch(Exception ex) {
			return "";
		}
		
	}

	/**
	 * Generates a JWT token containing username as subject, and userId and role as
	 * additional claims. These properties are taken from the specified User object.
	 * Tokens validity is infinite.
	 * 
	 * @param u
	 *            the user for which the token will be generated
	 * @return the JWT token
	 */
	public String generateToken(String rol, String correo) {
		Claims claims = Jwts.claims().setSubject("users/" + rol);
		claims.put("correo", correo);
		claims.put("rol", rol);

		return Jwts.builder()
				.setClaims(claims)
				.signWith(SignatureAlgorithm.HS512, secret)
				.compact();
	}
}
