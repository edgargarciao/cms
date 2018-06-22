package co.ufps.edu.dto;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class Novedad {

  private long id;
  private String nombre;
  private Date fecha;
  private MultipartFile imagen;
  private String imBase64image;
  private String fechaEnFormato;
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

  public Date getFecha() {
    return fecha;
  }

  public void setFecha(Date fecha) {
    this.fecha = fecha;
    LocalDate today = LocalDate.of(fecha.getYear(), fecha.getMonth()+1, fecha.getDate());
    String mes = today.getMonth().getDisplayName(TextStyle.FULL, new Locale("es","ES"));
    this.fechaEnFormato = fecha.getDate()+" de "+mes+" de "+(fecha.getYear()+1900);
  }

  public boolean isValidoParaRegistrar() {
    return (!StringUtils.isEmpty(this.nombre) && !StringUtils.isEmpty(this.fecha));
  }

  public boolean isValidoParaActualizar() {
    return (!StringUtils.isEmpty(this.nombre) && !StringUtils.isEmpty(this.fecha) && !StringUtils.isEmpty(imBase64image));
  }
  
  @Override
  public String toString() {
    return "Novedad [id=" + id + ", nombre=" + nombre + ", fecha=" + fecha + "]";
  }

  public MultipartFile getImagen() {
    return imagen;
  }

  public void setImagen(MultipartFile imagen) {
    this.imagen = imagen;
  }

  public String getImBase64image() {
    return imBase64image;
  }

  public void setImBase64image(String imBase64image) {
    this.imBase64image = imBase64image;
  }

  public String getFechaEnFormato() {
    return fechaEnFormato;
  }

  public void setFechaEnFormato(String fechaEnFormato) {
    this.fechaEnFormato = fechaEnFormato;
  }

  public Contenido getContenido() {
    return contenido;
  }

  public void setContenido(Contenido contenido) {
    this.contenido = contenido;
  }
  
}
