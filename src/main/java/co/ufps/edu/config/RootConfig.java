package co.ufps.edu.config;

import org.springframework.context.annotation.ComponentScan;

/**
 * Clase que permite escanear toda la configuraci�n existente en el paquete actual.
 * @author ufps
 *
 */
@ComponentScan(basePackages = { "co.ufps.edu.config.*" })	
public class RootConfig {
}
