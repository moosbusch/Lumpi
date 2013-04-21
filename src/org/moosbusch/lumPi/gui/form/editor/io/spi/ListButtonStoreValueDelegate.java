/*

 *
 */
package org.moosbusch.lumPi.gui.form.editor.io.spi;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.pivot.collections.Sequence;
import org.apache.pivot.wtk.ListButton;
import org.apache.pivot.wtk.ListView;
import org.moosbusch.lumPi.gui.form.editor.FormEditor;
import org.moosbusch.lumPi.gui.form.editor.io.StoreValueDelegate;
import org.moosbusch.lumPi.gui.form.spi.AbstractDynamicForm;
import org.springframework.util.ObjectUtils;

/**
 *
 * @author moosbusch
 */
public class ListButtonStoreValueDelegate<T extends ListButton> implements StoreValueDelegate<T> {

    private final FormEditor<T> formEditor;

    public ListButtonStoreValueDelegate(FormEditor<T> formEditor) {
        this.formEditor = Objects.requireNonNull(formEditor);
    }

    @Override
    public FormEditor<T> getFormEditor() {
        return formEditor;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void storeValue(Object context) {
        if (context != null) {
            ListButton listButton = getFormEditor().getComponent();
            String propertyName = listButton.getListDataKey();
            ListView.ListDataBindMapping bindMapping = listButton.getListDataBindMapping();
            Object newPropertyValue = bindMapping.valueOf(listButton.getListData());

            if (PropertyUtils.isWriteable(context, propertyName)) {
                listButton.store(context);
            } else {
                Object oldPropertyValue = null;

                try {
                    oldPropertyValue = PropertyUtils.getProperty(context, propertyName);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
                    Logger.getLogger(AbstractDynamicForm.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    if ((oldPropertyValue != null) && (oldPropertyValue != null)) {
                        if ((newPropertyValue instanceof java.util.Collection)
                                && (oldPropertyValue instanceof java.util.Collection)) {
                            java.util.Collection<Object> newColl =
                                    (java.util.Collection<Object>) newPropertyValue;
                            java.util.Collection<Object> oldColl =
                                    (java.util.Collection<Object>) oldPropertyValue;

                            for (Object obj : newColl) {
                                if (!oldColl.contains(obj)) {
                                    oldColl.add(obj);
                                }
                            }
                        } else if ((newPropertyValue instanceof Sequence)
                                && (oldPropertyValue instanceof Sequence)) {
                            Sequence<Object> newSeq = (Sequence<Object>) newPropertyValue;
                            Sequence<Object> oldSeq = (Sequence<Object>) oldPropertyValue;

                            for (int cnt = 0; cnt < newSeq.getLength(); cnt++) {
                                Object obj = newSeq.get(cnt);

                                if (oldSeq.indexOf(obj) == -1) {
                                    oldSeq.add(obj);
                                }
                            }
                        } else if ((newPropertyValue instanceof org.apache.pivot.collections.Set)
                                && (oldPropertyValue instanceof org.apache.pivot.collections.Set)) {
                            org.apache.pivot.collections.Set<Object> newColl =
                                    (org.apache.pivot.collections.Set<Object>) newPropertyValue;
                            org.apache.pivot.collections.Set<Object> oldColl =
                                    (org.apache.pivot.collections.Set<Object>) oldPropertyValue;

                            for (Object obj : newColl) {
                                if (!oldColl.contains(obj)) {
                                    oldColl.add(obj);
                                }
                            }
                        }  else if ((ObjectUtils.isArray(newPropertyValue))
                                && (ObjectUtils.isArray(oldPropertyValue))) {
                            Object[] newArray = (Object[]) newPropertyValue;
                            Object[] oldArray = (Object[]) oldPropertyValue;

                            for (Object obj : newArray) {
                                if (!ArrayUtils.contains(oldArray, obj)) {
                                    oldArray = ArrayUtils.add(oldArray, obj);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
