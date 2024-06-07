<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, java.text.SimpleDateFormat" %>
<%@ page import="br.com.livraria.model.Pedido, br.com.livraria.model.ItemCarrinho" %>
<%@ page import="br.com.livraria.model.Endereco" %>


<!DOCTYPE html>
<html>
<head>
    <title>Detalhes do Pedido</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <h1>Detalhes do Pedido</h1>
    <div>
        <p>Pedido : <%= ((Pedido)request.getAttribute("pedido")).getPedidoId() %></p>

        <p>Forma de Pagamento: <%= ((Pedido)request.getAttribute("pedido")).getFormaDePagamento() %></p>
        <p>Frete: <%= ((Pedido)request.getAttribute("pedido")).getFrete() %></p>
        <p>Valor Total: <%= ((Pedido)request.getAttribute("pedido")).getValorTotal() %></p>
        <p>Status: <%= ((Pedido)request.getAttribute("pedido")).getStatus() %></p>
        <p>Data: <%= new SimpleDateFormat("dd/MM/yyyy").format(((Pedido)request.getAttribute("pedido")).getData()) %></p>
    </div>

    <h3>Informações do Endereço de Entrega</h3>
    <div>

        <p>Logradouro: <%= ((Endereco)request.getAttribute("endereco")).getLogradouro() %></p>
        <p>Número: <%= ((Endereco)request.getAttribute("endereco")).getNumero() %></p>
        <p>Complemento: <%= ((Endereco)request.getAttribute("endereco")).getComplemento() %></p>
        <p>Bairro: <%= ((Endereco)request.getAttribute("endereco")).getBairro() %></p>
        <p>Cidade: <<%= ((Endereco)request.getAttribute("endereco")).getLocalidade() %></p>
        <p>UF: <%= ((Endereco)request.getAttribute("endereco")).getUf() %></p>
        <p>CEP: <%= ((Endereco)request.getAttribute("endereco")).getCep() %></p>
    </div>

    <h2>Itens do Pedido</h2>
    <table>
        <thead>
            <tr>
                <th>ID do Produto</th>
                <th>Nome do Produto</th>
                <th>Preço Unitário</th>
                <th>Quantidade</th>
                <th>Subtotal</th>
            </tr>
        </thead>
        <tbody>
            <%
            List<ItemCarrinho> itensPedido = (List<ItemCarrinho>) request.getAttribute("itensPedido");
            for (ItemCarrinho item : itensPedido) {
            %>
                <tr>
                    <td><%= item.getLivroId() %></td>
                    <td><%= item.getLivroNome() %></td>
                    <td><%= item.getLivroPreco() %></td>
                    <td><%= item.getQuantidade() %></td>
                    <td><%= item.getLivroPreco() * item.getQuantidade() %></td>
                </tr>
            <% } %>
        </tbody>
    </table>
    <a href="ListarPedidos">Voltar à lista de pedidos</a>
</body>
</html>
