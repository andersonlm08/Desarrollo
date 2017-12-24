/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.negocios.aliados.facturacion.adapter;

import co.com.negocios.aliados.facturacion.dao.TipoArticuloBean;
import co.com.negocios.aliados.facturacion.dto.TipoArticuloDTO;
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
public class TipoArticuloAdapter {
    
    private EntityManagerFactory emf = null;
    
    private final TipoArticuloBean articuloBean;
    
    public TipoArticuloAdapter() {
        this.emf = Persistence.createEntityManagerFactory("facturacionPU");
        articuloBean = new TipoArticuloBean();
    }
    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public void guardarTipoArticulo(TipoArticuloDTO tipoArticuloDTO) {
        EntityManager em = getEntityManager();        
        TipoArticulo tipoArticulo = new TipoArticulo();
        tipoArticulo.setDescripcion(tipoArticuloDTO.getDescripcion());
        articuloBean.guardarTipoArticulo(tipoArticulo, em);
    }
    
      public void actualizarTipoArticulo(TipoArticuloDTO tipoArticuloDTO) {
        EntityManager em = getEntityManager();
        TipoArticulo tipoArticulo = articuloBean.consultarTipoArticulo(tipoArticuloDTO.getCodigoTipoArticulo(), em);
        tipoArticulo.setDescripcion(tipoArticuloDTO.getDescripcion());
        articuloBean.actualizarTipoArticulo(tipoArticulo, em);
    }
    
    public List<TipoArticuloDTO> consultarTipoArticulos() {
        EntityManager em = getEntityManager();
        
        List<TipoArticuloDTO> listaTipoArticulo = new ArrayList<>();
        TipoArticuloDTO articuloDTO = null;
        List<TipoArticulo> consultarTipoArticulo = articuloBean.consultarTipoArticulos(em);
        
        for (TipoArticulo tipoArticulo : consultarTipoArticulo) {
            articuloDTO = new TipoArticuloDTO();
            articuloDTO.setCodigoTipoArticulo(tipoArticulo.getCodigoTipoArticulo());
            articuloDTO.setDescripcion(tipoArticulo.getDescripcion());
            listaTipoArticulo.add(articuloDTO);
        }
        
//        consultarTipoArticulo.forEach((tipoArticulo) -> {
//             articuloDTO = new TipoArticuloDTO();
//        });
//        
////        List<TipoArticuloDTO> functionedThings = 
//            consultarTipoArticulo.forEach((tipoArticulo) -> {
//            articuloDTO = new TipoArticuloDTO();
//            articuloDTO.setCodigoTipoArticulo(tipoArticulo.getCodigoTipoArticulo());
//            articuloDTO.setDescripcion(tipoArticulo.getDescripcion());
//            listaTipoArticulo.add(articuloDTO);
//        });
        
        return listaTipoArticulo;
    }
    
    public TipoArticuloDTO consultarTipoArticulo(Long id) {
        EntityManager em = getEntityManager();
        TipoArticuloDTO articuloDTO = new TipoArticuloDTO();
        TipoArticulo tipoArticulo = articuloBean.consultarTipoArticulo(id, em);
        articuloDTO.setCodigoTipoArticulo(tipoArticulo.getCodigoTipoArticulo());
        articuloDTO.setDescripcion(tipoArticulo.getDescripcion());
        return articuloDTO;
    }
        
    public void eliminarTipoArticulo(Long string) {
        EntityManager em = getEntityManager();
        articuloBean.eliminarTipoArticulo(string, em);
    }
    
}
