/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moosbusch.lumPi.beans.spring.impl;

import java.util.prefs.Preferences;
import org.moosbusch.lumPi.application.PivotApplicationContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Gunnar Kappei
 */
@Configuration
public class PreferencesFactoryBean implements FactoryBean<Preferences>,
        ApplicationContextAware {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public final Preferences getObject() throws Exception {
        return getApplicationContext().getPreferences();
    }

    @Override
    public final Class<Preferences> getObjectType() {
        return Preferences.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public PivotApplicationContext getApplicationContext() {
        return (PivotApplicationContext) applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
