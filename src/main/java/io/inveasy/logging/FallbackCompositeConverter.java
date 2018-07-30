package io.inveasy.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.CompositeConverter;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class FallbackCompositeConverter extends CompositeConverter<ILoggingEvent>
{
	@Override
	protected String transform(ILoggingEvent iLoggingEvent, String s)
	{
		if(s != null && !s.isEmpty())
			return s;
		else if(getFirstOption().equals("%d"))
			return OffsetDateTime.ofInstant(Instant.ofEpochMilli(iLoggingEvent.getTimeStamp()), ZoneOffset.UTC)
					.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
		else if(getFirstOption().equals("%t"))
			return iLoggingEvent.getThreadName();
		else return "";
	}
}
