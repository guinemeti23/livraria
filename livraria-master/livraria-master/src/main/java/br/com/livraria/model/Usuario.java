package br.com.livraria.model;

public class Usuario {
    private int id;
    private String nome;
    private String cpf;
    private String email;
    private String senha;
    private String grupo;
    private boolean ativo;


    public Usuario(String nome, String cpf, String email, String senha, String grupo, boolean ativo) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.grupo = grupo;
        this.ativo = ativo;
    }

    public Usuario(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
