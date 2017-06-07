package com.github.dmitriylamzin.util;

import org.jboss.logging.Logger;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

/**
 * Created by Dmitriy_Lamzin on 6/5/2017.
 */
public class LoggerProducer {
    @Produces
    public Logger produceLogger(InjectionPoint injectionPoint) {
        return
                Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }
}