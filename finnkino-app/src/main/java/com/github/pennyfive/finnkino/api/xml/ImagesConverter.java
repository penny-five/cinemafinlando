package com.github.pennyfive.finnkino.api.xml;

import com.github.pennyfive.finnkino.api.model.Images;

import org.simpleframework.xml.convert.Converter;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.OutputNode;

import java.util.HashMap;
import java.util.Map;

/**
 * Converts image url nodes received from Finnkino API to {@link com.github.pennyfive.finnkino.api.model.Images} instances.
 */
public class ImagesConverter implements Converter<Images> {

    @Override
    public Images read(InputNode inputNode) throws Exception {
        Map<String, String> sizeToUrl = new HashMap();
        InputNode child;
        while ((child = inputNode.getNext()) != null) {
            sizeToUrl.put(child.getName(), child.getValue());
        }
        return new Images(sizeToUrl);
    }

    @Override
    public void write(OutputNode outputNode, Images images) throws Exception {
        throw new UnsupportedOperationException();
    }
}
