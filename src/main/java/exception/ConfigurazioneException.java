package exception;

public class ConfigurazioneException extends RuntimeException {
    public ConfigurazioneException(String message) {
        super(message);
    }
    public ConfigurazioneException(String message, Throwable cause) { super(message, cause); }
}
