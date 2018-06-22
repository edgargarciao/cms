package co.ufps.edu.dao;

import org.apache.commons.io.Charsets;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import co.ufps.edu.bd.SpringDbMgr;
import co.ufps.edu.constantes.Constantes;
import co.ufps.edu.dto.Contenido;

/**
 * Clase que permite acceder a la capa de datos en el entorno de sub-categorias.
 * @author ufps
 *
 */
public class ComponenteDao {
  
  private SpringDbMgr springDbMgr;

  public ComponenteDao() {
    springDbMgr = new SpringDbMgr();
  }
  
  public Contenido obtenerContenidoComponentePorId(long idComponente, String tipo) {
    
    // Lista para retornar con los datos
    Contenido contenido = new Contenido();
    String tip = tipo;
    if((tip.toLowerCase()).equalsIgnoreCase("proximaactividad")) {
      tip = Constantes.ACTIVIDAD;
    }else if((tip).equalsIgnoreCase("noticia")) {
      tip = Constantes.NOTICIA;
    }else if((tip).equalsIgnoreCase("subcategoria")) {
      tip = Constantes.SUBCATEGORIA;
    }else if((tip).equalsIgnoreCase("novedad")) {
      tip = Constantes.NOVEDAD;
    }
    tipo = tipo.toLowerCase();
    
    // Consulta para realizar en base de datos
    MapSqlParameterSource map = new MapSqlParameterSource();
    map.addValue("id", idComponente);
    map.addValue("tip", tip);
    SqlRowSet sqlRowSet = springDbMgr.executeQuery( " SELECT    "+tipo+".ID                     id         ,"
                                                  + "           "+tipo+".NOMBRE                 nombre     ,"
                                                  
                                                  + "           contenido.id                    idContenido            ,"
                                                  + "           contenido.contenido             contenido              ,"
                                                  + "           contenido.TipoContenido_id      tipoContenido          ,"
                                                  + "           contenido.asociacion            asociacion             ,"
                                                  + "           contenido.tipoasociacion        tipoasociacion         ,"
                                                  + "           contenido.titulo                titulo                 "
                                                  
                                                  + "   FROM    "+tipo+"                                               "
                                                  + "INNER JOIN contenido  ON contenido.asociacion = "+tipo+".id    "
                                                  + "WHERE      "+tipo+".ID = :id                                   "
                                                  + "AND contenido.tipoasociacion = :tip ", map);

    // Consulto si la categoria existe
    if (sqlRowSet.next()) {
      
      contenido = new Contenido();
      contenido.setId(sqlRowSet.getLong("idContenido"));      
      byte []a = (byte[]) sqlRowSet.getObject("contenido");
      String res = new String(a,Charsets.UTF_8);
      contenido.setContenido(res);      
      contenido.setAsociacion(sqlRowSet.getLong("asociacion"));
      contenido.setNombre(sqlRowSet.getString("titulo"));
      contenido.setTipoAsociacion(sqlRowSet.getString("tipoasociacion"));
    }

    // Retorna la categoria desde base de datos
    return contenido;
  }
}
