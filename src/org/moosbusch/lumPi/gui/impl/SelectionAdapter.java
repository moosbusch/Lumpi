/*
 * Copyright 2014 Gunnar Kappei.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.moosbusch.lumPi.gui.impl;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Objects;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.pivot.collections.Dictionary;
import org.apache.pivot.wtk.Component;
import org.moosbusch.lumPi.gui.Selection;

/**
 *
 * @author Gunnar Kappei
 */
public class SelectionAdapter<T extends Object> implements Selection<T> {

    private final Dictionary.Pair<Selection.Expression, Reference<T>> data;
    private Reference<Component> eventSourceRef;

    public SelectionAdapter(Component eventSource, T value, String expr) {
        this.eventSourceRef = new WeakReference<>(Objects.requireNonNull(eventSource));
        this.data = new Dictionary.Pair<>(new Selection.Expression(expr),
                new WeakReference<>(Objects.requireNonNull(value)));
    }

    public SelectionAdapter(Component eventSource, T value, Selection.Expression expr) {
        this.eventSourceRef = new WeakReference<>(Objects.requireNonNull(eventSource));
        this.data = new Dictionary.Pair<>(expr,
                new WeakReference<>(Objects.requireNonNull(value)));
    }

    @Override
    public Component getEventSource() {
        return eventSourceRef.get();
    }

    @Override
    public void setEventSource(Component eventSource) {
        this.eventSourceRef = new WeakReference<>(
                Objects.requireNonNull(eventSource));
    }

    @Override
    public T getValue() {
        return data.value.get();
    }

    @Override
    public Selection.Expression getExpression() {
        return data.key;
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public T evaluate() throws Exception {
        if (getExpression().isEmpty()) {
            return getValue();
        } else {
            Object result = PropertyUtils.getProperty(getValue(),
                    getExpression().toString());
            if (result != null) {
                return (T) result;
            }
        }
        return null;
    }

}
