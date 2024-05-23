<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="br.com.livraria.model.Pedido" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Lista de Pedidos</title>
    <link rel="stylesheet" href="css/style.css"> <!-- Suponha que você tenha um arquivo CSS -->
</head>
<body>
    <h1>Lista de Pedidos</h1>
    <% List<Pedido> pedidos = (List<Pedido>) request.getAttribute("pedidos");
       for (Pedido pedido : pedidos) { %>
        <div class="pedido">
            <h3>Pedido ID: <%= pedido.getPedidoId() %></h3>
            <p>Data do Pedido: <%= new java.text.SimpleDateFormat("dd/MM/yyyy").format(pedido.getDataPedido()) %></p>
            <p>Valor Total: R$ <%= pedido.getValorTotal() %></p>
            <p>Status: <%= pedido.getStatus() %></p>
            <p>Forma de Pagamento: <%= pedido.getFormaDePagamento() %></p>
            <!-- Adicionar mais campos conforme necessário -->

            <a href="detalhesPedido.jsp?pedidoId=<%= pedido.getPedidoId() %>">Detalhes</a>

            <!-- Seleção de status com envio automático -->
            <form action="AlterarPedidoServlet" method="post">
                <input type="hidden" name="pedidoId" value="<%= pedido.getPedidoId() %>" />
                <select name="status" onchange="this.form.submit()">
                    <option ${pedido.status.equals("Aguardando pagamento") ? "selected" : ""} value="Aguardando pagamento">Aguardando pagamento</option>
                    <option ${pedido.status.equals("Pagamento rejeitado") ? "selected" : ""} value="Pagamento rejeitado">Pagamento rejeitado</option>
                    <option ${pedido.status.equals("Aguardando retirada") ? "selected" : ""} value="Aguardando retirada">Aguardando retirada</option>
                    <option ${pedido.status.equals("Em trânsito") ? "selected" : ""} value="Em trânsito">Em trânsito</option>
                    <option ${pedido.status.equals("Entregue") ? "selected" : ""} value="Entregue">Entregue</option>
                </select>
            </form>
        </div>
    <% } %>
</body>
</html>
