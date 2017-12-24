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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
 * @author jorge.lopez
 */
@Entity
@Table(name = "factura")
public class Factura implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "numero_factura")
    private Long numeroFactura;
    @Basic(optional = false)
    @Column(name = "nombre_empleado")
    private String nombreEmpleado;
    @Column(name = "fecha_facturacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFacturacion;
    @Column(name = "fecha_actualizacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaActualizacion;
    @Basic(optional = false)
    @Column(name = "cod_formapago")
    private long codFormapago;
    @Column(name = "total_factura")
    private Long totalFactura;
    @Column(name = "total_descuento")
    private Long totalDescuento;
    @Column(name = "subtotal")
    private Long subtotal;
    @Column(name = "fecha_vencimiento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVencimiento;
    @Column(name = "dias_plazo")
    private Long diasPlazo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor_pagado")
    private BigDecimal valorPagado;
    @Column(name = "debe")
    private BigDecimal debe;
    @Column(name = "observacion")
    private String observacion;
    @JoinColumn(name = "cod_cliente", referencedColumnName = "nit_documento")
    @ManyToOne(optional = false)
    private Cliente codCliente;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "factura")
    
    private List<DetalleFactura> detalleFacturaList;
//    @JoinColumn(name = "cod_pago", referencedColumnName = "numero_pago")
//    @ManyToOne(optional = false)
    private Pago codPago;
    
    public Factura() {
    }

    public Factura(Long numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public Factura(Long numeroFactura, String nombreEmpleado, long codFormapago) {
        this.numeroFactura = numeroFactura;
        this.nombreEmpleado = nombreEmpleado;
        this.codFormapago = codFormapago;
    }

    public Long getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(Long numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public Date getFechaFacturacion() {
        return fechaFacturacion;
    }

    public void setFechaFacturacion(Date fechaFacturacion) {
        this.fechaFacturacion = fechaFacturacion;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public long getCodFormapago() {
        return codFormapago;
    }

    public void setCodFormapago(long codFormapago) {
        this.codFormapago = codFormapago;
    }

    public Long getTotalFactura() {
        return totalFactura;
    }

    public void setTotalFactura(Long totalFactura) {
        this.totalFactura = totalFactura;
    }

    public Long getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Long subtotal) {
        this.subtotal = subtotal;
    }

    public Long getTotalDescuento() {
        return totalDescuento;
    }

    public void setTotalDescuento(Long totalDescuento) {
        this.totalDescuento = totalDescuento;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Long getDiasPlazo() {
        return diasPlazo;
    }

    public void setDiasPlazo(Long diasPlazo) {
        this.diasPlazo = diasPlazo;
    }

    public BigDecimal getValorPagado() {
        return valorPagado;
    }

    public void setValorPagado(BigDecimal valorPagado) {
        this.valorPagado = valorPagado;
    }

    public BigDecimal getDebe() {
        return debe;
    }

    public void setDebe(BigDecimal debe) {
        this.debe = debe;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Cliente getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(Cliente codCliente) {
        this.codCliente = codCliente;
    }

     public Pago getCodPago() {
        return codPago;
    }

    public void setCodPago(Pago codPago) {
        this.codPago = codPago;
    }
    
    @XmlTransient
    public List<DetalleFactura> getDetalleFacturaList() {
        return detalleFacturaList;
    }

    public void setDetalleFacturaList(List<DetalleFactura> detalleFacturaList) {
        this.detalleFacturaList = detalleFacturaList;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numeroFactura != null ? numeroFactura.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Factura)) {
            return false;
        }
        Factura other = (Factura) object;
        if ((this.numeroFactura == null && other.numeroFactura != null) || (this.numeroFactura != null && !this.numeroFactura.equals(other.numeroFactura))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.negocios.aliados.facturacion.entity.Factura[ numeroFactura=" + numeroFactura + " ]";
    }

}
