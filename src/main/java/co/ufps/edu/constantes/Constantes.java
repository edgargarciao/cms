package co.ufps.edu.constantes;

import java.net.InetAddress;
import java.net.UnknownHostException;
import co.ufps.edu.util.FileUtil;

public class Constantes {
  
  private static FileUtil fileUtil;
  
  public Constantes() {
    fileUtil = new FileUtil();
    this.RUTA = fileUtil.getProperties().getProperty("name");
  }

  public static final String NOVEDAD = "Novedad";
  public static final String ACTIVIDAD = "Actividad";
  public static final String NOTICIA = "Noticia";
  public static final String SUBCATEGORIA = "Subcategoria";
  // For development
    public static String RUTA = fileUtil.getProperties().getProperty("name");
  // For production
  //public static final String RUTA = "";
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
