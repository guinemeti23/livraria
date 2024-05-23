<!DOCTYPE html>
<html lang="pt-br">
<head>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cadastro de Usuário</title>
    <link rel="stylesheet" href="css/cadastroCliente.css">
</head>
<body>
<div class="container">
    <h1>Cadastro de Cliente</h1>
    <form action="AlternaCliente" method="post">
        <input type="text" name="nome" placeholder="Nome completo" value="<%= request.getParameter("nome") %>" required>
        <br><br>
        <input type="text" name="cpf" placeholder="CPF" value="<%= request.getParameter("cpf") %>" required pattern="\d{3}\.\d{3}\.\d{3}-\d{2}">
        <br><br>
        <input type="text" name="email" placeholder="Email" value="<%= request.getParameter("email") %>" required>
        <br><br>
        <input type="password" name="senha" placeholder="Senha" required>
        <br><br>
        <input type="password" name="confirmacaoSenha" placeholder="Confirmar Senha" required>
        <br><br>
        <input type="text" name="dataNascimento" placeholder="Data de Nascimento (YYYY-MM-DD)" value="<%= request.getParameter("dataNascimento") %>" required>
        <br><br>
        <select name="genero" required>
            <option value="">Selecione o gênero</option>
            <option value="masculino" <%= request.getParameter("genero").equals("masculino") ? "selected" : "" %>>Masculino</option>
            <option value="feminino" <%= request.getParameter("genero").equals("feminino") ? "selected" : "" %>>Feminino</option>
            <option value="outro" <%= request.getParameter("genero").equals("outro") ? "selected" : "" %>>Outro</option>
        </select>

        <br><br>
        <button type="submit" class="btn-ok">Alternar</button>
        <button type="button" class="btn-cancelar">Cancelar</button>
    </form>
</div>
</body>
</html>



