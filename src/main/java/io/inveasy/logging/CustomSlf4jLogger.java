/*
 * Copyright (C) 2017-2018 Guillaume Gravetot - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and strictly confidential
 * Written by Guillaume Gravetot <ggravetot@gmail.com>
 */

package io.inveasy.logging;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class CustomSlf4jLogger extends akka.event.slf4j.Slf4jLogger
{
	@Override
	public String formatTimestamp(long timestamp)
	{
		return OffsetDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
	}
}
