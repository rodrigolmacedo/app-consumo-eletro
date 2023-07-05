package com.fiap.grupo9.appconsumoeletro.dominio;

import java.util.Objects;
import java.util.UUID;

public class Endereco {
    private UUID uuid;
    private String cep;
    private String end;
    private String numero;
    private String bairro;
    private String cidade;
    private String uf;
    private String complemento;

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Endereco endereco)) return false;
        return Objects.equals(getCep(), endereco.getCep()) && Objects.equals(getNumero(), endereco.getNumero()) && Objects.equals(getBairro(), endereco.getBairro()) && Objects.equals(getCidade(), endereco.getCidade()) && Objects.equals(getUf(), endereco.getUf()) && Objects.equals(getComplemento(), endereco.getComplemento());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCep(), getNumero(), getBairro(), getCidade(), getUf(), getComplemento());
    }
}
