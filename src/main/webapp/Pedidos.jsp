<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="br.com.livraria.model.Pedido" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Lista de Pedidos</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<h1>Lista de Pedidos</h1>
<div class="pedidos">
    <% List<Pedido> pedidos = (List<Pedido>) request.getAttribute("pedidos");
       if (pedidos != null && !pedidos.isEmpty()) {
           for (Pedido pedido : pedidos) { %>
               <div class="pedido">
                   <h3>Pedido ID: <%= pedido.getPedidoId() %></h3>
                   <p>Valor Total: R$ <%= pedido.getValorTotal() %></p>
                   <p>Forma de Pagamento: <%= pedido.getFormaDePagamento() %></p>
                   <p>Frete: <%= pedido.getFrete() %></p>

                   <a href="DetalhesPedido?pedidoId=<%= pedido.getPedidoId() %>">Detalhes</a>
               </div>
    <%     }
       } else { %>
           <p>Nenhum pedido encontrado.</p>
    <% } %>
</div>
</body>
</html>
