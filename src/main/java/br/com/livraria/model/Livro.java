package br.com.livraria.model;


import java.util.List;

public class Livro {
    private int id;
    private String nome;
    private int quantidade;
    private double preco;
    private String descricao;
    private double avaliacao;
    private String imagemPrincipal;
    private String imagem2;
    private String imagem3;
    private String imagem4;
    private String imagem5;



    public Livro(int id, String nome, int quantidade, double preco, String descricao, double avaliacao,
                 String imagemPrincipal, String imagem2, String imagem3, String imagem4, String imagem5) {
        this.id = id;
        this.nome = nome;
        this.quantidade = quantidade;
        this.preco = preco;
        this.descricao = descricao;
        this.avaliacao = avaliacao;
        this.imagemPrincipal = imagemPrincipal;
        this.imagem2 = imagem2;
        this.imagem3 = imagem3;
        this.imagem4 = imagem4;
        this.imagem5 = imagem5;
    }

    public Livro(String nome, int quantidade, double preco, String descricao, double avaliacao) {
        this.id = id;
        this.nome = nome;
        this.quantidade = quantidade;
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

    public String getImagemPrincipal() {
        return imagemPrincipal;
    }

    public void setImagemPrincipal(String imagemPrincipal) {
        this.imagemPrincipal = imagemPrincipal;
    }

    public String getImagem2() {
        return imagem2;
    }

    public void setImagem2(String imagem2) {
        this.imagem2 = imagem2;
    }

    public String getImagem3() {
        return imagem3;
    }

    public void setImagem3(String imagem3) {
        this.imagem3 = imagem3;
    }

    public String getImagem4() {
        return imagem4;
    }

    public void setImagem4(String imagem4) {
        this.imagem4 = imagem4;
    }

    public String getImagem5() {
        return imagem5;
    }

    public void setImagem5(String imagem5) {
        this.imagem5 = imagem5;
    }
}