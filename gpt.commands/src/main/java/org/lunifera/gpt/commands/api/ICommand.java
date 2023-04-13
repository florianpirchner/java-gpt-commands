package org.lunifera.gpt.commands.api;

import org.lunifera.gpt.commands.api.IPrompter.CommandWrapper;

/**
 * A command is responsible, to provide the {@link IPrompter} with required
 * information, to integrate the commands properly into the prompt.
 * 
 * @author Florian
 */
public interface ICommand {

	/**
	 * Returns the name of the command. Eg. WEB_SEARCH.
	 * @return
	 */
	String getName();
	
	/**
	 * Returns a String representation of this command to be used by the
	 * {@link IPrompter}. It is important, to wrap the command inside the delimiter
	 * pre- and postfix, if they are not blank.
	 * <p>
	 * Eg command WEB_SEARCH. Prefix = { and postfix = }. So the command needs to be
	 * wrapped inside {WEB_SEARCH}. Pre- and postfix allow gpt to recognize the
	 * command properly. They are also part of the general description in the in system prompt.
	 * 
	 * @return
	 */
	String toPrompt(CommandWrapper wrapper);

}
