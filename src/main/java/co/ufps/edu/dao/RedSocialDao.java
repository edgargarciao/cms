package co.ufps.edu.dao;

import java.util.LinkedList;
import java.util.List;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import co.ufps.edu.bd.SpringDbMgr;
import co.ufps.edu.dto.RedSocial;

public class RedSocialDao {

	private SpringDbMgr springDbMgr;
	
	public RedSocialDao() {
	  springDbMgr = new SpringDbMgr();
	}
	
	/**
	 * Metodo que consulta en base de datos todos las redes sociales existentes.
	 * 
	 * @return Una lista con todos las redes sociales
	 */
	public List<RedSocial> getRedesSociales() {

		// Lista para retornar con los datos
	    List<RedSocial> redesSociales = new LinkedList<>();

	    // Consulta para realizar en base de datos
	    SqlRowSet sqlRowSet = springDbMgr.executeQuery(" SELECT * FROM redsocial ");

	    // Recorre cada registro obtenido de base de datos
	    while (sqlRowSet.next()) {
	      // Objeto en el que sera guardada la informacion del registro
	      RedSocial redSocial = new RedSocial();

	      redSocial.setId(sqlRowSet.getLong("id"));
	      redSocial.setNombre(sqlRowSet.getString("nombre"));
	      redSocial.setUrl(sqlRowSet.getString("url"));
	      redSocial.setLogo(sqlRowSet.getString("logo"));
	      
	      // Guarda el registro para ser retornado
	      redesSociales.add(redSocial);
	    }

	    // Retorna todas las redes socials desde base de datos
	    return redesSociales;
	}
	
	/**
	   * Método que registra una red social en base de datos
	   * 
	   * @param redSocial Objeto con todos los datos de la red social a registrar.
	   * @return El resultado de la acción.
	   */
	public String registrarRedSocial(RedSocial redSocial) {

	    // Agrego los datos del registro (nombreColumna/Valor)
	    MapSqlParameterSource map = new MapSqlParameterSource();
	    map.addValue("nombre", redSocial.getNombre());
	    map.addValue("url", redSocial.getUrl());
	    map.addValue("logo",redSocial.getLogo());

	    // Armar la sentencia de actualización debase de datos
	    String query = "INSERT INTO redsocial(nombre, url, logo) VALUES(:nombre, :url, :logo)";

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
	        : "La información de la red social ya se encuentra en el sistema.";
	  }

	  
	  /**
	   * Método que consulta en base de datos la informacion de una red social
	   * 
	   * @param idRedSocial Identificador de la red social.
	   * @return La informacion de una red social en un objeto.
	   */
	  public RedSocial obtenerRedSocialPorId(long idRedSocial) {
	    // Lista para retornar con los datos
		  RedSocial redSocial = new RedSocial();

	    // Consulta para realizar en base de datos
	    MapSqlParameterSource map = new MapSqlParameterSource();
	    map.addValue("id", idRedSocial);
	    SqlRowSet sqlRowSet = springDbMgr.executeQuery(" SELECT * FROM redsocial WHERE id = :id", map);

	    // Consulto si la red social existe
	    if (sqlRowSet.next()) {
	      // Almaceno los datos de contacto
	      redSocial.setId(sqlRowSet.getLong("id"));
	      redSocial.setNombre(sqlRowSet.getString("nombre"));
	      redSocial.setUrl(sqlRowSet.getString("url"));
	      redSocial.setLogo(sqlRowSet.getString("logo"));	      
	      
	    }

	    // Retorna red social desde base de datos
	    return redSocial;
	  }

	  
	  /**
	   * Método que consulta en base de datos la informacion de una red social
	   * 
	   * @param redSocial Objeto con la información de la red social
	   * @return Mesaje de confirmación del proceso.
	   */
	  public String editarRedSocial(RedSocial redSocial) {

	    // Agrego los datos del registro (nombreColumna/Valor)

	    MapSqlParameterSource map = new MapSqlParameterSource();
	    map.addValue("id", redSocial.getId());
	    map.addValue("nombre", redSocial.getNombre());
	    map.addValue("url", redSocial.getUrl());
	    map.addValue("logo", redSocial.getLogo());

	    // Armar la sentencia de actualización debase de datos
	    String query = "UPDATE redsocial SET nombre = :nombre, url = :url, logo = :logo   WHERE id = :id";

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
	        : "La red social ya se encuentra en el sistema.";
	  }
	  
	  
	  /**
	   * Método que elimina en base de datos la informacion de una red social
	   * 
	   * @param redSocial Objeto con la información de la red social
	   * @return Mesaje de confirmación del proceso.
	   */
	  public String eliminarRedSocial(RedSocial redSocial) {

		    // Agrego los datos de la eliminación (nombreColumna/Valor)
		    MapSqlParameterSource map = new MapSqlParameterSource();
		    map.addValue("id", redSocial.getId());

		    // Armar la sentencia de eliminación debase de datos
		    String query = "DELETE FROM redsocial WHERE id = :id";

		    // Ejecutar la sentencia
		    int result = 0;
		    try {
		      result = springDbMgr.executeDml(query, map);
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
		    // Si hubieron filas afectadas es por que si hubo registro, en caso contrario muestra el mensaje
		    // de error.
		    return (result == 1) ? "Eliminacion exitosa" : "No fue posible eliminar la red social";
	  }


	  /**
	   * Método que obtiene la cantidad de redes sociales registradas
	   * 
	   * @return Cantidad de redes sociales registradas
	   */
	  public int getCantidadRedesSociales() {
		  	int cant = 0;
		    // Consulta para realizar en base de datos
		    SqlRowSet sqlRowSet = springDbMgr.executeQuery(" SELECT COUNT(*) cantidad FROM redsocial"); 
		    
		    if (sqlRowSet.next()) {
		    	cant = sqlRowSet.getInt("cantidad");
		    }
		    return cant;
	  }
}
