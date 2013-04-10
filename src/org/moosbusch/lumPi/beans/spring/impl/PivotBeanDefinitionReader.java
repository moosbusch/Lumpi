/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moosbusch.lumPi.beans.spring.impl;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.pivot.beans.BXMLSerializer;
import org.apache.pivot.serialization.SerializationException;
import org.apache.pivot.util.Resources;
import org.moosbusch.lumPi.gui.window.spi.BindableWindow;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.support.AbstractBeanDefinitionReader;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.io.Resource;

/**
 *
 * @author moosbusch
 */
public class PivotBeanDefinitionReader extends AbstractBeanDefinitionReader {

    private final Resources appResources;
    private final BXMLSerializer windowSerializer;
    private BindableWindow applicationWindow;

    public PivotBeanDefinitionReader(BeanDefinitionRegistry registry,
            BXMLSerializer serializer, Resources resources) {
        super(registry);
        this.appResources = resources;
        this.windowSerializer = Objects.requireNonNull(serializer);
    }

    protected BindableWindow readObject(BXMLSerializer serializer, URL bxmlFileURL, Resources resources)
            throws IOException, SerializationException {
        BindableWindow result = getApplicationWindow();

        if (result != null) {
            return result;
        }

        if (resources != null) {
            result = (BindableWindow) serializer.readObject(bxmlFileURL, resources);
        } else {
            result = (BindableWindow) serializer.readObject(bxmlFileURL);
        }

        return result;
    }

    @Override
    public int loadBeanDefinitions(Resource resource) throws BeanDefinitionStoreException {
        BXMLSerializer serializer = getSerializer();
        BindableWindow window = null;

        try {
            window = readObject(serializer, resource.getURL(), getResources());
        } catch (IOException | SerializationException ex) {
            Logger.getLogger(PivotBeanDefinitionReader.class.getName()).log(
                    Level.SEVERE, null, ex);
        } finally {
            setApplicationWindow(window);
        }

        return serializer.getNamespace().getCount();
    }

    public Resources getResources() {
        return appResources;
    }

    public BXMLSerializer getSerializer() {
        return windowSerializer;
    }

    public BindableWindow getApplicationWindow() {
        return applicationWindow;
    }

    public void setApplicationWindow(BindableWindow applicationWindow) {
        this.applicationWindow = applicationWindow;
    }
}
