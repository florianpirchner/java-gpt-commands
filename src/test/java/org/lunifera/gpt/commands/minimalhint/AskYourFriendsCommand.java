package org.lunifera.gpt.commands.minimalhint;

import org.lunifera.gpt.commands.impl.AbstractCommand;

public class AskYourFriendsCommand extends AbstractCommand {

	public static final String NAME = "ASK_YOUR_FRIENDS";
	public static final String HINT = "Ask your friends";

	public AskYourFriendsCommand() {
		super(NAME, HINT);
	}

}
