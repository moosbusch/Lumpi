/*

 *
 */
package org.moosbusch.lumPi.gui.component.impl;

import java.util.Objects;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.ScrollPane;

/**
 *
 * @author moosbusch
 */
public class FillScrollPane extends ScrollPane {

    public FillScrollPane() {
        super(ScrollPane.ScrollBarPolicy.FILL_TO_CAPACITY,
              ScrollPane.ScrollBarPolicy.FILL_TO_CAPACITY);
    }

    public FillScrollPane(Component content) {
        this();
        init(Objects.requireNonNull(content));
    }

    private void init(Component content) {
        setView(content);
    }

}
