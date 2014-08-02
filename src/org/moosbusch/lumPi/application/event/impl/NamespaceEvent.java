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

package org.moosbusch.lumPi.application.event.impl;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import org.apache.pivot.collections.Map;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;

/**
 *
 * @author Gunnar Kappei
 */
public final class NamespaceEvent extends ApplicationEvent {

    private static final long serialVersionUID = -5415302590537024786L;

    public NamespaceEvent(Map<String, Object> source) {
        super(new WeakReference<>(source));
    }

    @Override
    public Map<String, Object> getSource() {
        return ((Reference<Map<String, Object>>) super.getSource()).get();
    }

    public Map<String, Object> getNamespace() {
        return getSource();
    }
}
