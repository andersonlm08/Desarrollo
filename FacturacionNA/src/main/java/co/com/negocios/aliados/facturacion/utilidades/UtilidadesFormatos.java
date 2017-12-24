/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.negocios.aliados.facturacion.utilidades;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

/**
 *
 * @author jorge.lopez
 */
public final class UtilidadesFormatos {

    private final static Logger LOGGER = Logger.getLogger(UtilidadesFormatos.class.getName());

    private UtilidadesFormatos() {
    }

    public static String formatoMonto(Integer parametro) {
        DecimalFormat myFormatter = new DecimalFormat("$###,###.###");
        return myFormatter.format(parametro);
    }

    public static String getFecha(Date fecha) {
        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd");
        return d.format(fecha);
    }

    public static Date sumarRestarDiasFecha(Date fecha, int dias) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha); // Configuramos la fecha que se recibe	
        calendar.add(Calendar.DAY_OF_YEAR, dias);  // numero de días a añadir, o restar en caso de días<0	
        return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos	
    }

    public static Date toDate(String fecha) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return formatter.parse(fecha);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static String formatoMoneda(Long monto) {
        DecimalFormat myFormatter = new DecimalFormat("$###,###.###");
        return myFormatter.format(monto);
    }
    
}
