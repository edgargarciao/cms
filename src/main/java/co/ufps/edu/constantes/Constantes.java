package co.ufps.edu.constantes;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Constantes {

  public static final String NOVEDAD = "Novedad";
  public static final String ACTIVIDAD = "Actividad";
  public static final String NOTICIA = "Noticia";
  public static final String SUBCATEGORIA = "Subcategoria";
  // For development
  //public static final String RUTA = "ufps-graduados";
  // For production
  public static final String RUTA = "";
  // For development
  //public static final String SERVER = "/";
  // For production
  public static final String SERVER = "";
  
  private static String getServer() {
    try {
      return InetAddress.getLocalHost().getCanonicalHostName().split("/")[0];
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
    return "";
  }
}
