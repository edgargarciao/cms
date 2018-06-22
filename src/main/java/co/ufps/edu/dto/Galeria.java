package co.ufps.edu.dto;
import java.sql.Date;
import java.util.ArrayList;
import org.springframework.util.StringUtils;


public class Galeria {

	private long id;
	private String nombre;
	private String descripcion;
	private Date fecha;
	private ArrayList<Imagen> imagenes;
	private String primeraImagen;
	
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
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public Date getFecha() {
		return fecha;
	}
	
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public boolean isValidoParaRegistrar() {
	    return (!StringUtils.isEmpty(this.nombre) && !StringUtils.isEmpty(this.fecha) && !StringUtils.isEmpty(this.descripcion) && this.imagenes.size()>0);
	 }

  public ArrayList<Imagen> getImagenes() {
    return imagenes;
  }

  public void setImagenes(ArrayList<Imagen> imagenes) {
    this.imagenes = imagenes;
  }

  public String getPrimeraImagen() {
    return primeraImagen;
  }

  public void setPrimeraImagen(String primeraImagen) {
    this.primeraImagen = primeraImagen;
  }
	
	
}
