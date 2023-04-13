package org.lunifera.gpt.commands.minimalhint;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.lunifera.gpt.commands.api.ICommand;
import org.lunifera.gpt.commands.impl.CommandApi;

public class Test {

	private final String OPENAI_KEY = System.getProperty("OPENAI_KEY");

	@org.junit.Test
	public void test() {
		CommandApi api = new CommandApi(OPENAI_KEY);
		api.setCommands(this::createCommands);

		// Ask your boss
		ICommand command = api.queryCommand("Need more money");
		assertEquals(AskYourBossCommand.NAME, command.getName());
		
		// Do web query
		command = api.queryCommand("Info about quantum physics.");
		assertEquals(WebSearchCommand.NAME, command.getName());
		
		// As friends
		command = api.queryCommand("I need some one to talk.");
		assertEquals(AskYourFriendsCommand.NAME, command.getName());
		
		// Also ask friends
		command = api.queryCommand("My friend is physicist. I need some info about quantum physics.");
		assertEquals(AskYourFriendsCommand.NAME, command.getName());
		
		// Ask you doctor
		command = api.queryCommand("My head feels hot");
		assertEquals(AskYourDoctorCommand.NAME, command.getName());
	}

	/**
	 * Create the list of commands.
	 * @return
	 */
	private List<ICommand> createCommands() {
		List<ICommand> commands = new ArrayList<>();
		commands.add(new AskYourBossCommand());
		commands.add(new AskYourFriendsCommand());
		commands.add(new AskYourDoctorCommand());
		commands.add(new WebSearchCommand());
		return commands;
	}
}
