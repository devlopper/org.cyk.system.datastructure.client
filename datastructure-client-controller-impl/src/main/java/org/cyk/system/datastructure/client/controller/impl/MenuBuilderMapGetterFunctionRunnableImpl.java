package org.cyk.system.datastructure.client.controller.impl;

import java.io.Serializable;

import javax.faces.context.FacesContext;

import org.cyk.system.datastructure.client.controller.entities.collection.set.nested.NestedSet;
import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.client.controller.component.menu.MenuBuilder;
import org.cyk.utility.client.controller.component.menu.MenuBuilderMap;
import org.cyk.utility.client.controller.component.menu.MenuBuilderMapGetter;
import org.cyk.utility.client.controller.component.menu.MenuItemBuilder;
import org.cyk.utility.client.controller.component.menu.MenuRenderTypeRowBar;
import org.cyk.utility.client.controller.icon.Icon;
import org.cyk.utility.scope.ScopeSession;

public class MenuBuilderMapGetterFunctionRunnableImpl extends AbstractFunctionRunnableImpl<MenuBuilderMapGetter> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public MenuBuilderMapGetterFunctionRunnableImpl() {
		setRunnable(new Runnable() {
			@Override
			public void run() {
				Object principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
				MenuBuilder menuBuilder = __inject__(MenuBuilder.class).setRenderType(__inject__(MenuRenderTypeRowBar.class));
				if(principal == null) {
					menuBuilder.addItems(
							__inject__(MenuItemBuilder.class).setCommandableName("Paramétrage").setCommandableIcon(Icon.GEARS).addChild(
									__inject__(MenuItemBuilder.class).setCommandableName("Ensemble").addEntitiesList(NestedSet.class)
									
							)
							
						);	
				}else {
					
				}
				setOutput(__inject__(MenuBuilderMap.class).set(ScopeSession.class,menuBuilder));
			}
		});
	}
	
}