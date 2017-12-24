/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.negocios.aliados.facturacion.dao;

import co.com.negocios.aliados.facturacion.entity.Proveedor;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import org.apache.log4j.Logger;

/**
 *
 * @author jorge.lopez
 */
public class ProveedoresBean {

    final static Logger LOGGER = Logger.getLogger(ProveedoresBean.class);

    public void guardarProveedor(Proveedor proveedor, EntityManager em) {
        EntityTransaction etx = em.getTransaction();

        try {
            etx.begin();         
            em.persist(proveedor);
            etx.commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            LOGGER.error(e);
        } 
    }

    public List<Proveedor> consultarProveedores(EntityManager em) {
        TypedQuery<Proveedor> query = null;
        try {
            query = em.createQuery("select c from Proveedor c", Proveedor.class);
        } catch (Exception e) {
            em.getTransaction().rollback();
            LOGGER.error(e);
        } 
        return query.getResultList();
    }

    public boolean existeProveedor(String id, EntityManager em) {
        TypedQuery<Proveedor> query = null;
        try {
            query = em.createQuery("select c from Proveedor c where c.nitDocumento = :id", Proveedor.class);
            query.setParameter("id", id);
        } catch (Exception e) {
            em.getTransaction().rollback();
            LOGGER.error(e);
        }
        return query.getResultList().isEmpty();
    }

    public Proveedor consultarProveedor(String id, EntityManager em) {
        TypedQuery<Proveedor> query = null;
        try {
            query = em.createQuery("select c from Proveedor c where c.nitDocumento = :id", Proveedor.class);
            query.setParameter("id", id);
        } catch (Exception e) {
            em.getTransaction().rollback();
            LOGGER.error(e);
        }
        return query.getResultList() != null && !query.getResultList().isEmpty() ? query.getResultList().get(0) : null;
    }

    public void eliminarProveedor(String string, EntityManager em) {
        EntityTransaction etx = em.getTransaction();
        try {
            etx.begin();
            Proveedor proveedor = consultarProveedor(string, em);
            em.remove(proveedor);
            etx.commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            LOGGER.error(e);
        } 
    }

    public void actualizarProveedor(Proveedor proveedor, EntityManager em) {
        EntityTransaction etx = em.getTransaction();
        try {
            etx.begin();
            em.persist(proveedor);
            etx.commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            LOGGER.error(e);
        }
    }

}
