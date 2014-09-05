/*
Copyright 2013 Gunnar Kappei

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package org.moosbusch.lumPi.application;

import java.util.Collection;
import java.util.Map;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

/**
 *
 * @author moosbusch
 */
public interface OSGiController {

    public static final String SERVICE_ID_PROPERTY_NAME = "id";

    public void startFramework(BundleActivator... bundleActivators) throws Exception;

    public void stopFramework() throws Exception;

    public Map<String, Object> getOSGiProperties();

    public BundleContext getBundleContext();

    public String getServiceIdPropertyName();

    public <S> Collection<ServiceReference<S>> getServiceReferences(
            Class<S> type, String serviceId) throws InvalidSyntaxException;

    public <S> S getService(ServiceReference<S> sr);

    public boolean ungetService(ServiceReference<?> sr);

}