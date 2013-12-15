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
package org.moosbusch.lumPi.gui.form.editor.binding.spi;

import org.apache.commons.lang3.StringUtils;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.Button.State;
import org.apache.pivot.wtk.content.ButtonData;
import org.moosbusch.lumPi.gui.form.editor.FormEditor;

/**
 *
 * @author moosbusch
 * @param <T>
 */
public abstract class AbstractButtonFormEditorBindMapping<T extends Button>
        extends AbstractFormEditorBindMapping<T>
        implements Button.ButtonDataBindMapping,
        Button.SelectedBindMapping, Button.StateBindMapping {

    public AbstractButtonFormEditorBindMapping(FormEditor<T> editor) {
        super(editor);
    }

    protected void bindButton(T editorComponent) {
    }

    @Override
    public final void bind(T editorComponent) {
        editorComponent.setButtonDataBindType(getBindType());
        editorComponent.setSelectedBindType(getBindType());
        editorComponent.setStateBindType(getBindType());
        editorComponent.setButtonDataBindMapping(this);
        editorComponent.setSelectedBindMapping(this);
        editorComponent.setStateBindMapping(this);
        bindButton(editorComponent);
    }

    @Override
    public Object toButtonData(Object value) {
        ButtonData result = new ButtonData();

        if (value != null) {
            result.setText(value.toString());
            result.setUserData(value);
        }

        return result;
    }

    @Override
    public Object valueOf(Object buttonData) {
        if (buttonData != null) {
            if (buttonData instanceof ButtonData) {
                return ((ButtonData) buttonData).getUserData();
            }
        } else {
            buttonData = new ButtonData();
        }

        return buttonData;
    }

    @Override
    public boolean isSelected(Object value) {
        if (value != null) {
            if (value instanceof Boolean) {
                return (boolean) value;
            }
        }

        return false;
    }

    @Override
    public Object valueOf(boolean selected) {
        return (Boolean) selected;
    }

    @Override
    public State toState(Object value) {
        if (value != null) {
            if (value instanceof Boolean) {
                if ((Boolean) value) {
                    return State.SELECTED;
                } else {
                    return State.UNSELECTED;
                }
            } else if (value instanceof Number) {
                Number n = (Number) value;

                if (n.intValue() == -1) {
                    return State.UNSELECTED;
                } else if (n.intValue() == 0) {
                    return State.MIXED;
                } else if (n.intValue() == 1) {
                    return State.SELECTED;
                }
            } else if (value instanceof String) {
                if (StringUtils.equalsIgnoreCase(value.toString(),
                        State.UNSELECTED.toString())) {
                    return State.UNSELECTED;
                } else if (StringUtils.equalsIgnoreCase(value.toString(),
                        State.SELECTED.toString())) {
                    return State.SELECTED;
                } else if (StringUtils.equalsIgnoreCase(value.toString(),
                        State.MIXED.toString())) {
                    return State.MIXED;
                }
            }
        }

        return State.UNSELECTED;
    }

    @Override
    public Object valueOf(State state) {
        if (state != null) {
            if (state == State.UNSELECTED) {
                return Boolean.FALSE;
            }

            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }
}
