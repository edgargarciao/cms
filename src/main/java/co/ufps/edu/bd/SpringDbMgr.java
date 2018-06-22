package co.ufps.edu.bd;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.project.MavenProject;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.util.ResourceUtils;
import co.ufps.edu.dto.ResultDB;
import co.ufps.edu.util.FileUtil;

/**
 * Class with basic operations to interact with Database System.
 * 
 * <p>
 * This class contains specific implementation using Spring-JDBC Project. For more information check
 * Spring Framework Reference Documentation.
 * 
 * @see <a href=
 *      "https://docs.spring.io/spring/docs/current/spring-framework-reference/html/spring-data-tier.html">
 *      https://docs.spring.io/spring/docs/current/spring-framework-reference/html/spring-data-tier.html
 *      </a>
 */
public class SpringDbMgr {

  private DataSource dataSource;
  private FileUtil fileUtil;

  public SpringDbMgr() {
    fileUtil = new FileUtil();
    initDataSource2();
  }
  
  private void initDataSource2() {

    
    
    try {
      Class.forName(fileUtil.getProperties().getProperty("driver"));
    } catch (ClassNotFoundException e) {
      new Exception();
    }

    Properties p = new Properties();
    p.setProperty("user", fileUtil.getProperties().getProperty("user"));
    p.setProperty("password", fileUtil.getProperties().getProperty("password"));
    p.setProperty("driverClassName", fileUtil.getProperties().getProperty("driver"));

    dataSource = new DriverManagerDataSource(
        "jdbc:"+fileUtil.getProperties().getProperty("databasetype")+"://"+fileUtil.getProperties().getProperty("ip")+":"+fileUtil.getProperties().getProperty("portdabase")+"/"+fileUtil.getProperties().getProperty("databasename")+"?useUnicode=true&amp;characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false",
        
        p);
  }
  
  /**
   * This method implements SELECT query execution logic without parameters in Database System using
   * Spring-JDBC.
   * 
   * @param query Text that represents query to be executed.
   * @return Set of rows returned by the query.
   * @throws AppException If there is any problem in the execution.
   */
  public SqlRowSet executeQuery(String query) {
    try {
      NamedParameterJdbcTemplate namedParameterJdbcTemplate =
          new NamedParameterJdbcTemplate(dataSource);

      MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
      SqlRowSet sqlRowSet = namedParameterJdbcTemplate.queryForRowSet(query, mapSqlParameterSource);
      return sqlRowSet;
    } catch (Exception e) {
    }


    return null;
  }

  /**
   * This method implements SELECT query execution logic in Database System using Spring-JDBC.
   * 
   * @param query Text that represents query to be executed.
   * @param parameterMap Object containing all parameters required to bind SQL variables.
   * @return Set of rows returned by the query.
   * @throws AppException If there is any problem in the execution.
   */
  public SqlRowSet executeQuery(String query, MapSqlParameterSource parameterMap) {
    try {
      NamedParameterJdbcTemplate namedParameterJdbcTemplate =
          new NamedParameterJdbcTemplate(dataSource);

      SqlRowSet sqlRowSet = namedParameterJdbcTemplate.queryForRowSet(query, parameterMap);

      return sqlRowSet;
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  /**
   * This method implements INSERT/UPDATE/DELETE query execution logic without parameters in
   * Database System using Spring-JDBC.
   * 
   * @param query Text that represents query to be executed.
   * @return the number of rows affected.
   * @throws AppException If there is any problem in the execution.
   */
  public int executeDml(String query) {
    try {
      NamedParameterJdbcTemplate namedParameterJdbcTemplate =
          new NamedParameterJdbcTemplate(dataSource);

      MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
      int affectedRows = namedParameterJdbcTemplate.update(query, mapSqlParameterSource);

      return affectedRows;
    } catch (Exception e) {
      e.printStackTrace();
    }

    return -1;
  }

  /**
   * This method implements INSERT/UPDATE/DELETE query execution logic in Database System using
   * Spring-JDBC.
   * 
   * @param query Text that represents DML to be executed.
   * @param parameterMap Object containing all parameters required to bind SQL variables.
   * @return the number of rows affected.
   * @throws AppException If there is any problem in the execution.
   */
  public int executeDml(String query, MapSqlParameterSource parameterMap) {
    try {
      NamedParameterJdbcTemplate namedParameterJdbcTemplate =
          new NamedParameterJdbcTemplate(dataSource);
      KeyHolder keyHolder = new GeneratedKeyHolder();
      int affectedRows = namedParameterJdbcTemplate.update(query, parameterMap, keyHolder);

      return affectedRows;
    } catch (Exception e) {

    }

    return 0;
  }

  public ResultDB executeDmlWithKey(String query, MapSqlParameterSource parameterMap) {

    ResultDB resultDB = new ResultDB();
    NamedParameterJdbcTemplate namedParameterJdbcTemplate =
        new NamedParameterJdbcTemplate(dataSource);
    KeyHolder keyHolder = new GeneratedKeyHolder();
    int affectedRows = namedParameterJdbcTemplate.update(query, parameterMap, keyHolder);
    Long generatedId = keyHolder.getKey().longValue();
    resultDB.setResult(affectedRows);
    resultDB.setKey(generatedId);
    return resultDB;
  }

  /**
   * This method set a dataSource.
   * 
   * @param dataSource for set
   */
  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public void main2(String[] args) {

    try {
      Class.forName("com.mysql.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      new Exception();
    }

    Properties p = new Properties();
    p.setProperty("user", "root");
    p.setProperty("password", "123454");
    p.setProperty("driverClassName", "com.mysql.jdbc.Driver");

    String instanceConnectionName = "moodleuvirtualufps:northamerica-northeast1:ufpsgraduados";

    // TODO: fill this in
    // The database from which to list tables.
    String databaseName = "mysql";

    DataSource dataSource = new DriverManagerDataSource(String.format(
        "jdbc:mysql://google/%s?cloudSqlInstance=%s&amp"
            + "socketFactory=com.google.cloud.sql.mysql.SocketFactory",
        databaseName, instanceConnectionName), p);
    // jdbc:mysql://localhost:3306/dbname

    try (Statement statement = dataSource.getConnection().createStatement()) {
      ResultSet resultSet = statement.executeQuery("SHOW TABLES");
      while (resultSet.next()) {
        System.out.println(resultSet.getString(1));
      }
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }


  }

  public static void main(String[] args) {
    try {
      Class.forName("com.mysql.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      new Exception();
    }
    String instanceConnectionName = "moodleuvirtualufps:northamerica-northeast1:ufpsgraduados";
    Properties p = new Properties();
    p.setProperty("user", "root");
    // p.setProperty("password", "");
    p.setProperty("password", "123454");
    //p.setProperty("driverClassName", "com.mysql.jdbc.Driver");

    // dataSource = new
    // DriverManagerDataSource("jdbc:mysql://localhost:3306/graduados?useUnicode=true&amp;characterEncoding=utf8&useLegacyDatetimeCode=false&serverTimezone=UTC",
    // p);
    DataSource s = new DriverManagerDataSource(
        //"jdbc:mysql://35.203.35.232:3306/graduados?useUnicode=true&amp;characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false",
          "jdbc:mysql://35.203.35.232:3306/graduados?useUnicode=true&amp;characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false",
        p);


    // jdbc:mysql://localhost:3306/dbname
    NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(s);

    MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
    String rest = "INSERT INTO subcategoria VALUES (nombre,descripcion,orden,Categoria_id) \n";
    SqlRowSet sqlRowSet = namedParameterJdbcTemplate.queryForRowSet("select * from subcategoria",
        mapSqlParameterSource);
    while (sqlRowSet.next()) {
      // System.out.println("id --> "+sqlRowSet.getLong("id"));
      rest += "('" + sqlRowSet.getString("nombre") + "','" + sqlRowSet.getString("nombre") + "',"
          + sqlRowSet.getString("orden");
    }

    // namedParameterJdbcTemplate.update("DELETE FROM visita", new MapSqlParameterSource());

    mapSqlParameterSource = new MapSqlParameterSource();
    mapSqlParameterSource.addValue("correo", "edgaryesidgo@ufps.edu.co");
    mapSqlParameterSource.addValue("pass", "1234");
    sqlRowSet = namedParameterJdbcTemplate.queryForRowSet(
        " SELECT    categoria.id                    idCategoria,            "
            + "           categoria.nombre                nombreCategoria,        "
            + "           categoria.descripcion           descripcionCategoria,   "
            + "           categoria.orden                 ordenCategoria,         "
            + "           subcategoria.id                 idSubcategoria,         "
            + "           subcategoria.nombre             nombreSubCategoria,     "
            + "           subcategoria.descripcion        descripcionSubCategoria,"
            + "           subcategoria.orden              ordenSubCategoria       "
            + "   FROM    subcategoria                                            "
            + "INNER JOIN categoria  ON categoria.id = subcategoria.Categoria_id  "
            + "ORDER BY   categoria.orden ASC,subcategoria.orden ASC              ",
        new MapSqlParameterSource());

    while (sqlRowSet.next()) {
      System.out.println("id --> " + sqlRowSet.getLong("idCategoria"));
    }


  }

}
