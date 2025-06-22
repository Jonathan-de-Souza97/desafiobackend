package br.com.grupocasasbahia.desafiobackend.vendedor.application.response;

public class Resposta<T> {
    private boolean Sucesso;
    private String Mensagem;
    private T Dados;

    public Resposta() {
    }

    public Resposta(boolean sucesso, String mensagem, T dados){
        Sucesso = sucesso;
        Mensagem = mensagem;
        Dados = dados;
    }

    public static <T> Resposta<T> successo(T data) {
        return new Resposta<>(true, "Operação realizada", data);
    }

    public static <T> Resposta<T> erro(String message) {
        return new Resposta<>(false, message, null);
    }

    public boolean Sucedido() {
        return Sucesso;
    }

    public String getMensagem() {
        return Mensagem;
    }

    public T getDados() {
        return Dados;
    }
}
