package org.example.jersey.examples.bookmark.util.persistence;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PersistenceUnit {

    /**
     * (Optional) The name by which the entity manager factory
     * is to be accessed in the environment referencing context;
     * not needed when dependency injection is used.
     */
    String name() default "";

    /**
     * (Optional) The name of the persistence unit as defined
     * in the {@code persistence.xml} file. If specified, the
     * persistence unit for the entity manager factory that is
     * accessible in JNDI must have the same name.
     */
    String unitName() default "";

}
