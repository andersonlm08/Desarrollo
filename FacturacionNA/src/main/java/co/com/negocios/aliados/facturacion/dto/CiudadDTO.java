/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.negocios.aliados.facturacion.dto;


import java.util.List;

/**
 *
 * @author ander
 */
public class CiudadDTO {
    
    private String codigoCiudad;
    private String mombreCiudad;
    private List<ClienteDTO> clienteListDTO;
    private List<ProveedorDTO> proveedorListDTO;

    public String getCodigoCiudad() {
        return codigoCiudad;
    }

    public void setCodigoCiudad(String codigoCiudad) {
        this.codigoCiudad = codigoCiudad;
    }

    public String getMombreCiudad() {
        return mombreCiudad;
    }

    public void setMombreCiudad(String mombreCiudad) {
        this.mombreCiudad = mombreCiudad;
    }

    public List<ClienteDTO> getClienteListDTO() {
        return clienteListDTO;
    }

    public void setClienteListDTO(List<ClienteDTO> clienteListDTO) {
        this.clienteListDTO = clienteListDTO;
    }

    public List<ProveedorDTO> getProveedorListDTO() {
        return proveedorListDTO;
    }

    public void setProveedorListDTO(List<ProveedorDTO> proveedorListDTO) {
        this.proveedorListDTO = proveedorListDTO;
    }
    
    
}
