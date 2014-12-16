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
package io.github.moosbusch.lumpi.beans;

/**
 *
 * @author moosbusch
 */
public interface Submitable<T extends Object> extends ValueStore<T> {

    public static final String CANCELED_PROPERTYNAME = "canceled";
    public static final String SUBMITTED_PROPERTYNAME = "submitted";

    public void submit();

    public void cancel();

    public boolean canSubmit(final T value);

    public boolean isCanceled();

    public void setCanceled(boolean canceled);

    public boolean isSubmitted();

    public void setSubmitted(boolean submitted);

    public void onCancel();

    public void onSubmit(T value);

    public T modifyValueBeforeSubmit(T value);

}
