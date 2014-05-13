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

package org.moosbusch.lumPi.gui.form.editor.validator.spi;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.beanutils.MethodUtils;
import org.moosbusch.lumPi.gui.form.editor.validator.FormValidator;

/**
 *
 * @author Gunnar Kappei
 */
public abstract class AbstractFormValidator<T extends Object, V extends Object>
    implements FormValidator<T, V> {

    @Override
    public boolean isValid(String input) {
        Boolean result = Boolean.FALSE;
        V validator = getValidator();

        try {
            result = (Boolean) MethodUtils.invokeExactMethod(
                    validator, IS_VALID_METHOD_NAME, input);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
            Logger.getLogger(AbstractFormValidator.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    @Override
    public T validate(String input) {
        Object result = null;
        V validator = getValidator();

        try {
            result = (Boolean) MethodUtils.invokeExactMethod(
                    validator, VALIDATE_METHOD_NAME, input);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
            Logger.getLogger(AbstractFormValidator.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (result != null) {
            return (T) result;
        }

        return null;
    }

}
