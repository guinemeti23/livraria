package br.com.livraria.model;

public class ItemCarrinho {
    private int livroId;
    private String livroNome;
    private double livroPreco;
    private int quantidade;

    public ItemCarrinho() {
    }

    public ItemCarrinho(int livroId, String livroNome, double livroPreco, int quantidade) {
        this.livroId = livroId;
        this.livroNome = livroNome;
        this.livroPreco = livroPreco;
        this.quantidade = quantidade;
    }

    public int getLivroId() {
        return livroId;
    }

    public void setLivroId(int livroId) {
        this.livroId = livroId;
    }

    public String getLivroNome() {
        return livroNome;
    }

    public void setLivroNome(String livroNome) {
        this.livroNome = livroNome;
    }

    public double getLivroPreco() {
        return livroPreco;
    }

    public void setLivroPreco(double livroPreco) {
        this.livroPreco = livroPreco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
