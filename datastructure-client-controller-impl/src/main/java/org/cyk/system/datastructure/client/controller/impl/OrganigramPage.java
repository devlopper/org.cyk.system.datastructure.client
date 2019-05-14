package org.cyk.system.datastructure.client.controller.impl;

import java.io.Serializable;
import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.cyk.system.datastructure.client.controller.api.collection.set.nested.NestedSetController;
import org.cyk.system.datastructure.client.controller.entities.collection.set.nested.NestedSet;
import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.client.controller.component.menu.MenuItemBuilder;
import org.cyk.utility.client.controller.component.tree.Tree;
import org.cyk.utility.client.controller.component.tree.TreeBuilder;
import org.cyk.utility.client.controller.component.tree.TreeRenderTypeOrganigram;
import org.cyk.utility.client.controller.component.window.WindowContainerManagedWindowBuilder;
import org.cyk.utility.client.controller.component.window.WindowContainerManagedWindowBuilderBlank;
import org.cyk.utility.client.controller.event.EventName;
import org.cyk.utility.client.controller.icon.Icon;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.hierarchy.HierarchyNode;
import org.cyk.utility.hierarchy.HierarchyNodeData;
import org.primefaces.event.organigram.OrganigramNodeDragDropEvent;

import lombok.Getter;
import lombok.Setter;

@ManagedBean
//@ViewScoped
@SessionScoped
public class OrganigramPage extends AbstractPageContainerManagedImpl implements Serializable {
 
	private static final long serialVersionUID = 1L;

	@Getter @Setter private Tree tree;
	@Getter @Setter private String code;
	
	@Override
    protected void __listenPostConstruct__() {
    	super.__listenPostConstruct__();
    	TreeBuilder treeBuilder = __inject__(TreeBuilder.class);
    	treeBuilder.setRenderType(__inject__(TreeRenderTypeOrganigram.class));
    	Collection<NestedSet> nestedSets = DependencyInjection.inject(NestedSetController.class).read();        
                   
        for(NestedSet index : nestedSets) {
        	if(index.getParent() == null) {
        		treeBuilder.getRoot(Boolean.TRUE).getHierarchyNode(Boolean.TRUE).setData(index);
        	    build(treeBuilder.getRoot(Boolean.TRUE).getHierarchyNode(Boolean.TRUE), nestedSets, 1);
        	}
        }
        
        treeBuilder
        	.addEvent(EventName.DRAG_DROP, new Runnable() {
    			@Override
    			public void run() {
    				OrganigramNodeDragDropEvent event =  (OrganigramNodeDragDropEvent) tree.getEvents().getByName(EventName.DRAG_DROP).getFunction().getProperties().getParameter();
    				
    				NestedSet nestedSet = (NestedSet) ((HierarchyNodeData)event.getOrganigramNode().getData()).getValue();
    		        NestedSet parent = DependencyInjection.inject(NestedSetController.class).readOneByBusinessIdentifier(
    		        		((NestedSet) ((HierarchyNodeData)event.getTargetOrganigramNode().getData()).getValue()).getIdentifier()
    		        		);
    		        
    		        nestedSet.getParent().setIdentifier(parent.getIdentifier());
    		        DependencyInjection.inject(NestedSetController.class).update(nestedSet, new Properties().setFields("parent"));
    			}
    		})
        	
        	.addEvent(EventName.CONTEXT_MENU, new Runnable() {
    			@Override
    			public void run() {}
    		})
        	;
         
        MenuItemBuilder addMenuItemBuilder = __inject__(MenuItemBuilder.class);
        addMenuItemBuilder.setCommandableName("Ajouter").setCommandableIcon(Icon.PLUS).addCommandableEvent(EventName.CLICK,"PF('addDialog').show(); return false;");
        
        MenuItemBuilder removeMenuItemBuilder = __inject__(MenuItemBuilder.class);
        removeMenuItemBuilder.setCommandableName("Supprimer").setCommandableIcon(Icon.REMOVE);
        removeMenuItemBuilder.getCommandable().getCommand(Boolean.TRUE).getFunction(Boolean.TRUE).addTryRunRunnables(new Runnable() {
			@Override
			public void run() {
				NestedSet nestedSet = (NestedSet) tree.getSelectedNodeDataValue();
				tree.removeData();
		        __inject__(NestedSetController.class).delete(nestedSet);
			}
		});
        removeMenuItemBuilder.getCommandable().setGetByIdentifierExpressionLanguageFormat("organigramPage.tree.menu.getCommandableByIdentifier('%s')");
        removeMenuItemBuilder.getCommandable().addUpdatables(treeBuilder);
        
        treeBuilder.getMenu(Boolean.TRUE).addItems(addMenuItemBuilder,removeMenuItemBuilder);

        treeBuilder.getAddNodeCommandable(Boolean.TRUE).setName("Ajouter").setIcon(Icon.PLUS)
        		.addCommandFunctionTryRunRunnable(new Runnable() {
					
					@Override
					public void run() {
						NestedSet data = (NestedSet) __inject__(NestedSet.class).setIdentifier(code).setParent((NestedSet) tree.getSelectedNodeDataValue());
				        __inject__(NestedSetController.class).create(data);
				        tree.addData(data);
				        code = null;	
					}
				})
        		.addEvent(EventName.COMPLETE, "PF('addDialog').hide();")
        		.addUpdatables(treeBuilder,"@(.addDialog)")
        		.execute().getOutput();
        
        tree = treeBuilder.execute().getOutput();
    }
	
	@Override
    protected WindowContainerManagedWindowBuilder __getWindowContainerManagedWindowBuilder__() {
    	return __inject__(WindowContainerManagedWindowBuilderBlank.class);
    }
	
    private void build(HierarchyNode parent,Collection<NestedSet> nestedSets,Integer level) {
    	for(NestedSet index : nestedSets)
    		if(index.getParent()!=null && index.getParent().getIdentifier().equals( ((NestedSet)parent.getData().getValue()).getIdentifier()))
    			build(parent.addNode(index).getLastChild(), nestedSets, level + 1);
    }
 
}