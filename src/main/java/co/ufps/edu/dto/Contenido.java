package co.ufps.edu.dto;

import java.io.File;
import java.util.ArrayList;
import org.springframework.util.StringUtils;

public class Contenido {

  private long id;
  private String nombre;
  private String tipoAsociacion;
  private long asociacion;
  private String contenido;
  private TipoContenido tipoContenido;
  private String url;
  private ArrayList<String> conn;

  public Contenido() {
    
  }
  
  public Contenido(long id, String nombre, String tipoAsociacion, long asociacion, String contenido,
      TipoContenido tipoContenido, String url) {
    super();
    this.id = id;
    this.nombre = nombre;
    this.tipoAsociacion = tipoAsociacion;
    this.asociacion = asociacion;
    this.contenido = contenido;
    this.tipoContenido = tipoContenido;
    this.url = url;
  }



  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getTipoAsociacion() {
    return tipoAsociacion;
  }

  public void setTipoAsociacion(String tipoAsociacion) {
    this.tipoAsociacion = tipoAsociacion;
  }

  public long getAsociacion() {
    return asociacion;
  }

  public void setAsociacion(long asociacion) {
    this.asociacion = asociacion;
  }

  public String getContenido() {
    return contenido;
  }

  public void setContenido(String contenido) {
    this.contenido = contenido;
  }

  public boolean isValidoParaRegistrar() {
    if(this.tipoContenido.getId() == 1) {
      return (!StringUtils.isEmpty(this.nombre)
          && (conn.size()>0)
          && !StringUtils.isEmpty(this.tipoContenido.getNombre())
          && !StringUtils.isEmpty(this.tipoAsociacion) && this.asociacion != 0);
     
    }else {
      return (!StringUtils.isEmpty(this.nombre)
          && (this.contenido.length()>0)
          && !StringUtils.isEmpty(this.tipoContenido.getNombre())
          && !StringUtils.isEmpty(this.tipoAsociacion) && this.asociacion != 0);
      
    }
    
    
    }

  public TipoContenido getTipoContenido() {
    return tipoContenido;
  }

  public void setTipoContenido(TipoContenido tipoContenido) {
    this.tipoContenido = tipoContenido;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  @Override
  public String toString() {
    return "Contenido [id=" + id + ", nombre=" + nombre + ", tipoAsociacion=" + tipoAsociacion
        + ", asociacion=" + asociacion + ",  \n contenido=" + contenido + ", \n tipoContenido="
        + tipoContenido + ", url=" + url + "]";
  }

  public ArrayList<String> getConn() {
    return conn;
  }

  public void setConn(ArrayList<String> conn) {
    this.conn = conn;
  }

}
