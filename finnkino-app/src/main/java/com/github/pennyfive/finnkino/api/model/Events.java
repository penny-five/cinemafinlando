package com.github.pennyfive.finnkino.api.model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 *
 */
@Root(strict = false)
public class Events implements Container<Event> {
    @ElementList(entry = "Event", inline = true)
    private List<Event> events;

    @Override
    public List<Event> getItems() {
        return events;
    }
}
