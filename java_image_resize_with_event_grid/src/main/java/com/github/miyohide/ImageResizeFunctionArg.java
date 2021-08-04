package com.github.miyohide;

public class ImageResizeFunctionArg {
    private EventSchema eventSchema;
    private byte[] input;

    public ImageResizeFunctionArg(EventSchema eventSchema, byte[] input) {
        this.eventSchema = eventSchema;
        this.input = input;
    }

    public EventSchema getEventSchema() {
        return eventSchema;
    }

    public byte[] getInput() {
        return input;
    }
}
