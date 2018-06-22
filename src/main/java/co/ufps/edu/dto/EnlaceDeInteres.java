package co.ufps.edu.dto;

import org.springframework.util.StringUtils;

public class EnlaceDeInteres {

	private long id;
	private String nombre;
	private String url;
	
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
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public boolean isValidoParaRegistrar() {
	    return (!StringUtils.isEmpty(this.nombre) && !StringUtils.isEmpty(this.url));
	}

	@Override
	public String toString() {
		return "EnlaceDeInteres [id=" + id + ", nombre=" + nombre + ", url=" + url + "]";
	}

	
}
