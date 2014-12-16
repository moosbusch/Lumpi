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
package io.github.moosbusch.lumpi.gui.activity;

import org.apache.commons.lang3.StringUtils;
import org.apache.pivot.util.MessageBus;
import org.apache.pivot.util.MessageBusListener;
import org.apache.pivot.wtk.ActivityIndicator;

/**
 *
 * @author moosbusch
 */
public class MessageBusActivityIndicator extends ActivityIndicator
    implements MessageBusListener<String> {

    public static final String ACTIVATE = "messageBusActivityIndicatorActivate";
    public static final String DEACTIVATE = "messageBusActivityIndicatorDeactivate";

    public MessageBusActivityIndicator() {
        init();
    }

    private void init() {
        MessageBus.subscribe(String.class, this);
    }

    @Override
    public final void messageSent(String message) {
        if (StringUtils.equalsIgnoreCase(message, ACTIVATE)) {
            setActive(true);
        } else if (StringUtils.equalsIgnoreCase(message, DEACTIVATE)) {
            setActive(false);
        }
    }

}
