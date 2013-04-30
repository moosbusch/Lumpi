/*

 *
 */
package org.moosbusch.lumPi.beans.spring.impl;

import java.io.IOException;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.apache.pivot.beans.BXMLSerializer;
import org.apache.pivot.beans.Bindable;
import org.apache.pivot.collections.Map;
import org.apache.pivot.serialization.SerializationException;
import org.apache.pivot.util.Resources;
import org.apache.pivot.wtk.Action;
import org.moosbusch.lumPi.action.ChildWindowAction;
import org.moosbusch.lumPi.beans.spring.PivotApplicationContext;
import org.moosbusch.lumPi.gui.window.spi.BindableWindow;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author moosbusch
 */
@Configuration
public class PivotFactoryBean implements FactoryBean<BindableWindow> {

    @Autowired
    private ApplicationContext applicationContext;
    private URL bxml;
    private Resources appResources;
    private BXMLSerializer windowSerializer;
    private Reference<BindableWindow> applicationWindowRef;

    private Map<String, Object> processNamespace(BXMLSerializer serializer) {
        Map<String, Object> result = serializer.getNamespace();

        for (String id : result) {
            Object obj = Objects.requireNonNull(result.get(id));

            obj = injectSpringBeans(id, obj);

            if (obj instanceof Bindable) {
                bind(serializer, (Bindable) obj);
            }

            if (obj instanceof Action) {
                registerAction(id, (Action) obj);
            }

            if (obj instanceof ChildWindowAction) {
                ChildWindowAction cwa = (ChildWindowAction) obj;
                BindableWindow window = Objects.requireNonNull(getApplicationWindow());
                cwa.setApplicationWindow(window);
            }
        }

        return result;
    }

    private Object injectSpringBeans(String id, Object namespaceObject) {
        SpringAnnotationInjector<?, ?> inj = getApplicationContext().getInjector();
        inj.inject(namespaceObject);
        return namespaceObject;
    }

    protected BindableWindow readObject(BXMLSerializer serializer, URL bxmlFileURL, Resources resources)
            throws IOException, SerializationException {
        BindableWindow result = getApplicationWindow();

        if (result == null) {
            if (resources != null) {
                result = (BindableWindow) serializer.readObject(bxmlFileURL, resources);
            } else {
                result = (BindableWindow) serializer.readObject(bxmlFileURL);
            }
        }

        return result;
    }

    protected void registerAction(String actionNameKey, Action action) {
        if (StringUtils.isNotBlank(actionNameKey)) {
            Action.getNamedActions().put(actionNameKey, action);
        }
    }

    protected void bind(BXMLSerializer serializer, Bindable bindable) {
        serializer.bind(bindable);
        bindable.initialize(serializer.getNamespace(),
                serializer.getLocation(), serializer.getResources());
    }

    @Override
    public final BindableWindow getObject() throws Exception {
        BXMLSerializer serializer = Objects.requireNonNull(getSerializer());
        URL bxmlFileURL = Objects.requireNonNull(getBxml());
        Resources resources = getResources();
        BindableWindow result = readObject(serializer, bxmlFileURL, resources);
        setApplicationWindow(Objects.requireNonNull(result));
        getApplicationContext().setNamespace(processNamespace(serializer));
        getApplicationContext().setResources(resources);
        return result;
    }

    @Override
    public final Class<BindableWindow> getObjectType() {
        return BindableWindow.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public PivotApplicationContext getApplicationContext() {
        return (PivotApplicationContext) applicationContext;
    }

    public URL getBxml() {
        return bxml;
    }

    public void setBxml(URL bxml) {
        this.bxml = bxml;
    }

    public Resources getResources() {
        return appResources;
    }

    public void setResources(Resources resources) {
        this.appResources = resources;
    }

    public BXMLSerializer getSerializer() {
        return windowSerializer;
    }

    public void setSerializer(BXMLSerializer serializer) {
        this.windowSerializer = serializer;
    }

    public BindableWindow getApplicationWindow() {
        if (applicationWindowRef != null) {
            return applicationWindowRef.get();
        }

        return null;
    }

    public void setApplicationWindow(BindableWindow applicationWindow) {
        if (applicationWindowRef == null) {
            this.applicationWindowRef = new WeakReference<>(applicationWindow);
        }
    }

}
