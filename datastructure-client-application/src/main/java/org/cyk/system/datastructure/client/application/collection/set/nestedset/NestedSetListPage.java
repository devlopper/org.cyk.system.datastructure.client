package org.cyk.system.datastructure.client.application.collection.set.nestedset;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.ws.rs.core.Response;

import org.cyk.jee.utility.client.controller.web.jsf.primefaces.AbstractPageImpl;
import org.cyk.system.datastructure.server.representation.api.collection.set.nested.NestedSetRepresentation;
import org.cyk.system.datastructure.server.representation.entities.collection.set.nested.NestedSetDto;

@Named @RequestScoped
public class NestedSetListPage extends AbstractPageImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<NestedSet> nestedSets;
	
	public List<NestedSet> getNestedSets() {
		if(nestedSets == null){
			NestedSetRepresentation nestedSetRepresentation = __getProxy__(NestedSetRepresentation.class);
			Response response = nestedSetRepresentation.getMany();
			Collection<NestedSetDto> dtos = __readEntityAsCollection__(response, NestedSetDto.class);
			nestedSets = (List<NestedSet>) __injectInstanceHelper__().buildMany(NestedSet.class, dtos);
		}
		return nestedSets;
	}
}
