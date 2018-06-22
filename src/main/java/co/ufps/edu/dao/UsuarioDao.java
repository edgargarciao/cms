package co.ufps.edu.dao;

import java.util.Properties;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;
import co.ufps.edu.bd.SpringDbMgr;
import co.ufps.edu.constantes.Constantes;
import co.ufps.edu.dto.Usuario;
import co.ufps.edu.util.MailUtil;

/**
 * Clase que permite acceder a la capa de datos en el entorno de actividades.
 * 
 * @author ufps
 *
 */
@Component
public class UsuarioDao {


  private SpringDbMgr springDbMgr;
  private MailUtil mailUtil;
  private JavaMailSenderImpl javaMailSender;

  public UsuarioDao() {
    springDbMgr = new SpringDbMgr();
    mailUtil = new MailUtil();
    javaMailSender = new JavaMailSenderImpl();
    javaMailSender.setHost("smtp.gmail.com");
    javaMailSender.setPort(587);
    javaMailSender.setProtocol("smtp");
    javaMailSender.setUsername("edgar.yesid.garcia.ortiz@gmail.com");
    javaMailSender.setPassword("94100209440");
    Properties props = new Properties();
    props.put("mail.smtp.starttls.enable", "starttls");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.user", "edgar.yesid.garcia.ortiz@gmail.com");
    props.put("mail.smtp.password", "94100209440");
    props.put("mail.smtp.port", "587");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
    javaMailSender.setJavaMailProperties(props);
  }

  /**
   * Método que obtiene el numero de actividades guardadas en base de datos.
   * 
   * @return La cantidad de actividades registradas.
   */
  public String enviarCorreo(String correo) {
    int cant = 0;
    // Consulta para realizar en base de datos
    MapSqlParameterSource map = new MapSqlParameterSource();
    map.addValue("correo", correo);
    SqlRowSet sqlRowSet = springDbMgr.executeQuery(
        " SELECT Contraseña pass FROM usuario where correoInstitucional = :correo", map);

    String pass = "";
    if (sqlRowSet.next()) {
      pass = sqlRowSet.getString("pass");
    }

    String mensaje = "El correo no esta registrado en el sistema. Contacte al administrador.";
    if (!pass.equals("")) {

      String subject = "Recuperación de contraseña ADMIN-UFPS";
      String body = "Su contraseña es : " + pass;
      String to[] = {correo};
      // mensaje = mailUtil.sendFromGMail(to, subject, body);

      SimpleMailMessage smm = new SimpleMailMessage();

      smm.setFrom("edgar.yesid.garcia.ortiz@gmail.com");
      smm.setTo(correo);
      smm.setSubject(subject);
      smm.setText(body);
      try {
        javaMailSender.send(smm);
        mensaje = "Actualizacion";
      }catch(Exception e) {
        mensaje = "Correo no autorizado. Debes permitir el acceso de aplicaciones no seguras en la configuracion de google.";
      }
      
    }
    return mensaje;
  }

  public String actualizarUsuario(String correo, Usuario usuario) {
    // Agrego los datos del registro (nombreColumna/Valor)
    MapSqlParameterSource map = new MapSqlParameterSource();
    map.addValue("correo", correo);
    map.addValue("contraseña", usuario.getContrasenaNueva());


    // Armar la sentencia de actualización debase de datos
    String query =
        "UPDATE usuario SET contraseña = :contraseña WHERE correoInstitucional = :correo";

    // Ejecutar la sentencia
    int result = 0;
    try {
      result = springDbMgr.executeDml(query, map);
    } catch (Exception e) {
      new Exception();
    }
    // Si hubieron filas afectadas es por que si hubo registro, en caso contrario muestra el mensaje
    // de error.
    return (result == 1) ? "Actualizacion exitosa"
        : "Error al actualizar la contraseña. Contacte al administrador del sistema.";
  }


}
