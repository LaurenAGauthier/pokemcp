package ai.pokemcp.app.exception;

public class UnknownMcpCommand extends IllegalArgumentException {
    public UnknownMcpCommand() {
        super("Unknown command");
    }
}
