package co.com.negocios.aliados.facturacion.utilidades;

import co.com.negocios.aliados.facturacion.dto.EmpresaDTO;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UtilidadesProperties {

//    public static final String RUTA_PROPIEDADES = File.listRoots()[0] + "D:\\recursos\\configuracion.properties";
    public static final String RUTA_PROPIEDADES = "F:\\sistema\\software\\recursos\\configuracion.properties";
    private static Logger log = Logger.getLogger(UtilidadesProperties.class.getName());

    /**
     * Creamos un Objeto de tipo Properties
     */
    private Properties propiedades;

    public UtilidadesProperties() {
        cargarPropiedades();
    }

    private void cargarPropiedades() {
        propiedades = new Properties();
        try {
            /**
             * Cargamos el archivo desde la ruta especificada
             */
            propiedades.load(new FileInputStream(RUTA_PROPIEDADES));

        } catch (FileNotFoundException e) {
            log.severe("Error, El archivo no exite");
        } catch (IOException e) {
            log.severe("Error, No se puede leer el archivo");
        }
    }

    private String obtenerPropiedad(String parametro) throws FileNotFoundException {
        return propiedades.getProperty(parametro);
    }

    public EmpresaDTO consultarDatosEmpresa() {
        EmpresaDTO empresaDTO = new EmpresaDTO();
        try {
            empresaDTO.setNit(obtenerPropiedad("empresa.nit"));
            empresaDTO.setEmpresa(obtenerPropiedad("empresa.empresa"));
            empresaDTO.setDireccion(obtenerPropiedad("empresa.direccion"));
            empresaDTO.setTelefono(obtenerPropiedad("empresa.celular"));
            empresaDTO.setFax(obtenerPropiedad("empresa.fax"));
            empresaDTO.setCelular(obtenerPropiedad("empresa.telefono"));
            empresaDTO.setEmail(obtenerPropiedad("empresa.email"));
            empresaDTO.setWeb(obtenerPropiedad("empresa.web"));
            empresaDTO.setNumeroLicencia(obtenerPropiedad("empresa.numero.licencia"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(UtilidadesProperties.class.getName()).log(Level.SEVERE, null, ex);
        }
        return empresaDTO;
    }
//    
//    public static void main(String[] args) {
//        UtilidadesProperties properties= new UtilidadesProperties();
//        
//        EmpresaDTO consultarDatosEmpresa = properties.consultarDatosEmpresa();
//        
//        System.out.println("  asdfsd " +consultarDatosEmpresa.getCelular() );
//        
//    }

}
