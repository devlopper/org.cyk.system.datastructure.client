package org.cyk.system.datastructure.client.controller.entities.collection.set.nested;

import org.cyk.utility.client.controller.data.DataIdentifiedByString;

public interface NestedSet extends DataIdentifiedByString {

	NestedSet getParent();
	NestedSet setParent(NestedSet parent);
	
	@Override NestedSet setIdentifier(Object identifier);
	
	String PROPERTY_PARENT = "parent";
}
