/*
 * Copyright (c) 2012, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package org.example.jersey.examples.bookmark;

import jakarta.inject.Singleton;
import jakarta.ws.rs.ApplicationPath;

import org.example.jersey.examples.bookmark.resource.UsersResource;
import org.example.jersey.examples.bookmark.util.persistence.PersistenceUnit;
import org.example.jersey.examples.bookmark.util.persistence.PersistenceUnitResolver;
import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jettison.JettisonFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * @author Michal Gajdos
 */
@ApplicationPath("/")
public class MyApplication extends ResourceConfig {
 
    public MyApplication() {
        registerClasses(UsersResource.class);
        register(new JettisonFeature());
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(PersistenceUnitResolver.class)
                    .to(new TypeLiteral<InjectionResolver<PersistenceUnit>>(){})
                    .in(Singleton.class);
            }
        });
    }
}