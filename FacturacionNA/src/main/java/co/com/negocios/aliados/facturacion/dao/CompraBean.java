/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.negocios.aliados.facturacion.dao;

import static co.com.negocios.aliados.facturacion.dao.FacturaBean.logger;
import co.com.negocios.aliados.facturacion.dto.CompraDTO;
import co.com.negocios.aliados.facturacion.dto.DetalleCompraDTO;
import co.com.negocios.aliados.facturacion.dto.DetalleFacturaDTO;
import co.com.negocios.aliados.facturacion.dto.FacturaDTO;
import co.com.negocios.aliados.facturacion.entity.Cliente;
import co.com.negocios.aliados.facturacion.entity.Compra;
import co.com.negocios.aliados.facturacion.entity.DetalleCompra;
import co.com.negocios.aliados.facturacion.entity.DetalleCompraPK;
import co.com.negocios.aliados.facturacion.entity.DetalleFactura;
import co.com.negocios.aliados.facturacion.entity.DetalleFacturaPK;
import co.com.negocios.aliados.facturacion.entity.Factura;
import co.com.negocios.aliados.facturacion.entity.Producto;
import co.com.negocios.aliados.facturacion.entity.Proveedor;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

/**
 *
 * @author jorge.lopez
 */
public class CompraBean {

    final static Logger logger = org.apache.log4j.Logger.getLogger(CompraBean.class);

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("facturacionPU");
    EntityManager em = emf.createEntityManager();

    public Long guardarCompra(CompraDTO compraDTO, Proveedor proveedor) {
        Long secuencia = 0L;
        try {
            EntityTransaction etx = em.getTransaction();
            etx.begin();
            //Hacer prueba si hace rollback de secuencia
            secuencia = consultarSecuenciaCompra();

            Compra compra = new Compra();
            compra.setNumeroCompra(secuencia);
            compra.setFacturaCompra(compraDTO.getFacturaCompra());
            compra.setFechaRegistro(new Date());
            compra.setFechaCompra(new Date());
            compra.setDiasPlazo(compraDTO.getDiasPlazo());
            compra.setFechaVencimiento(compraDTO.getFechaVencimiento());
            compra.setUsuarioRegistro(compraDTO.getUsuarioRegistro());
            compra.setTotalCompra(compraDTO.getTotalCompra());
            compra.setSubtotal(compraDTO.getSubtotal());
            compra.setTotalDescuento(compraDTO.getTotalDescuento());
            compra.setIva(0L);
            compra.setValorPagado(new BigDecimal(0));
            compra.setDebe(new BigDecimal(0));
            compra.setObservacion(compraDTO.getObservacion());
            compra.setNitProveedor(proveedor);

            em.persist(compra);
            compra.setDetalleCompraList(getDetalleCompra(compraDTO.getDetalleCompraList(), secuencia));
            em.merge(compra);

            etx.commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            logger.error(e);
        }
        return secuencia;
    }

    public List<DetalleCompra> getDetalleCompra(List<DetalleCompraDTO> detalleCompraDTO, Long secuencia) {
        List<DetalleCompra> listaDetalles = new ArrayList<>();
        DetalleCompra detalleCompra = null;
        for (DetalleCompraDTO detalleCompraDTOTemp : detalleCompraDTO) {
            detalleCompra = new DetalleCompra();

            DetalleCompraPK detalleFacturaPK = new DetalleCompraPK(secuencia, detalleCompraDTOTemp.getCodigoProducto());
            detalleCompra.setDetalleCompraPK(detalleFacturaPK);
            detalleCompra.setCantidad(detalleCompraDTOTemp.getCantidad());
            detalleCompra.setCosto(detalleCompraDTOTemp.getCosto());
            detalleCompra.setDescuento(detalleCompraDTOTemp.getDescuento());
            detalleCompra.setSubtotal(new BigDecimal(detalleCompraDTOTemp.getSubtotal()));
            detalleCompra.setTotal(new BigDecimal(detalleCompraDTOTemp.getTotal()));
            actualizarInventarioProducto(detalleCompraDTOTemp.getCodigoProducto(), detalleCompraDTOTemp.getCantidad(), detalleCompraDTOTemp.getCosto());

            listaDetalles.add(detalleCompra);
        }
        return listaDetalles;
    }

    private void actualizarInventarioProducto(String codigoProducto, long cantidadRestar, BigDecimal precioCompra) {
        Producto producto = consultarProducto(codigoProducto);
        producto.setCantidad(producto.getCantidad() + cantidadRestar);
        producto.setPrecioCompra(precioCompra.intValue());
        actualizarProducto(producto);
    }

    public void actualizarProducto(Producto producto) {
        try {
            em.merge(producto);
        } catch (Exception e) {
            em.getTransaction().rollback();
            logger.error(e);
        }
    }

    public Producto consultarProducto(String id) {
        TypedQuery<Producto> query = null;
        try {
            query = em.createQuery("select c from Producto c where c.codigoProducto = :id", Producto.class);
            query.setParameter("id", id);
        } catch (Exception e) {
            em.getTransaction().rollback();
            logger.error(e);
        }
        return query.getResultList() != null && !query.getResultList().isEmpty() ? query.getResultList().get(0) : null;
    }

    public List<Proveedor> consultarProveedores() {
        TypedQuery<Proveedor> query = null;
        try {
            query = em.createQuery("select c from Proveedor c", Proveedor.class);
        } catch (Exception e) {
            em.getTransaction().rollback();
            logger.error(e);
        }
        return query.getResultList();
    }

    public Long consultarSecuenciaCompra() {
        Query q = em.createNativeQuery("SELECT  nextval('public.sq_compra')");
        Object singleResult = q.getSingleResult();
        return new Long(singleResult.toString());
    }

}
