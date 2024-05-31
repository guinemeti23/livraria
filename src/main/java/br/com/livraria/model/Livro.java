package br.com.livraria.model;

import java.util.List;

import java.util.List;

public class Livro {
    private int id;
    private String nome;
    private int quantidade;
    private double preco;
    private String descricao;
    private double avaliacao;
    private List<String> imagens; // Lista para armazenar as imagens

    // Construtor com ID
    public Livro(int id, String nome, int quantidade, double preco, String descricao, double avaliacao, List<String> imagens) {
        this.id = id;
        this.nome = nome;
        this.quantidade = quantidade;
        this.preco = preco;
        this.descricao = descricao;
        this.avaliacao = avaliacao;
        this.imagens = imagens;
    }

    // Construtor sem ID
    public Livro(String nome, int quantidade, double preco, String descricao, double avaliacao, List<String> imagens) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.preco = preco;
        this.descricao = descricao;
        this.avaliacao = avaliacao;
        this.imagens = imagens;
    }

    // Getters e setters
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

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public List<String> getImagens() {
        return imagens;
    }

    public void setImagens(List<String> imagens) {
        this.imagens = imagens;
    }
}
