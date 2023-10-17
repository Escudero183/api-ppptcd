package gob.pe.devida.ppptcd.config.exception;

/**
 * File created by Linygn Escudero$ on 17/10/2023$
 */
public class RestException {
    private final String errorMessage;

    public RestException(String errorMessage) {
        super();
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
