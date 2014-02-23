package com.github.pennyfive.finnkino.io.xml;

import com.github.pennyfive.finnkino.api.model.ImageUrlContainer;

import org.simpleframework.xml.convert.Converter;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.OutputNode;

import java.util.HashMap;
import java.util.Map;

/**
 * Converts image url nodes received from Finnkino API to {@link com.github.pennyfive.finnkino.api.model.ImageUrlContainer} instances.
 */
public class ImageUrlContainerConverter implements Converter<ImageUrlContainer> {

    @Override
    public ImageUrlContainer read(InputNode inputNode) throws Exception {
        Map<String, String> sizeToUrl = new HashMap();
        InputNode child;
        while ((child = inputNode.getNext()) != null) {
            sizeToUrl.put(child.getName(), child.getValue());
        }
        return new ImageUrlContainer(sizeToUrl);
    }

    @Override
    public void write(OutputNode outputNode, ImageUrlContainer imageUrlContainer) throws Exception {
        throw new UnsupportedOperationException();
    }
}
