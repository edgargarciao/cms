package co.ufps.edu.dto;

import java.sql.Date;

public class Imagen {

  private int id;
  private String imagen;
  private String descripcion;
  
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public String getImagen() {
    return imagen;
  }
  public void setImagen(String imagen) {
    this.imagen = imagen;
  }
  public String getDescripcion() {
    return descripcion;
  }
  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }
  
  
}
