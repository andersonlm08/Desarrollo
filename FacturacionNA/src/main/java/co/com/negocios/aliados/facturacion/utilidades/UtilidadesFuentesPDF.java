/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.negocios.aliados.facturacion.utilidades;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jolopez
 */
public final class UtilidadesFuentesPDF {

    private UtilidadesFuentesPDF() {
    }

    public static Font obtenerFuentegarden() {
        BaseFont bf_russian = null;
        try {
            bf_russian = BaseFont.createFont("Grandma's Garden.ttf", "Cp1250", BaseFont.EMBEDDED);
        } catch (DocumentException ex) {
            Logger.getLogger(FacturaPDF.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FacturaPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
        com.itextpdf.text.Font russian = new com.itextpdf.text.Font(bf_russian, 18);
        return russian;
    }
//    public static void main(String[] args) {
//        Font obtenerFuentegarden = UtilidadesFuentesPDF.obtenerFuentegarden();
//    }
}
