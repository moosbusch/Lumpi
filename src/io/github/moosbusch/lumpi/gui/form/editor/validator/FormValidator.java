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

package io.github.moosbusch.lumpi.gui.form.editor.validator;

import org.apache.pivot.wtk.validation.Validator;

/**
 *
 * @author Gunnar Kappei
 */
public interface FormValidator<T extends Object, V extends Object> extends Validator {

    public static final String IS_VALID_METHOD_NAME = "isValid";
    public static final String VALIDATE_METHOD_NAME = "validate";

    public V getValidator();

    @Override
    public boolean isValid(String input);

    public T validate(String input);
}
