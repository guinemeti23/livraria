<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="br.com.livraria.model.ItemCarrinho, br.com.livraria.model.Endereco, br.com.livraria.model.Cliente" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Resumo do Pedido</title>
</head>
<body>
<header>
    <span>Resumo do Pedido</span>
</header>
<main>
    <h1>Detalhes do Pedido</h1>
    <form action="CadastroPedido" method="POST">
        <section>
            <h2>Itens no Carrinho</h2>
            <% List<ItemCarrinho> itensCarrinho = (List<ItemCarrinho>) session.getAttribute("carrinho");
            if (itensCarrinho != null) {
                for (ItemCarrinho item : itensCarrinho) { %>
                    <p><%= item.getLivroNome() %> - Quantidade: <%= item.getQuantidade() %>, Preço: R$<%= item.getLivroPreco() %></p>
                <% }
            } %>
        </section>
        <section>
            <h2>Forma de Pagamento: <%= session.getAttribute("formaPagamento") %></h2>
            <h2>Frete: <%= session.getAttribute("freteSelecionado") %></h2>
            <h2>Endereço de Entrega:</h2>
            <% Endereco endereco = (Endereco) session.getAttribute("enderecoSelecionado");
            if (endereco != null) { %>
                <p>CEP: <%= endereco.getCep() %>, Rua: <%= endereco.getLogradouro() %>, Número: <%= endereco.getNumero() %></p>
            <% } %>
        </section>
        <section>
            <h2>Total da Compra: R$<%= session.getAttribute("valorTotal") %></h2>
        </section>
        <button type="submit">Finalizar Pedido</button>
    </form>
</main>
</body>
</html>
