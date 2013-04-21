/*

 *
 */
package org.moosbusch.lumPi.beans.spring.impl;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.net.URL;
import org.apache.pivot.collections.Map;
import org.apache.pivot.util.Resources;
import org.moosbusch.lumPi.beans.spring.PivotApplicationContext;
import org.moosbusch.lumPi.beans.spring.SpringAnnotationInjector;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 * @author moosbusch
 */
public class PivotAnnotationConfigApplicationContext
        extends AnnotationConfigApplicationContext
        implements PivotApplicationContext {

    private URL location;
    private Resources resources;
    private Map<String, Object> namespace;
    private Reference<PivotApplicationContext> childContextRef;
    private SpringAnnotationInjector<?, ?> injector;

    @Override
    public final PivotAnnotationConfigApplicationContext getParent() {
        return null;
    }

    @Override
    public final void setParent(ApplicationContext parent) {
    }

    @Override
    public final boolean isParent() {
        return true;
    }

    @Override
    public final boolean isChild() {
        return false;
    }

    @Override
    public PivotApplicationContext getChild() {
        return childContextRef.get();
    }

    @Override
    public void setChild(PivotApplicationContext childContext) {
        this.childContextRef = new WeakReference<>(childContext);
    }

    @Override
    public URL getLocation() {
        return location;
    }

    @Override
    public void setLocation(URL location) {
        this.location = location;
    }

    @Override
    public Map<String, Object> getNamespace() {
        return namespace;
    }

    @Override
    public void setNamespace(Map<String, Object> pivotNamespace) {
        this.namespace = pivotNamespace;
    }

    @Override
    public Resources getResources() {
        return resources;
    }

    @Override
    public void setResources(Resources resources) {
        this.resources = resources;
    }

    @Override
    public SpringAnnotationInjector<?, ?> getInjector() {
        if (injector == null) {
            injector = new DefaultSpringAnnotationInjector(
                    getBeanFactory());
        }

        return injector;
    }

}
