package co.ufps.edu.dto;

import java.sql.Date;

public class Visita {

  private String ip;
  private Date fecha;
  
  public String getIp() {
    return ip;
  }
  public void setIp(String ip) {
    this.ip = ip;
  }
  public Date getFecha() {
    return fecha;
  }
  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }
  
  
}
