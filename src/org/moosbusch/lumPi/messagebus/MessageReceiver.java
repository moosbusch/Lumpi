/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moosbusch.lumPi.messagebus;

import org.apache.pivot.collections.Dictionary;
import org.apache.pivot.util.MessageBusListener;

/**
 *
 * @author moosbusch
 */
public interface MessageReceiver
        extends MessageBusListener<Dictionary.Pair<String, Object>> {
}
