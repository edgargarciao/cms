package co.ufps.edu.dto;

import java.sql.Date;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class Actividad {

  private long id;
  private String nombre;
  private String lugar;
  private Date fechaInicial;
  private Date fechaFinal;
  private Contenido contenido;
  private String fechaEnFormato;
  
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

  public boolean isValidoParaRegistrar() {
    return (!StringUtils.isEmpty(this.nombre) && !StringUtils.isEmpty(this.lugar) && !StringUtils.isEmpty(this.fechaInicial.toString()) && !StringUtils.isEmpty(this.fechaFinal.toString()));
  }

  public String getLugar() {
    return lugar;
  }

  public void setLugar(String lugar) {
    this.lugar = lugar;
  }

  public Date getFechaInicial() {
    return fechaInicial;
  }

  public void setFechaInicial(Date fechaInicial) {
    this.fechaInicial = fechaInicial;
  }

  public Date getFechaFinal() {
    return fechaFinal;
  }

  public void setFechaFinal(Date fechaFinal) {
    this.fechaFinal = fechaFinal;
  }


  @Override
  public String toString() {
    return "Actividad [id=" + id + ", nombre=" + nombre + ", lugar=" + lugar + ", fechaInicial="
        + fechaInicial + ", fechaFinal=" + fechaFinal + "]";
  }
  
  public boolean isValidoParaActualizar() {
    return (!StringUtils.isEmpty(this.nombre) && !StringUtils.isEmpty(this.lugar) && !StringUtils.isEmpty(fechaInicial.toString()) && !StringUtils.isEmpty(fechaFinal.toString())) ;
  }

  public Contenido getContenido() {
    return contenido;
  }

  public void setContenido(Contenido contenido) {
    this.contenido = contenido;
  }

  public String getFechaEnFormato() {
    return fechaEnFormato;
  }

  public void setFechaEnFormato(String fechaEnFormato) {
    this.fechaEnFormato = fechaEnFormato;
  }

  public void crearFechaFormato() {
    if(fechaInicial.getTime() == fechaFinal.getTime()) {
      fechaEnFormato = fechaInicial.toString();
    }else {
      fechaEnFormato = "Desde "+fechaInicial.toString()+" Hasta "+fechaFinal.toString(); 
    }
    
  }
  
  public static void main(String[] args) {
    Date d = new Date(118,11,02);
    System.out.println(d.toString());
  }

  public boolean HaySolapamiento() {
    return (fechaInicial.getTime() > fechaFinal.getTime());
  }

}
