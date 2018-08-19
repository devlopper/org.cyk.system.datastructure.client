package org.cyk.system.datastructure.client.application.collection.set.nestedset;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NestedSet implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String identifier;
	private String code;
	private String group;
	private String parent;
	private String leftIndex;
	private String rightIndex;
	private String numberOfChildren;
	private String numberOfDescendant;
	private String numberOfAscendant;
	
}
