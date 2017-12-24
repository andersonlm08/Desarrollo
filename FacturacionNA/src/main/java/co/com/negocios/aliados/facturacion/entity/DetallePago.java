/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.negocios.aliados.facturacion.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ander
 */
@Entity
@Table(name = "detalle_pago")
public class DetallePago implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DetallePagoPK detallePagoPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "monto")
    private BigDecimal monto;
    @Basic(optional = false)
    @Column(name = "fecha_pago")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPago;
    @JoinColumn(name = "cod_pago", referencedColumnName = "numero_pago", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Pago pago;

    public DetallePago() {
    }

    public DetallePago(DetallePagoPK detallePagoPK) {
        this.detallePagoPK = detallePagoPK;
    }

    public DetallePago(DetallePagoPK detallePagoPK, BigDecimal monto, Date fechaPago) {
        this.detallePagoPK = detallePagoPK;
        this.monto = monto;
        this.fechaPago = fechaPago;
    }

    public DetallePago(long numeroDetallePago, long codPago) {
        this.detallePagoPK = new DetallePagoPK(numeroDetallePago, codPago);
    }

    public DetallePagoPK getDetallePagoPK() {
        return detallePagoPK;
    }

    public void setDetallePagoPK(DetallePagoPK detallePagoPK) {
        this.detallePagoPK = detallePagoPK;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (detallePagoPK != null ? detallePagoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetallePago)) {
            return false;
        }
        DetallePago other = (DetallePago) object;
        if ((this.detallePagoPK == null && other.detallePagoPK != null) || (this.detallePagoPK != null && !this.detallePagoPK.equals(other.detallePagoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication1.DetallePago[ detallePagoPK=" + detallePagoPK + " ]";
    }
    
}
