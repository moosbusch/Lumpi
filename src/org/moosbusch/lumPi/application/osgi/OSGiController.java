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
package org.moosbusch.lumPi.application.osgi;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.felix.framework.Felix;
import org.apache.felix.framework.util.FelixConstants;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
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

    public static class Adapter implements OSGiController {

        private Felix felix;

        @Override
        public final BundleContext getBundleContext() {
            return felix.getBundleContext();
        }

        @Override
        public void startFramework(BundleActivator... bundleActivators) throws Exception {
            Map<String, Object> osgiProperties = getOSGiProperties();
            osgiProperties.put(FelixConstants.SYSTEMBUNDLE_ACTIVATORS_PROP,
                    Arrays.asList(bundleActivators));
            this.felix = new Felix(osgiProperties);
            felix.start();
        }

        @Override
        public void stopFramework() throws Exception {
            if (felix != null) {
                try {
                    felix.stop();
                    felix.waitForStop(0);
                } catch (BundleException | InterruptedException ex) {
                    Logger.getLogger(Adapter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        @Override
        public <T> Collection<ServiceReference<T>> getServiceReferences(
                Class<T> type, String serviceId) throws InvalidSyntaxException {
            String idFilter = "(" + getServiceIdPropertyName() + "=" + serviceId + ")";
            return getBundleContext().getServiceReferences(type, idFilter);
        }

        @Override
        public <S> S getService(ServiceReference<S> sr) {
            return getBundleContext().getService(sr);
        }

        @Override
        public boolean ungetService(ServiceReference<?> sr) {
            return getBundleContext().ungetService(sr);
        }

        @Override
        public java.util.Map<String, Object> getOSGiProperties() {
            return new HashMap<>();
        }

        @Override
        public String getServiceIdPropertyName() {
            return SERVICE_ID_PROPERTY_NAME;
        }

    }
}
