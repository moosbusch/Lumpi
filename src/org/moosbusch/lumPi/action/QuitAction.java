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
package org.moosbusch.lumPi.action;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.pivot.wtk.Component;
import org.moosbusch.lumPi.application.LumPiApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author moosbusch
 */
public interface QuitAction extends ApplicationAction {

    public boolean canExit();

    public static abstract class Adapter extends ApplicationAction.Adapter
        implements QuitAction {

        @Autowired
        private LumPiApplicationContext appCtx;

        @Override
        public void doPerform(Component source) {
            if (canExit()) {
                try {
                    appCtx.getApplication().shutdown(true);
                } catch (Exception ex) {
                    Logger.getLogger(QuitAction.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
