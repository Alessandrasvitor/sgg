package br.com.ajenterprice.sgg_api.exception;

public class ServiceException extends RuntimeException {

    /**
     * Cria nova instancia da classe ServicoException
     */
    public ServiceException() {
        super();
    }

    /**
     * Cria nova instancia da classe ServicoException
     *
     * @param message Mensagem a ser enviada na exception
     */
    public ServiceException(String message) {
        super(message);
    }
}
