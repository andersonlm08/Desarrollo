/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.negocios.aliados.facturacion.adapter;

import co.com.negocios.aliados.facturacion.dao.ProductosBean;
import co.com.negocios.aliados.facturacion.dto.ProductoDTO;
import co.com.negocios.aliados.facturacion.dto.ProveedorDTO;
import co.com.negocios.aliados.facturacion.dto.TipoArticuloDTO;
import co.com.negocios.aliados.facturacion.entity.Producto;
import co.com.negocios.aliados.facturacion.entity.Proveedor;
import co.com.negocios.aliados.facturacion.entity.TipoArticulo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ander
 */
public class ProductosAdapter {

    private EntityManagerFactory emf = null;

    private final ProductosBean productosBean;

    public ProductosAdapter() {
        this.emf = Persistence.createEntityManagerFactory("facturacionPU");
        productosBean = new ProductosBean();
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void guardarProducto(ProductoDTO productoDTO) {
        EntityManager em = getEntityManager();
        Producto producto = getProducto(productoDTO);
        productosBean.guardarProducto(producto, em);
    }

    public void actualizarProducto(ProductoDTO productoDTO) {
        EntityManager em = getEntityManager();
        Producto producto = productosBean.consultarProducto(productoDTO.getCodigoProducto(), em);
        producto.setCodigoProducto(productoDTO.getCodigoProducto());
        producto.setDescripcion(productoDTO.getDescripcion());

        TipoArticulo tipoArticulo = new TipoArticulo();
        tipoArticulo.setCodigoTipoArticulo(productoDTO.getCodTipoArticuloDTO().getCodigoTipoArticulo());
        producto.setCodTipoArticulo(tipoArticulo);
        producto.setCantidad(productoDTO.getCantidad());

        Proveedor proveedor = new Proveedor();
        proveedor.setNitDocumento(productoDTO.getCodProveedorDTO().getNitDocumento());
        producto.setCodProveedor(proveedor);

        productosBean.actualizarProducto(producto, em);
    }

    public List<ProductoDTO> consultarProductos() {
        EntityManager em = getEntityManager();

        List<ProductoDTO> listaProductos = new ArrayList<>();
        ProductoDTO productoDTO = null;
        List<Producto> consultarProductos = productosBean.consultarProductos(em);
        for (Producto producto : consultarProductos) {
            productoDTO = new ProductoDTO();
            productoDTO.setCodigoProducto(producto.getCodigoProducto());
            productoDTO.setDescripcion(producto.getDescripcion());
            productoDTO.setCantidad(producto.getCantidad());
            productoDTO.setFechaIngreso(producto.getFechaIngreso());
            productoDTO.setPedido(producto.getPedido());
            productoDTO.setPrecioCompra(producto.getPrecioCompra());

            TipoArticuloDTO tipoArticuloDTO = new TipoArticuloDTO();
            tipoArticuloDTO.setCodigoTipoArticulo(producto.getCodTipoArticulo().getCodigoTipoArticulo());
            tipoArticuloDTO.setDescripcion(producto.getDescripcion());
            productoDTO.setCodTipoArticuloDTO(tipoArticuloDTO);

            ProveedorDTO proveedorDTO = new ProveedorDTO();
            proveedorDTO.setNitDocumento(producto.getCodProveedor().getNitDocumento());
            proveedorDTO.setNombreComercial(producto.getCodProveedor().getNombreComercial());
            productoDTO.setCodProveedorDTO(proveedorDTO);

            listaProductos.add(productoDTO);
        }

        return listaProductos;
    }

    public ProductoDTO consultarProducto(String id) {
        EntityManager em = getEntityManager();

        Producto producto = productosBean.consultarProducto(id, em);

        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setCodigoProducto(producto.getCodigoProducto());
        productoDTO.setDescripcion(producto.getDescripcion());
        productoDTO.setCantidad(producto.getCantidad());
        productoDTO.setFechaIngreso(producto.getFechaIngreso());
        productoDTO.setPedido(producto.getPedido());
        productoDTO.setPrecioCompra(producto.getPrecioCompra());

        TipoArticuloDTO tipoArticuloDTO = new TipoArticuloDTO();
        tipoArticuloDTO.setCodigoTipoArticulo(producto.getCodTipoArticulo().getCodigoTipoArticulo());
        tipoArticuloDTO.setDescripcion(producto.getDescripcion());
        productoDTO.setCodTipoArticuloDTO(tipoArticuloDTO);

        ProveedorDTO proveedorDTO = new ProveedorDTO();
        proveedorDTO.setNitDocumento(producto.getCodProveedor().getNitDocumento());
        proveedorDTO.setNombreComercial(producto.getCodProveedor().getNombreComercial());
        productoDTO.setCodProveedorDTO(proveedorDTO);

        return productoDTO;
    }

    public boolean existeProducto(String id) {
        EntityManager em = getEntityManager();
        return productosBean.existeProducto(id, em);
    }

    public void eliminarProducto(String string) {
        EntityManager em = getEntityManager();
        productosBean.eliminarProducto(string, em);
    }

    private Producto getProducto(ProductoDTO productoDTO) {
        Producto producto = new Producto();
        producto.setCodigoProducto(productoDTO.getCodigoProducto());
        producto.setDescripcion(productoDTO.getDescripcion());

        TipoArticulo tipoArticulo = new TipoArticulo();
        tipoArticulo.setCodigoTipoArticulo(productoDTO.getCodTipoArticuloDTO().getCodigoTipoArticulo());
        producto.setCodTipoArticulo(tipoArticulo);
        producto.setCantidad(productoDTO.getCantidad());

        Proveedor proveedor = new Proveedor();
        proveedor.setNitDocumento(productoDTO.getCodProveedorDTO().getNitDocumento());
        producto.setCodProveedor(proveedor);

        return producto;
    }

}
