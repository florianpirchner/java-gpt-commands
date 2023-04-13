package org.lunifera.gpt.commands.minimalhint;

import org.lunifera.gpt.commands.impl.AbstractCommand;

public class AskYourDoctorCommand extends AbstractCommand {

	public static final String NAME = "ASK_YOUR_DOCTOR";
	public static final String HINT = "Ask your doctor";

	public AskYourDoctorCommand() {
		super(NAME, HINT);
	}

}
