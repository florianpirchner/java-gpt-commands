package org.lunifera.gpt.commands.impl;

import org.lunifera.gpt.commands.api.ICommand;
import org.lunifera.gpt.commands.api.ICommandWrapper;

public abstract class AbstractCommand implements ICommand {

	protected final String name;
	protected final String hints;

	public AbstractCommand(String name, String hints) {
		super();
		this.name = name;
		this.hints = hints;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String toPrompt(ICommandWrapper wrapper) {
		StringBuilder sb = new StringBuilder();
		sb.append(wrapper.wrapCommand(name));

		if (hints != null && !hints.isEmpty()) {
			sb.append(" - ");
			sb.append(hints);
		}

		return sb.toString();
	}

	@Override
	public String toString() {
		return "AbstractCommand [name=" + name + ", hints=" + hints + "]";
	}

}
