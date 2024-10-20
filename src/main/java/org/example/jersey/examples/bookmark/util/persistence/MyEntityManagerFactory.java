package org.example.jersey.examples.bookmark.util.persistence;

import org.glassfish.hk2.api.Factory;

import jakarta.persistence.EntityManagerFactory;

public class MyEntityManagerFactory implements Factory<EntityManagerFactory>{
    @Override
    public EntityManagerFactory provide() {
        return EntityManagerFactoryHolder.emf;
    }

    @Override
    public void dispose(EntityManagerFactory instance) {
        
    }   
}
