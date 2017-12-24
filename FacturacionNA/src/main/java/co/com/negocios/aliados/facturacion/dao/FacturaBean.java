/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.negocios.aliados.facturacion.dao;

import co.com.negocios.aliados.facturacion.dto.DetalleFacturaDTO;
import co.com.negocios.aliados.facturacion.dto.FacturaDTO;
import co.com.negocios.aliados.facturacion.entity.Cliente;
import co.com.negocios.aliados.facturacion.entity.DetalleFactura;
import co.com.negocios.aliados.facturacion.entity.DetalleFacturaPK;
import co.com.negocios.aliados.facturacion.entity.Factura;
import co.com.negocios.aliados.facturacion.entity.Producto;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import static co.com.negocios.aliados.facturacion.dao.ClientesBean.LOGGER;

/**
 *
 * @author jorge.lopez
 */
public class FacturaBean {

    final static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(FacturaBean.class);

    //Verificar para solo crear una instancia para todo el proyecto
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("facturacionPU");
    EntityManager em = emf.createEntityManager();

    public Long guardarFactura(FacturaDTO facturaDTO, Cliente cliente) {
        Long secuencia = 0L;
        try {
            EntityTransaction etx = em.getTransaction();
            etx.begin();
            //Hacer prueba si hace rollback de secuencia
            secuencia = consultarSecuenciaFactura();
            Factura factura = new Factura();
            factura.setNumeroFactura(secuencia);
            factura.setCodCliente(cliente);
            factura.setNombreEmpleado("Diana Ruiz");
            factura.setCodFormapago(1L);
            factura.setTotalFactura(facturaDTO.getTotalFactura());
            factura.setSubtotal(facturaDTO.getSubtotal());
            factura.setTotalDescuento(facturaDTO.getTotalDescuento());
            factura.setFechaVencimiento(facturaDTO.getFechaVencimiento());
            factura.setDiasPlazo(facturaDTO.getDiasPlazo());
            factura.setObservacion(facturaDTO.getObservacion());

            em.persist(factura);
            factura.setDetalleFacturaList(getDetalleFactura(facturaDTO.getDetalleFacturaList(), secuencia));
            em.merge(factura);
            etx.commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            logger.error(e);
        }
        return secuencia;
    }

    public List<DetalleFactura> getDetalleFactura(List<DetalleFacturaDTO> detalleFacturaDTO, Long secuencia) {
        List<DetalleFactura> listaDetalles = new ArrayList<>();
        DetalleFactura detalleFactura = null;
        for (DetalleFacturaDTO detalleFacturaDTOTemp : detalleFacturaDTO) {
            detalleFactura = new DetalleFactura();
            DetalleFacturaPK detalleFacturaPK = new DetalleFacturaPK(secuencia, detalleFacturaDTOTemp.getCodigoProducto());
            detalleFactura.setDetalleFacturaPK(detalleFacturaPK);
            detalleFactura.setCantidad(detalleFacturaDTOTemp.getCantidad());
            detalleFactura.setCosto(detalleFacturaDTOTemp.getCosto());
            detalleFactura.setDescuento(detalleFacturaDTOTemp.getDescuento());
            detalleFactura.setSubtotal(new BigDecimal(detalleFacturaDTOTemp.getSubtotal()));
            detalleFactura.setTotal(new BigDecimal(detalleFacturaDTOTemp.getTotal()));
            actualizarInventarioProducto(detalleFacturaDTOTemp.getCodigoProducto(), detalleFacturaDTOTemp.getCantidad());
            listaDetalles.add(detalleFactura);
        }
        return listaDetalles;
    }

    private void actualizarInventarioProducto(String codigoProducto, long cantidadRestar) {
        Producto producto = consultarProducto(codigoProducto);
        producto.setCantidad(producto.getCantidad() - cantidadRestar);
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

    public List<Cliente> consultarClientes() {
        TypedQuery<Cliente> query = null;
        try {
            query = em.createQuery("select c from Cliente c", Cliente.class);
        } catch (Exception e) {
            em.getTransaction().rollback();
            logger.error(e);
        }
        return query.getResultList();
    }

    public Long consultarSecuenciaFactura() {
        Query q = em.createNativeQuery("SELECT  nextval('public.sq_factura')");
        Object singleResult = q.getSingleResult();
        return new Long(singleResult.toString());
    }

        public Cliente consultarCliente(String id) {
        TypedQuery<Cliente> query = null;
        try {
            query = em.createQuery("select c from Cliente c where c.nitDocumento = :id", Cliente.class);
            query.setParameter("id", id);
        } catch (Exception e) {
            em.getTransaction().rollback();
            logger.error(e);
        }
        return query.getResultList() != null && !query.getResultList().isEmpty() ? query.getResultList().get(0) : null;
    }
//    public void guardarDetalleFactura(Factura factura) {
//        EntityTransaction etx = em.getTransaction();
//        etx.begin();
//        em.persist(factura);
//        etx.commit();
//    }
//    public Factura consultarFactua(String codigoFactura){
//        Factura factura = null;
//        
//        return factura;
//    }

    public FacturaDTO consultarFacturaDto(Long numeroFactura) {
        FacturaDTO facturaDTO = null;
        
        Factura consultarFactura = consultarFactura(numeroFactura);
        
        facturaDTO = getFactura(consultarFactura);
        return facturaDTO;
    }
    
     private FacturaDTO getFactura(Factura factura) {
         
         FacturaDTO facturaDTO = new FacturaDTO();
        List<DetalleFacturaDTO> listaDetalleDTO = new ArrayList<>();
        DetalleFacturaDTO detalleFacturaDTO= null;
        
        facturaDTO.setCodigoCliente(factura.getCodCliente().getNitDocumento());
        facturaDTO.setTotalFactura(factura.getTotalFactura());
        facturaDTO.setTotalDescuento(factura.getTotalDescuento());
        facturaDTO.setFechaFacturacion(factura.getFechaFacturacion());
        facturaDTO.setFechaVencimiento(factura.getFechaVencimiento());
        facturaDTO.setNombreEmpleado(factura.getNombreEmpleado());
        facturaDTO.setNumeroFactura(factura.getNumeroFactura());
        facturaDTO.setSubtotal(factura.getSubtotal());
        facturaDTO.setDiasPlazo(factura.getDiasPlazo());
        
         for (DetalleFactura  detalleFactura : factura.getDetalleFacturaList()) {
             detalleFacturaDTO = new  DetalleFacturaDTO();
             detalleFacturaDTO.setCantidad(detalleFactura.getCantidad());
             detalleFacturaDTO.setCodigoProducto(detalleFactura.getProducto().getCodigoProducto());
             detalleFacturaDTO.setCosto(detalleFactura.getCosto());
             detalleFacturaDTO.setDescuento(detalleFactura.getDescuento());
             detalleFacturaDTO.setSubtotal(detalleFactura.getSubtotal().longValue());
             detalleFacturaDTO.setTotal(detalleFactura.getTotal().longValue());
             
             listaDetalleDTO.add(detalleFacturaDTO);
         }
         facturaDTO.setDetalleFacturaList(listaDetalleDTO);
         return facturaDTO;
    }
    public Factura consultarFactura(Long id) {
        TypedQuery<Factura> query = null;
        try {
            query = em.createQuery("SELECT f FROM Factura f WHERE f.numeroFactura = :numeroFactura", Factura.class);
            query.setParameter("numeroFactura", id);
        } catch (Exception e) {
            em.getTransaction().rollback();
            logger.error(e);
        }
        return query.getResultList() != null && !query.getResultList().isEmpty() ? query.getResultList().get(0) : null;
    }

   
}
