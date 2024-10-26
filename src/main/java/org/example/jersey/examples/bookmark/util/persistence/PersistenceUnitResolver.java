package org.example.jersey.examples.bookmark.util.persistence;

import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.ServiceHandle;

import jakarta.persistence.EntityManagerFactory;

public class PersistenceUnitResolver implements InjectionResolver<PersistenceUnit> {
    @Override
    public Object resolve(Injectee injectee, ServiceHandle<?> root) {
        if (EntityManagerFactory.class == injectee.getRequiredType()) {
            PersistenceUnit annotation = injectee.getParent().getAnnotation(PersistenceUnit.class);
            return EntityManagerFactoryHolder.emfHolder.get(annotation.unitName());
        }
 
        return null;
    }

    @Override
    public boolean isConstructorParameterIndicator() {
        return false;
    }

    @Override
    public boolean isMethodParameterIndicator() {
        return false;
    }
}
