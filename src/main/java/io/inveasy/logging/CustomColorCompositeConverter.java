/*
 * Copyright 2018 Guillaume Gravetot
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.inveasy.logging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.CompositeConverter;

public class CustomColorCompositeConverter extends CompositeConverter<ILoggingEvent>
{
	private String color;
	
	private boolean highlight = false;
	private boolean highlightText = false;
	private boolean noColor = false;
	
	private boolean bold;
	private boolean faint;
	private boolean italic;
	private boolean underline;
	
	private static final String OPEN_STRING = ((char)27) + "[";
	private static final String CLOSE_STRING = "m";
	private static final String END_STRING = ((char)27) + "[0m";
	
	@Override
	public void start()
	{
		if(getOptionList().get(0).equals("highlight"))
		{
			highlight = true;
			highlightText = getOptionList().contains("text");
		}
		else
		{
			if(getOptionList().get(0).matches("\\d*"))
				color = getOptionList().get(0);
			else
				noColor = true;
		}
		
		bold = getOptionList().contains("bold");
		faint = getOptionList().contains("faint");
		italic = getOptionList().contains("italic");
		underline = getOptionList().contains("underline");
		
		super.start();
	}
	
	@Override
	protected String transform(ILoggingEvent event, String in)
	{
		StringBuilder sb = new StringBuilder()
				.append(OPEN_STRING);
		
		if(bold)
			sb.append("1;");
		else if(faint)
			sb.append("2;");
		
		if(italic)
			sb.append("3;");
		if(underline)
			sb.append("4;");
		
		if(!noColor)
		{
			if(highlight)
				sb.append(getHighlightColorCode(event));
			else
				sb.append(color);
		}
		
		if(sb.substring(sb.length() - 1).equals(";"))
			sb.delete(sb.length() - 1, sb.length());
		
		return sb.append(CLOSE_STRING)
				.append(in)
				.append(END_STRING)
				.toString();
	}
	
	private String getHighlightColorCode(ILoggingEvent event)
	{
		Level level = event.getLevel();
		if(highlightText)
		{
			switch(level.toInt())
			{
				case Level.ERROR_INT:
					return "31";
				case Level.WARN_INT:
					return "33";
				case Level.INFO_INT:
					return "0";
				default:
					return "2;93";
			}
		}
		else
		{
			switch(level.toInt())
			{
				case Level.ERROR_INT:
					return "1;31";
				case Level.WARN_INT:
					return "1;33";
				case Level.INFO_INT:
					return "32";
				default:
					return "2;93";
			}
		}
	}
}
