<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="br.com.livraria.model.ItemCarrinho" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Resumo do Pedido</title>
    <style>
        /* Estilos omitidos para brevidade */
    </style>
</head>
<body>
<header>
    <span>Resumo do Pedido</span>
</header>
<main>
    <h1>Detalhes do Pedido</h1>
    <section>
        <h2>Itens no Carrinho</h2>
        <% List<ItemCarrinho> itensCarrinho = (List<ItemCarrinho>) session.getAttribute("carrinho");
        if (itensCarrinho != null) {
            for (ItemCarrinho item : itensCarrinho) { %>
                <p><%= item.getNomeProduto() %> - Quantidade: <%= item.getQuantidade() %>, Preço: <%= item.getPreco() %></p>
            <% }
        } %>
    </section>
    <section>
        <h2>Forma de Pagamento: <%= request.getParameter("payment") %></h2>
        <h2>Frete: R$<%= request.getParameter("frete") %></h2>
    </section>
    <section>
        <% double total = 0;
        if (itensCarrinho != null) {
            for (ItemCarrinho item : itensCarrinho) {
                total += item.getPreco() * item.getQuantidade();
            }
        }
        total += Double.parseDouble(request.getParameter("frete"));
        %>
        <h2>Total da Compra: R$<%= total %></h2>
    </section>
</main>
</body>
</html>
