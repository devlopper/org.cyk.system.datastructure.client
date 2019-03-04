package org.cyk.system.datastructure.client.controller.entities.collection.set.nested;

import org.cyk.utility.client.controller.data.Data;

public interface NestedSet extends Data {

	NestedSet getParent();
	NestedSet setParent(NestedSet parent);
	
	@Override NestedSet setCode(String code);
	
	String PROPERTY_PARENT = "parent";
}
