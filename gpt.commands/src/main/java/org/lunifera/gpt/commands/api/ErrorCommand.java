package org.lunifera.gpt.commands.api;

public class ErrorCommand implements ICommand {

	@Override
	public String getName() {
		return "__ERROR_COMMAND";
	}

	@Override
	public String toPrompt(ICommandWrapper delimiter) {
		throw new UnsupportedOperationException();
	}

}
