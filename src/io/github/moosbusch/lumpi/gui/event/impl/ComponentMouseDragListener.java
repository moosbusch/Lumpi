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
package io.github.moosbusch.lumpi.gui.event.impl;

import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.ComponentMouseButtonListener;
import org.apache.pivot.wtk.ComponentMouseListener;
import org.apache.pivot.wtk.Mouse;

/**
 *
 * @author moosbusch
 */
public abstract class ComponentMouseDragListener extends ComponentMouseButtonListener.Adapter
        implements ComponentMouseListener {

    private final boolean consumeEvent;
    private boolean immediateRepaint = true;
    private boolean dragging = false;
    private boolean repaintOnDrag = false;
    private int currentX, currentY, currentDx, currentDy;

    public ComponentMouseDragListener() {
        this(false);
    }

    public ComponentMouseDragListener(boolean consumeEvent) {
        this.consumeEvent = consumeEvent;
    }

    private int getCurrentX() {
        return currentX;
    }

    private void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    private int getCurrentY() {
        return currentY;
    }

    private void setCurrentY(int currentY) {
        this.currentY = currentY;
    }

    private int getCurrentDx() {
        return currentDx;
    }

    private void setCurrentDx(int currentDx) {
        this.currentDx = currentDx;
    }

    private int getCurrentDy() {
        return currentDy;
    }

    private void setCurrentDy(int currentDy) {
        this.currentDy = currentDy;
    }

    private boolean isDragging() {
        return dragging;
    }

    private void setDragging(Component cmp, int x, int y, boolean dragging) {
        this.dragging = dragging;

        if (dragging) {
            beginDrag(cmp, x, y);
        } else {
            endDrag(cmp, x, y);

            if (!isRepaintOnDrag()) {
                cmp.repaint(isImmediateRepaint());
            }
        }
    }

    public boolean isConsumeEvent() {
        return consumeEvent;
    }

    public boolean isRepaintOnDrag() {
        return repaintOnDrag;
    }

    public void setRepaintOnDrag(boolean repaintOnDrag) {
        this.repaintOnDrag = repaintOnDrag;
    }

    public boolean isImmediateRepaint() {
        return immediateRepaint;
    }

    public void setImmediateRepaint(boolean immediateRepaint) {
        this.immediateRepaint = immediateRepaint;
    }

    public static void attach(Component cmp, ComponentMouseDragListener l) {
        cmp.getComponentMouseListeners().add(l);
        cmp.getComponentMouseButtonListeners().add(l);
    }

    public static void remove(Component cmp, ComponentMouseDragListener l) {
        cmp.getComponentMouseListeners().remove(l);
        cmp.getComponentMouseButtonListeners().remove(l);
    }

    public abstract void beginDrag(Component component, int x, int y);

    public abstract void onDrag(Component component, int x, int y, int dx, int dy);

    public abstract void endDrag(Component component, int x, int y);

    @Override
    public final boolean mouseMove(Component component, int x, int y) {
        if (isDragging()) {
            int oldX = getCurrentX();
            int oldY = getCurrentY();
            int deltaX = Math.max(Math.abs(oldX), Math.abs(x))
                    - Math.min(Math.abs(oldX), Math.abs(x));
            int deltaY = Math.max(Math.abs(oldY), Math.abs(y))
                    - Math.min(Math.abs(oldY), Math.abs(y));

            setCurrentX(x);
            setCurrentY(y);

            if (oldX > x) {
                setCurrentDx(-deltaX);
            } else {
                setCurrentDx(deltaX);
            }

            if (oldY > y) {
                setCurrentDy(-deltaY);
            } else {
                setCurrentDy(deltaY);
            }

            onDrag(component, getCurrentX(), getCurrentY(),
                    getCurrentDx(), getCurrentDy());

            if (isRepaintOnDrag()) {
                component.repaint();
            }
        }

        return isConsumeEvent();
    }

    @Override
    public final boolean mouseDown(Component component, Mouse.Button button,
            int x, int y) {
        setCurrentX(x);
        setCurrentY(y);
        setDragging(component, x, y, true);
        return isConsumeEvent();
    }

    @Override
    public final boolean mouseUp(Component component, Mouse.Button button,
            int x, int y) {
        setCurrentX(-1);
        setCurrentY(-1);
        setCurrentDx(0);
        setCurrentDy(0);
        setDragging(component, x, y, false);
        return isConsumeEvent();
    }

    @Override
    public final void mouseOver(Component component) {
    }

    @Override
    public final void mouseOut(Component component) {
    }

    @Override
    public final boolean mouseClick(Component component, Mouse.Button button,
            int x, int y, int count) {
        return false;
    }
}
