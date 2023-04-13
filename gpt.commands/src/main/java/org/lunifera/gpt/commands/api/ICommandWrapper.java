package org.lunifera.gpt.commands.api;

/**
 * Describes how to wrap the command. By default, the command will be wrapped
 * following the format "PREFIX%sPOSTFIX". This is required, to ensure that
 * gpt-4 can recognize the raw command part properly.
 * <p>
 * Eg: WEB_SEARCH to {WEB_SEARCH}, if pre- and postfix is { and } and vies
 * versa.
 */
public interface ICommandWrapper {

	DefaultCommandWrapper DEFAULT = new DefaultCommandWrapper("{", "}");

	String getPrefix();

	String getPostfix();

	/**
	 * WEB_SEARCH to {WEB_SEARCH}, if pre- and postfix is { and }.
	 * 
	 * @param command
	 * @return
	 */
	String wrapCommand(String command);

	/**
	 * {WEB_SEARCH} to WEB_SEARCH, if pre- and postfix is { and }.
	 * 
	 * @param wrappedCommand
	 * @return
	 */
	String unwrapCommand(String wrappedCommand);

	/**
	 * Describes how to wrap the command. By default, the command will be wrapped
	 * following the format "{%s}". This is required, to ensure that gpt-4 can
	 * recognize the raw command part properly.
	 * <p>
	 * Eg: {SEARCH_WEB} - in cases you need more information.
	 */
	public static class DefaultCommandWrapper implements ICommandWrapper {

		private final String prefix;
		private final String postfix;

		public DefaultCommandWrapper(String preFix, String postFix) {
			super();
			this.prefix = preFix;
			this.postfix = postFix;
		}

		@Override
		public String getPrefix() {
			return prefix;
		}

		@Override
		public String getPostfix() {
			return postfix;
		}

		@Override
		public String wrapCommand(String command) {
			if (prefix == null) {
				return command;
			}

			return prefix + command + postfix;
		}

		@Override
		public String unwrapCommand(String wrappedCommand) {
			if (prefix == null) {
				return wrappedCommand;
			}
			String command = wrappedCommand.trim();

			if (wrappedCommand.startsWith(prefix)) {
				command = wrappedCommand.substring(1, wrappedCommand.length());
			}

			if (command.endsWith(postfix)) {
				command = command.substring(0, command.length() - 1);
			}

			return command;
		}

	}
}