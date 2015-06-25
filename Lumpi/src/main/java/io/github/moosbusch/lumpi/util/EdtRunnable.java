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
package io.github.moosbusch.lumpi.util;

import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author moosbusch
 */
public abstract class EdtRunnable implements Runnable {

    public final void invokeLater() {
        EventQueue.invokeLater(this);
    }

    public final void invokeAndWait() {
        if (!EventQueue.isDispatchThread()) {
            try {
                EventQueue.invokeAndWait(this);
            } catch (InterruptedException | InvocationTargetException ex) {
                Logger.getLogger(EdtRunnable.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            invokeLater();
        }
    }
}
