package org.lunifera.gpt.commands.impl;

import java.util.List;
import java.util.function.Supplier;

import org.lunifera.gpt.commands.api.ErrorCommand;
import org.lunifera.gpt.commands.api.ICommand;
import org.lunifera.gpt.commands.api.ICommandWrapper;
import org.lunifera.gpt.commands.api.IPrompter;
import org.lunifera.gpt.commands.api.InvalidCommand;

public class Prompter implements IPrompter {

	private ICommandWrapper commandWrapper = ICommandWrapper.DEFAULT;

	@Override
	public String getSystemPrompt(Supplier<List<? extends ICommand>> commands) {
		String template = getDefaultSystemPromptTemplate(commandWrapper);

		String prompt = template.replaceFirst(COMMAND_LIST_TEMPLATE, createCommdansPromptSection(commands));
		return prompt;
	}

	@Override
	public void setCommandWrapper(ICommandWrapper commandWrapper) {
		this.commandWrapper = commandWrapper;
	}

	/**
	 * Returns the commands text to be included in the prompt.
	 * 
	 * @param commands
	 * @return
	 */
	private String createCommdansPromptSection(Supplier<List<? extends ICommand>> commands) {
		StringBuilder sb = new StringBuilder();
		commands.get().forEach(c -> {
			sb.append("- ");
			sb.append(c.toPrompt(ICommandWrapper.DEFAULT));
			sb.append("\n");
		});
		return sb.toString();
	}

	@Override
	public ICommand findCommand(String commandString, Supplier<List<? extends ICommand>> commands) {
		String unwrappedCommandString = unwrapRawCommand(commandString);
		if (unwrappedCommandString.isBlank()) {
			return createErrorCommand("No response");
		}

		ICommand command = commands.get().stream().filter(c -> c.getName().equals(unwrappedCommandString)).findFirst()
				.orElse(null);
		if (command == null) {
			command = createInvalidCommand();
		}

		return command;
	}

	protected ICommand createErrorCommand(String text) {
		return new ErrorCommand();
	}

	protected InvalidCommand createInvalidCommand() {
		return new InvalidCommand();
	}

	/**
	 * GPT will return the command in the form {Prefix}COMMAND{POSTFIX}.
	 * <p>
	 * Eg. {WEB_SEARCH}. So we will remove the pre- and postfix here to get the raw
	 * command WEB_SEARCH.
	 * 
	 * @param commandString
	 * @return
	 */
	protected String unwrapRawCommand(String commandString) {
		return commandWrapper.unwrapCommand(commandString);
	}

}
