package co.ufps.edu.dto;

import org.springframework.util.StringUtils;

public class Usuario {

  private String contrasenaAntigua;
  private String contrasenaNueva;
  private String contrasenaNueva2;
  
  

  public String getContrasenaAntigua() {
    return contrasenaAntigua;
  }

  public void setContrasenaAntigua(String contrasenaAntigua) {
    this.contrasenaAntigua = contrasenaAntigua;
  }

  public String getContrasenaNueva() {
    return contrasenaNueva;
  }

  public void setContrasenaNueva(String contrasenaNueva) {
    this.contrasenaNueva = contrasenaNueva;
  }

  public String getContrasenaNueva2() {
    return contrasenaNueva2;
  }

  public void setContrasenaNueva2(String contrasenaNueva2) {
    this.contrasenaNueva2 = contrasenaNueva2;
  }

  public boolean isValidoParaRegistrar() {
    return (!StringUtils.isEmpty(this.contrasenaAntigua) && !StringUtils.isEmpty(this.contrasenaNueva) && !StringUtils.isEmpty(this.contrasenaNueva2));
  }
  
  public boolean seRepiten() {
    return this.contrasenaNueva.equals(this.contrasenaNueva2);
  }

}
