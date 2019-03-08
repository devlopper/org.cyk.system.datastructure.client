package org.cyk.system.datastructure.client.controller.impl;

import java.io.Serializable;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

import org.cyk.system.datastructure.client.controller.api.collection.set.nested.NestedSetController;
import org.cyk.system.datastructure.client.controller.entities.collection.set.nested.NestedSet;
import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.properties.Properties;
import org.primefaces.component.organigram.OrganigramHelper;
import org.primefaces.event.organigram.OrganigramNodeCollapseEvent;
import org.primefaces.event.organigram.OrganigramNodeDragDropEvent;
import org.primefaces.event.organigram.OrganigramNodeExpandEvent;
import org.primefaces.event.organigram.OrganigramNodeSelectEvent;
import org.primefaces.model.DefaultOrganigramNode;
import org.primefaces.model.OrganigramNode;

@ManagedBean
@ViewScoped
public class OrganigramView implements Serializable {
 
	private static final long serialVersionUID = 1L;

	private OrganigramNode rootNode;
    private OrganigramNode selection;
 
    private boolean zoom = false;
    private String style = "width: 1200px";
    private int leafNodeConnectorHeight = 0;
    private boolean autoScrollToSelection = false;
 
    private String employeeName;
 
    @PostConstruct
    public void init() {
        selection = new DefaultOrganigramNode(null, "Ridvan Agar", null);
        
        Collection<NestedSet> nestedSets = DependencyInjection.inject(NestedSetController.class).read();
        
        for(NestedSet index : nestedSets) {
        	if(index.getParent() == null) {
        		rootNode = new DefaultOrganigramNode(index.getCode(), null);
        	    rootNode.setCollapsible(false);
        	    rootNode.setDroppable(true);
        	    rootNode.setRowKey(index.getCode());
        	    build(index, rootNode, nestedSets, 1);
        	}
        }
    }
    
    private void build(NestedSet parent,OrganigramNode node,Collection<NestedSet> nestedSets,Integer level) {
    	for(NestedSet index : nestedSets)
    		if(index.getParent()!=null && index.getParent().getCode().equals(parent.getCode())) {
    			 OrganigramNode childNode = new DefaultOrganigramNode(index.getCode(), node);
    			 childNode.setDroppable(true);
    			 childNode.setDraggable(true);
    			 childNode.setSelectable(true);
    			 childNode.setRowKey(index.getCode());
    			 build(index, childNode, nestedSets, level + 1);
    		}
    }
 
    public void nodeDragDropListener(OrganigramNodeDragDropEvent event) {
    	System.out.println("OrganigramView.nodeDragDropListener()");
        FacesMessage message = new FacesMessage();
        message.setSummary("Node '" + event.getOrganigramNode().getData() + "' moved from " + event.getSourceOrganigramNode().getData() + " to '" + event.getTargetOrganigramNode().getData() + "'");
        message.setSeverity(FacesMessage.SEVERITY_INFO);
        //NestedSet nestedSet = (NestedSet) event.getOrganigramNode().getData();
        //NestedSet parent = (NestedSet) event.getTargetOrganigramNode().getData();
        
        NestedSet nestedSet = DependencyInjection.inject(NestedSetController.class).readOneByBusinessIdentifier(event.getOrganigramNode().getData());
        NestedSet parent = DependencyInjection.inject(NestedSetController.class).readOneByBusinessIdentifier(event.getTargetOrganigramNode().getData());
        
        nestedSet.getParent().setCode(parent.getCode());
        DependencyInjection.inject(NestedSetController.class).update(nestedSet, new Properties().setFields("parent"));
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
 
    public void nodeSelectListener(OrganigramNodeSelectEvent event) {
        FacesMessage message = new FacesMessage();
        message.setSummary("Node '" + event.getOrganigramNode().getData() + "' selected.");
        message.setSeverity(FacesMessage.SEVERITY_INFO);
 
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
 
    public void nodeCollapseListener(OrganigramNodeCollapseEvent event) {
        FacesMessage message = new FacesMessage();
        message.setSummary("Node '" + event.getOrganigramNode().getData() + "' collapsed.");
        message.setSeverity(FacesMessage.SEVERITY_INFO);
 
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
 
    public void nodeExpandListener(OrganigramNodeExpandEvent event) {
        FacesMessage message = new FacesMessage();
        message.setSummary("Node '" + event.getOrganigramNode().getData() + "' expanded.");
        message.setSeverity(FacesMessage.SEVERITY_INFO);
 
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
 
    public void removeDivision() {
        // re-evaluate selection - might be a differenct object instance if viewstate serialization is enabled
        OrganigramNode currentSelection = OrganigramHelper.findTreeNode(rootNode, selection);
        setMessage(currentSelection.getData() + " will entfernt werden.", FacesMessage.SEVERITY_INFO);
    }
 
    public void removeEmployee() {
        // re-evaluate selection - might be a differenct object instance if viewstate serialization is enabled
        OrganigramNode currentSelection = OrganigramHelper.findTreeNode(rootNode, selection);
        currentSelection.getParent().getChildren().remove(currentSelection);
    }
 
    public void addEmployee() {
        // re-evaluate selection - might be a differenct object instance if viewstate serialization is enabled
        OrganigramNode currentSelection = OrganigramHelper.findTreeNode(rootNode, selection);
 
        OrganigramNode employee = new DefaultOrganigramNode("employee", employeeName, currentSelection);
        employee.setDraggable(true);
        employee.setSelectable(true);
    }
 
    private void setMessage(String msg, FacesMessage.Severity severity) {
        FacesMessage message = new FacesMessage();
        message.setSummary(msg);
        message.setSeverity(severity);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
 
    public OrganigramNode getRootNode() {
        return rootNode;
    }
 
    public void setRootNode(OrganigramNode rootNode) {
        this.rootNode = rootNode;
    }
 
    public OrganigramNode getSelection() {
        return selection;
    }
 
    public void setSelection(OrganigramNode selection) {
        this.selection = selection;
    }
 
    public boolean isZoom() {
        return zoom;
    }
 
    public void setZoom(boolean zoom) {
        this.zoom = zoom;
    }
 
    public String getEmployeeName() {
        return employeeName;
    }
 
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
 
    public String getStyle() {
        return style;
    }
 
    public void setStyle(String style) {
        this.style = style;
    }
 
    public int getLeafNodeConnectorHeight() {
        return leafNodeConnectorHeight;
    }
 
    public void setLeafNodeConnectorHeight(int leafNodeConnectorHeight) {
        this.leafNodeConnectorHeight = leafNodeConnectorHeight;
    }
 
    public boolean isAutoScrollToSelection() {
        return autoScrollToSelection;
    }
 
    public void setAutoScrollToSelection(boolean autoScrollToSelection) {
        this.autoScrollToSelection = autoScrollToSelection;
    }
}