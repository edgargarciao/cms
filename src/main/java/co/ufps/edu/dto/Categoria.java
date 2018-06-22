package co.ufps.edu.dto;

import java.util.ArrayList;
import java.util.List;
import org.springframework.util.StringUtils;

public class Categoria {

  private long id;
  private String nombre;
  private String descripcion;
  private int orden;
  private List<SubCategoria> subcategorias;
  private String contenido;

  public Categoria() {
    subcategorias = new ArrayList<>();
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
    return (!StringUtils.isEmpty(this.nombre) && !StringUtils.isEmpty(this.descripcion));
  }

  @Override
  public String toString() {
    return "Categoria [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion
        + ", orden=" + orden + "]";
  }

  public List<SubCategoria> getSubcategorias() {
    return subcategorias;
  }

  public void setSubcategorias(List<SubCategoria> subcategorias) {
    this.subcategorias = subcategorias;
  }

  public void agregarSubcategoria(SubCategoria subCategoria) {
    this.subcategorias.add(subCategoria);

  }

  public String getContenido() {
    return contenido;
  }

  public void setContenido(String contenido) {
    this.contenido = contenido;
  }
  
  public boolean haySubCategorias() {
    return this.subcategorias.size()>0;
  }

}
