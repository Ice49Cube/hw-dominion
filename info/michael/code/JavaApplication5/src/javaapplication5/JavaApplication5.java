/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication5;

import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author SoftVibe
 */
public class JavaApplication5 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("JavaApplication5PU");
        EntityManager manager = factory.createEntityManager();
        
        Games games = new Games();
        games.setStartdate(new Date());
        games.setId(5);
        
        manager.getTransaction().begin();
        manager.detach(games);
        //manager.persist(games);
        manager.getTransaction().commit();
    }
    
}
