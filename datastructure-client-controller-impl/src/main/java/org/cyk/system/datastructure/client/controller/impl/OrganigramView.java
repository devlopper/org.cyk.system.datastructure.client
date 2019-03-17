package org.cyk.system.datastructure.client.controller.impl;

import java.io.Serializable;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.cyk.system.datastructure.client.controller.api.collection.set.nested.NestedSetController;
import org.cyk.system.datastructure.client.controller.entities.collection.set.nested.NestedSet;
import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.client.controller.component.tree.Tree;
import org.cyk.utility.client.controller.component.tree.TreeBuilder;
import org.cyk.utility.client.controller.event.Event;
import org.cyk.utility.client.controller.event.EventName;
import org.cyk.utility.client.controller.web.jsf.primefaces.component.OrganigramNodeBuilder;
import org.cyk.utility.hierarchy.HierarchyNode;
import org.cyk.utility.hierarchy.HierarchyNodeData;
import org.primefaces.event.organigram.OrganigramNodeDragDropEvent;
import org.primefaces.model.OrganigramNode;

import lombok.Getter;
import lombok.Setter;

@ManagedBean
//@ViewScoped
@SessionScoped
public class OrganigramView extends AbstractObject implements Serializable {
 
	private static final long serialVersionUID = 1L;

	 @Getter @Setter private Tree tree;
 
    @PostConstruct
    public void init() { 
        initTree();
    }
    
    public void initTree() { 
        Collection<NestedSet> nestedSets = DependencyInjection.inject(NestedSetController.class).read();        
        HierarchyNode hierarchyNode = null;               
        for(NestedSet index : nestedSets) {
        	if(index.getParent() == null) {
        		hierarchyNode = __inject__(HierarchyNode.class).setData(index);
        		hierarchyNode.setIsCollapsible(Boolean.FALSE); 
        	    build(hierarchyNode, nestedSets, 1);
        	}
        }
        
        OrganigramNode rootNode = __inject__(OrganigramNodeBuilder.class).setHierarchyNode(hierarchyNode).execute().getOutput();
        
        tree = __inject__(TreeBuilder.class)
        	.addEvent(EventName.DRAG_DROP, new Runnable() {
    			@Override
    			public void run() {
    				OrganigramNodeDragDropEvent event =  null;
    				for(Event index : tree.getEvents().get())
    					if(index.getName().equals(EventName.DRAG_DROP)) {
    						event = (OrganigramNodeDragDropEvent) index.getFunction().getProperties().getParameter();
    					}
    				NestedSet nestedSet = (NestedSet) ((HierarchyNodeData)event.getOrganigramNode().getData()).getValue();
    		        NestedSet parent = DependencyInjection.inject(NestedSetController.class).readOneByBusinessIdentifier(
    		        		((NestedSet) ((HierarchyNodeData)event.getTargetOrganigramNode().getData()).getValue()).getCode()
    		        		);
    		        
    		        nestedSet.getParent().setCode(parent.getCode());
    		        DependencyInjection.inject(NestedSetController.class).update(nestedSet, new Properties().setFields("parent"));
    		        
    		        initTree();
    			}
    		})
        	.execute().getOutput();
         
        tree.getProperties().setRoot(rootNode);
    }
    
    private void build(HierarchyNode parent,Collection<NestedSet> nestedSets,Integer level) {
    	for(NestedSet index : nestedSets)
    		if(index.getParent()!=null && index.getParent().getCode().equals( ((NestedSet)parent.getData().getValue()).getCode()))
    			build(parent.addNode(index).getLastChild(), nestedSets, level + 1);
    }
 
}