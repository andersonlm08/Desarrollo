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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jorge.lopez
 */
@Entity
@Table(name = "tipo_articulo")
public class TipoArticulo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "codigo_tipo_articulo")
    @SequenceGenerator(name = "TipoArticulo.codigo_tipo_articulo",
            sequenceName = "sq_tipo_producto", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TipoArticulo.codigo_tipo_articulo")
    private Long codigoTipoArticulo;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codTipoArticulo")
    private List<Producto> productoList;

    public TipoArticulo() {
    }

    public TipoArticulo(Long codigoTipoArticulo) {
        this.codigoTipoArticulo = codigoTipoArticulo;
    }

    public TipoArticulo(Long codigoTipoArticulo, String descripcion) {
        this.codigoTipoArticulo = codigoTipoArticulo;
        this.descripcion = descripcion;
    }

    public Long getCodigoTipoArticulo() {
        return codigoTipoArticulo;
    }

    public void setCodigoTipoArticulo(Long codigoTipoArticulo) {
        this.codigoTipoArticulo = codigoTipoArticulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<Producto> getProductoList() {
        return productoList;
    }

    public void setProductoList(List<Producto> productoList) {
        this.productoList = productoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoTipoArticulo != null ? codigoTipoArticulo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoArticulo)) {
            return false;
        }
        TipoArticulo other = (TipoArticulo) object;
        if ((this.codigoTipoArticulo == null && other.codigoTipoArticulo != null) || (this.codigoTipoArticulo != null && !this.codigoTipoArticulo.equals(other.codigoTipoArticulo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return descripcion;
    }

}
