package org.lunifera.gpt.commands.minimalhint;

import org.lunifera.gpt.commands.impl.AbstractCommand;

public class AskYourBossCommand extends AbstractCommand {

	public static final String NAME = "ASK_YOUR_BOSS";
	public static final String HINT = "Ask your boss";

	public AskYourBossCommand() {
		super(NAME, HINT);
	}

}
