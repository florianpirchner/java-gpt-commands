package org.lunifera.gpt.commands.minimalhint;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.lunifera.gpt.commands.api.ICommand;
import org.lunifera.gpt.commands.impl.CommandApi;

public class CommandTest {

	private final String OPENAI_KEY = System.getProperty("OPENAI_KEY");

	@Test
	public void testCommandExecution() {
		CommandApi api = new CommandApi(OPENAI_KEY);

		// Register commands at the API
		// AskYourBossCommand
		// AskYourFriendsCommand
		// AskYourDoctorCommand
		// WebSearchCommand
		api.setCommands(this::createCommands);

		// Test different queries and their expected commands

		// Need more money --> Ask your boss
		testCommand(api, "Need more money", AskYourBossCommand.NAME);

		// Info about quantum physics --> Ask the Web
		testCommand(api, "Info about quantum physics.", WebSearchCommand.NAME);

		// I need some one to talk --> Ask your friends
		testCommand(api, "I need some one to talk.", AskYourFriendsCommand.NAME);

		// My friend is physicist. I need some info about quantum physics --> Ask your
		// friends
		testCommand(api, "My friend is physicist. I need some info about quantum physics.", AskYourFriendsCommand.NAME);

		// My head feels hot --> Ask your doctor
		testCommand(api, "My head feels hot", AskYourDoctorCommand.NAME);
	}

	private void testCommand(CommandApi api, String query, String expectedCommandName) {
		ICommand command = api.queryCommand(query);
		assertEquals(expectedCommandName, command.getName());
	}

	/**
	 * Creates and returns a list of commands.
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
