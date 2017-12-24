/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.negocios.aliados.facturacion.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jorge.lopez
 */
@Entity
@Table(name = "ciudad")
public class Ciudad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codigo_ciudad")
    private String codigoCiudad;
    @Basic(optional = false)
    @Column(name = "mombre_ciudad")
    private String mombreCiudad;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codCiudad")
    private List<Cliente> clienteList;
    @OneToMany(mappedBy = "codCiudad")
    private List<Proveedor> proveedorList;

    public Ciudad() {
    }

    public Ciudad(String codigoCiudad) {
        this.codigoCiudad = codigoCiudad;
    }

    public Ciudad(String codigoCiudad, String mombreCiudad) {
        this.codigoCiudad = codigoCiudad;
        this.mombreCiudad = mombreCiudad;
    }

    public String getCodigoCiudad() {
        return codigoCiudad;
    }

    public void setCodigoCiudad(String codigoCiudad) {
        this.codigoCiudad = codigoCiudad;
    }

    public String getMombreCiudad() {
        return mombreCiudad;
    }

    public void setMombreCiudad(String mombreCiudad) {
        this.mombreCiudad = mombreCiudad;
    }

    @XmlTransient
    public List<Cliente> getClienteList() {
        return clienteList;
    }

    public void setClienteList(List<Cliente> clienteList) {
        this.clienteList = clienteList;
    }

    @XmlTransient
    public List<Proveedor> getProveedorList() {
        return proveedorList;
    }

    public void setProveedorList(List<Proveedor> proveedorList) {
        this.proveedorList = proveedorList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoCiudad != null ? codigoCiudad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ciudad)) {
            return false;
        }
        Ciudad other = (Ciudad) object;
        if ((this.codigoCiudad == null && other.codigoCiudad != null) || (this.codigoCiudad != null && !this.codigoCiudad.equals(other.codigoCiudad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.negocios.aliados.facturacion.entity.Ciudad[ codigoCiudad=" + codigoCiudad + " ]";
    }
    
}
