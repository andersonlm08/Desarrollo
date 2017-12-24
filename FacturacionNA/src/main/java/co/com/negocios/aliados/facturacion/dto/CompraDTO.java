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
public class CompraDTO {

    private Long numeroCompra;
    private String facturaCompra;
    private Date fechaRegistro;
    private Date fechaCompra;
    private Long diasPlazo;
    private Date fechaVencimiento;
    private String usuarioRegistro;
    private Long subtotal;
    private Long totalDescuento;
    private long totalCompra;
    private Long iva;
    private BigDecimal valorPagado;
    private BigDecimal debe;
    private String observacion;
    private String nitProveedor;
    private List<DetalleCompraDTO> detalleCompraList;

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

    public String getNitProveedor() {
        return nitProveedor;
    }

    public void setNitProveedor(String nitProveedor) {
        this.nitProveedor = nitProveedor;
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

    public List<DetalleCompraDTO> getDetalleCompraList() {
        return detalleCompraList;
    }

    public void setDetalleCompraList(List<DetalleCompraDTO> detalleCompraList) {
        this.detalleCompraList = detalleCompraList;
    }

}
