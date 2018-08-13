package org.cyk.system.datastructure.client.application.collection.set.nestedset;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.ws.rs.core.UriBuilder;

import org.cyk.system.datastructure.server.representation.api.collection.set.nested.NestedSetRepresentation;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

@Named @RequestScoped
public class NestedSetListPage implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<NestedSet> nestedSets;
	
	@PostConstruct
	private void lp(){
		System.out.println("NestedSetListPage.lp()");
	}
	
	public List<NestedSet> getNestedSets() {
		System.out.println("NestedSetListPage.getNestedSets()");
		if(nestedSets == null){
			nestedSets = new ArrayList<>();
			ResteasyClient client = new ResteasyClientBuilder().build();
			ResteasyWebTarget target = client.target(UriBuilder.fromPath("localhost:8081"));
			NestedSetRepresentation nestedSetRepresentation = target.proxy(NestedSetRepresentation.class);
			System.out.println("LIST : "+nestedSetRepresentation.getMany());
			System.out.println("COUNT : "+nestedSetRepresentation.count());
			for(org.cyk.system.datastructure.server.representation.entities.collection.set.nested.NestedSet index : nestedSetRepresentation.getMany()){
				NestedSet nestedSet = new NestedSet();
				nestedSet.setCode(index.getCode());
				nestedSet.setParent(index.getParent());
				nestedSet.setGroup(index.getGroup());
				nestedSet.setLeftIndex(index.getLeftIndex());
				nestedSet.setRightIndex(index.getRightIndex());
				nestedSet.setNumberOfAscendant(index.getNumberOfAscendant());
				nestedSet.setNumberOfChildren(index.getNumberOfChildren());
				nestedSet.setNumberOfDescendant(index.getNumberOfDescendant());
				nestedSets.add(nestedSet);
			}
		}
		return nestedSets;
	}
}
