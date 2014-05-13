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

import org.apache.pivot.util.CalendarDate;
import org.apache.pivot.util.Time;
import org.apache.pivot.util.concurrent.TaskGroup;
import org.apache.pivot.util.concurrent.TaskSequence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Gunnar Kappei
 */
@Configuration
public class PivotMiscBean {

    @Bean
    public CalendarDate createCalendarDate() {
        return new CalendarDate();
    }

    @Bean
    public Time createTime() {
        return new Time();
    }

    @Bean
    public TaskGroup createTaskGroup() {
        return new TaskGroup();
    }

    @Bean
    public TaskSequence createTaskSequence() {
        return new TaskSequence();
    }
}
