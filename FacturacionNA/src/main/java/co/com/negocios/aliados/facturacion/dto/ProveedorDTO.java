/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.negocios.aliados.facturacion.dto;

import co.com.negocios.aliados.facturacion.entity.Proveedor;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author ander
 */
public class ProveedorDTO {

    private String nitDocumento;
    private String nombreComercial;
    private String direccion;
    private String telefono;
    private String fax;
    private String email;
    private String web;
    private String representante;
    private List<CompraDTO> compraListDTO;
    private CiudadDTO codCiudadDTO;
    private List<ProductoDTO> productoListDTO;

    public String getNitDocumento() {
        return nitDocumento;
    }

    public void setNitDocumento(String nitDocumento) {
        this.nitDocumento = nitDocumento;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
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

    public List<CompraDTO> getCompraListDTO() {
        return compraListDTO;
    }

    public void setCompraListDTO(List<CompraDTO> compraListDTO) {
        this.compraListDTO = compraListDTO;
    }

    public CiudadDTO getCodCiudadDTO() {
        return codCiudadDTO;
    }

    public void setCodCiudadDTO(CiudadDTO codCiudadDTO) {
        this.codCiudadDTO = codCiudadDTO;
    }

    public List<ProductoDTO> getProductoListDTO() {
        return productoListDTO;
    }

    public void setProductoListDTO(List<ProductoDTO> productoListDTO) {
        this.productoListDTO = productoListDTO;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nitDocumento != null ? nitDocumento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Proveedor)) {
            return false;
        }
        final ProveedorDTO other = (ProveedorDTO) object;
        return Objects.equals(this.nitDocumento, other.nitDocumento);
//        return !((this.nitDocumento == null && other.nitDocumento != null) || (this.nitDocumento != null && !this.nitDocumento.equals(other.nitDocumento)));
    }
    
//      public boolean equals(Object obj) {
//        if (this == obj) {
//            return true;
//        }
//        if (obj == null) {
//            return false;
//        }
//        if (getClass() != obj.getClass()) {
//            return false;
//        }
//        final TipoArticuloDTO other = (TipoArticuloDTO) obj;
//        return Objects.equals(this.codigoTipoArticulo, other.codigoTipoArticulo);
//    }
    @Override
    public String toString() {
        return nombreComercial;
    }

}
