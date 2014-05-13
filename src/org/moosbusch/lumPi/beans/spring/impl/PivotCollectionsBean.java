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

package org.moosbusch.lumPi.beans.spring.impl;

import org.apache.pivot.collections.*;
import org.apache.pivot.io.FileList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Gunnar Kappei
 */

@Configuration
public class PivotCollectionsBean {

    @Bean
    public Sequence.Tree.ImmutablePath createImmutablePath() {
        return new Sequence.Tree.ImmutablePath();
    }

    @Bean
    public Sequence.Tree<? extends Object> createTree() {
        return new Sequence.Tree<>();
    }

    @Bean
    public ArrayList<? extends Object> createArrayList() {
        return new ArrayList<>();
    }

    @Bean
    public ArrayQueue<? extends Object> createArrayQueue() {
        return new ArrayQueue<>();
    }

    @Bean
    public ArrayStack<? extends Object> createArrayStack() {
        return new ArrayStack<>();
    }

    @Bean
    public HashSet<? extends Object> createHashSet() {
        return new HashSet<>();
    }

    @Bean
    public HashMap<? extends Object, ? extends Object> createHashMap() {
        return new HashMap<>();
    }

    @Bean
    public LinkedList<? extends Object> createLinkedList() {
        return new LinkedList<>();
    }

    @Bean
    public LinkedQueue<? extends Object> createLinkedQueue() {
        return new LinkedQueue<>();
    }

    @Bean
    public LinkedStack<? extends Object> createLinkedStack() {
        return new LinkedStack<>();
    }

    @Bean
    public FileList createFileList() {
        return new FileList();
    }

}
