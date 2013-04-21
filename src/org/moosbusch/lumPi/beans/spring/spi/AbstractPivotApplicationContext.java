/*

 *
 */
package org.moosbusch.lumPi.beans.spring.spi;

import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.Objects;
import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.pivot.collections.Map;
import org.apache.pivot.util.Resources;
import org.moosbusch.lumPi.application.DesktopApplication;
import org.moosbusch.lumPi.beans.spring.PivotApplicationContext;
import org.moosbusch.lumPi.beans.spring.SpringAnnotationInjector;
import org.moosbusch.lumPi.beans.spring.impl.PivotAnnotationConfigApplicationContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.io.Resource;

/**
 *
 * @author moosbusch
 */
public abstract class AbstractPivotApplicationContext
        extends GenericXmlApplicationContext implements PivotApplicationContext {

    public AbstractPivotApplicationContext(DesktopApplication application) {
        init(application);
    }

    private void init(DesktopApplication application) {
        registerShutdownHook();
        loadAnnotationConfig(application);
        loadXmlConfig(application);
    }

    private void loadAnnotationConfig(DesktopApplication application) {
        Class<?> pivotFactoryBeanClass = Objects.requireNonNull(
                application.getPivotBeanFactoryClass());
        String[] beanPackages = application.getAnnotatedBeanPackages();
        Class<?>[] annotatedClasses = application.getAnnotatedClasses();
        PivotAnnotationConfigApplicationContext annoCtx =
                Objects.requireNonNull(createAnnotationContext());

        annoCtx.registerShutdownHook();
        annoCtx.setLocation(application.getBXMLConfiguration());
        annoCtx.refresh();
        annoCtx.setChild(this);
        setParent(annoCtx);

        if ((ArrayUtils.isNotEmpty(annotatedClasses))) {
            annoCtx.register(ArrayUtils.add(annotatedClasses, pivotFactoryBeanClass));
        } else {
            annoCtx.register(pivotFactoryBeanClass);
        }

        if ((ArrayUtils.isNotEmpty(beanPackages))) {
            annoCtx.scan(beanPackages);
        }
    }

    private void loadXmlConfig(DesktopApplication application) {
        String[] configLocations = configLocationsFromURLs(application.getBeanConfigurations());
        XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(this);

        for (int cnt = 0; cnt < configLocations.length; cnt++) {
            Resource res = getResource(configLocations[cnt]);

            if (res != null) {
                xmlReader.loadBeanDefinitions(res);
            }
        }

        refresh();
    }

    private String[] configLocationsFromURLs(URL[] locations) {
        if (ArrayUtils.isNotEmpty(locations)) {
            String[] result = new String[locations.length];

            for (int cnt = 0; cnt < result.length; cnt++) {
                result[cnt] = locations[cnt].toExternalForm();
            }

            return result;
        }
        return null;
    }

    protected abstract PivotAnnotationConfigApplicationContext createAnnotationContext();

    @Override
    public final SpringAnnotationInjector<?, ?> getInjector() {
        return getParent().getInjector();
    }

    @Override
    public final PivotApplicationContext getChild() {
        return null;
    }

    @Override
    public final void setChild(PivotApplicationContext childContext) {
    }

    @Override
    public final boolean isParent() {
        return false;
    }

    @Override
    public final boolean isChild() {
        return true;
    }

    @Override
    public boolean containsBean(String id) {
        boolean result = false;

        if (StringUtils.isNotBlank(id)) {
            result = super.containsBean(id);

            if ((!result) && (getParent() != null)) {
                result = getParent().containsBean(id);
            }

            if ((!result) && (getNamespace() != null)) {
                result = getNamespace().containsKey(id);
            }
        }

        return result;
    }

    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        return super.getBean(requiredType);
    }

    @Override
    public Object getBean(String id) {
        Object result = null;

        if (StringUtils.isNotBlank(id)) {
            result = super.getBean(id);

            if ((result == null) && (getParent() != null)) {
                result = getParent().getBean(id);
            }

            if ((result == null) && (getNamespace() != null)) {
                result = getNamespace().get(id);
            }
        }

        return result;
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) {
        Object result = super.getBean(name, requiredType);

        if ((result == null) && (getParent() != null)) {
            result = getParent().getBean(name, requiredType);
        }

        if ((result == null) && (getNamespace() != null)) {
            result = getNamespace().get(name);

            if (result != null) {
                if (ClassUtils.isAssignable(result.getClass(), requiredType)) {
                    return (T) result;
                }
            }
        }

        return null;
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        Object result = super.getBean(name, args);

        if ((result == null) && (getParent() != null)) {
            result = getParent().getBean(name, args);
        }

        if ((result == null) && (getNamespace() != null)) {
            result = getNamespace().get(name);

            if (result != null) {
                Constructor<?> constructor;

                if (ArrayUtils.isNotEmpty(args)) {
                    Class<?>[] parameterTypes = new Class<?>[0];

                    for (Object arg : args) {
                        parameterTypes = ArrayUtils.add(parameterTypes, arg.getClass());
                    }

                    constructor = ConstructorUtils.getAccessibleConstructor(
                            result.getClass(), parameterTypes);
                } else {
                    constructor = ConstructorUtils.getAccessibleConstructor(
                            result.getClass(), new Class<?>[0]);
                }

                if (constructor != null) {
                    return result;
                }
            }
        }

        return null;
    }

    @Override
    public PivotAnnotationConfigApplicationContext getParent() {
        ApplicationContext result = super.getParent();

        if (result != null) {
            return (PivotAnnotationConfigApplicationContext) result;
        }

        return null;
    }

    @Override
    public final Map<String, Object> getNamespace() {
        return getParent().getNamespace();
    }

    @Override
    public final void setNamespace(Map<String, Object> pivotNamespace) {
        getParent().setNamespace(pivotNamespace);
    }

    @Override
    public final Resources getResources() {
        return getParent().getResources();
    }

    @Override
    public final void setResources(Resources resources) {
        getParent().setResources(resources);
    }

    @Override
    public URL getLocation() {
        return getParent().getLocation();
    }

    @Override
    public void setLocation(URL location) {
        getParent().setLocation(location);
    }
}
