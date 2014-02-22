package com.github.pennyfive.finnkino.io.xml;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.simpleframework.xml.convert.Converter;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.OutputNode;

/**
 * Converts time nodes received from Finnkino API to {@link org.joda.time.DateTime} object.
 */
public class DateTimeConverter implements Converter<DateTime> {

    @Override
    public DateTime read(InputNode inputNode) throws Exception {
        return ISODateTimeFormat.dateTimeNoMillis().parseDateTime(inputNode.getValue());
    }

    @Override
    public void write(OutputNode outputNode, DateTime dateTime) throws Exception {

    }
}
