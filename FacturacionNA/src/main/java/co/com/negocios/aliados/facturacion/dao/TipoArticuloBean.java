/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.negocios.aliados.facturacion.dao;

import co.com.negocios.aliados.facturacion.entity.TipoArticulo;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import org.apache.log4j.Logger;

/**
 *
 * @author jorge.lopez
 */
public class TipoArticuloBean {

    final static Logger LOGGER = Logger.getLogger(TipoArticuloBean.class);

//    EntityManagerFactory emf = Persistence.createEntityManagerFactory("facturacionPU");
//    EntityManager em = emf.createEntityManager();

    public void guardarTipoArticulo(TipoArticulo tipoArticulo, EntityManager em) {
        EntityTransaction etx = em.getTransaction();
        try {
            etx.begin();            
            em.persist(tipoArticulo);
            etx.commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            LOGGER.error(e);
        } 
    }
    
    public void actualizarTipoArticulo(TipoArticulo tipoArticulo, EntityManager em) {
        EntityTransaction etx = em.getTransaction();
        try {
            etx.begin();            
            em.persist(tipoArticulo);
            etx.commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            LOGGER.error(e);
        }
    }

    public List<TipoArticulo> consultarTipoArticulos(EntityManager em) {
        TypedQuery<TipoArticulo> query = null;
        try {
            query = em.createQuery("select c from TipoArticulo c", TipoArticulo.class);
        } catch (Exception e) {
            em.getTransaction().rollback();
            LOGGER.error(e);
        }
        return query.getResultList();
    }

    public TipoArticulo consultarTipoArticulo(Long id, EntityManager em) {
        TypedQuery<TipoArticulo> query = null;
        try {
            query = em.createQuery("select c from TipoArticulo c where c.codigoTipoArticulo = :id", TipoArticulo.class);
            query.setParameter("id", id);
        } catch (Exception e) {
            em.getTransaction().rollback();
            LOGGER.error(e);
        }
        return query.getResultList() != null && !query.getResultList().isEmpty() ? query.getResultList().get(0) : null;
    }

    public void eliminarTipoArticulo(Long string, EntityManager em) {
        EntityTransaction etx = em.getTransaction();
        try {
            etx.begin();
            TipoArticulo tipoArticulo = consultarTipoArticulo(string, em);
            em.remove(tipoArticulo);
            etx.commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            LOGGER.error(e);
        }
    }
}
