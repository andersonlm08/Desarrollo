/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.negocios.aliados.facturacion.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author jorge.lopez
 */
public class FacturaDTO {

    private Long numeroFactura;
    private String nombreEmpleado;
    private Date fechaFacturacion;
    private Date fechaActualizacion;
    private long codFormapago;
    private Long totalFactura;
    private Long subtotal;
    private Long totalDescuento;
    private Date fechaVencimiento;
    private Long diasPlazo;
    private BigDecimal valorPagado;
    private BigDecimal debe;
    private String observacion;
    private String codigoCliente;
    private List<DetalleFacturaDTO> detalleFacturaList;

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

    public Long getTotalDescuento() {
        return totalDescuento;
    }

    public Long getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Long subtotal) {
        this.subtotal = subtotal;
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

    public String getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public List<DetalleFacturaDTO> getDetalleFacturaList() {
        return detalleFacturaList;
    }

    public void setDetalleFacturaList(List<DetalleFacturaDTO> detalleFacturaList) {
        this.detalleFacturaList = detalleFacturaList;
    }

    
}
