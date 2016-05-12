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
    @NamedQuery(name = "Gamestacks.findAll", query = "SELECT g FROM Gamestacks g"),
    @NamedQuery(name = "Gamestacks.findById", query = "SELECT g FROM Gamestacks g WHERE g.id = :id"),
    @NamedQuery(name = "Gamestacks.findByGame", query = "SELECT g FROM Gamestacks g WHERE g.game = :game"),
    @NamedQuery(name = "Gamestacks.findByDeck", query = "SELECT g FROM Gamestacks g WHERE g.deck = :deck"),
    @NamedQuery(name = "Gamestacks.findByOrder", query = "SELECT g FROM Gamestacks g WHERE g.order = :order"),
    @NamedQuery(name = "Gamestacks.findByCard", query = "SELECT g FROM Gamestacks g WHERE g.card = :card"),
    @NamedQuery(name = "Gamestacks.findByCount", query = "SELECT g FROM Gamestacks g WHERE g.count = :count")})
public class Gamestacks implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(nullable = false)
    private int game;
    @Column(length = 9)
    private String deck;
    @Basic(optional = false)
    @Column(nullable = false)
    private int order;
    @Basic(optional = false)
    @Column(nullable = false, length = 20)
    private String card;
    @Basic(optional = false)
    @Column(nullable = false)
    private int count;

    public Gamestacks() {
    }

    public Gamestacks(Integer id) {
        this.id = id;
    }

    public Gamestacks(Integer id, int game, int order, String card, int count) {
        this.id = id;
        this.game = game;
        this.order = order;
        this.card = card;
        this.count = count;
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

    public String getDeck() {
        return deck;
    }

    public void setDeck(String deck) {
        this.deck = deck;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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
        if (!(object instanceof Gamestacks)) {
            return false;
        }
        Gamestacks other = (Gamestacks) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication5.Gamestacks[ id=" + id + " ]";
    }
    
}
