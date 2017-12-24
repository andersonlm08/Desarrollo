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
public class DetalleFacturaPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "cod_factura")
    private long codFactura;
    @Basic(optional = false)
    @Column(name = "cod_producto")
    private String codProducto;

    public DetalleFacturaPK() {
    }

    public DetalleFacturaPK(long codFactura, String codProducto) {
        this.codFactura = codFactura;
        this.codProducto = codProducto;
    }

    public long getCodFactura() {
        return codFactura;
    }

    public void setCodFactura(long codFactura) {
        this.codFactura = codFactura;
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
        hash += (int) codFactura;
        hash += (codProducto != null ? codProducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleFacturaPK)) {
            return false;
        }
        DetalleFacturaPK other = (DetalleFacturaPK) object;
        if (this.codFactura != other.codFactura) {
            return false;
        }
        if ((this.codProducto == null && other.codProducto != null) || (this.codProducto != null && !this.codProducto.equals(other.codProducto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.negocios.aliados.facturacion.entity.DetalleFacturaPK[ codFactura=" + codFactura + ", codProducto=" + codProducto + " ]";
    }
    
}
