package co.ufps.edu.dto;

import org.springframework.util.StringUtils;

public class Contacto {

	private long id;
	private String nombre;
	
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

	public boolean isValidoParaRegistrar() {
	    return (!StringUtils.isEmpty(this.nombre));
	}
	
	@Override
	public String toString() {
		return "Contacto [id=" + id + ", nombre=" + nombre + "]";
	}
	
	
}
