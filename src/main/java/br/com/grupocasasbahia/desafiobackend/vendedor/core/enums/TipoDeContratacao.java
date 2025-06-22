package br.com.grupocasasbahia.desafiobackend.vendedor.core.enums;

public enum TipoDeContratacao {
    Outsourcing, CLT, PJ;

    public static TipoDeContratacao converteAPartirDoCodigo(int codigoTipoContratacao){
        return TipoDeContratacao.values()[codigoTipoContratacao];
    }
}
