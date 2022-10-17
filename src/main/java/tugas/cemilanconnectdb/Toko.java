/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tugas.cemilanconnectdb;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Wiratama
 */
@Entity
@Table(name = "toko")
@NamedQueries({
    @NamedQuery(name = "Toko.findAll", query = "SELECT t FROM Toko t"),
    @NamedQuery(name = "Toko.findByIdToko", query = "SELECT t FROM Toko t WHERE t.idToko = :idToko"),
    @NamedQuery(name = "Toko.findByNamaToko", query = "SELECT t FROM Toko t WHERE t.namaToko = :namaToko"),
    @NamedQuery(name = "Toko.findByAlamat", query = "SELECT t FROM Toko t WHERE t.alamat = :alamat"),
    @NamedQuery(name = "Toko.findByNoTelp", query = "SELECT t FROM Toko t WHERE t.noTelp = :noTelp"),
    @NamedQuery(name = "Toko.findByIdMenu", query = "SELECT t FROM Toko t WHERE t.idMenu = :idMenu")})
public class Toko implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_toko")
    private String idToko;
    @Basic(optional = false)
    @Column(name = "nama_toko")
    private String namaToko;
    @Basic(optional = false)
    @Column(name = "alamat")
    private String alamat;
    @Basic(optional = false)
    @Column(name = "no_telp")
    private String noTelp;
    @Basic(optional = false)
    @Column(name = "id_menu")
    private String idMenu;

    public Toko() {
    }

    public Toko(String idToko) {
        this.idToko = idToko;
    }

    public Toko(String idToko, String namaToko, String alamat, String noTelp, String idMenu) {
        this.idToko = idToko;
        this.namaToko = namaToko;
        this.alamat = alamat;
        this.noTelp = noTelp;
        this.idMenu = idMenu;
    }

    public String getIdToko() {
        return idToko;
    }

    public void setIdToko(String idToko) {
        this.idToko = idToko;
    }

    public String getNamaToko() {
        return namaToko;
    }

    public void setNamaToko(String namaToko) {
        this.namaToko = namaToko;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public String getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(String idMenu) {
        this.idMenu = idMenu;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idToko != null ? idToko.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Toko)) {
            return false;
        }
        Toko other = (Toko) object;
        if ((this.idToko == null && other.idToko != null) || (this.idToko != null && !this.idToko.equals(other.idToko))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tugas.cemilanconnectdb.Toko[ idToko=" + idToko + " ]";
    }
    
}
