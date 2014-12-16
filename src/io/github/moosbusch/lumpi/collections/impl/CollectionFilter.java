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
package io.github.moosbusch.lumpi.collections.impl;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.pivot.collections.Collection;
import org.apache.pivot.collections.Group;
import org.apache.pivot.collections.Sequence;
import org.apache.pivot.util.Filter;

/**
 *
 * @author moosbusch
 */
public class CollectionFilter<T extends Object, K extends Collection<T>> {

    private Filter<T> filter;

    public Filter<T> getFilter() {
        return filter;
    }

    public void setFilter(Filter<T> filter) {
        this.filter = filter;
    }

    @SuppressWarnings("unchecked")
    public K getFilteredCollection(K src) {
        if ((src != null) && (getFilter() != null)) {
            Filter<T> fil = getFilter();
            K result = null;

            try {
                Class.forName(src.getClass().getName());
                result = (K) src.getClass().newInstance();
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(CollectionFilter.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (result != null) {
                    Iterator<T> srcIterator = src.iterator();

                    while (srcIterator.hasNext()) {
                        T item = srcIterator.next();

                        if (fil.include(item)) {
                            if (src instanceof Sequence<?>) {
                                ((Sequence<T>) result).add(item);
                            } else if (src instanceof Group<?>) {
                                ((Group<T>) result).add(item);
                            }
                        }
                    }
                }
            }

            return src;
        }

        return src;
    }
}
