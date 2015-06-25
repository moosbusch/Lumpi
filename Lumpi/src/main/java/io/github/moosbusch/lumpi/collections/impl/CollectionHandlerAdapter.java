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

package io.github.moosbusch.lumpi.collections.impl;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import org.apache.pivot.collections.Dictionary;
import org.apache.pivot.collections.Sequence;
import org.apache.pivot.collections.Stack;
import io.github.moosbusch.lumpi.collections.CollectionHandler;

/**
 *
 * @author Gunnar Kappei
 */
public class CollectionHandlerAdapter<T extends Object, V extends Object> implements CollectionHandler<T, V> {

    @SuppressWarnings(value = "unchecked")
    public final void processCollectionObject(Object coll) {
        if (coll != null) {
            if (coll instanceof java.util.List) {
                process((java.util.List<T>) coll);
            } else if (coll instanceof java.util.Set) {
                process((java.util.Set<T>) coll);
            } else if (coll instanceof org.apache.pivot.collections.Set) {
                process((org.apache.pivot.collections.Set<T>) coll);
            } else if (coll instanceof org.apache.pivot.collections.Sequence) {
                if (coll instanceof org.apache.pivot.collections.List) {
                    process((org.apache.pivot.collections.List<T>) coll);
                } else {
                    process((org.apache.pivot.collections.Sequence<T>) coll);
                }
            } else if (coll instanceof java.util.Map) {
                process((java.util.Map<T, V>) coll);
            } else if (coll instanceof org.apache.pivot.collections.Map) {
                process((org.apache.pivot.collections.Map<T, V>) coll);
            } else if (coll instanceof org.apache.pivot.collections.Dictionary) {
                process((org.apache.pivot.collections.Dictionary<T, V>) coll);
            } else if (coll instanceof java.util.Queue) {
                process((java.util.Queue<T>) coll);
            } else if (coll instanceof org.apache.pivot.collections.Queue) {
                process((org.apache.pivot.collections.Queue<T>) coll);
            } else if (coll instanceof org.apache.pivot.collections.Stack) {
                process((org.apache.pivot.collections.Stack<T>) coll);
            } else if (coll.getClass().isArray()) {
                process((T[]) coll);
            }
        }
    }

    @Override
    public void process(List<T> list) {
    }

    @Override
    public void process(org.apache.pivot.collections.List<T> list) {
    }

    @Override
    public void process(Set<T> set) {
    }

    @Override
    public void process(org.apache.pivot.collections.Set<T> set) {
    }

    @Override
    public void process(Sequence<T> seq) {
    }

    @Override
    public void process(T[] array) {
    }

    @Override
    public void process(Map<T, V> map) {
    }

    @Override
    public void process(org.apache.pivot.collections.Map<T, V> map) {
    }

    @Override
    public void process(Queue<T> queue) {
    }

    @Override
    public void process(org.apache.pivot.collections.Queue<T> queue) {
    }

    @Override
    public void process(Stack<T> list) {
    }

    @Override
    public void process(Dictionary<T, V> list) {
    }

}
