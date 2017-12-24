/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.negocios.aliados.facturacion.dao;

import co.com.negocios.aliados.facturacion.entity.Cliente;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import org.apache.log4j.Logger;
/**
 *
 * @author jorge.lopez
 */
public class ClientesBean {

     final static Logger LOGGER = Logger.getLogger(CompraBean.class);

//    EntityManagerFactory emf = Persistence.createEntityManagerFactory("facturacionPU");
//    EntityManager em = emf.createEntityManager();

    public void guardarCliente(Cliente cliente, EntityManager em) {
        EntityTransaction etx = em.getTransaction();
        try {
            etx.begin();
            em.persist(cliente);
            etx.commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            LOGGER.error(e);
        } 
    }

    public List<Cliente> consultarClientes(EntityManager em) {
        TypedQuery<Cliente> query = null;
        try {
            query = em.createQuery("select c from Cliente c", Cliente.class);
        } catch (Exception e) {
            em.getTransaction().rollback();
            LOGGER.error(e);
        } 
        return query.getResultList();
    }

    public boolean existeCliente(String id, EntityManager em) {
        TypedQuery<Cliente> query = null;
        try {
            query = em.createQuery("select c from Cliente c where c.nitDocumento = :id", Cliente.class);
            query.setParameter("id", id);
        } catch (Exception e) {
            em.getTransaction().rollback();
            LOGGER.error(e);
        }
        return query.getResultList().isEmpty();
    }

    public Cliente consultarCliente(String id, EntityManager em) {
        TypedQuery<Cliente> query = null;
        try {
            query = em.createQuery("select c from Cliente c where c.nitDocumento = :id", Cliente.class);
            query.setParameter("id", id);
        } catch (Exception e) {
            em.getTransaction().rollback();
            LOGGER.error(e);
        }
        return query.getResultList() != null && !query.getResultList().isEmpty() ? query.getResultList().get(0) : null;
    }

    public void eliminarCliente(String string, EntityManager em) {
        EntityTransaction etx = em.getTransaction();
        try {
            etx.begin();
            Cliente cliente = consultarCliente(string, em);
            em.remove(cliente);
            etx.commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            LOGGER.error(e);
        } 
    }

    public void actualizarCliente(Cliente cliente, EntityManager em) {
        EntityTransaction etx = em.getTransaction();
        etx.begin();
        try {          
            em.persist(cliente);
            etx.commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            LOGGER.error(e);
        } 
    }
}
