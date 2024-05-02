<%@ page import="br.com.livraria.model.Livro" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Loja de Livros</title>
    <link rel="stylesheet" type="text/css" href="css/telaInicial.css">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>

<body>
<header>
    <div class="logo">
        <img src="https://img.freepik.com/vetores-premium/elementos-de-modelo-de-design-de-icone-de-logotipo-de-livro-logotipo-criativo-com-livro-aberto-e-ramo-de-louros_968697-470.jpg?w=740" alt="Logo da Loja">
    </div>
    <div class="user-actions">
        <% if(session.getAttribute("Cliente") == null) { %>
            <a href="loginCliente.html">Login</a>
            <a href="cadastroCliente.html">Cadastre-se</a>
        <% } else { %>
            <p><%= session.getAttribute("Cliente") %></p>
            <a href="alternarCliente.jsp">Editar</a>
            <a href="cadasdroEndereco.jsp">Adicionar Endereço</a>
            <a href="LogoutServlet">Deslogar</a>
        <% } %>
        <div class="cart-icon">
            <i class="fas fa-shopping-cart"></i>
        </div>
    </div>
</header>

<div class="livros-container">
    <% for (Livro livro : (List<Livro>)request.getAttribute("livrosImagens")) { %>
        <!-- Livro -->
        <div class="livro">
            <h3><%= livro.getNome() %></h3>
            <img src="Img/<%= livro.getImagemPrincipal() %>" alt="<%= livro.getNome() %>">
            <p>Valor: R$ <%= livro.getPreco() %></p>
            <a href="visualizarProduto.jsp?id=<%= livro.getId() %>&nome=<%= livro.getNome() %>&preco=<%= livro.getPreco() %>&descricao=<%= livro.getDescricao() %>&avaliacao=<%= livro.getAvaliacao() %>&imagemPrincipal=<%= livro.getImagemPrincipal() %>&imagem2=<%= livro.getImagem2() %>&imagem3=<%= livro.getImagem3() %>&imagem4=<%= livro.getImagem4() %>&imagem5=<%= livro.getImagem5() %>">Detalhes</a>


        </div>
    <% } %>
</div>
</body>

</html>
