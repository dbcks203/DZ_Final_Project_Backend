package com.douzone.filter;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

public class HttpStatusFilter extends Filter<ILoggingEvent> {

    @Override
    public FilterReply decide(ILoggingEvent event) {
        if (event.getMessage().contains("status_code")) {
            int statusCodeIndex = event.getMessage().indexOf("status_code:") + 12;
            int statusCode = Integer.parseInt(event.getMessage().substring(statusCodeIndex, statusCodeIndex + 3));
            if (statusCode >= 500) {
                return FilterReply.ACCEPT;
            } else {
                return FilterReply.DENY;
            }
        }
        return FilterReply.DENY;
    }
}
