package co.ufps.edu.dto;

import java.sql.Date;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class Noticia {

  private long id;
  private String nombre;
  private String descripcion;
  private Date fecha;
  private int orden;
  private MultipartFile imagen1;
  private String im1Base64image;
  private Contenido contenido;
  
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

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public int getOrden() {
    return orden;
  }

  public void setOrden(int orden) {
    this.orden = orden;
  }

  public boolean isValidoParaRegistrar() {
    return (!StringUtils.isEmpty(this.nombre) && !StringUtils.isEmpty(this.descripcion) && !StringUtils.isEmpty(fecha.toString()));
  }
  
  public boolean isValidoParaActualizar() {
    return (!StringUtils.isEmpty(this.nombre) && !StringUtils.isEmpty(this.descripcion) && !StringUtils.isEmpty(fecha.toString()) && !StringUtils.isEmpty(im1Base64image) );
  }


  @Override
  public String toString() {
    return "Noticia [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", fecha="
        + fecha + ", orden=" + orden + "]";
  }

  public Date getFecha() {
    return fecha;
  }

  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }

  public MultipartFile getImagen1() {
    return imagen1;
  } 

  public void setImagen1(MultipartFile imagen1) {
    this.imagen1 = imagen1;
  }

  public String getIm1Base64image() {
    return im1Base64image;
  }

  public void setIm1Base64image(String im1Base64image) {
    this.im1Base64image = im1Base64image;
  }

  public Contenido getContenido() {
    return contenido;
  }

  public void setContenido(Contenido contenido) {
    this.contenido = contenido;
  }
  
}
