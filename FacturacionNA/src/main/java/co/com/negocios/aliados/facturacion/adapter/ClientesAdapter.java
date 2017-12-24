/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.negocios.aliados.facturacion.adapter;

import co.com.negocios.aliados.facturacion.dao.ClientesBean;
import co.com.negocios.aliados.facturacion.dto.CiudadDTO;
import co.com.negocios.aliados.facturacion.dto.ClienteDTO;
import co.com.negocios.aliados.facturacion.entity.Ciudad;
import co.com.negocios.aliados.facturacion.entity.Cliente;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.apache.log4j.Logger;

/**
 *
 * @author jorge.lopez
 */
public class ClientesAdapter {

    private final static Logger LOGGER = Logger.getLogger(ClientesAdapter.class);

    private EntityManagerFactory emf = null;

    private final ClientesBean clientesBean;

    public ClientesAdapter() {
        this.emf = Persistence.createEntityManagerFactory("facturacionPU");
        clientesBean = new ClientesBean();
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void guardarCliente(ClienteDTO clienteDTO) {
        EntityManager em = getEntityManager();
        try {
            clientesBean.guardarCliente(getCliente(clienteDTO), em);
        } catch (Exception e) {
            em.getTransaction().rollback();
            LOGGER.error(e);
        }
    }
    
    public void actualizarCliente(ClienteDTO clienteDTO) {
        EntityManager em = getEntityManager();
        Cliente cliente = clientesBean.consultarCliente(clienteDTO.getNitDocumento(), em);
        cliente.setNitDocumento(clienteDTO.getNitDocumento());
        cliente.setCliente(clienteDTO.getCliente());
        cliente.setDireccion(clienteDTO.getDireccion());
        cliente.setTelefono(clienteDTO.getTelefono());
        cliente.setFax(clienteDTO.getFax());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setWeb(clienteDTO.getWeb());
        cliente.setRepresentante(clienteDTO.getRepresentante());
        Ciudad ciudad = new Ciudad();
        ciudad.setCodigoCiudad("01");
        cliente.setCodCiudad(ciudad);
        cliente.setActivo(true);
        clientesBean.actualizarCliente(cliente, em);
    }
    
    public List<ClienteDTO> consultarClientes() {
        EntityManager em = getEntityManager();

        List<ClienteDTO> listaClientes = new ArrayList<>();
        ClienteDTO clienteDTO;
        List<Cliente> consultarClientes = clientesBean.consultarClientes(em);
        for (Cliente consultarCliente : consultarClientes) {
            clienteDTO = new ClienteDTO();
            clienteDTO.setNitDocumento(consultarCliente.getNitDocumento());
            clienteDTO.setCliente(consultarCliente.getCliente());
            CiudadDTO ciudadDTO = new CiudadDTO();
            ciudadDTO.setCodigoCiudad(consultarCliente.getCodCiudad().getCodigoCiudad());
            clienteDTO.setCodCiudadDTO(ciudadDTO);
            clienteDTO.setDireccion(consultarCliente.getDireccion());
            clienteDTO.setEmail(consultarCliente.getEmail());
            clienteDTO.setFax(consultarCliente.getFax());
            clienteDTO.setRepresentante(consultarCliente.getRepresentante());
            clienteDTO.setTelefono(consultarCliente.getTelefono());
            clienteDTO.setWeb(consultarCliente.getWeb());
            clienteDTO.setActivo(consultarCliente.getActivo());
            listaClientes.add(clienteDTO);
        }
        return listaClientes;
    }

    public boolean existeCliente(String id) {
        EntityManager em = getEntityManager();
        return clientesBean.existeCliente(id, em);
    }

    public ClienteDTO consultarCliente(String id) {
        EntityManager em = getEntityManager();

        Cliente consultarCliente = clientesBean.consultarCliente(id, em);
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setNitDocumento(consultarCliente.getNitDocumento());
        clienteDTO.setCliente(consultarCliente.getCliente());
        CiudadDTO ciudadDTO = new CiudadDTO();
        ciudadDTO.setCodigoCiudad(consultarCliente.getCodCiudad().getCodigoCiudad());
        clienteDTO.setCodCiudadDTO(ciudadDTO);
        clienteDTO.setDireccion(consultarCliente.getDireccion());
        clienteDTO.setEmail(consultarCliente.getEmail());
        clienteDTO.setFax(consultarCliente.getFax());
        clienteDTO.setRepresentante(consultarCliente.getRepresentante());
        clienteDTO.setTelefono(consultarCliente.getTelefono());
        clienteDTO.setWeb(consultarCliente.getWeb());
        clienteDTO.setActivo(consultarCliente.getActivo());
        return clienteDTO;
    }

    public void eliminarCliente(String string) {
        EntityManager em = getEntityManager();
        clientesBean.eliminarCliente(string, em);
    }

    

    private Cliente getCliente(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setNitDocumento(clienteDTO.getNitDocumento());
        cliente.setCliente(clienteDTO.getCliente());
        cliente.setDireccion(clienteDTO.getDireccion());
        cliente.setTelefono(clienteDTO.getTelefono());
        cliente.setFax(clienteDTO.getFax());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setWeb(clienteDTO.getWeb());
        cliente.setRepresentante(clienteDTO.getRepresentante());
        Ciudad ciudad = new Ciudad();
        ciudad.setCodigoCiudad("01");
        cliente.setCodCiudad(ciudad);
        cliente.setActivo(true);

        return cliente;
    }

}
