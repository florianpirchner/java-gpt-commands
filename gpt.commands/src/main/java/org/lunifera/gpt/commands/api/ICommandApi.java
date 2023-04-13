package org.lunifera.gpt.commands.api;

import java.util.List;
import java.util.function.Supplier;

/**
 * The command api builds the prompts for a given input and sends them to
 * https://platform.openai.com/docs/api-reference/chat/create, to get a proper
 * command.
 * 
 * @author Florian
 *
 */
public interface ICommandApi {

	/**
	 * Sets the commands supplier to the api. These {@link ICommand commands} will
	 * be used by the {@link IPrompter} to create prompts.
	 * 
	 * @param commands
	 */
	void setCommands(Supplier<List<? extends ICommand>> commands);

	/**
	 * Sets the {@link IPrompter prompter} to be used to api. The {@link IPrompter
	 * prompter} is responsible to create the required prompts.
	 * 
	 * @param prompter
	 */
	void setPrompter(IPrompter prompter);

	/**
	 * Tries to query a command for the given userInput.
	 * 
	 * @param userInput
	 * @return
	 */
	ICommand queryCommand(String userInput);

}
