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
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author SoftVibe
 */
@Entity
@Table(catalog = "dominion", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"game", "name"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Players.findAll", query = "SELECT p FROM Players p"),
    @NamedQuery(name = "Players.findById", query = "SELECT p FROM Players p WHERE p.id = :id"),
    @NamedQuery(name = "Players.findByGame", query = "SELECT p FROM Players p WHERE p.game = :game"),
    @NamedQuery(name = "Players.findByName", query = "SELECT p FROM Players p WHERE p.name = :name"),
    @NamedQuery(name = "Players.findByBuys", query = "SELECT p FROM Players p WHERE p.buys = :buys"),
    @NamedQuery(name = "Players.findByCoins", query = "SELECT p FROM Players p WHERE p.coins = :coins"),
    @NamedQuery(name = "Players.findByActions", query = "SELECT p FROM Players p WHERE p.actions = :actions")})
public class Players implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(nullable = false)
    private int game;
    @Basic(optional = false)
    @Column(nullable = false, length = 20)
    private String name;
    private Integer buys;
    private Integer coins;
    private Integer actions;

    public Players() {
    }

    public Players(Integer id) {
        this.id = id;
    }

    public Players(Integer id, int game, String name) {
        this.id = id;
        this.game = game;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getGame() {
        return game;
    }

    public void setGame(int game) {
        this.game = game;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBuys() {
        return buys;
    }

    public void setBuys(Integer buys) {
        this.buys = buys;
    }

    public Integer getCoins() {
        return coins;
    }

    public void setCoins(Integer coins) {
        this.coins = coins;
    }

    public Integer getActions() {
        return actions;
    }

    public void setActions(Integer actions) {
        this.actions = actions;
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
        if (!(object instanceof Players)) {
            return false;
        }
        Players other = (Players) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication5.Players[ id=" + id + " ]";
    }
    
}
