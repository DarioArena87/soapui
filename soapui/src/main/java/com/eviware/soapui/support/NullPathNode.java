package com.eviware.soapui.support;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ValueNode;

import java.io.IOException;

public class NullPathNode extends ValueNode {

    @Override
    public JsonNodeType getNodeType() {
        return null;
    }

    @Override
    public String asText() {
        return "null";
    }

    @Override
    public String asText(String defaultValue) {
        return defaultValue;
    }

    @Override
    public boolean equals(Object o) {
        return (o == this);
    }

    @Override
    public JsonToken asToken() {
        return JsonToken.VALUE_NULL;
    }

    @Override
    public int hashCode() {
        return JsonNodeType.NULL.ordinal();
    }

    @Override
    public final void serialize(JsonGenerator g, SerializerProvider provider) throws IOException {
        provider.defaultSerializeNull(g);
    }
}
