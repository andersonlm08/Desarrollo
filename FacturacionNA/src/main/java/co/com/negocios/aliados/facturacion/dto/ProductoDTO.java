/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.negocios.aliados.facturacion.dto;

import java.util.Date;
import java.util.List;

/**
 *
 * @author ander
 */
public class ProductoDTO {

    private String codigoProducto;

    private String descripcion;

    private Integer precioCompra;

    private long cantidad;

    private Long pedido;

    private Date fechaIngreso;

    private List<DetalleFacturaDTO> detalleFacturaListDTO;

    private ProveedorDTO codProveedorDTO;

    private TipoArticuloDTO codTipoArticuloDTO;

    private List<DetalleCompraDTO> detalleCompraListDTO;

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(Integer precioCompra) {
        this.precioCompra = precioCompra;
    }

    public long getCantidad() {
        return cantidad;
    }

    public void setCantidad(long cantidad) {
        this.cantidad = cantidad;
    }

    public Long getPedido() {
        return pedido;
    }

    public void setPedido(Long pedido) {
        this.pedido = pedido;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public List<DetalleFacturaDTO> getDetalleFacturaListDTO() {
        return detalleFacturaListDTO;
    }

    public void setDetalleFacturaListDTO(List<DetalleFacturaDTO> detalleFacturaListDTO) {
        this.detalleFacturaListDTO = detalleFacturaListDTO;
    }

    public ProveedorDTO getCodProveedorDTO() {
        return codProveedorDTO;
    }

    public void setCodProveedorDTO(ProveedorDTO codProveedorDTO) {
        this.codProveedorDTO = codProveedorDTO;
    }

    public TipoArticuloDTO getCodTipoArticuloDTO() {
        return codTipoArticuloDTO;
    }

    public void setCodTipoArticuloDTO(TipoArticuloDTO codTipoArticuloDTO) {
        this.codTipoArticuloDTO = codTipoArticuloDTO;
    }

    public List<DetalleCompraDTO> getDetalleCompraListDTO() {
        return detalleCompraListDTO;
    }

    public void setDetalleCompraListDTO(List<DetalleCompraDTO> detalleCompraListDTO) {
        this.detalleCompraListDTO = detalleCompraListDTO;
    }

}
