package com.github.pennyfive.finnkino.util;

import com.github.pennyfive.finnkino.api.model.ImageUrlContainer;
import com.github.pennyfive.finnkino.io.xml.DateTimeConverter;
import com.github.pennyfive.finnkino.io.xml.ImageUrlContainerConverter;

import org.joda.time.DateTime;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.Registry;
import org.simpleframework.xml.convert.RegistryStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;

/**
 *
 */
public class Serializers {

    public static Serializer DEFAULT = createDefaultSerializer();

    private static Serializer createDefaultSerializer() {
        Registry registry = new Registry();
        Strategy strategy = new RegistryStrategy(registry);
        Serializer serializer = new Persister(strategy);

        try {
            registry.bind(DateTime.class, new DateTimeConverter());
            registry.bind(ImageUrlContainer.class, new ImageUrlContainerConverter());
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }

        return serializer;
    }
}
