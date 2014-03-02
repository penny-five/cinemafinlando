package com.github.pennyfive.finnkino.api.xml;

import android.util.SparseArray;

import com.github.pennyfive.finnkino.api.model.Images;

import org.simpleframework.xml.convert.Converter;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.OutputNode;

/**
 * Converts image url nodes received from Finnkino API to {@link com.github.pennyfive.finnkino.api.model.Images} instances.
 */
public class ImagesConverter implements Converter<Images> {

    @Override
    public Images read(InputNode inputNode) throws Exception {
        SparseArray<Object> array = new SparseArray<>();
        InputNode child;
        while ((child = inputNode.getNext()) != null) {
            array.put(child.getName().hashCode(), child.getValue());
        }
        return new Images(array);
    }

    @Override
    public void write(OutputNode outputNode, Images images) throws Exception {
        throw new UnsupportedOperationException();
    }
}
