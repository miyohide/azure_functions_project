package com.github.miyohide;

import java.util.Date;
import java.util.Map;

public class EventSchema {
    public String topic;
    public String subject;
    public String eventType;
    public Date eventTime;
    public String dataVersion;
    public String metadataVersion;
    public Map<String, Object> data;
}
