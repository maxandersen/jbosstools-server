/******************************************************************************* 
 * Copyright (c) 2011 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.ide.eclipse.as.management.core;

import java.io.File;
import java.text.MessageFormat;

import org.eclipse.core.runtime.IProgressMonitor;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author André Dietisheim
 */
public class JBoss7ManagerServiceProxy extends ServiceTracker<IJBoss7ManagerService, IJBoss7ManagerService>
		implements IJBoss7ManagerService {

	public JBoss7ManagerServiceProxy(BundleContext context, String asVersion) throws InvalidSyntaxException {
		super(
				context,
				context.createFilter(MessageFormat
						.format("(&(objectClass={0})(as.version={1}))", IJBoss7ManagerService.class.getCanonicalName(), asVersion)), null); //$NON-NLS-1$
	}

	public void init() throws Exception {
		checkedGetService().init();
	}

	public IJBoss7DeploymentResult deployAsync(IAS7ManagementDetails details, String deploymentName, File file,
			IProgressMonitor monitor) throws Exception {
		return checkedGetService().deployAsync(details, deploymentName, file, monitor);
	}

	public IJBoss7DeploymentResult deploySync(IAS7ManagementDetails details, String deploymentName, File file,
			IProgressMonitor monitor) throws Exception {
		return checkedGetService().deployAsync(details, deploymentName, file, monitor);
	}

	public IJBoss7DeploymentResult undeployAsync(IAS7ManagementDetails details, String deploymentName, boolean removeFile,
			IProgressMonitor monitor) throws Exception {
		return checkedGetService().undeployAsync(details, deploymentName, removeFile, monitor);
	}

	public IJBoss7DeploymentResult syncUndeploy(IAS7ManagementDetails details, String deploymentName, boolean removeFile,
			IProgressMonitor monitor) throws Exception {
		return checkedGetService().syncUndeploy(details, deploymentName, removeFile, monitor);
	}

	public JBoss7DeploymentState getDeploymentState(IAS7ManagementDetails details, String deploymentName) throws Exception {
		return checkedGetService().getDeploymentState(details, deploymentName);
	}

	public JBoss7ServerState getServerState(IAS7ManagementDetails details) throws Exception {
		return checkedGetService().getServerState(details);
	}

	public boolean isRunning(IAS7ManagementDetails details) throws Exception {
		try {
			return checkedGetService().isRunning(details);
		} catch (Exception e) {
			return false;
		}
	}

	public void stop(IAS7ManagementDetails details) throws Exception {
		checkedGetService().stop(details);
	}

    public String execute(IAS7ManagementDetails details, String request) throws Exception {
        return checkedGetService().execute(details, request);
    }

    private IJBoss7ManagerService checkedGetService() throws JBoss7ManangerException {
		IJBoss7ManagerService service = getService();
		if (service == null) {
			throw new JBoss7ManangerException("Could not acquire JBoss Management service"); //$NON-NLS-1$
		}
		return service;
	}

	public void dispose() {
		IJBoss7ManagerService service = getService();
		if( service != null )
			service.dispose();
		close();
	}

}
