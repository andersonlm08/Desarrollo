/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.negocios.aliados.facturacion.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author jorge.lopez
 */
@Embeddable
public class DetalleCompraPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "cod_compra")
    private long codCompra;
    @Basic(optional = false)
    @Column(name = "cod_producto")
    private String codProducto;

    public DetalleCompraPK() {
    }

    public DetalleCompraPK(long codCompra, String codProducto) {
        this.codCompra = codCompra;
        this.codProducto = codProducto;
    }

    public long getCodCompra() {
        return codCompra;
    }

    public void setCodCompra(long codCompra) {
        this.codCompra = codCompra;
    }

    public String getCodProducto() {
        return codProducto;
    }

    public void setCodProducto(String codProducto) {
        this.codProducto = codProducto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) codCompra;
        hash += (codProducto != null ? codProducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleCompraPK)) {
            return false;
        }
        DetalleCompraPK other = (DetalleCompraPK) object;
        if (this.codCompra != other.codCompra) {
            return false;
        }
        if ((this.codProducto == null && other.codProducto != null) || (this.codProducto != null && !this.codProducto.equals(other.codProducto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.negocios.aliados.facturacion.entity.DetalleCompraPK[ codCompra=" + codCompra + ", codProducto=" + codProducto + " ]";
    }
    
}
