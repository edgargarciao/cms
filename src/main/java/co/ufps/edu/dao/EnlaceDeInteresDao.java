package co.ufps.edu.dao;

import java.util.LinkedList;
import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import co.ufps.edu.bd.SpringDbMgr;
import co.ufps.edu.dto.EnlaceDeInteres;

public class EnlaceDeInteresDao {

	private SpringDbMgr springDbMgr;
	
	public EnlaceDeInteresDao() {
	  springDbMgr = new SpringDbMgr();
	}
	

	/**
	 * Metodo que consulta en base de datos todas los enlaces existentes.
	 * 
	 * @return Una lista con todos los enlaces
	 */
	public List<EnlaceDeInteres> getEnlacesDeInteres() {

		// Lista para retornar con los datos
	    List<EnlaceDeInteres> enlacesDeInteres = new LinkedList<>();
	    // Consulta para realizar en base de datos
	    SqlRowSet sqlRowSet = springDbMgr.executeQuery(" SELECT * FROM enlaceinteres ");
	    
	    // Recorre cada registro obtenido de base de datos
	    while (sqlRowSet.next()) {
	      // Objeto en el que sera guardada la informacion del registro
	      EnlaceDeInteres enlaceDeInteres = new EnlaceDeInteres();

	      enlaceDeInteres.setId(sqlRowSet.getLong("id"));
	      enlaceDeInteres.setNombre(sqlRowSet.getString("nombre"));
	      enlaceDeInteres.setUrl(sqlRowSet.getString("url"));
	      // Guarda el registro para ser retornado
	      enlacesDeInteres.add(enlaceDeInteres);
	    }
	    // Retorna todos los enlaces de interes desde base de datos
	    return enlacesDeInteres;
	}
	
	/**
	   * Método que registra un enlace de interes en base de datos
	   * 
	   * @param enlaceDeInteres Objeto con todos los datos del enlace a registrar.
	   * @return El resultado de la acción.
	   */
	public String registrarEnlaceDeInteres(EnlaceDeInteres enlaceDeInteres) {	    

	    // Agrego los datos del registro (nombreColumna/Valor)
	    MapSqlParameterSource map = new MapSqlParameterSource();
	    map.addValue("nombre", enlaceDeInteres.getNombre());
	    map.addValue("url", enlaceDeInteres.getUrl());
	    
	    // Armar la sentencia de actualización de base de datos
	    String query = "INSERT INTO enlaceinteres(nombre, url) VALUES(:nombre, :url)";

	    // Ejecutar la sentencia
	    int result = 0;
	    try {
	      result = springDbMgr.executeDml(query, map);
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    // Si hubieron filas afectadas es por que si hubo registro, en caso contrario muestra el mensaje
	    // de error.
	    return (result == 1) ? "Registro exitoso"
	        : "La información del enlace de interes ya se encuentra en el sistema.";
	  }

	  
	  /**
	   * Método que consulta en base de datos la informacion de un enlace de interes
	   * 
	   * @param idEnlaceDeInteres Identificador del enlace.
	   * @return La informacion de un enlace de interes en un objeto.
	   */
	  public EnlaceDeInteres obtenerEnlaceDeInteresPorId(long idEnlaceDeInteres) {
	    // Lista para retornar con los datos
		  EnlaceDeInteres enlaceDeInteres = new EnlaceDeInteres();

	    // Consulta para realizar en base de datos
	    MapSqlParameterSource map = new MapSqlParameterSource();
	    map.addValue("id", idEnlaceDeInteres);
	    SqlRowSet sqlRowSet = springDbMgr.executeQuery(" SELECT * FROM enlaceinteres WHERE id = :id", map);
	    
	    // Consulto si la novedad existe
	    if (sqlRowSet.next()) {
	      // Almaceno los datos de contacto
	    	enlaceDeInteres.setId(sqlRowSet.getLong("id"));
	    	enlaceDeInteres.setNombre(sqlRowSet.getString("nombre"));
	    	enlaceDeInteres.setUrl(sqlRowSet.getString("url"));
	    }

	    // Retorna enlace de interes desde base de datos
	    return enlaceDeInteres;
	  }

	  
	  /**
	   * Método que consulta en base de datos la informacion de un enlace de interes
	   * 
	   * @param idEnlaceDeInteres Identificador de enlace de interes.
	   * @return La informacion de un enlace de interes en un objeto.
	   */
	  public String editarEnlaceDeInteres(EnlaceDeInteres enlaceDeInteres) {
	    

	    // Agrego los datos del registro (nombreColumna/Valor)

	    MapSqlParameterSource map = new MapSqlParameterSource();
	    map.addValue("id", enlaceDeInteres.getId());
	    map.addValue("nombre", enlaceDeInteres.getNombre());
	    map.addValue("url", enlaceDeInteres.getUrl());

	    // Armar la sentencia de actualización debase de datos
	    String query = "UPDATE enlaceinteres SET nombre = :nombre, url = :url  WHERE id = :id";

	    // Ejecutar la sentencia
	    int result = 0;
	    try {
	      result = springDbMgr.executeDml(query, map);
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    // Si hubieron filas afectadas es por que si hubo registro, en caso contrario muestra el mensaje
	    // de error.
	    return (result == 1) ? "Actualizacion exitosa"
	        : "El enlace de interes ya se encuentra en el sistema.";
	  }
	  
	  
	  public String eliminarEnlaceDeInteres(EnlaceDeInteres enlaceDeInteres) {
		    

		    // Agrego los datos de la eliminación (nombreColumna/Valor)
		    MapSqlParameterSource map = new MapSqlParameterSource();
		    map.addValue("id", enlaceDeInteres.getId());

		    // Armar la sentencia de eliminación debase de datos
		    String query = "DELETE FROM enlaceinteres WHERE id = :id";

		    // Ejecutar la sentencia
		    int result = 0;
		    try {
		      result = springDbMgr.executeDml(query, map);
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
		    // Si hubieron filas afectadas es por que si hubo registro, en caso contrario muestra el mensaje
		    // de error.
		    return (result == 1) ? "Eliminacion exitosa" : "No fue posible eliminar el enlace de interes";
	  }
	  
	  
	  /*
	   *  Método que obtiene la cantidad de enlaces de interes registrados
	   */
	  public int getCantidadEnlacesDeInteres() {
		  	int cant = 0;
		    // Consulta para realizar en base de datos
		    SqlRowSet sqlRowSet = springDbMgr.executeQuery(" SELECT COUNT(*) cantidad FROM enlaceinteres "); 
		    
		    if (sqlRowSet.next()) {
		    	cant = sqlRowSet.getInt("cantidad");
		    }
		    return cant;
	  }
}
