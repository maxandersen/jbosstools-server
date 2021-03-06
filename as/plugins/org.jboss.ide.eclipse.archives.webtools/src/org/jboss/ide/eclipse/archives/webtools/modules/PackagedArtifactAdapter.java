/*******************************************************************************
 * Copyright (c) 2007 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.jboss.ide.eclipse.archives.webtools.modules;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.wst.server.core.IModule;
import org.eclipse.wst.server.core.IModuleArtifact;
import org.eclipse.wst.server.core.model.ModuleArtifactAdapterDelegate;
/**
 * 
 * @author Rob Stryker rob.stryker@redhat.com
 *
 */
public class PackagedArtifactAdapter extends ModuleArtifactAdapterDelegate {

	public PackagedArtifactAdapter() {
	}

	public IModuleArtifact getModuleArtifact(Object obj) {
		if( obj instanceof IJavaProject ) {
			IProject[] projects2 = ResourcesPlugin.getWorkspace().getRoot().getProjects();
			boolean done = false;
			String jpName = ((IJavaProject)obj).getElementName();
			for( int i = 0; i < projects2.length && !done; i++ ) {
				if( projects2[i].getName().equals(jpName)) {
					done = true;
					obj = projects2[i];
					break;
				}
			}
		} 
		
		if( obj instanceof IProject ) {
			PackageModuleFactory factory = PackageModuleFactory.getFactory();
			if( factory != null ) {
//				IModule[] mods = factory.getModulesFromProject((IProject)obj);
//				if( mods != null && mods.length != 0) {
//					return getArtifact(mods);
//				}
			}
		}
		return null;
	}
		
	protected IModuleArtifact getArtifact(IModule[] mod) {
		//return new PackagedArtifact(mod);
		// TODO Blocking on eclipse bug 174372 
		return null;
	}
	
	public class PackagedArtifact implements IModuleArtifact{
		protected IModule mod;
		public PackagedArtifact(IModule module) {
			this.mod = module;
		}
		public IModule getModule() {
			return mod;
		}
	}
}
