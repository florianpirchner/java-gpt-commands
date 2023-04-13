package org.lunifera.gpt.commands.api;

import org.lunifera.gpt.commands.api.IPrompter.CommandWrapper;

public class InvalidCommand implements ICommand {

	@Override
	public String getName() {
		return "__INVALID_COMMAND";
	}

	@Override
	public String toPrompt(CommandWrapper delimiter) {
		throw new UnsupportedOperationException();
	}

}
