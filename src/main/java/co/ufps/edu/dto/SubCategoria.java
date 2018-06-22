package co.ufps.edu.dto;

import org.springframework.util.StringUtils;

public class SubCategoria {

  private long id;
  private String nombre;
  private String descripcion;
  private int orden;
  private Categoria categoria;
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
    return (!StringUtils.isEmpty(this.nombre) && !StringUtils.isEmpty(this.descripcion)  && (this.categoria.getId()>0));
  }
  
  

  public Categoria getCategoria() {
    return categoria;
  }

  public void setCategoria(Categoria categoria) {
    this.categoria = categoria;
  }

  @Override
  public String toString() {
    return "SubCategoria [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion
        + ", orden=" + orden + ", categoria=" + categoria + "]";
  }

  public Contenido getContenido() {
    return contenido;
  }

  public void setContenido(Contenido contenido) {
    this.contenido = contenido;
  }

}
