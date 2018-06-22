package co.ufps.edu.util;

import sun.misc.BASE64Encoder;

/**
 * Clase que permite usar servicios para el tratamiento de imagenes.
 * @author ufps
 *
 */
@SuppressWarnings("restriction")
public class ImagenUtil {

  /**
   * Metodo que permite convertir una imagen de un vector de bytes a una cadena de string.
   * @param imagen Contenido de la imagen en bytes.
   * @return Una cadena de caracteres con la imagen codificada en base 64.
   */
  public String convertirImagen(byte[] imagen) {
    BASE64Encoder base64Encoder = new BASE64Encoder();
    StringBuilder imageString = new StringBuilder();
    imageString.append("data:image/png;base64,");
    imageString.append(base64Encoder.encode(imagen)); 
    return imageString.toString();
  }
}
