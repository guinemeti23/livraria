package br.com.livraria.model;


import java.util.List;

public class Livro {
    private int id;
    private String nome;
    private int quantidade;
    private List<String> imagens;
    private double preco;
    private String descricao;

    public Livro(String nome, int quantidade, List<String> imagens, double preco, String descricao, double avaliacao) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.imagens = imagens;
        this.preco = preco;
        this.descricao = descricao;
        this.avaliacao = avaliacao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(double avaliacao) {
        this.avaliacao = avaliacao;
    }

    private double avaliacao;

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

    public List<String> getImagens() {
        return imagens;
    }

    public void setImagens(List<String> imagens) {
        this.imagens = imagens;
    }


    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
}