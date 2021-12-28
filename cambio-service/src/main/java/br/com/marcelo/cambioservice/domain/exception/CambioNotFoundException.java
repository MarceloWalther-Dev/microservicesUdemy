package br.com.marcelo.cambioservice.domain.exception;

public class CambioNotFoundException extends EntityNotFoundException {

    public CambioNotFoundException(String message) {
        super(message);
    }

    public CambioNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CambioNotFoundException(Long id) {
        this(String.format("Não foi possivel encontrar cambio com o id %d", id));
    }

    public CambioNotFoundException() {
        super();
    }
}
