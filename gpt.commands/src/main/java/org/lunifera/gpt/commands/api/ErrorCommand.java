package org.lunifera.gpt.commands.api;

import org.lunifera.gpt.commands.api.IPrompter.CommandWrapper;

public class ErrorCommand implements ICommand {

	@Override
	public String getName() {
		return "__ERROR_COMMAND";
	}

	@Override
	public String toPrompt(CommandWrapper delimiter) {
		throw new UnsupportedOperationException();
	}

}
