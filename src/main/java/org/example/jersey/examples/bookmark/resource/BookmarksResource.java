/*
 * Copyright (c) 2010, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package org.example.jersey.examples.bookmark.resource;

import java.net.URI;
import java.util.Collection;
import java.util.Date;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;

import jakarta.persistence.EntityManager;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.example.jersey.examples.bookmark.entity.BookmarkEntity;
import org.example.jersey.examples.bookmark.util.tx.TransactionManager;
import org.example.jersey.examples.bookmark.util.tx.Transactional;

/**
 * @author Jakub Podlesak
 * @author Paul Sandoz
 * @author Michal Gajdos
 */
public class BookmarksResource {

    UriInfo uriInfo; // actual uri info
    EntityManager em; // entity manager provided by parent resource

    UserResource userResource; // parent user resource

    /**
     * Creates a new instance of BookmarksResource
     */
    public BookmarksResource(UriInfo uriInfo, EntityManager em, UserResource userResource) {
        this.uriInfo = uriInfo;
        this.em = em;
        this.userResource = userResource;
    }

    public Collection<BookmarkEntity> getBookmarks() {
        return userResource.getUserEntity().getBookmarkEntityCollection();
    }

    @Path("{bmid: .+}")
    public BookmarkResource getBookmark(@PathParam("bmid") String bmid) {
        return new BookmarkResource(uriInfo, em, userResource.getUserEntity(), bmid);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JSONArray getBookmarksAsJsonArray() {
        JSONArray uriArray = new JSONArray();
        for (BookmarkEntity bookmarkEntity : getBookmarks()) {
            UriBuilder ub = uriInfo.getAbsolutePathBuilder();
            URI bookmarkUri = ub
                    .path(bookmarkEntity.getBookmarkEntityPK().getBmid())
                    .build();
            uriArray.put(bookmarkUri.toASCIIString());
        }
        return uriArray;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postForm(JSONObject bookmark) throws JSONException {
        final BookmarkEntity bookmarkEntity = new BookmarkEntity(getBookmarkId(bookmark.getString("uri")),
                userResource.getUserEntity().getUserid());

        bookmarkEntity.setUri(bookmark.getString("uri"));
        bookmarkEntity.setUpdated(new Date());
        bookmarkEntity.setSdesc(bookmark.getString("sdesc"));
        bookmarkEntity.setLdesc(bookmark.getString("ldesc"));
        userResource.getUserEntity().getBookmarkEntityCollection().add(bookmarkEntity);

        TransactionManager.manage(new Transactional(em) {
            public void transact() {
                em.merge(userResource.getUserEntity());
            }
        });

        URI bookmarkUri = uriInfo.getAbsolutePathBuilder()
                .path(bookmarkEntity.getBookmarkEntityPK().getBmid())
                .build();
        return Response.created(bookmarkUri).build();
    }

    private String getBookmarkId(String uri) {
        return uri;
    }
}