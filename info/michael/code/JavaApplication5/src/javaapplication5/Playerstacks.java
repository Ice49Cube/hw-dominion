/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication5;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author SoftVibe
 */
@Entity
@Table(catalog = "dominion", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Playerstacks.findAll", query = "SELECT p FROM Playerstacks p"),
    @NamedQuery(name = "Playerstacks.findById", query = "SELECT p FROM Playerstacks p WHERE p.id = :id"),
    @NamedQuery(name = "Playerstacks.findByPlayer", query = "SELECT p FROM Playerstacks p WHERE p.player = :player"),
    @NamedQuery(name = "Playerstacks.findByOrder", query = "SELECT p FROM Playerstacks p WHERE p.order = :order"),
    @NamedQuery(name = "Playerstacks.findByStacktype", query = "SELECT p FROM Playerstacks p WHERE p.stacktype = :stacktype"),
    @NamedQuery(name = "Playerstacks.findByCard", query = "SELECT p FROM Playerstacks p WHERE p.card = :card")})
public class Playerstacks implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(nullable = false)
    private int player;
    @Basic(optional = false)
    @Column(nullable = false)
    private int order;
    @Column(length = 7)
    private String stacktype;
    @Basic(optional = false)
    @Column(nullable = false, length = 20)
    private String card;

    public Playerstacks() {
    }

    public Playerstacks(Integer id) {
        this.id = id;
    }

    public Playerstacks(Integer id, int player, int order, String card) {
        this.id = id;
        this.player = player;
        this.order = order;
        this.card = card;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getStacktype() {
        return stacktype;
    }

    public void setStacktype(String stacktype) {
        this.stacktype = stacktype;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Playerstacks)) {
            return false;
        }
        Playerstacks other = (Playerstacks) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication5.Playerstacks[ id=" + id + " ]";
    }
    
}
