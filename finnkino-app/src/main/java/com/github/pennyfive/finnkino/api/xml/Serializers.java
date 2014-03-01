package com.github.pennyfive.finnkino.api.xml;

import com.github.pennyfive.finnkino.api.model.Images;

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
    public static final Serializer DEFAULT = createDefaultSerializer();

    private static Serializer createDefaultSerializer() {
        Registry registry = new Registry();
        Strategy strategy = new RegistryStrategy(registry);
        Serializer serializer = new Persister(strategy);

        try {
            registry.bind(DateTime.class, new DateTimeConverter());
            registry.bind(Images.class, new ImagesConverter());
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }

        return serializer;
    }

    private Serializers() {}

    ;
}