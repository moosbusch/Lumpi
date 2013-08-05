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
package org.moosbusch.lumPi.application.osgi.impl;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 *
 * @author moosbusch
 */
public class BundleActivatorAdapter implements BundleActivator {

    private Reference<BundleContext> bundleContextRef;

    protected void startContext(BundleContext bc) {
    }

    protected void stopContext(BundleContext bc) {
    }

    @Override
    public final void start(BundleContext bc) throws Exception {
        this.bundleContextRef = new WeakReference<>(bc);
        startContext(bc);
    }

    @Override
    public final void stop(BundleContext bc) throws Exception {
        this.bundleContextRef = null;
        stopContext(bc);
    }

    public BundleContext getBundleContext() {
        return bundleContextRef.get();
    }

}
