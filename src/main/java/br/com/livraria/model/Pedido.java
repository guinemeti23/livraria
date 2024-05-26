package br.com.livraria.model;

import java.util.ArrayList;
import java.util.List;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private int pedidoId;
    private int clienteId;
    private int enderecoId;
    private String formaDePagamento;
    private String frete;
    private double valorTotal;
    private Date data;
    private String status;
    private List<ItemCarrinho> itens;

    // Construtores
    public Pedido() {
        this.itens = new ArrayList<>();
        this.data = new Date();
        this.status = "aguardando pagamento";
    }

    public Pedido(int clienteId, int enderecoId, String formaDePagamento, String frete, double valorTotal) {
        this.clienteId = clienteId;
        this.enderecoId = enderecoId;
        this.formaDePagamento = formaDePagamento;
        this.frete = frete;
        this.valorTotal = valorTotal;
        this.itens = new ArrayList<>();
        this.data = new Date();
        this.status = "aguardando pagamento";
    }

    public Pedido(int clienteId, int enderecoId, String formaDePagamento, String frete, double valorTotal, Date data, String status) {
        this.clienteId = clienteId;
        this.enderecoId = enderecoId;
        this.formaDePagamento = formaDePagamento;
        this.frete = frete;
        this.valorTotal = valorTotal;
        this.itens = new ArrayList<>();
        this.data = data;
        this.status = "aguardando pagamento";
    }

    // Métodos para manipular o total do pedido
    public void calcularValorTotal() {
        valorTotal = 0;
        for (ItemCarrinho item : itens) {
            valorTotal += item.getLivroPreco() * item.getQuantidade();
        }
    }

    public void adicionarItem(ItemCarrinho item) {
        itens.add(item);
        atualizarValorTotal();
    }

    public void removerItem(ItemCarrinho item) {
        itens.remove(item);
        atualizarValorTotal();
    }

    private void atualizarValorTotal() {
        valorTotal = 0;
        for (ItemCarrinho item : itens) {
            valorTotal += item.getLivroPreco() * item.getQuantidade();
        }
    }

    // Getters e Setters
    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ItemCarrinho> getItens() {
        return itens;
    }

    public void setItens(List<ItemCarrinho> itens) {
        this.itens = itens;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public int getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(int pedidoId) {
        this.pedidoId = pedidoId;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public int getEnderecoId() {
        return enderecoId;
    }

    public void setEnderecoId(int enderecoId) {
        this.enderecoId = enderecoId;
    }

    public String getFormaDePagamento() {
        return formaDePagamento;
    }

    public void setFormaDePagamento(String formaDePagamento) {
        this.formaDePagamento = formaDePagamento;
    }

    public String getFrete() {
        return frete;
    }

    public void setFrete(String frete) {
        this.frete = frete;
    }
}
