package com.factory.control.domain.bo;

import lombok.Value;

@Value
public class InMemoryFileContainer {
    String name;
    String extension;
    byte[] content;

    public String getFullName() {
        return name.replace(" ", "-") + "." + extension;
    }
}
