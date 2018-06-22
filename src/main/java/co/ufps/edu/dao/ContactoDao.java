package co.ufps.edu.dao;

import java.util.LinkedList;
import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import co.ufps.edu.bd.SpringDbMgr;
import co.ufps.edu.dto.Contacto;

/**
 * @author Miguel Gonzalez
 *
 */
public class ContactoDao {

	private SpringDbMgr springDbMgr;
	
	public ContactoDao() {
	  springDbMgr = new SpringDbMgr();
	}
	
	/**
	 * Metodo que consulta en base de datos todos los contactos existentes.
	 * 
	 * @return Una lista con todos los contactos
	 */
	public List<Contacto> getContactos() {

		// Lista para retornar con los datos
	    List<Contacto> contactos = new LinkedList<>();

	    // Consulta para realizar en base de datos
	    SqlRowSet sqlRowSet = springDbMgr.executeQuery(" SELECT * FROM contacto ");

	    // Recorre cada registro obtenido de base de datos
	    while (sqlRowSet.next()) {
	      // Objeto en el que sera guardada la informacion del registro
	      Contacto contacto = new Contacto();

	      contacto.setId(sqlRowSet.getLong("id"));
	      contacto.setNombre(sqlRowSet.getString("nombre"));

	      // Guarda el registro para ser retornado
	      contactos.add(contacto);
	    }

	    // Retorna todos los contactos desde base de datos
	    return contactos;
	}
	
	/**
	   * Método que registra un contacto en base de datos
	   * 
	   * @param contacto Objeto con todos los datos del contacto a registrar.
	   * @return El resultado de la acción.
	   */
	public String registrarConacto(Contacto contacto) {
	    SpringDbMgr springDbMgr = new SpringDbMgr();

	    // Agrego los datos del registro (nombreColumna/Valor)
	    MapSqlParameterSource map = new MapSqlParameterSource();
	    map.addValue("nombre", contacto.getNombre());

	    // Armar la sentencia de actualización debase de datos
	    String query = "INSERT INTO contacto(nombre) VALUES(:nombre)";

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
	        : "La información del contacto ya se encuentra en el sistema.";

	  }

	  
	  /**
	   * Método que consulta en base de datos la informacion de  un contacto
	   * 
	   * @param idContacto Identificador de contacto.
	   * @return La informacion de un contacto en un objeto.
	   */
	  public Contacto obtenerContactoPorId(long idContacto) {
	    // Lista para retornar con los datos
	    Contacto contacto = new Contacto();

	    // Consulta para realizar en base de datos
	    MapSqlParameterSource map = new MapSqlParameterSource();
	    map.addValue("id", idContacto);
	    SqlRowSet sqlRowSet = springDbMgr.executeQuery(" SELECT * FROM contacto WHERE id = :id", map);

	    // Consulto si el contacto existe
	    if (sqlRowSet.next()) {
	      // Almaceno los datos de contacto
	      contacto.setId(sqlRowSet.getLong("id"));
	      contacto.setNombre(sqlRowSet.getString("nombre"));
	    }

	    // Retorna contacto desde base de datos
	    return contacto;
	  }

	  
	  /**
	   * Método que consulta en base de datos la informacion de  un contacto
	   * 
	   * @param idContacto Identificador de contacto.
	   * @return La informacion de un contacto en un objeto.
	   */
	  public String editarContacto(Contacto contacto) {
	    SpringDbMgr springDbMgr = new SpringDbMgr();

	    // Agrego los datos del registro (nombreColumna/Valor)

	    MapSqlParameterSource map = new MapSqlParameterSource();
	    map.addValue("id", contacto.getId());
	    map.addValue("nombre", contacto.getNombre());

	    // Armar la sentencia de actualización debase de datos
	    String query = "UPDATE contacto SET nombre = :nombre  WHERE id = :id";

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
	        : "El contacto ya se encuentra en el sistema.";
	  }
	  
	  
	  public String eliminarContacto(Contacto contacto) {
		    SpringDbMgr springDbMgr = new SpringDbMgr();

		    // Agrego los datos de la eliminación (nombreColumna/Valor)
		    MapSqlParameterSource map = new MapSqlParameterSource();
		    map.addValue("id", contacto.getId());

		    // Armar la sentencia de eliminación debase de datos
		    String query = "DELETE FROM contacto WHERE id = :id";

		    // Ejecutar la sentencia
		    int result = 0;
		    try {
		      result = springDbMgr.executeDml(query, map);
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
		    // Si hubieron filas afectadas es por que si hubo registro, en caso contrario muestra el mensaje
		    // de error.
		    return (result == 1) ? "Eliminacion exitosa" : "No fue posible eliminar el contacto";
	  }
	  
	  
	  
	  /*
	   *  Método que obtiene la cantidad de contactos registrados
	   */
	  public int getCantidadContactos() {
		  	int cant = 0;
		    // Consulta para realizar en base de datos
		    SqlRowSet sqlRowSet = springDbMgr.executeQuery(" SELECT COUNT(*) cantidad FROM contacto "); 
		    
		    if (sqlRowSet.next()) {
		    	cant = sqlRowSet.getInt("cantidad");
		    }
		    return cant;
	  }
}
