package org.cyk.system.datastructure.client.application.collection.set.nestedset;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.datastructure.server.representation.api.collection.set.nested.NestedSetRepresentation;
import org.cyk.system.datastructure.server.representation.entities.collection.set.nested.NestedSetDto;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageImpl;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class NestedSetEditPage extends AbstractPageImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private NestedSet nestedSet = new NestedSet();
		
	public void create() {
		NestedSetRepresentation nestedSetRepresentation = __getProxy__(NestedSetRepresentation.class);
		nestedSetRepresentation.createOne(__injectInstanceHelper__().buildOne(NestedSetDto.class, nestedSet));
	}
}
