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
public class ClienteDTO {

    private String nitDocumento;
    private String cliente;
    private String direccion;
    private String telefono;
    private String fax;
    private String email;
    private String web;
    private String representante;
    private boolean activo;
    private CiudadDTO codCiudadDTO;
    private List<FacturaDTO> facturaListDTO;

    public String getNitDocumento() {
        return nitDocumento;
    }

    public void setNitDocumento(String nitDocumento) {
        this.nitDocumento = nitDocumento;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getRepresentante() {
        return representante;
    }

    public void setRepresentante(String representante) {
        this.representante = representante;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public CiudadDTO getCodCiudadDTO() {
        return codCiudadDTO;
    }

    public void setCodCiudadDTO(CiudadDTO codCiudadDTO) {
        this.codCiudadDTO = codCiudadDTO;
    }

    public List<FacturaDTO> getFacturaListDTO() {
        return facturaListDTO;
    }

    public void setFacturaListDTO(List<FacturaDTO> facturaListDTO) {
        this.facturaListDTO = facturaListDTO;
    }

    @Override
    public String toString() {
        return cliente;
    }
}
