package de.othr.sw.talk.service;

import java.util.logging.Logger;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

@Dependent
public class LoggerProvider {

    @Produces
    public Logger createLogger(InjectionPoint target) {
        return Logger.getLogger("Talk-"+target.getMember().getDeclaringClass().getSimpleName());
    }
}
