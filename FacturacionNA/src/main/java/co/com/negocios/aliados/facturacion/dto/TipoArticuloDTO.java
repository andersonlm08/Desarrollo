/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.negocios.aliados.facturacion.dto;

import java.util.Objects;

/**
 *
 * @author jorge.lopez
 */
public class TipoArticuloDTO {
    
    private Long codigoTipoArticulo;
  
    private String descripcion;

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
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoTipoArticulo != null ? codigoTipoArticulo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TipoArticuloDTO other = (TipoArticuloDTO) obj;
        return Objects.equals(this.codigoTipoArticulo, other.codigoTipoArticulo);
    }
    
    @Override
    public String toString() {
        return descripcion;
    }
    
}
