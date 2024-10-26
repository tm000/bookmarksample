package org.example.jersey.examples.bookmark.util.persistence;

import java.util.HashMap;
import java.util.Map;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.ws.rs.WebApplicationException;

@WebListener
public class EntityManagerFactoryHolder implements ServletContextListener  {
    public static Map<String, EntityManagerFactory> emfHolder = new HashMap<>();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContextListener.super.contextInitialized(sce);
        // emf = Persistence.createEntityManagerFactory("BookmarkPU");
        EntityManagerFactory emf = getEmf();
        emfHolder.put(emf.getName(), emf);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);
        for (EntityManagerFactory emf : emfHolder.values()) {
            if (emf.isOpen()) emf.close();
        }
    }

    private static EntityManagerFactory getEmf() {
        try {
            InitialContext ic = new InitialContext();
            return (EntityManagerFactory) ic.lookup("java:EntityManagerFactory");
        } catch (NamingException ne) {
            throw new WebApplicationException(ne);
        }
    }
}
