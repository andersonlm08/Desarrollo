/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.negocios.aliados.facturacion.dao;

import co.com.negocios.aliados.facturacion.dto.UsuarioDTO;
import co.com.negocios.aliados.facturacion.entity.Usuario;
import co.com.negocios.aliados.facturacion.seguridad.Seguridad;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import org.apache.log4j.Logger;

/**
 *
 * @author jorge.lopez
 */
public class UsuariosBean {

    final static Logger logger = Logger.getLogger(UsuariosBean.class);

     EntityManagerFactory emf = Persistence.createEntityManagerFactory("facturacionPU");
    EntityManager em = emf.createEntityManager();

    public void guardarUsuario(UsuarioDTO usuarioDTO) {
        EntityTransaction etx = em.getTransaction();

        try {
            etx.begin();
            Usuario usuario = new Usuario();
            usuario.setDocumento(usuarioDTO.getDocumento());
            usuario.setNombres(usuarioDTO.getNombres());
            usuario.setApellidos(usuarioDTO.getApellidos());
            usuario.setEmail(usuarioDTO.getEmail());
            usuario.setTelefono(usuarioDTO.getTelefono());
            usuario.setUsuario(usuarioDTO.getUsuario());
            usuario.setClave(Seguridad.encriptar(usuarioDTO.getClave()));        
            em.persist(usuario);
            etx.commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            logger.error(e);
        } 
    }

    public List<Usuario> consultarUsuarios() {
        TypedQuery<Usuario> query = null;
        try {
            query = em.createQuery("select c from Usuario c", Usuario.class);
        } catch (Exception e) {
            em.getTransaction().rollback();
            logger.error(e);
        } 
        return query.getResultList();
    }

    public boolean existeUsuario(String id) {
        TypedQuery<Usuario> query = null;
        try {
            query = em.createQuery("select c from Usuario c where c.documento = :id", Usuario.class);
            query.setParameter("id", id);
        } catch (Exception e) {
            em.getTransaction().rollback();
            logger.error(e);
        }
        return query.getResultList().isEmpty();
    }

    public Usuario consultarUsuario(String id) {
        TypedQuery<Usuario> query = null;
        try {
            query = em.createQuery("select c from Usuario c where c.usuario = :id", Usuario.class);
            query.setParameter("id", id);
        } catch (Exception e) {
            em.getTransaction().rollback();
            logger.error(e);
        }
        return query.getResultList() != null && !query.getResultList().isEmpty() ? query.getResultList().get(0) : null;
    }

    public void eliminarUsuario(String string) {
        EntityTransaction etx = em.getTransaction();
        try {
            etx.begin();
            Usuario usuario = consultarUsuario(string);
            em.remove(usuario);
            etx.commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            logger.error(e);
        } 
    }

    public void actualizarUsuario(UsuarioDTO usuarioDTO) {
        EntityTransaction etx = em.getTransaction();

        try {
            etx.begin();
            Usuario usuario = consultarUsuario(usuarioDTO.getUsuario());
            usuario.setDocumento(usuarioDTO.getDocumento());
            usuario.setNombres(usuarioDTO.getNombres());
            usuario.setApellidos(usuarioDTO.getApellidos());
            usuario.setEmail(usuarioDTO.getEmail());
            usuario.setTelefono(usuarioDTO.getTelefono());
            usuario.setUsuario(usuarioDTO.getUsuario());
            usuario.setClave(Seguridad.encriptar(usuarioDTO.getClave()));        
            em.persist(usuario);
            etx.commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            logger.error(e);
        }
    }
}
