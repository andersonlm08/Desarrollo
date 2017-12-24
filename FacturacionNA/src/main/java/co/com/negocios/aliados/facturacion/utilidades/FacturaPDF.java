package co.com.negocios.aliados.facturacion.utilidades;

import co.com.negocios.aliados.facturacion.adapter.ProductosAdapter;
import co.com.negocios.aliados.facturacion.dao.ProductosBean;
import co.com.negocios.aliados.facturacion.dto.DetalleFacturaDTO;
import co.com.negocios.aliados.facturacion.dto.EmpresaDTO;
import co.com.negocios.aliados.facturacion.dto.FacturaDTO;
import co.com.negocios.aliados.facturacion.entity.Cliente;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.commons.lang.exception.ExceptionUtils;

public class FacturaPDF {

   
    public static Font obtenerFuentegarden() {
//        Font ttfBase = null;
//        Font ttfReal = null;
//        try {
//            InputStream myStream = new BufferedInputStream(FacturaPDF.class.getResourceAsStream("Grandma's Garden.ttf"));
//            ttfBase = Font.createFont(Font.NORMAL, myStream);
//            ttfReal = ttfBase.deriveFont(Font.PLAIN, 24);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            System.err.println("akshar font not loaded.");
//        }

        BaseFont bf_russian = null;
        try {
            bf_russian = BaseFont.createFont("FreeSans.ttf", "CP1251", BaseFont.EMBEDDED);
        } catch (DocumentException ex) {
            Logger.getLogger(FacturaPDF.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FacturaPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
        Font russian = new Font(bf_russian, 12);
        return russian;
    }
    
    final static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(FacturaPDF.class);

//    private static Font fuenteTitulo = new Font(BaseFont.createFont()Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);

    private static Font fuenteTitulosTabla = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.BOLD);

    /**
     * *************************************************************************
     */
    public void generarFactura(FacturaDTO facturaDTO, EmpresaDTO empresa, Cliente cliente) {
        // Creacion del documento con los margenes
        Document document = new Document(PageSize.A3, 10, 10, 50, 50);
        Font obtenerFuentegarden = UtilidadesFuentesPDF.obtenerFuentegarden();
        try {

            // El archivo pdf que vamos a generar
            FileOutputStream fileOutputStream = new FileOutputStream(
                    "Remision_" + facturaDTO.getNumeroFactura() + "_" + cliente.getCliente() + "_" + facturaDTO.getCodigoCliente() + ".pdf");
            // Obtener la instancia del PdfWriter
            PdfWriter.getInstance(document, fileOutputStream);

            // Abrir el documento
            document.open();

            /**
             * **********************************************
             */
            PdfPTable externa = new PdfPTable(3);

            Paragraph saltoLinea3 = new Paragraph();
            saltoLinea3.add(new Phrase(Chunk.NEWLINE));
            document.add(saltoLinea3);

            //Cliente
            PdfPTable tablaInternaCabeceraEmpresa = new PdfPTable(2);

            Paragraph clientepar = new Paragraph();
            clientepar.add(new Phrase(empresa.getEmpresa(), obtenerFuentegarden));
            PdfPCell cellCliente = new PdfPCell(clientepar);
            cellCliente.setColspan(2);
            cellCliente.setBorderColor(BaseColor.WHITE);
            cellCliente.setHorizontalAlignment(Element.ALIGN_CENTER);
            tablaInternaCabeceraEmpresa.addCell(cellCliente);

            Paragraph nit = new Paragraph();
            nit.add(new Phrase(empresa.getNit() + " Régimen simplificado", obtenerFuentegarden));
            PdfPCell cellNit = new PdfPCell(nit);
            cellNit.setColspan(2);
            cellNit.setBorderColor(BaseColor.WHITE);
            cellNit.setHorizontalAlignment(Element.ALIGN_CENTER);
            tablaInternaCabeceraEmpresa.addCell(cellNit);

            Paragraph telefono = new Paragraph();
            telefono.add(new Phrase(empresa.getDireccion() + " Teléfono: " + empresa.getCelular(), obtenerFuentegarden));
            PdfPCell cell4 = new PdfPCell(telefono);
            cell4.setColspan(2);
            cell4.setBorderColor(BaseColor.WHITE);
            cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
            tablaInternaCabeceraEmpresa.addCell(cell4);

            Paragraph correo = new Paragraph();
            correo.add(new Phrase(empresa.getEmail(), obtenerFuentegarden));
            PdfPCell cell5 = new PdfPCell(correo);
            cell5.setColspan(2);
            cell5.setBorderColor(BaseColor.WHITE);
            cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
            tablaInternaCabeceraEmpresa.addCell(cell5);

            PdfPCell cellFechaFactura35456 = new PdfPCell(tablaInternaCabeceraEmpresa);
            cellFechaFactura35456.setColspan(2);
            cellFechaFactura35456.setBorderColor(BaseColor.BLACK);

            externa.addCell(cellFechaFactura35456);

            /**
             * ***************************************
             */
            PdfPTable tablaInternaCabeceraEmpresaDetalle = new PdfPTable(1);

            Paragraph remision = new Paragraph();
            remision.add(new Phrase("Remisión: No. " + facturaDTO.getNumeroFactura()));
            PdfPCell cellRemision = new PdfPCell(remision);
            cellRemision.setColspan(1);
            //Poner cuadro negro
            cellRemision.setBorderColor(BaseColor.WHITE);
            cellRemision.setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPCell cellFechaFactura355 = new PdfPCell();
            cellFechaFactura355.setBorderColor(BaseColor.WHITE);

            tablaInternaCabeceraEmpresaDetalle.addCell(cellFechaFactura355);
            tablaInternaCabeceraEmpresaDetalle.addCell(cellFechaFactura355);
            tablaInternaCabeceraEmpresaDetalle.addCell(cellFechaFactura355);
            tablaInternaCabeceraEmpresaDetalle.addCell(cellFechaFactura355);
            tablaInternaCabeceraEmpresaDetalle.addCell(cellFechaFactura355);

            tablaInternaCabeceraEmpresaDetalle.addCell(cellRemision);

//            document.add(tablaInternaCabeceraEmpresaDetalle);
            PdfPCell cellFechaFactura35 = new PdfPCell(tablaInternaCabeceraEmpresaDetalle);
            cellFechaFactura35.setColspan(1);
            cellFechaFactura35.setBorderColor(BaseColor.BLACK);

            externa.addCell(cellFechaFactura35);

            document.add(externa);

            /**
             * ********************************************************************************************
             */
            /**
             * **********************************************************************
             */
            Paragraph saltoLinea = new Paragraph();
            saltoLinea.add(new Phrase(Chunk.NEWLINE));
            document.add(saltoLinea);

            PdfPTable tablaDatosClienteExterna = new PdfPTable(2);

            //Cliente
            PdfPTable tablaCabeceraCliente = new PdfPTable(1);

            Paragraph cliente2 = new Paragraph();
            cliente2.add(new Phrase("Cliente: " + cliente.getCliente()));
            PdfPCell cellCliente2 = new PdfPCell(cliente2);
            cellCliente2.setColspan(1);
            cellCliente2.setBorderColor(BaseColor.WHITE);
            cellCliente2.setHorizontalAlignment(Element.ALIGN_LEFT);
            tablaCabeceraCliente.addCell(cellCliente2);

            Paragraph nit2 = new Paragraph();
            nit2.add(new Phrase("Nit: " + cliente.getNitDocumento()));
            PdfPCell cellNit2 = new PdfPCell(nit2);
            cellNit2.setColspan(1);
            cellNit2.setBorderColor(BaseColor.WHITE);
            cellNit2.setHorizontalAlignment(Element.ALIGN_LEFT);
            tablaCabeceraCliente.addCell(cellNit2);

            Paragraph direccion2 = new Paragraph();
            direccion2.add(new Phrase("Direccion: " + cliente.getDireccion()));
            PdfPCell cell32 = new PdfPCell(direccion2);
            cell32.setColspan(1);
            cell32.setBorderColor(BaseColor.WHITE);
            cell32.setHorizontalAlignment(Element.ALIGN_LEFT);
            tablaCabeceraCliente.addCell(cell32);

            Paragraph telefono2 = new Paragraph();
            telefono2.add(new Phrase("Teléfono: " + cliente.getTelefono()));
            PdfPCell cell42 = new PdfPCell(telefono2);
            cell42.setColspan(1);
            cell42.setBorderColor(BaseColor.WHITE);
            cell42.setHorizontalAlignment(Element.ALIGN_LEFT);
            tablaCabeceraCliente.addCell(cell42);

            tablaDatosClienteExterna.addCell(tablaCabeceraCliente);

            PdfPTable tablaCabeceraDetalle = new PdfPTable(1);

            Paragraph fechaFactura2 = new Paragraph();
            fechaFactura2.add(new Phrase("Fecha factura: " + UtilidadesFormatos.getFecha(new Date())));
            PdfPCell cellFechaFactura2 = new PdfPCell(fechaFactura2);
            cellFechaFactura2.setColspan(1);
            cellFechaFactura2.setBorderColor(BaseColor.WHITE);
            cellFechaFactura2.setHorizontalAlignment(Element.ALIGN_LEFT);
            tablaCabeceraDetalle.addCell(cellFechaFactura2);

            Paragraph vendedor2 = new Paragraph();
            vendedor2.add(new Phrase("Vendedor: " + "Diana Ruiz"));
            PdfPCell cellVendedor2 = new PdfPCell(vendedor2);
            cellVendedor2.setColspan(1);
            cellVendedor2.setBorderColor(BaseColor.WHITE);
            cellVendedor2.setHorizontalAlignment(Element.ALIGN_LEFT);
            tablaCabeceraDetalle.addCell(cellVendedor2);

            Paragraph vencimiento2 = new Paragraph();
            vencimiento2.add(new Phrase("Vencimiento: " + UtilidadesFormatos.getFecha(facturaDTO.getFechaVencimiento())));

            PdfPCell cellVencimiento2 = new PdfPCell(vencimiento2);
            cellVencimiento2.setColspan(1);
            cellVencimiento2.setBorderColor(BaseColor.WHITE);
            cellVencimiento2.setHorizontalAlignment(Element.ALIGN_LEFT);
            tablaCabeceraDetalle.addCell(cellVencimiento2);

            Paragraph plazo2 = new Paragraph();
            plazo2.add(new Phrase("Plazo: " + facturaDTO.getDiasPlazo()));
            PdfPCell cellPlazo2 = new PdfPCell(plazo2);
            cellPlazo2.setColspan(1);
            cellPlazo2.setBorderColor(BaseColor.WHITE);
            cellPlazo2.setHorizontalAlignment(Element.ALIGN_LEFT);
            tablaCabeceraDetalle.addCell(cellPlazo2);

            tablaDatosClienteExterna.addCell(tablaCabeceraDetalle);

            document.add(tablaDatosClienteExterna);

            //salto de linea
            Paragraph saltoLineaDatosCliente = new Paragraph();
            saltoLineaDatosCliente.add(new Phrase(Chunk.NEWLINE));
            document.add(saltoLineaDatosCliente);

            PdfPTable table = new PdfPTable(7);

            PdfPCell c1 = new PdfPCell(new Phrase("CANTIDAD", fuenteTitulosTabla));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
//            c1.setMinimumHeight(50);

            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("DESCRIPCIÓN", fuenteTitulosTabla));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setColspan(2);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("VALOR UNIT", fuenteTitulosTabla));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("DESCUENTO", fuenteTitulosTabla));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("SUBTOTAL", fuenteTitulosTabla));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("TOTAL", fuenteTitulosTabla));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            table.setHeaderRows(1);

            PdfPCell c2;

            List<DetalleFacturaDTO> detalleFacturaList = facturaDTO.getDetalleFacturaList();

            ProductosAdapter productosBean = new ProductosAdapter();
            for (DetalleFacturaDTO detalleFacturaList1 : detalleFacturaList) {

                c2 = new PdfPCell(new Phrase("" + detalleFacturaList1.getCantidad()));
                c2.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c2);

                c2 = new PdfPCell(new Phrase("" + productosBean.consultarProducto(detalleFacturaList1.getCodigoProducto()).getDescripcion()));
                c2.setHorizontalAlignment(Element.ALIGN_CENTER);
                c2.setColspan(2);
                table.addCell(c2);

                c2 = new PdfPCell(new Phrase("" + detalleFacturaList1.getCosto()));
                c2.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c2);

                c2 = new PdfPCell(new Phrase("" + detalleFacturaList1.getDescuento()));
                c2.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c2);

                c2 = new PdfPCell(new Phrase("" + detalleFacturaList1.getSubtotal()));
                c2.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c2);

                c2 = new PdfPCell(new Phrase("" + detalleFacturaList1.getTotal()));
                c2.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c2);

            }

            document.add(table);

            saltoLinea = new Paragraph();
            saltoLinea.add(new Phrase(Chunk.NEWLINE));
            document.add(saltoLinea);

            //Tabla descuento
            PdfPTable table3 = new PdfPTable(5);

            c1 = new PdfPCell();
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBorderColor(BaseColor.WHITE);
            table3.addCell(c1);

            c1 = new PdfPCell();
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBorderColor(BaseColor.WHITE);
            table3.addCell(c1);

            c1 = new PdfPCell();
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBorderColor(BaseColor.WHITE);
            table3.addCell(c1);

            c1 = new PdfPCell(new Phrase("Total descuento"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table3.addCell(c1);

            c2 = new PdfPCell(new Phrase(UtilidadesFormatos.formatoMoneda(facturaDTO.getTotalDescuento())));
            c2.setHorizontalAlignment(Element.ALIGN_CENTER);
            table3.addCell(c2);

            document.add(table3);

            //Tabla subtotal
            table3 = new PdfPTable(5);

            c1 = new PdfPCell();
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBorderColor(BaseColor.WHITE);
            table3.addCell(c1);

            c1 = new PdfPCell();
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBorderColor(BaseColor.WHITE);
            table3.addCell(c1);

            c1 = new PdfPCell();
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBorderColor(BaseColor.WHITE);
            table3.addCell(c1);

            c1 = new PdfPCell(new Phrase("Subtotal"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table3.addCell(c1);

            c2 = new PdfPCell(new Phrase(UtilidadesFormatos.formatoMoneda(facturaDTO.getSubtotal())));
            c2.setHorizontalAlignment(Element.ALIGN_CENTER);
            table3.addCell(c2);

            document.add(table3);

            //Tabla total
            table3 = new PdfPTable(5);

            c1 = new PdfPCell();
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBorderColor(BaseColor.WHITE);
            table3.addCell(c1);

            c1 = new PdfPCell();
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBorderColor(BaseColor.WHITE);
            table3.addCell(c1);

            c1 = new PdfPCell();
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBorderColor(BaseColor.WHITE);
            table3.addCell(c1);

            c1 = new PdfPCell(new Phrase("Total a pagar"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table3.addCell(c1);

            c2 = new PdfPCell(new Phrase(UtilidadesFormatos.formatoMoneda(facturaDTO.getTotalFactura())));
            c2.setHorizontalAlignment(Element.ALIGN_CENTER);
            table3.addCell(c2);

            document.add(table3);

            // Cerrar el documento
            document.close();

            // Abrir el archivo
            File file = new File("Remision_" + facturaDTO.getNumeroFactura() + "_" + cliente.getCliente() + "_" + facturaDTO.getCodigoCliente() + ".pdf");
            Desktop.getDesktop().open(file);
        } catch (FileNotFoundException ex) {
            logger.error(ExceptionUtils.getFullStackTrace(ex));
            JOptionPane.showMessageDialog(null, "La factura anterior aún se encuentra abierta", "MENSAJE DEL SISTEMA", JOptionPane.ERROR_MESSAGE);

        } catch (DocumentException | IOException ex) {
            logger.error(ExceptionUtils.getFullStackTrace(ex));
            JOptionPane.showMessageDialog(null, "Ocurrió un error generando la remisión, comuniquese con el adminstrador del sistema", "MENSAJE DEL SISTEMA", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

//           public static void main(String[] args) {
//
////        Empresa consultarEmpresa = new Empresa();
////        EmpresaBean empresaBean = new EmpresaBean();
////        Empresa consultarEmpresa = empresaBean.consultarEmpresa();
//        List<DetalleFactura> listaDetalle = new ArrayList<>();
//        DetalleFactura detalleFactura = null;
//
//        for (int i = 0; i < 10; i++) {
//            detalleFactura = new DetalleFactura();
//            detalleFactura.setCantidad(i);
//            Producto producto = new Producto();
//            producto.setCodigoProducto("1241");
//            producto.setDescripcion("unooo");
//            detalleFactura.setProducto(producto);
//            listaDetalle.add(detalleFactura);
//        }
//
//        Factura factura = new Factura();
//        Cliente cliente = new Cliente();
//        cliente.setCliente("tes");
//        cliente.setNitDocumento("101");
//        cliente.setNitDocumento("sdf");
//        cliente.setDireccion("xfghjk");
//        cliente.setTelefono("asdfgy");
//        
//        factura.setCodCliente(cliente);
//        factura.setCodFormapago(1L);
//        factura.setDetalleFacturaList(listaDetalle);
//        factura.setFechaFacturacion(new Date());
//        factura.setFechaVencimiento(new Date());
//        factura.setTotalFactura(188888888L);
//        
//
//        FacturaPDF facturaPDF = new FacturaPDF();
//    }
}
