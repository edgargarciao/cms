package co.ufps.edu.view;


import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import co.ufps.edu.dao.ActividadDao;
import co.ufps.edu.dao.CategoriaDao;
import co.ufps.edu.dao.ContactoDao;
import co.ufps.edu.dao.ContenidoDao;
import co.ufps.edu.dao.EnlaceDeInteresDao;
import co.ufps.edu.dao.GaleriaDao;
import co.ufps.edu.dao.LogoDao;
import co.ufps.edu.dao.NoticiaDao;
import co.ufps.edu.dao.NovedadDao;
import co.ufps.edu.dao.RedSocialDao;
import co.ufps.edu.dao.SubCategoriaDao;
import co.ufps.edu.dto.Actividad;
import co.ufps.edu.dto.Categoria;
import co.ufps.edu.dto.Contacto;
import co.ufps.edu.dto.Contenido;
import co.ufps.edu.dto.EnlaceDeInteres;
import co.ufps.edu.dto.Galeria;
import co.ufps.edu.dto.Noticia;
import co.ufps.edu.dto.Novedad;
import co.ufps.edu.dto.RedSocial;
import co.ufps.edu.dto.SubCategoria;


public class XlsView extends AbstractXlsView {

	private CategoriaDao categoriaDao = new CategoriaDao();
	private SubCategoriaDao subCategoriaDao = new SubCategoriaDao();
	private ContenidoDao contenidoDao = new ContenidoDao();
	private NoticiaDao noticiaDao = new NoticiaDao();
	private ActividadDao actividadDao = new ActividadDao();
	private NovedadDao novedadDao = new NovedadDao();
	private EnlaceDeInteresDao enlaceDeInteresDao = new EnlaceDeInteresDao();
	private GaleriaDao galeriaDao = new GaleriaDao();
	private ContactoDao contactoDao = new ContactoDao();
	private RedSocialDao redSocialDao = new RedSocialDao();
	
    @Override
    protected void buildExcelDocument( Map<String, Object> model, Workbook workbook,  HttpServletRequest request, HttpServletResponse response) throws Exception 
    {

    	// Nombre del excel.
        response.setHeader("Content-Disposition", "attachment; filename=\"Reporte.xls\"");
        
        
        // Se crea la hoja para principal de totales.
        Sheet sheet = workbook.createSheet("Totales de contenidos");
        
        //Encabezado de la hoja
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("NOMBRE");
        header.createCell(2).setCellValue("TOTAL");
        
        //Se carga la información en la hoja
        Row Row = sheet.createRow(1);
        Row.createCell(0).setCellValue(1);
        Row.createCell(1).setCellValue("Categoria");
        Row.createCell(2).setCellValue(categoriaDao.getCantidadCategorias());
        
        Row = sheet.createRow(2);
        Row.createCell(0).setCellValue(2);
        Row.createCell(1).setCellValue("Subcategoria");
        Row.createCell(2).setCellValue(subCategoriaDao.getCantidadSubCategorias());
        
        Row = sheet.createRow(3);
        Row.createCell(0).setCellValue(3);
        Row.createCell(1).setCellValue("Contenido");
        Row.createCell(2).setCellValue(contenidoDao.getCantidadContenidos());
        
        Row = sheet.createRow(4);
        Row.createCell(0).setCellValue(4);
        Row.createCell(1).setCellValue("Noticia");
        Row.createCell(2).setCellValue(noticiaDao.getCantidadNoticias());
        
        Row = sheet.createRow(5);
        Row.createCell(0).setCellValue(5);
        Row.createCell(1).setCellValue("Actividad");
        Row.createCell(2).setCellValue(actividadDao.getCantidadActividades());
        
        Row = sheet.createRow(6);
        Row.createCell(0).setCellValue(6);
        Row.createCell(1).setCellValue("Novedad");
        Row.createCell(2).setCellValue(novedadDao.getCantidadNovedades());
        
        Row = sheet.createRow(7);
        Row.createCell(0).setCellValue(7);
        Row.createCell(1).setCellValue("Enlace de interés");
        Row.createCell(2).setCellValue(enlaceDeInteresDao.getCantidadEnlacesDeInteres());
        
        Row = sheet.createRow(8);
        Row.createCell(0).setCellValue(8);
        Row.createCell(1).setCellValue("Galeria");
        Row.createCell(2).setCellValue(galeriaDao.getCantidadGalerias());
        
        Row = sheet.createRow(9);
        Row.createCell(0).setCellValue(9);
        Row.createCell(1).setCellValue("Contacto");
        Row.createCell(2).setCellValue(contactoDao.getCantidadContactos());
        
        Row = sheet.createRow(10);
        Row.createCell(0).setCellValue(10);
        Row.createCell(1).setCellValue("Red social");
        Row.createCell(2).setCellValue(redSocialDao.getCantidadRedesSociales());
        
        
        // Se crea la hoja para categorias.
        Sheet sheetCategoria = workbook.createSheet("Categorias");
        
    	List<Categoria> categorias = categoriaDao.getCategoriasConSubcategorias();

    	//Encabezado de la hoja
        Row headerCategoria = sheetCategoria.createRow(0);
        headerCategoria.createCell(0).setCellValue("ID");
        headerCategoria.createCell(1).setCellValue("NOMBRE");
        headerCategoria.createCell(2).setCellValue("DESCRIPCIÓN");
        
        int count_row = 1;
        //Se carga la información en la hoja
        for (Categoria categoria :categorias) {
            Row categoriaRow = sheetCategoria.createRow(count_row++);

            categoriaRow.createCell(0).setCellValue(categoria.getId());
            categoriaRow.createCell(1).setCellValue(categoria.getNombre());
            categoriaRow.createCell(2).setCellValue(categoria.getDescripcion());
        }
        
        
        // Se crea la hoja para subcategorias.
        Sheet sheetSubCategoria = workbook.createSheet("Subcategorias");
        
    	List<SubCategoria> subCategorias = subCategoriaDao.getSubCategorias();

    	//Encabezado de la hoja
        Row headerSubCategoria = sheetSubCategoria.createRow(0);
        headerSubCategoria.createCell(0).setCellValue("ID");
        headerSubCategoria.createCell(1).setCellValue("NOMBRE");
        headerSubCategoria.createCell(2).setCellValue("DESCRIPCIÓN");
        
        count_row = 1;
        //Se carga la información en la hoja
        for (SubCategoria subCategoria :subCategorias) {
            Row subCategoriaRow = sheetSubCategoria.createRow(count_row++);

            subCategoriaRow.createCell(0).setCellValue(subCategoria.getId());
            subCategoriaRow.createCell(1).setCellValue(subCategoria.getNombre());
            subCategoriaRow.createCell(2).setCellValue(subCategoria.getDescripcion());
        }
   
        
        
        // Se crea la hoja para contenidos.
        Sheet sheetContenido = workbook.createSheet("Contenidos");
        
    	List<Contenido> contenidos = contenidoDao.getContenidos();

    	//Encabezado de la hoja
        Row headerContenido = sheetContenido.createRow(0);
        headerContenido.createCell(0).setCellValue("ID");
        headerContenido.createCell(1).setCellValue("NOMBRE");
        
        count_row = 1;
        //Se carga la información en la hoja
        for (Contenido contenido :contenidos) {
            Row contenidoRow = sheetContenido.createRow(count_row++);

            contenidoRow.createCell(0).setCellValue(contenido.getId());
            contenidoRow.createCell(1).setCellValue(contenido.getNombre());
        }
        
        
        
        // Se crea la hoja para noticias.
        Sheet sheetNoticia = workbook.createSheet("Noticias");
        
    	List<Noticia> noticias = noticiaDao.getNoticias();

    	//Encabezado de la hoja
        Row headerNoticia = sheetNoticia.createRow(0);
        headerNoticia.createCell(0).setCellValue("ID");
        headerNoticia.createCell(1).setCellValue("NOMBRE");
        headerNoticia.createCell(2).setCellValue("FECHA");
        
        count_row = 1;
        
        //Se carga la información en la hoja
        for (Noticia noticia :noticias) {
            Row noticiaRow = sheetNoticia.createRow(count_row++);
            noticiaRow.createCell(0).setCellValue(noticia.getId());
            noticiaRow.createCell(1).setCellValue(noticia.getNombre());
            noticiaRow.createCell(2).setCellValue(noticia.getFecha());  
        }
        
        
        // Se crea la hoja para actividades.
        Sheet sheetActividad = workbook.createSheet("Actividades");
        
    	List<Actividad> actividades = actividadDao.getActividades();

    	//Encabezado de la hoja
        Row headerActividad = sheetActividad.createRow(0);
        headerActividad.createCell(0).setCellValue("ID");
        headerActividad.createCell(1).setCellValue("NOMBRE");
        headerActividad.createCell(2).setCellValue("LUGAR");
        headerActividad.createCell(3).setCellValue("FECHA INICIAL");
        headerActividad.createCell(4).setCellValue("FECHA FINAL");
        
        count_row = 1;
        
        //Se carga la información en la hoja
        for (Actividad actividad :actividades) {
            Row ActividadRow = sheetActividad.createRow(count_row++);
            ActividadRow.createCell(0).setCellValue(actividad.getId());
            ActividadRow.createCell(1).setCellValue(actividad.getNombre());
            ActividadRow.createCell(2).setCellValue(actividad.getLugar());
            ActividadRow.createCell(3).setCellValue(actividad.getFechaInicial());
            ActividadRow.createCell(4).setCellValue(actividad.getFechaFinal());
            
        }
        
        
        // Se crea la hoja para novedades.
        Sheet sheetNovedad = workbook.createSheet("Novedades");
        
    	List<Novedad> novedades = novedadDao.getNovedades();

    	//Encabezado de la hoja
        Row headerNovedad = sheetNovedad.createRow(0);
    	headerNovedad.createCell(0).setCellValue("ID");
    	headerNovedad.createCell(1).setCellValue("NOMBRE");
    	headerNovedad.createCell(2).setCellValue("FECHA");
        
        count_row = 1;
        
        //Se carga la información en la hoja
        for (Novedad novedad :novedades) {
            Row NovedadRow = sheetNovedad.createRow(count_row++);
            NovedadRow.createCell(0).setCellValue(novedad.getId());
            NovedadRow.createCell(1).setCellValue(novedad.getNombre());
            NovedadRow.createCell(2).setCellValue(novedad.getFecha());
        }
        
        
        // Se crea la hoja para enlaces de interés.
        Sheet sheetEnlace = workbook.createSheet("Enlaces de inerés");
        
    	List<EnlaceDeInteres> enlaces = enlaceDeInteresDao.getEnlacesDeInteres();

    	//Encabezado de la hoja
        Row headerEnalce = sheetEnlace.createRow(0);
        headerEnalce.createCell(0).setCellValue("ID");
        headerEnalce.createCell(1).setCellValue("NOMBRE");
        headerEnalce.createCell(2).setCellValue("URL");
        
        count_row = 1;
        
        //Se carga la información en la hoja
        for ( EnlaceDeInteres enlace :enlaces) {
            Row EnlaceRow = sheetEnlace.createRow(count_row++);
            EnlaceRow.createCell(0).setCellValue(enlace.getId());
            EnlaceRow.createCell(1).setCellValue(enlace.getNombre());
            EnlaceRow.createCell(2).setCellValue(enlace.getUrl());
        }
        
        
    	// Se crea la hoja para galeria.
        Sheet sheetGaleria = workbook.createSheet("Galerias");
        
    	List<Galeria> galerias = galeriaDao.getGalerias();

    	//Encabezado de la hoja
        Row headerGaleria = sheetGaleria.createRow(0);
        headerGaleria.createCell(0).setCellValue("ID");
        headerGaleria.createCell(1).setCellValue("NOMBRE");
        headerGaleria.createCell(2).setCellValue("DESCRIPCIÓN");
        headerGaleria.createCell(3).setCellValue("FECHA");
        
        count_row = 1;
        
        //Se carga la información en la hoja
        for ( Galeria galeria :galerias) {
            Row GaleriaRow = sheetGaleria.createRow(count_row++);
            GaleriaRow.createCell(0).setCellValue(galeria.getId());
            GaleriaRow.createCell(1).setCellValue(galeria.getNombre());
            GaleriaRow.createCell(2).setCellValue(galeria.getDescripcion());
            GaleriaRow.createCell(3).setCellValue(galeria.getFecha());
        }
        
        
        // Se crea la hoja para contactos.
        Sheet sheetContacto = workbook.createSheet("Contactos");
        
    	List<Contacto> contactos = contactoDao.getContactos();

    	//Encabezado de la hoja
        Row headerContacto = sheetContacto.createRow(0);
        headerContacto.createCell(0).setCellValue("ID");
        headerContacto.createCell(1).setCellValue("NOMBRE");
        
        count_row = 1;
        
        //Se carga la información en la hoja
        for ( Contacto contacto :contactos) {
            Row ContactoRow = sheetContacto.createRow(count_row++);
            ContactoRow.createCell(0).setCellValue(contacto.getId());
            ContactoRow.createCell(1).setCellValue(contacto.getNombre());
        }
        
        
        // Se crea la hoja para redes sociales.
        Sheet sheetRedSocial = workbook.createSheet("Redes Sociales");
        
    	List<RedSocial> redesSociales = redSocialDao.getRedesSociales();

    	//Encabezado de la hoja
        Row headerRedSocial = sheetRedSocial.createRow(0);
        headerRedSocial.createCell(0).setCellValue("ID");
        headerRedSocial.createCell(1).setCellValue("NOMBRE");
        headerRedSocial.createCell(2).setCellValue("URL");
        
        count_row = 1;
        
        //Se carga la información en la hoja
        for ( RedSocial redSocial :redesSociales) {
            Row RedSocialRow = sheetRedSocial.createRow(count_row++);
            RedSocialRow.createCell(0).setCellValue(redSocial.getId());
            RedSocialRow.createCell(1).setCellValue(redSocial.getNombre());
            RedSocialRow.createCell(2).setCellValue(redSocial.getUrl());
        }
    }

}
