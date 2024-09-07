package com.cannon.nop.interfaces;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Iterator;

@Slf4j
@Component
public class LoadedConfigFileListener implements ApplicationListener<ApplicationReadyEvent>, Ordered {



    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        MutablePropertySources propertySources = event.getApplicationContext().getEnvironment().getPropertySources();
        Iterator<PropertySource<?>> propertySourceIterator = propertySources.iterator();
        propertySourceIterator.forEachRemaining(propertySource -> log.info("Successfully loaded: \"{}\" into application context", propertySource.getName()));
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}