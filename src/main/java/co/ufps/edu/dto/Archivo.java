package co.ufps.edu.dto;

import java.io.InputStream;

public class Archivo {

  private String nombre;
  private InputStream contenido;
  private String tipo;
  
  public String getNombre() {
    return nombre;
  }
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  public InputStream getContenido() {
    return contenido;
  }
  public void setContenido(InputStream contenido) {
    this.contenido = contenido;
  }
  public String getTipo() {
    return tipo;
  }
  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

}
