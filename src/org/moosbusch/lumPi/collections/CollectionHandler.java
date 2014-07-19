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
package org.moosbusch.lumPi.collections;

/**
 *
 * @author moosbusch
 */
public interface CollectionHandler<T extends Object, V extends Object> {

    public void process(java.util.List<T> list);

    public void process(org.apache.pivot.collections.List<T> list);

    public void process(java.util.Set<T> set);

    public void process(org.apache.pivot.collections.Set<T> set);

    public void process(org.apache.pivot.collections.Sequence<T> seq);

    public void process(java.util.Map<T, V> map);

    public void process(org.apache.pivot.collections.Map<T, V> map);

    public void process(java.util.Queue<T> queue);

    public void process(org.apache.pivot.collections.Queue<T> queue);

    public void process(org.apache.pivot.collections.Stack<T> list);

    public void process(org.apache.pivot.collections.Dictionary<T, V> list);

    public void process(T[] array);

    public void processCollectionObject(Object coll);

}
