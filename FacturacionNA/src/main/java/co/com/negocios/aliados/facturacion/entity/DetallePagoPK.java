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
 * @author ander
 */
@Embeddable
public class DetallePagoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "numero_detalle_pago")
    private long numeroDetallePago;
    @Basic(optional = false)
    @Column(name = "cod_pago")
    private long codPago;

    public DetallePagoPK() {
    }

    public DetallePagoPK(long numeroDetallePago, long codPago) {
        this.numeroDetallePago = numeroDetallePago;
        this.codPago = codPago;
    }

    public long getNumeroDetallePago() {
        return numeroDetallePago;
    }

    public void setNumeroDetallePago(long numeroDetallePago) {
        this.numeroDetallePago = numeroDetallePago;
    }

    public long getCodPago() {
        return codPago;
    }

    public void setCodPago(long codPago) {
        this.codPago = codPago;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) numeroDetallePago;
        hash += (int) codPago;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetallePagoPK)) {
            return false;
        }
        DetallePagoPK other = (DetallePagoPK) object;
        if (this.numeroDetallePago != other.numeroDetallePago) {
            return false;
        }
        if (this.codPago != other.codPago) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication1.DetallePagoPK[ numeroDetallePago=" + numeroDetallePago + ", codPago=" + codPago + " ]";
    }
    
}
