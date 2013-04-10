/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moosbusch.lumPi.gui.activity;

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
