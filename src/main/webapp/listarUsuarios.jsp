<%@ page import="br.com.livraria.model.Usuario" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Listar Usuários</title>
    <link rel="stylesheet" href="css/listarUsuarios.css">
</head>

<body>
    <div>
            <% if (session != null && session.getAttribute("loggedUser") != null) { %>
                <p>Usuário logado: <%= session.getAttribute("loggedUser") %></p>
                <a href="/LogoutUsuarioServlet">Logout</a>
            <% } else { %>
                <p>Usuário não está logado</p>
            <% } %>
        </div>
    <h1>Listar Usuários</h1>

    <form action="/ListarUsuariosServlet" method="GET">
        <label for="nomeUsuario">Nome do Usuário:</label>
        <input type="text" id="nomeUsuario" name="nomeUsuario">
        <button type="submit">Buscar</button>
    </form>

    <table>
        <thead>
            <tr>
                <th>Nome</th>
                <th>Email</th>
                <th>Status</th>
                <th>Grupo</th>
                <th>Ações</th>
            </tr>
        </thead>
        <tbody>
            <% for (Usuario usuario : (List<Usuario>) request.getAttribute("usuarios")) { %>
                <tr>
                    <td><%= usuario.getNome() %></td>
                    <td><%= usuario.getEmail() %></td>
                    <td><%= usuario.isAtivo() ? "Ativo" : "Inativo" %></td>
                    <td><%= usuario.getGrupo() %></td>
                    <td>
                        <form action="/AlterarStatusUsuario" method="POST">
                            <input type="hidden" name="userId" value="<%= usuario.getId() %>">
                            <input type="hidden" name="status" value="<%= !usuario.isAtivo() %>">
                            <button type="submit"><%= usuario.isAtivo() ? "Desativar" : "Ativar" %></button>
                        </form>


                        <form action="/PreencheUsuarioServlet" method="GET">
                            <input type="hidden" name="userId" value="<%= usuario.getId() %>">
                            <button type="submit">Alterar</button>
                        </form>
                    </td>
                </tr>
            <% } %>
        </tbody>
    </table>
</body>

</html>
