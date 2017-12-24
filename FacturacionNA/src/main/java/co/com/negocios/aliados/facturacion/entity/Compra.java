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
@Table(name = "compra")
public class Compra implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "numero_compra")
    private Long numeroCompra;
    @Basic(optional = false)
    @Column(name = "factura_compra")
    private String facturaCompra;
    @Basic(optional = false)
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "fecha_compra")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCompra;
    @Basic(optional = false)
    @Column(name = "dias_plazo")
    private Long diasPlazo;
    @Column(name = "fecha_vencimiento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVencimiento;
    @Basic(optional = false)
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Basic(optional = false)
    @Column(name = "total_compra")
    private long totalCompra;
    @Column(name = "total_descuento")
    private Long totalDescuento;
    @Column(name = "subtotal")
    private Long subtotal;
    @Column(name = "iva")
    private Long iva;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "valor_pagado")
    private BigDecimal valorPagado;
    @Column(name = "debe")
    private BigDecimal debe;
    @Column(name = "observacion")
    private String observacion;
    @JoinColumn(name = "nit_proveedor", referencedColumnName = "nit_documento")
    @ManyToOne(optional = false)
    private Proveedor nitProveedor;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "compra")
    private List<DetalleCompra> detalleCompraList;

    public Compra() {
    }

    public Compra(Long numeroCompra) {
        this.numeroCompra = numeroCompra;
    }

    public Compra(Long numeroCompra, String facturaCompra, Date fechaRegistro, Long diasPlazo, String usuarioRegistro, long totalCompra, BigDecimal valorPagado) {
        this.numeroCompra = numeroCompra;
        this.facturaCompra = facturaCompra;
        this.fechaRegistro = fechaRegistro;
        this.diasPlazo = diasPlazo;
        this.usuarioRegistro = usuarioRegistro;
        this.totalCompra = totalCompra;
        this.valorPagado = valorPagado;
    }

    public Long getNumeroCompra() {
        return numeroCompra;
    }

    public void setNumeroCompra(Long numeroCompra) {
        this.numeroCompra = numeroCompra;
    }

    public String getFacturaCompra() {
        return facturaCompra;
    }

    public void setFacturaCompra(String facturaCompra) {
        this.facturaCompra = facturaCompra;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public Long getDiasPlazo() {
        return diasPlazo;
    }

    public void setDiasPlazo(Long diasPlazo) {
        this.diasPlazo = diasPlazo;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getUsuarioRegistro() {
        return usuarioRegistro;
    }

    public void setUsuarioRegistro(String usuarioRegistro) {
        this.usuarioRegistro = usuarioRegistro;
    }

    public long getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(long totalCompra) {
        this.totalCompra = totalCompra;
    }

    public Long getTotalDescuento() {
        return totalDescuento;
    }

    public void setTotalDescuento(Long totalDescuento) {
        this.totalDescuento = totalDescuento;
    }

    public Long getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Long subtotal) {
        this.subtotal = subtotal;
    }

    public Long getIva() {
        return iva;
    }

    public void setIva(Long iva) {
        this.iva = iva;
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

    public Proveedor getNitProveedor() {
        return nitProveedor;
    }

    public void setNitProveedor(Proveedor nitProveedor) {
        this.nitProveedor = nitProveedor;
    }

    @XmlTransient
    public List<DetalleCompra> getDetalleCompraList() {
        return detalleCompraList;
    }

    public void setDetalleCompraList(List<DetalleCompra> detalleCompraList) {
        this.detalleCompraList = detalleCompraList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numeroCompra != null ? numeroCompra.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Compra)) {
            return false;
        }
        Compra other = (Compra) object;
        if ((this.numeroCompra == null && other.numeroCompra != null) || (this.numeroCompra != null && !this.numeroCompra.equals(other.numeroCompra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.negocios.aliados.facturacion.entity.Compra[ numeroCompra=" + numeroCompra + " ]";
    }
    
}
