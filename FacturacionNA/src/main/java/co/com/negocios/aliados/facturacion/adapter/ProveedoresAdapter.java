/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.negocios.aliados.facturacion.adapter;

import co.com.negocios.aliados.facturacion.dao.ProveedoresBean;
import co.com.negocios.aliados.facturacion.dto.CiudadDTO;
import co.com.negocios.aliados.facturacion.dto.ProveedorDTO;
import co.com.negocios.aliados.facturacion.entity.Ciudad;
import co.com.negocios.aliados.facturacion.entity.Proveedor;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ander
 */
public class ProveedoresAdapter {

    private EntityManagerFactory emf = null;

    private final ProveedoresBean proveedoresBean;

    public ProveedoresAdapter() {
        this.emf = Persistence.createEntityManagerFactory("facturacionPU");
        proveedoresBean = new ProveedoresBean();
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void guardarProveedor(ProveedorDTO proveedorDTO) {
        EntityManager em = getEntityManager();
        proveedoresBean.guardarProveedor(getProveedor(proveedorDTO), em);
    }

    public void actualizarProveedor(ProveedorDTO proveedorDTO) {
        EntityManager em = getEntityManager();
        Proveedor proveedor = proveedoresBean.consultarProveedor(proveedorDTO.getNitDocumento(), em);
        proveedor.setNitDocumento(proveedorDTO.getNitDocumento());
        proveedor.setNombreComercial(proveedorDTO.getNombreComercial());
        proveedor.setDireccion(proveedorDTO.getDireccion());
        proveedor.setTelefono(proveedorDTO.getTelefono());
        proveedor.setFax(proveedorDTO.getFax());
        proveedor.setEmail(proveedorDTO.getEmail());
        proveedor.setWeb(proveedorDTO.getWeb());
        proveedor.setRepresentante(proveedorDTO.getRepresentante());
        Ciudad ciudad = new Ciudad();
        ciudad.setCodigoCiudad("01");
        proveedor.setCodCiudad(ciudad);
        
        proveedoresBean.actualizarProveedor(proveedor, em);
    }

    public boolean existeProveedor(String id) {
        EntityManager em = getEntityManager();
        return proveedoresBean.existeProveedor(id, em);
    }

    public List<ProveedorDTO> consultarProveedores() {
        EntityManager em = getEntityManager();
        List<ProveedorDTO> listaProveedores = new ArrayList<>();
        ProveedorDTO proveedorDTO;
        List<Proveedor> consultarProveedores = proveedoresBean.consultarProveedores(em);
        for (Proveedor consultarProveedor : consultarProveedores) {
            proveedorDTO = new ProveedorDTO();
            proveedorDTO.setDireccion(consultarProveedor.getDireccion());
            proveedorDTO.setEmail(consultarProveedor.getEmail());
            proveedorDTO.setFax(consultarProveedor.getFax());
            proveedorDTO.setNitDocumento(consultarProveedor.getNitDocumento());
            proveedorDTO.setNombreComercial(consultarProveedor.getNombreComercial());
            proveedorDTO.setRepresentante(consultarProveedor.getRepresentante());
            proveedorDTO.setTelefono(consultarProveedor.getTelefono());
            proveedorDTO.setWeb(consultarProveedor.getWeb());
            CiudadDTO ciudadDTO = new CiudadDTO();
            ciudadDTO.setCodigoCiudad(consultarProveedor.getCodCiudad().getCodigoCiudad());
            ciudadDTO.setMombreCiudad(consultarProveedor.getCodCiudad().getMombreCiudad());
            proveedorDTO.setCodCiudadDTO(ciudadDTO);

            listaProveedores.add(proveedorDTO);
        }

        return listaProveedores;
    }

    public ProveedorDTO consultarProveedor(String id) {

        EntityManager em = getEntityManager();
        Proveedor consultarProveedor = proveedoresBean.consultarProveedor(id, em);
        ProveedorDTO proveedorDTO = new ProveedorDTO();
        proveedorDTO.setDireccion(consultarProveedor.getDireccion());
        proveedorDTO.setEmail(consultarProveedor.getEmail());
        proveedorDTO.setFax(consultarProveedor.getFax());
        proveedorDTO.setNitDocumento(consultarProveedor.getNitDocumento());
        proveedorDTO.setNombreComercial(consultarProveedor.getNombreComercial());
        proveedorDTO.setRepresentante(consultarProveedor.getRepresentante());
        proveedorDTO.setTelefono(consultarProveedor.getTelefono());
        proveedorDTO.setWeb(consultarProveedor.getWeb());
        CiudadDTO ciudadDTO = new CiudadDTO();
        ciudadDTO.setCodigoCiudad(consultarProveedor.getCodCiudad().getCodigoCiudad());
        ciudadDTO.setMombreCiudad(consultarProveedor.getCodCiudad().getMombreCiudad());

        return proveedorDTO;
    }

    public void eliminarProveedor(String string) {
        EntityManager em = getEntityManager();
        proveedoresBean.eliminarProveedor(string, em);
    }

    public Proveedor getProveedor(ProveedorDTO proveedorDTO) {

        Proveedor proveedor = new Proveedor();
        proveedor.setNitDocumento(proveedorDTO.getNitDocumento());
        proveedor.setNombreComercial(proveedorDTO.getNombreComercial());
        proveedor.setDireccion(proveedorDTO.getDireccion());
        proveedor.setTelefono(proveedorDTO.getTelefono());
        proveedor.setFax(proveedorDTO.getFax());
        proveedor.setEmail(proveedorDTO.getEmail());
        proveedor.setWeb(proveedorDTO.getWeb());
        proveedor.setRepresentante(proveedorDTO.getRepresentante());
        Ciudad ciudad = new Ciudad();
        ciudad.setCodigoCiudad("01");
        proveedor.setCodCiudad(ciudad);

        return proveedor;
    }

}
