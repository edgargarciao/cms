package co.ufps.edu.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import co.ufps.edu.bd.SpringDbMgr;

/**
 * Clase que permite acceder a la capa de datos en el entorno de acceso.
 * @author ufps
 *
 */
public class LoginDao {

	SpringDbMgr springDbMgr = new SpringDbMgr();

	public String authenticate(String correo, String contraseña) {
		try{
    		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
    		mapSqlParameterSource.addValue("correo", correo);
    		mapSqlParameterSource.addValue("pass", contraseña);
    		SqlRowSet sqlRowSet = springDbMgr.executeQuery("SELECT id FROM usuario "
    				+ "	WHERE correoInstitucional = :correo AND Contraseña = :pass", mapSqlParameterSource);
    				
    		
    		if ((sqlRowSet.next())) {
    			return "admin";
    		}
		}catch(Exception e) {
		  
		}
		
		
		return "";
		
	}

}
