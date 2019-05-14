package org.cyk.system.datastructure.client.controller.entities.collection.set.nested;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.annotation.Input;
import org.cyk.utility.client.controller.component.annotation.InputChoice;
import org.cyk.utility.client.controller.component.annotation.InputChoiceOne;
import org.cyk.utility.client.controller.component.annotation.InputChoiceOneCombo;
import org.cyk.utility.client.controller.data.AbstractDataIdentifiedByStringImpl;

public class NestedSetImpl extends AbstractDataIdentifiedByStringImpl implements NestedSet,Serializable {
	private static final long serialVersionUID = 1L;

	@Input @InputChoice @InputChoiceOne @InputChoiceOneCombo
	private NestedSet parent;
	
	@Override
	public NestedSet getParent() {
		return parent;
	}
	
	@Override
	public NestedSet setParent(NestedSet parent) {
		this.parent = parent;
		return this;
	}
	
	@Override
	public NestedSet setIdentifier(Object identifier) {
		return (NestedSet) super.setIdentifier(identifier);
	}
	
}
