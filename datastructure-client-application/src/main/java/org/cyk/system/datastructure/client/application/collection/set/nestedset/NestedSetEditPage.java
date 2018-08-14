package org.cyk.system.datastructure.client.application.collection.set.nestedset;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.ws.rs.core.UriBuilder;

import org.cyk.system.datastructure.server.representation.api.collection.set.nested.NestedSetRepresentation;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class NestedSetEditPage implements Serializable {
	private static final long serialVersionUID = 1L;

	private NestedSet nestedSet = new NestedSet();
		
	public void create() {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(UriBuilder.fromPath("http://localhost:8081"));
		NestedSetRepresentation nestedSetRepresentation = target.proxy(NestedSetRepresentation.class);
		org.cyk.system.datastructure.server.representation.entities.collection.set.nested.NestedSet dto = 
				new org.cyk.system.datastructure.server.representation.entities.collection.set.nested.NestedSet();
		dto.setCode(nestedSet.getCode());
		dto.setGroup(nestedSet.getGroup());
		dto.setParent(nestedSet.getParent());
		nestedSetRepresentation.createOne(dto);
	}
}
