package co.com.negocios.aliados.facturacion.test;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.ZapfDingbatsList;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * 
 * @author http://datojava.blogspot.com/
 *
 */
public class DJiTextEjemplo {

//	public static void main(String[] args) {
//
//		// Creacion del documento con los margenes
//		Document document = new Document(PageSize.A4, 35, 30, 50, 50);
//		try {
//
//			// El archivo pdf que vamos a generar
//			FileOutputStream fileOutputStream = new FileOutputStream(
//					"reportePDFDatoJava.pdf");
//			
//			// Obtener la instancia del PdfWriter
//			PdfWriter.getInstance(document, fileOutputStream);
//
//			// Abrir el documento
//			document.open();
//			
//			Image image = null;
//
//			// Obtenemos el logo de datojava
//			image = Image.getInstance("fotoDJ.png");
//			image.scaleAbsolute(80f, 60f);
//
//			// Crear las fuentes para el contenido y los titulos
//			Font fontContenido = FontFactory.getFont(
//					FontFactory.TIMES_ROMAN.toString(), 11, Font.NORMAL,
//					BaseColor.DARK_GRAY);
//			Font fontTitulos = FontFactory.getFont(
//					FontFactory.TIMES_BOLDITALIC, 11, Font.UNDERLINE,
//					BaseColor.RED);
//
//			// Creacion de una tabla
//			PdfPTable table = new PdfPTable(1);
//
//			// Agregar la imagen anterior a una celda de la tabla
//			PdfPCell cell = new PdfPCell(image);
//
//			// Propiedades de la celda
//			cell.setColspan(5);
//			cell.setBorderColor(BaseColor.WHITE);
//			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//
//			// Agregar la celda a la tabla
//			table.addCell(cell);
//
//			// Agregar la tabla al documento
//			document.add(table);
//
//			// Cargar otra imagen
//			image = Image.getInstance("fujifilmex1-1.png");
//			image.scaleAbsolute(180f, 140f);
//
//			// Agregar la imagen
//			document.add(image);
//
//			// Creacion del parrafo
//			Paragraph paragraph = new Paragraph();
//
//			// Agregar un titulo con su respectiva fuente
//			paragraph.add(new Phrase("Caracter�sticas:", fontTitulos));
//
//			// Agregar saltos de linea
//			paragraph.add(new Phrase(Chunk.NEWLINE));
//			paragraph.add(new Phrase(Chunk.NEWLINE));
//
//			// Agregar contenido con su respectiva fuente
//			paragraph
//					.add(new Phrase(
//							"El sensor de la X-E1 presenta el mismo excelente rendimiento que el X-Trans CMOS "
//									+ "de 16 megap�xeles del modelo superior de la serie X, la X-Pro1. Gracias la matriz "
//									+ "de filtro de color con disposici�n aleatoria de los p�xeles, desarrollada originalmente"
//									+ " por Fujifilm, el sensor X-Trans CMOS elimina la necesidad del filtro �ptico de paso bajo"
//									+ " que se utiliza en los sistemas convencionales para inhibir el muar� a expensas de la"
//									+ " resoluci�n. Esta matriz innovadora permite al sensor X-Trans CMOS captar la luz sin filtrar"
//									+ " del objetivo y obtener una resoluci�n sin precedentes. La exclusiva disposici�n aleatoria de"
//									+ " la matriz de filtro de color resulta asimismo muy eficaz para mejorar la separaci�n de ruido"
//									+ " en la fotograf�a de alta sensibilidad. Otra ventaja del gran sensor APS-C es la capacidad"
//									+ " para crear un hermoso efecto �bokeh�, el est�tico efecto desenfocado que se crea al disparar"
//									+ " con poca profundidad de campo.",
//							fontContenido));
//
//			paragraph.add(new Phrase(Chunk.NEWLINE));
//			paragraph.add(new Phrase(Chunk.NEWLINE));
//			paragraph.add(new Phrase(Chunk.NEWLINE));
//			paragraph.add(new Phrase("Otras Caracater�sticas:", fontTitulos));
//
//			// Agregar el parrafo al documento
//			document.add(paragraph);
//
//			// La lista final
//			List listaFinal = new List(List.UNORDERED);
//			ListItem listItem = new ListItem();
//			List list = new List();
//
//			// Crear sangria en la lista
//			list.setListSymbol(new Chunk("   "));
//			ListItem itemNuevo = new ListItem("   ");
//
//			// ZapfDingbatsListm, lista con simbolos
//			List listSymbol = new ZapfDingbatsList(51);
//
//			// Agregar contenido a la lista
//			listSymbol
//					.add(new ListItem(
//							"Sensor CMOS X-Trans � Consigue una calidad de imagen superior",
//							fontContenido));
//			listSymbol
//					.add(new ListItem(
//							"Visor electr�nico OLED de 2,36 pulgadas de alta resoluci�n y luminosidad",
//							fontContenido));
//			listSymbol.add(new ListItem("Dise�o y accesorios", fontContenido));
//			listSymbol.add(new ListItem("R�pida respuesta", fontContenido));
//
//			itemNuevo.add(listSymbol);
//			list.add(itemNuevo);
//			listItem.add(list);
//
//			// Agregar todo a la lista final
//			listaFinal.add(listItem);
//
//			// Agregar la lista
//			document.add(listaFinal);
//
//			paragraph = new Paragraph();
//			paragraph.add(new Phrase(Chunk.NEWLINE));
//			paragraph.add(new Phrase(Chunk.NEWLINE));
//			document.add(paragraph);
//
//			// Crear tabla nueva con dos posiciones
//			table = new PdfPTable(2);
//			float[] longitudes = { 5.0f, 5.0f };
//
//			// Establecer posiciones de celdas
//			table.setWidths(longitudes);
//			cell = new PdfPCell();
//
//			// Cargar imagenes del producto y agregarlas
//			image = Image.getInstance("fujifilmex1-2.png");
//			image.scaleAbsolute(40f, 20f);
//			table.getDefaultCell().setBorderColor(BaseColor.WHITE);
//			table.addCell(image);
//			image = Image.getInstance("fujifilmex1-3.png");
//			image.scaleAbsolute(40f, 20f);
//			table.addCell(image);
//
//			// Agregar la tabla al documento
//			document.add(table);
//
//			// Cerrar el documento
//			document.close();
//
//			// Abrir el archivo
//			File file = new File("reportePDFDatoJava.pdf");
//			Desktop.getDesktop().open(file);
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//	}
}
