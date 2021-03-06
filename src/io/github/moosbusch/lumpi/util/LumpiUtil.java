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
package io.github.moosbusch.lumpi.util;

import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.apache.pivot.beans.BXML;
import org.apache.pivot.beans.BeanMonitor;
import org.apache.pivot.beans.Bindable;
import org.apache.pivot.beans.PropertyChangeListener;
import org.apache.pivot.beans.Resolvable;
import org.apache.pivot.collections.ArrayList;
import org.apache.pivot.collections.Sequence;
import org.apache.pivot.collections.Sequence.Tree.Path;
import org.apache.pivot.wtk.Action;
import org.apache.pivot.wtk.ApplicationContext.QueuedCallback;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.TreeView;
import org.apache.pivot.wtk.Visual;
import org.apache.pivot.wtk.content.TreeBranch;
import org.apache.pivot.wtk.content.TreeNode;
import org.springframework.util.ObjectUtils;

/**
 *
 * @author moosbusch
 */
public class LumpiUtil {

    private LumpiUtil() {
    }

    public static QueuedCallback queueCallback(Runnable callback) {
        return org.apache.pivot.wtk.ApplicationContext.queueCallback(callback);
    }

    public static QueuedCallback queueCallback(Runnable callback, boolean wait) {
        return org.apache.pivot.wtk.ApplicationContext.queueCallback(callback, wait);
    }

    public static void handleUncaughtException(Exception ex) {
        org.apache.pivot.wtk.ApplicationContext.handleUncaughtException(ex);
    }

    public static Map<String, Object> getBXMLFieldValues(Bindable obj) throws IllegalAccessException {
        Map<String, Object> result = new HashMap<>();
        Class<?> type = obj.getClass();
        Field[] allFields = FieldUtils.getAllFields(type);

        if (ArrayUtils.isNotEmpty(allFields)) {
            for (Field field : allFields) {
                BXML bxmlAnnotation = field.getAnnotation(BXML.class);

                if (bxmlAnnotation != null) {
                    String id = bxmlAnnotation.id();
                    Object fieldValue = FieldUtils.readField(field, obj, true);

                    if (StringUtils.isNotBlank(id)) {
                        result.put(id, fieldValue);
                    } else {
                        result.put(field.getName(), fieldValue);
                    }
                }
            }
        }

        return result;
    }

    public static BufferedImage createCompatibleImage(int width, int height) {
        GraphicsEnvironment gd = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsConfiguration conf = gd.getDefaultScreenDevice().getDefaultConfiguration();
        return conf.createCompatibleImage(width, height);
    }

    public static BufferedImage toBufferedImage(Visual visual) {
        BufferedImage result = createCompatibleImage(visual.getWidth(), visual.getHeight());
        Graphics2D g2 = result.createGraphics();
        visual.paint(g2);
        g2.dispose();

        return result;
    }

    public static boolean pathEndsWith(java.nio.file.Path p, String end) {
        return p.getFileName().toString().endsWith(end);
    }

    public static boolean pathStartsWith(java.nio.file.Path p, String start) {
        return p.getFileName().toString().startsWith(start);
    }

    public static String getNamespaceIdForBean(Object bean, Resolvable resolvable) {
        return getNamespaceIdForBean(bean, resolvable.getNamespace());
    }

    public static String getNamespaceIdForBean(Object bean,
            org.apache.pivot.collections.Map<String, Object> namespace) {
        if (bean == null) {
            throw new NullPointerException();
        }

        for (String id : namespace) {
            Object obj = Objects.requireNonNull(namespace.get(id));

            if (obj.equals(bean)) {
                return id;
            }
        }

        return null;
    }

    public static Class<?> getPropertyType(Class<?> type, String propertyName) {
        String[] propertyTokens = StringUtils.split(propertyName, ".");
        Class<?> result = type;

        for (String propertyToken : propertyTokens) {
            PropertyDescriptor[] descriptors
                    = PropertyUtils.getPropertyDescriptors(result);

            for (PropertyDescriptor descriptor : descriptors) {
                if (StringUtils.equalsIgnoreCase(propertyToken, descriptor.getName())) {
                    result = descriptor.getPropertyType();
                    break;
                } else {
                    result = null;
                }
            }
        }

        return result;
    }

    public static void firePropertyChangeListeners(Object bean, String propertyName,
            Iterable<PropertyChangeListener> listeners) {

        for (PropertyChangeListener listener : listeners) {
            listener.propertyChanged(bean, propertyName);
        }
    }

    public static void firePropertyChangeListeners(Object bean, String propertyName,
            BeanMonitor monitor) {
        if (bean.equals(monitor.getBean())) {
            firePropertyChangeListeners(bean, propertyName, monitor.getPropertyChangeListeners());
        }
    }

    public static Collection<Type> getParameterTypesForCollection(Class<?> type) {
        if (isCollectionOrSequence(type)) {
            Map<TypeVariable<?>, Type> m
                    = TypeUtils.getTypeArguments((ParameterizedType) type.getGenericSuperclass());
            return m.values();
        }

        return null;
    }

    public static boolean isPrimitiveTypeOrString(Class<?> type) {
        return (ClassUtils.isPrimitiveOrWrapper(type)
                || (ClassUtils.isAssignable(type, String.class)));
    }

    public static boolean isPivotCollection(Class<?> type) {
        return ClassUtils.isAssignable(type,
                org.apache.pivot.collections.Collection.class);
    }

    public static boolean isCollection(Class<?> type) {
        return ((ClassUtils.isAssignable(type, java.util.Collection.class)))
                || (ClassUtils.isAssignable(type,
                        org.apache.pivot.collections.Collection.class));
    }

    public static boolean isSequence(Class<?> type) {
        return ClassUtils.isAssignable(type,
                org.apache.pivot.collections.Sequence.class);
    }

    public static boolean isArray(Class<?> type) {
        return type.isArray();
    }

    public static boolean isEnum(Class<?> type) {
        return type.isEnum();
    }

    public static boolean isCollectionOrSequence(Class<?> type) {
        return (ClassUtils.isAssignable(type, java.util.Collection.class))
                || (ClassUtils.isAssignable(type,
                        org.apache.pivot.collections.Collection.class))
                || (ClassUtils.isAssignable(type,
                        org.apache.pivot.collections.Sequence.class));
    }

    public static boolean isCollectionOrSequenceOrArrayOrEnum(Class<?> type) {
        if (!((ClassUtils.isAssignable(type, java.util.Collection.class)))
                || (ClassUtils.isAssignable(type,
                        org.apache.pivot.collections.Collection.class))
                || (ClassUtils.isAssignable(type,
                        org.apache.pivot.collections.Sequence.class))) {
            return type.isArray() || type.isEnum();
        }

        return true;
    }

    public static TreeNode getTreeNodeByPath(TreeView treeView, Path path) {
        org.apache.pivot.collections.List<TreeNode> nodes
                = (org.apache.pivot.collections.List<TreeNode>) treeView.getTreeData();
        TreeNode node = null;
        TreeBranch branch = null;

        for (int index : path) {
            node = nodes.get(index);

            if (node instanceof TreeBranch) {
                branch = (TreeBranch) node;
                nodes = branch;
            }
        }

        return node;
    }

    public static void clearSequence(Sequence<?> seq) {
        seq.remove(0, seq.getLength());
    }

    public static int indexOf(org.apache.pivot.collections.List<?> listData, Object value) {
        if (listData != null) {
            org.apache.pivot.collections.List<Object> data = (org.apache.pivot.collections.List<Object>) listData;

            return data.indexOf(value);
        }

        return -1;
    }

    public static Object get(org.apache.pivot.collections.List<?> listData, int index) {
        if (listData != null) {
            org.apache.pivot.collections.List<Object> data = (org.apache.pivot.collections.List<Object>) listData;

            if ((index >= 0) && (index < listData.getLength())) {
                return data.get(index);
            }
        }

        return null;
    }

    public static <T> org.apache.pivot.collections.List<T> toListData(Object value) {
        org.apache.pivot.collections.List<T> result = new ArrayList<>();

        if (value != null) {
            if (value instanceof java.util.Collection) {
                java.util.Collection<T> coll
                        = (java.util.Collection<T>) value;

                for (T obj : coll) {
                    result.add(obj);
                }
            } else if (value instanceof Sequence) {
                Sequence<T> seq = (Sequence<T>) value;

                for (int cnt = 0; cnt < seq.getLength(); cnt++) {
                    T obj = seq.get(cnt);
                    result.add(obj);
                }
            } else if (value instanceof org.apache.pivot.collections.Set) {
                org.apache.pivot.collections.Set<T> set
                        = (org.apache.pivot.collections.Set<T>) value;

                for (T obj : set) {
                    result.add(obj);
                }
            } else if (ObjectUtils.isArray(value)) {
                T[] array = (T[]) value;

                for (T obj : array) {
                    array = ArrayUtils.add(array, obj);
                }
            } else {
                result.add((T) value);
            }
        }

        return result;
    }

    public static boolean allNotBlank(final String... items) {
        if (items != null) {
            for (String item : items) {
                if (StringUtils.isBlank(item)) {
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    public static String requireNotBlank(String str) {
        if (StringUtils.isBlank(str)) {
            throw new IllegalArgumentException();
        }

        return str;
    }

    public static Action getAction(String actionId) {
        return Action.getNamedActions().get(Objects.requireNonNull(actionId));
    }

    public static void registerAction(String actionId, Action action) {
        Action.getNamedActions().put(Objects.requireNonNull(actionId),
                Objects.requireNonNull(action));
    }

    public static void unregisterAction(String actionId) {
        Action.getNamedActions().remove(Objects.requireNonNull(actionId));
    }

    public static void enableAction(String actionId) {
        Objects.requireNonNull(getAction(actionId)).setEnabled(true);
    }

    public static void disableAction(String actionId) {
        Objects.requireNonNull(getAction(actionId)).setEnabled(false);
    }

    public static boolean containsNot(Sequence<? super Object> seq, Object obj) {
        return (!contains(seq, obj));
    }

    public static boolean contains(Sequence<? super Object> seq, Object obj) {
        if (seq != null) {
            return (seq.indexOf(obj) >= 0);
        }

        return false;
    }

    public static boolean containsNotAny(Sequence<? super Object> seq, Object... objs) {
        return (!containsAny(seq, objs));
    }

    public static boolean containsAny(Sequence<? super Object> seq, Object... objs) {
        if (seq != null) {
            if (ArrayUtils.isNotEmpty(objs)) {
                for (Object obj : objs) {
                    if (seq.indexOf(obj) >= 0) {
                        return true;
                    }
                }
            } else {
                return contains(seq, null);
            }
        }

        return false;
    }

    public static void printComponentStyles(Component cmp) {
        for (String styleKey : cmp.getStyles()) {
            System.out.println(styleKey);
        }
    }

    public static Object getComponentStyle(Component cmp, String style) {
        return cmp.getStyles().get(style);
    }

    public static void setComponentStyle(Component cmp, String style, Object value) {
        cmp.getStyles().put(style, value);
    }

    public static <T> Set<T> asSet(T[] values) {
        Set<T> result = new HashSet<>();

        if (ArrayUtils.isNotEmpty(values)) {
            result.addAll(Arrays.asList(values));
        }

        return result;
    }

    public static <T> List<T> asList(T[] values) {
        List<T> result = new java.util.ArrayList<>();

        if (ArrayUtils.isNotEmpty(values)) {
            result.addAll(Arrays.asList(values));
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    public static <T> T cast(Object obj, Class<?> type) {
        if ((obj != null) && (type != null)) {
            if (type.isPrimitive()) {
                Class<?> wrapperType = ClassUtils.primitiveToWrapper(type);

                if (ClassUtils.isAssignable(obj.getClass(), wrapperType)) {
                    return (T) wrapperType.cast(obj);
                }
            } else {
                if (ClassUtils.isAssignable(obj.getClass(), type)) {
                    return (T) type.cast(obj);
                }
            }
        }

        return null;
    }

    public static void validIndex(int index, Object arrayOrCollection) {
        if ((!(arrayOrCollection instanceof Array))
                && (!(arrayOrCollection instanceof Collection<?>))) {
            throw new IllegalArgumentException(
                    "Object is neither Array nor Collection!");
        }

        if (index < 0) {
            throw new IllegalArgumentException("Invalid index! Must be >= 0.");
        } else {
            int length;

            if (arrayOrCollection instanceof Array) {
                length = Array.getLength(arrayOrCollection);

                if (length < index) {
                    throw new IllegalArgumentException(
                            "Invalid index! Must be < " + length);
                }
            } else if (arrayOrCollection instanceof Collection<?>) {
                Collection<?> col = (Collection<?>) arrayOrCollection;
                length = col.size();

                if (length < index) {
                    throw new IllegalArgumentException(
                            "Invalid index! Must be < " + length);
                }
            } else if (arrayOrCollection instanceof Sequence<?>) {
                Sequence<?> col = (Sequence<?>) arrayOrCollection;
                length = col.getLength();

                if (length < index) {
                    throw new IllegalArgumentException(
                            "Invalid index! Must be < " + length);
                }
            }
        }
    }

    public static Set<Class<?>> getSuperTypes(Class<?> type,
            boolean includeType, boolean includeSuperClasses,
            boolean includeInterfaces) {
        Set<Class<?>> result = new LinkedHashSet<>();
        List<Class<?>> interfaces = ClassUtils.getAllInterfaces(type);
        List<Class<?>> superClasses = ClassUtils.getAllSuperclasses(type);

        if (includeType) {
            result.add(type);
        }

        if (includeSuperClasses) {
            result.addAll(superClasses);
        }

        if (includeInterfaces) {
            result.addAll(interfaces);
        }

        return result;
    }

    public static boolean isEDT() {
        return EventQueue.isDispatchThread();
    }

    public static boolean isInstanceOf(Object obj, Class<?> type) {
        if (obj != null) {
            return ClassUtils.isAssignable(obj.getClass(), type);
        }

        return false;
    }

    public static void logOnEDT(final Class<?> type, final Level level, final Throwable ex) {
        new EdtRunnable() {
            @Override
            public void run() {
                Logger.getLogger(type.getClass().getName()).log(level, null, ex);
            }

        }.invokeLater();

    }

    public static Set<String> getBeanPropertyNames(Object bean) {
        return getBeanPropertyNames(bean.getClass());
    }

    public static Set<String> getBeanPropertyNames(Class<?> beanClass) {
        Set<String> result = new HashSet<>();
        BeanInfo beanInfo = null;

        try {
            beanInfo = Introspector.getBeanInfo(beanClass);
        } catch (IntrospectionException ex) {
            Logger.getLogger(LumpiUtil.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (beanInfo != null) {
                PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

                if (ArrayUtils.isNotEmpty(propertyDescriptors)) {
                    for (PropertyDescriptor pd : propertyDescriptors) {
                        result.add(pd.getName());
                    }
                }
            }
        }

        return result;
    }

    public static void printBeanPropertyNames(Class<?> beanClass) {
        Set<String> result = getBeanPropertyNames(beanClass);

        for (String propertyName : result) {
            System.out.println(propertyName);
        }
    }

    public static void printBeanPropertyNames(Object bean) {
        Set<String> result = getBeanPropertyNames(bean);

        for (String propertyName : result) {
            System.out.println(propertyName);
        }
    }

}
