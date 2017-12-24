/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.negocios.aliados.facturacion.dao;

import co.com.negocios.aliados.facturacion.entity.Producto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import org.apache.log4j.Logger;

/**
 *
 * @author jorge.lopez
 */
public class ProductosBean {

    final static Logger LOGGER = Logger.getLogger(ProductosBean.class);

//    EntityManagerFactory emf = Persistence.createEntityManagerFactory("facturacionPU");
//    EntityManager em = emf.createEntityManager();

    public void guardarProducto(Producto producto, EntityManager em) {
        EntityTransaction etx = em.getTransaction();
        try {
            etx.begin();           
            em.persist(producto);
            etx.commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            LOGGER.error(e);
        } 
    }

    public List<Producto> consultarProductos(EntityManager em) {
        TypedQuery<Producto> query = null;
        try {
            query = em.createQuery("select c from Producto c", Producto.class);
        } catch (Exception e) {
            em.getTransaction().rollback();
            LOGGER.error(e);
        }
        return query.getResultList();
    }

    public Producto consultarProducto(String id, EntityManager em) {
        TypedQuery<Producto> query = null;
        try {
            query = em.createQuery("select c from Producto c where c.codigoProducto = :id", Producto.class);
            query.setParameter("id", id);         
        } catch (Exception e) {
            em.getTransaction().rollback();
            LOGGER.error(e);
        }
        return query.getResultList() != null && !query.getResultList().isEmpty() ? query.getResultList().get(0) : null;
    }

    public boolean existeProducto(String id, EntityManager em) {
        TypedQuery<Producto> query = null;
        try {
            query = em.createQuery("select c from Producto c where c.codigoProducto = :id", Producto.class);
            query.setParameter("id", id);
        } catch (Exception e) {
            em.getTransaction().rollback();
            LOGGER.error(e);
        }
        return query.getResultList().isEmpty();
    }

    public void eliminarProducto(String string, EntityManager em) {
        EntityTransaction etx = em.getTransaction();
        try {
            etx.begin();
            Producto producto = consultarProducto(string, em);
            em.remove(producto);
            etx.commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            LOGGER.error(e);
        } 
    }

    public void actualizarProducto(Producto producto, EntityManager em) {
        EntityTransaction etx = em.getTransaction();
        try {
            etx.begin();
           em.merge(producto);
            etx.commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            LOGGER.error(e);
        }
    }

//    public void actualizarProducto(Producto producto, EntityManager em) {
//        EntityTransaction etx = em.getTransaction();
//        try {
//            etx.begin();
//            em.merge(producto);
//            etx.commit();
//        } catch (Exception e) {
//            em.getTransaction().rollback();
//            LOGGER.error(e);
//        }
//    }

}
