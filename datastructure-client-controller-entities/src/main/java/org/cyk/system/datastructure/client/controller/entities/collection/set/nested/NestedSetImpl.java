package org.cyk.system.datastructure.client.controller.entities.collection.set.nested;

import java.io.Serializable;

import org.cyk.utility.client.controller.data.AbstractDataImpl;

public class NestedSetImpl extends AbstractDataImpl implements NestedSet,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public NestedSet setCode(String code) {
		return (NestedSet) super.setCode(code);
	}
	
}
