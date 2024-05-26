<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="br.com.livraria.model.Pedido" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Lista de Pedidos</title>
    <link rel="stylesheet" href="css/style.css"> <!-- Suponha que você tenha um arquivo CSS -->
    <style>
        table {
            width: 100%;
            border-collapse: collapse; /* Colapsa as bordas para que elas não se dupliquem */
        }
        th, td {
            border: 1px solid black; /* Adiciona uma borda sólida preta */
            padding: 8px; /* Adiciona um pouco de espaço interno em torno do texto */
            text-align: left; /* Alinha o texto à esquerda nas células */
        }
        th {
            background-color: #f2f2f2; /* Um fundo cinza claro para os cabeçalhos */
        }
    </style>
</head>
<body>
    <h1>Lista de Pedidos</h1>
    <table>
        <thead>
            <tr>
                <th>ID do Pedido</th>
                <th>Data do Pedido</th>
                <th>Valor Total</th>
                <th>Status</th>
                <th>Forma de Pagamento</th>
                <th>Detalhes</th>
                <th>Alterar Status</th>
            </tr>
        </thead>
        <tbody>
            <% List<Pedido> pedidos = (List<Pedido>) request.getAttribute("pedidos");
               for (Pedido pedido : pedidos) { %>
                <tr>
                    <td><%= pedido.getPedidoId() %></td>
                    <td><%= new java.text.SimpleDateFormat("dd/MM/yyyy").format(pedido.getData()) %></td>
                    <td>R$ <%= pedido.getValorTotal() %></td>
                    <td><%= pedido.getStatus() %></td>
                    <td><%= pedido.getFormaDePagamento() %></td>
                    <td><a href="DetalhesPedido?pedidoId=<%= pedido.getPedidoId() %>">Detalhes</a></td>

                    <td>
                       <form action="AlterarPedido" method="post">
                           <input type="hidden" name="pedidoId" value="<%= pedido.getPedidoId() %>" />
                           <select name="status" onchange="this.form.submit()">
                               <option <%= pedido.getStatus().equals("Aguardando pagamento") ? "selected" : "" %> value="Aguardando pagamento">Aguardando pagamento</option>
                               <option <%= pedido.getStatus().equals("Pagamento rejeitado") ? "selected" : "" %> value="Pagamento rejeitado">Pagamento rejeitado</option>
                               <option <%= pedido.getStatus().equals("Aguardando retirada") ? "selected" : "" %> value="Aguardando retirada">Aguardando retirada</option>
                               <option <%= pedido.getStatus().equals("Em transito") ? "selected" : "" %> value="Em transito">Em trânsito</option>
                               <option <%= pedido.getStatus().equals("Entregue") ? "selected" : "" %> value="Entregue">Entregue</option>
                           </select>
                       </form>

                    </td>
                </tr>
            <% } %>
        </tbody>
    </table>
</body>
</html>
