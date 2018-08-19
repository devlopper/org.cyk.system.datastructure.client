package org.cyk.system.datastructure.client.application.collection.set.nestedset;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.UriBuilder;

import org.cyk.system.datastructure.server.representation.api.collection.set.nested.NestedSetRepresentation;
import org.cyk.system.datastructure.server.representation.entities.collection.set.nested.NestedSetDto;
import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.instance.InstanceHelper;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

@Named @RequestScoped
public class NestedSetListPage implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<NestedSet> nestedSets;
	
	public List<NestedSet> getNestedSets() {
		if(nestedSets == null){
			ResteasyClient client = new ResteasyClientBuilder().build();
			ResteasyWebTarget target = client.target(UriBuilder.fromPath("http://localhost:8081"));
			
			NestedSetRepresentation nestedSetRepresentation = target.proxy(NestedSetRepresentation.class);
			Collection<NestedSetDto> dtos = nestedSetRepresentation.getMany().readEntity(new GenericType<Collection<NestedSetDto>>(){});
			nestedSets = (List<NestedSet>) DependencyInjection.inject(InstanceHelper.class).buildMany(NestedSet.class, dtos);
		}
		return nestedSets;
	}
}
