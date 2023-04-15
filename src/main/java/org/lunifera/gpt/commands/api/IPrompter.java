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
	 * Parts of this prompt are copied and modified from "[Auto-GPT"](https://github.com/Torantulino/Auto-GPT).
	 *
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
					+ "You will never ask a follow-up question!\n" + "\n" //
					+ "And you MUST only answer the {COMMAND}!"; //

	/**
	 * Returns the DEFAULT_SYSTEM_PROMPT but replaces the pre- and postfix.
	 * 
	 * @return
	 */
	default String getDefaultSystemPromptTemplate(ICommandWrapper delimiter) {
		return DEFAULT_SYSTEM_PROMPT.replaceAll(PREFIX_TEMPLATE, delimiter.getPrefix())//
				.replaceAll(POSTFIX_TEMPLATE, delimiter.getPostfix());
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
	 * Sets the command delimiter. See {@link ICommandWrapper}.
	 * 
	 * @param wrapper
	 */
	void setCommandWrapper(ICommandWrapper wrapper);

	/**
	 * Returns the command delimiter. See {@link ICommandWrapper}.
	 * 
	 * @return
	 */
	default ICommandWrapper getCommandWrapper() {
		return ICommandWrapper.DEFAULT;
	}

}
