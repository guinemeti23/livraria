<%@ page import="br.com.livraria.model.Usuario" %>
<%@ page import="java.util.List" %>

<%
Usuario usuario = (Usuario) request.getAttribute("usuario");
%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Alterar Usuário</title>
    <link rel="stylesheet" href="css/cadastro.css">
</head>
<body>
<div>
    <h1>Alterar Usuário</h1>
    <form action="AlternaUsuario" method="post">
        <input type="text" name="nome" placeholder="Nome" value="<%= usuario.getNome() %>" required>
        <br><br>
        <input type="text" name="cpf" placeholder="CPF" value="<%= usuario.getCpf() %>" required pattern="\d{3}\.\d{3}\.\d{3}-\d{2}">
        <br><br>
        <input type="text" name="email" placeholder="Email" value="<%= usuario.getEmail() %>" readonly>

        <br><br>
        <input type="password" name="senha" placeholder="Senha" required>
        <br><br>
        <input type="password" name="confirmacaoSenha" placeholder="Confirmar Senha" required>
        <br><br>
        <label>
            <input type="radio" name="grupo" value="estoquista" <% if(usuario.getGrupo().equals("estoquista")) { %> checked <% } %> required> Estoquista
        </label>
        <label>
            <input type="radio" name="grupo" value="administrador" <% if(usuario.getGrupo().equals("administrador")) { %> checked <% } %> required> Administrador
        </label>
        <br><br>
        <span class="error-message" style="color: red;"></span>
        <button type="submit" class="btn-ok">Ok</button>
        <button type="button" class="btn-cancelar">Cancelar</button>
    </form>
</div>
</body>
</html>
