package co.ufps.edu.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.springframework.util.ResourceUtils;

public class FileUtil {

  private Properties properties;
  
  public FileUtil() {
    properties = new Properties();
    try {
        File file = ResourceUtils.getFile("classpath:database.properties");
        InputStream in = new FileInputStream(file);
        properties.load(in);
    } catch (IOException e) {
        e.printStackTrace();
    }
    
  }

  public Properties getProperties() {
    return properties;
  }

  public void setProperties(Properties properties) {
    this.properties = properties;
  }
  
}
