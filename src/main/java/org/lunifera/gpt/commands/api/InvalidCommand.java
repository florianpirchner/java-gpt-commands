package org.lunifera.gpt.commands.api;

public class InvalidCommand implements ICommand {

	@Override
	public String getName() {
		return "__INVALID_COMMAND";
	}

	@Override
	public String toPrompt(ICommandWrapper delimiter) {
		throw new UnsupportedOperationException();
	}

}
