<!DOCTYPE html>
<html lang="en">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Listar Usuários</title>
    <link rel="stylesheet" href="css/listarUsuarios.css">
</head>
<body>
<h1>Listar Usuarios</h1>

<form action="/ListarUsuariosServlet" method="GET">
    <label for="nomeUsuario">Nome do Usuário:</label>
    <input type="text" id="nomeUsuario" name="nomeUsuario">
    <button type="submit">Buscar</button>
</form>

<table>
    <tr>
        <th>Nome</th>
        <th>Email</th>
        <th>Status</th>
        <th>Grupo</th>
        <th>Ações</th>
    </tr>
    <c:forEach var="usuario" items ="${usuarios}">
    <tr>
        <td>${usuario.nome}</td>
        <td>${usuario.email()}</td>
        <td>Ativo</td>
        <td>Grupo do Usuário</td>
        <td>
        </td>
    </tr>
    </c:forEach>
</table>
</body>
</html>
