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
package io.github.moosbusch.lumpi.action.impl;

import io.github.moosbusch.lumpi.action.spi.AbstractQuitAction;
import io.github.moosbusch.lumpi.action.QuitAction;

/**
 *
 * @author moosbusch
 */
public class DefaultQuitAction extends AbstractQuitAction {

    @Override
    public boolean canExit() {
        return true;
    }

}
