<%@ page import="br.com.livraria.model.Usuario" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tela de login</title>
    <link rel="stylesheet" href="css/inicio.css">
</head>
<body>
<div>
    <h1>Principal</h1>
    <a href="ListarProdutoServlet" class="btn-list">Listar Produtos</a>
    <% if (session.getAttribute("isAdmin") != null && (Boolean) session.getAttribute("isAdmin")) { %>
        <a href="ListarUsuariosServlet" id="listarUsuariosBtn" class="btn-list">Listar Usuários</a>
    <% } %>

    <a href="ListarPedidosEstoque" class="btn-list">Listar Pedidos</a>
</div>
</body>
</html>
