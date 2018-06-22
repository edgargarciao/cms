package co.ufps.edu.dto;

import org.springframework.web.multipart.MultipartFile;

public class Logo {

  private long id;
  private String tipo;
  private MultipartFile contenido;
  private String imBase64image;
  
  public long getId() {
    return id;
  }
  public void setId(long id) {
    this.id = id;
  }
  public String getTipo() {
    return tipo;
  }
  public void setTipo(String tipo) {
    this.tipo = tipo;
  }
  public MultipartFile getContenido() {
    return contenido;
  }
  public void setContenido(MultipartFile contenido) {
    this.contenido = contenido;
  }
  public String getImBase64image() {
    return imBase64image;
  }
  public void setImBase64image(String imBase64image) {
    this.imBase64image = imBase64image;
  }
  @Override
  public String toString() {
    return "Logo [id=" + id + ", tipo=" + tipo + "]";
  }

  

}
