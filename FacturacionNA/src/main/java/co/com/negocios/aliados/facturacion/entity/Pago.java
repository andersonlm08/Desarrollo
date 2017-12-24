/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.negocios.aliados.facturacion.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ander
 */
@Entity
@Table(name = "pago")
public class Pago implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "numero_pago")
    private Long numeroPago;
    @Basic(optional = false)
    @Column(name = "dias_pago")
    private long diasPago;
    @Column(name = "fecha_vencimiento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVencimiento;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "total_factura")
    private BigDecimal totalFactura;
    @Basic(optional = false)
    @Column(name = "total_pagado")
    private BigDecimal totalPagado;
    @Basic(optional = false)
    @Column(name = "estado_pago")
    private boolean estadoPago;
    
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codPago")
    private List<Factura> facturaList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pago")
    private List<DetallePago> detallePagoList;

    public Pago() {
    }

    public Pago(Long numeroPago) {
        this.numeroPago = numeroPago;
    }

    public Pago(Long numeroPago, long diasPago, BigDecimal totalPagado, BigDecimal totalDeuda, boolean estadoPago) {
        this.numeroPago = numeroPago;
        this.diasPago = diasPago;
        this.totalPagado = totalPagado;
        this.estadoPago = estadoPago;
    }

    public Long getNumeroPago() {
        return numeroPago;
    }

    public void setNumeroPago(Long numeroPago) {
        this.numeroPago = numeroPago;
    }

    public long getDiasPago() {
        return diasPago;
    }

    public void setDiasPago(long diasPago) {
        this.diasPago = diasPago;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public BigDecimal getTotalPagado() {
        return totalPagado;
    }

    public void setTotalPagado(BigDecimal totalPagado) {
        this.totalPagado = totalPagado;
    }

    public BigDecimal getTotalFactura() {
        return totalFactura;
    }

    public void setTotalFactura(BigDecimal totalFactura) {
        this.totalFactura = totalFactura;
    }   

    public boolean getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(boolean estadoPago) {
        this.estadoPago = estadoPago;
    }

    @XmlTransient
    public List<Factura> getFacturaList() {
        return facturaList;
    }

    public void setFacturaList(List<Factura> facturaList) {
        this.facturaList = facturaList;
    }

    @XmlTransient
    public List<DetallePago> getDetallePagoList() {
        return detallePagoList;
    }

    public void setDetallePagoList(List<DetallePago> detallePagoList) {
        this.detallePagoList = detallePagoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numeroPago != null ? numeroPago.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pago)) {
            return false;
        }
        Pago other = (Pago) object;
        if ((this.numeroPago == null && other.numeroPago != null) || (this.numeroPago != null && !this.numeroPago.equals(other.numeroPago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication1.Pago[ numeroPago=" + numeroPago + " ]";
    }
    
}
