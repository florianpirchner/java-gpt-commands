package org.lunifera.gpt.commands.api;

import java.util.List;
import java.util.function.Supplier;

/**
 * An interface with the responsibility to create prompts. And to convert the
 * received command string to a proper command.
 * 
 * @author Florian
 */
public interface IPrompter {

	public static final String PREFIX_TEMPLATE = "\\{___PREFIX___}";
	public static final String POSTFIX_TEMPLATE = "\\{___POSTFIX___}";
	public static final String COMMAND_LIST_TEMPLATE = "\\{___COMMAND_LIST___}";

	/**
	 * {___PREFIX___}, {___POSTFIX___} and {___COMMAND_LIST___} need to be replaced
	 * later.
	 */
	public static final String DEFAULT_SYSTEM_PROMPT = //
			"You are a CommandEngine. Your only task is to deliver the best matching {___PREFIX___}COMMAND{___POSTFIX___} from a list of COMMANDS as a response.\n" //
					+ "\n" //
					+ "To make your decision, you receive a text. Based on this text, you choose the best matching {___PREFIX___}COMMAND{___POSTFIX___} and respond with it.\n" //
					+ "\n" //
					+ "\n" //
					+ "COMMANDS:\n" //
					+ "{___COMMAND_LIST___}" //
					+ "\n" //
					+ "You will never ask a follow-up question!\n"
					+ "\n" //
					+ "And you MUST only answer the {COMMAND}!"; //

	/**
	 * Returns the DEFAULT_SYSTEM_PROMPT but replaces the pre- and postfix.
	 * 
	 * @return
	 */
	default String getDefaultSystemPromptTemplate(CommandWrapper delimiter) {
		return DEFAULT_SYSTEM_PROMPT.replaceAll(PREFIX_TEMPLATE, delimiter.preFix)//
				.replaceAll(POSTFIX_TEMPLATE, delimiter.postFix);
	}

	/**
	 * Returns the "system" prompt to be used in the OpenAI Chat API.
	 * 
	 * @param see {@link ICommandApi#setCommands(Supplier)}
	 * 
	 * @return
	 */
	String getSystemPrompt(Supplier<List<? extends ICommand>> commands);

	/**
	 * Returns a proper command for the given commandString in respect to the system
	 * prompt.
	 * 
	 * @param commandString
	 * @param commands
	 * @return
	 */
	ICommand findCommand(String commandString, Supplier<List<? extends ICommand>> commands);

	/**
	 * Sets the command delimiter. See {@link CommandWrapper}.
	 * 
	 * @param delimiter
	 */
	void setCommandWrapper(CommandWrapper delimiter);

	/**
	 * Returns the command delimiter. See {@link CommandWrapper}.
	 * 
	 * @return
	 */
	default CommandWrapper getCommandWrapper() {
		return CommandWrapper.DEFAULT;
	}

	/**
	 * Describes how to wrap the command. By default, the command will be wrapped
	 * following the format "{%s}". This is required, to ensure that gpt-4 can
	 * recognize the raw command part properly.
	 * <p>
	 * Eg: {SEARCH_WEB} - in cases you need more information.
	 */
	public static class CommandWrapper {

		public static final CommandWrapper DEFAULT = new CommandWrapper("{", "}");

		public final String preFix;
		public final String postFix;

		public CommandWrapper(String preFix, String postFix) {
			super();
			this.preFix = preFix;
			this.postFix = postFix;
		}

		public String wrapCommand(String command) {
			if (preFix == null) {
				return command;
			}

			return preFix + command + postFix;
		}

		public String unwrapCommand(String wrappedCommand) {
			if (preFix == null) {
				return wrappedCommand;
			}
			String command = wrappedCommand.trim();

			if (wrappedCommand.startsWith(preFix)) {
				command = wrappedCommand.substring(1, wrappedCommand.length());
			}

			if (command.endsWith(postFix)) {
				command = command.substring(0, command.length() - 1);
			}

			return command;
		}

	}
}
