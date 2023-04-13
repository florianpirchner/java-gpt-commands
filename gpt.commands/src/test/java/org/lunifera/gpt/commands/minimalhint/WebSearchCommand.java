package org.lunifera.gpt.commands.minimalhint;

import org.lunifera.gpt.commands.impl.AbstractCommand;

public class WebSearchCommand extends AbstractCommand {

	public static final String NAME = "WEB_SEARCH";
	public static final String HINT = "Ask the web";

	public WebSearchCommand() {
		super(NAME, HINT);
	}

}
