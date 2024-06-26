<%@ page import="br.com.livraria.model.Livro" %>
<%@ page import="br.com.livraria.model.Cliente" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Arrays" %>

<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Loja de Livros</title>
    <link rel="stylesheet" type="text/css" href="css/visualizarProduto.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <style>
        .detalhes-produto {
            position: relative;
            padding-bottom: 60px; /* Ajuste conforme necessário */
        }

        .comprar-btn {
            position: absolute;
            bottom: 0;
            right: 0;
            padding: 10px 20px;
            background-color: #5e282c; /* Cor do cabeçalho */
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .comprar-btn:hover {
            background-color: #402025; /* Tom mais escuro do cabeçalho ao passar o mouse */
        }
    </style>
</head>

<body>
<header>
    <div class="logo">
        <a href="/LivrosCImagensServlet">
            <img src="https://img.freepik.com/vetores-premium/elementos-de-modelo-de-design-de-icone-de-logotipo-de-livro-logotipo-criativo-com-livro-aberto-e-ramo-de-louros_968697-470.jpg?w=740" alt="Logo da Loja">
        </a>
    </div>
    <div class="user-actions">
        <% if(session.getAttribute("cliente") == null) { %>

            <a href="#" onclick="loginWithRedirect()">Login</a>

            <a href="cadastroCliente.html">Cadastre-se</a>
        <% } else { %>
            <p><%= ((Cliente) session.getAttribute("cliente")).getEmail() %></p>
            <a href="alternarCliente.jsp?id=<%= ((Cliente) session.getAttribute("cliente")).getId() %>&nome=<%= ((Cliente) session.getAttribute("cliente")).getNomeCompleto() %>&cpf=<%= ((Cliente) session.getAttribute("cliente")).getCpf() %>&email=<%= ((Cliente) session.getAttribute("cliente")).getEmail() %>&senha=<%= ((Cliente) session.getAttribute("cliente")).getSenha() %>&dataNascimento=<%= ((Cliente) session.getAttribute("cliente")).getDataNascimento() %>&genero=<%= ((Cliente) session.getAttribute("cliente")).getGenero() %>">Editar</a>

            <a href="cadasdroEndereco.jsp">Adicionar Endereço</a>
            <a href="LogoutServlet">Deslogar</a>
        <% } %>
        <div class="cart-icon">
            <i class="fas fa-shopping-cart"></i>
        </div>
    </div>
</header>

<div class="titulo-livro">
    <h1><%= request.getParameter("nome") %></h1>
</div>

<div class="carrossel-container">
    <div class="carrossel">
        <%
        List<String> imagens = Arrays.asList(
            request.getParameter("imagens").split(","));
        for (String imagem : imagens) {
        %>
            <img src="Img/<%= imagem %>" alt="Imagem do Livro">
        <%
        }
        %>
    </div>
    <button class="prev">&#10094;</button>
    <button class="next">&#10095;</button>
</div>

<div class="detalhes-produto">
    <p><strong>Preço:</strong> <strong class="valor">R$ <%= request.getParameter("preco") %></strong></p>
    <p><strong>Avaliação:</strong> <%= request.getParameter("avaliacao") %></p>
    <p><strong>Descrição:</strong> <%= request.getParameter("descricao") %></p>
    <button class="comprar-btn" id="comprar-btn" data-livro-id="<%= request.getParameter("id") %>" data-livro-nome="<%= request.getParameter("nome") %>" data-livro-preco="<%= request.getParameter("preco") %>">Comprar</button>
</div>

<script src="visualizarProduto.js"></script>
<script>
    // Função para adicionar o item ao carrinho
    function adicionarAoCarrinho(livroId, livroNome, livroPreco) {
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "adicionarAoCarrinho?livroId=" + livroId + "&livroNome=" + livroNome + "&livroPreco=" + livroPreco, true);
        xhr.onreadystatechange = function () {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    alert("Livro adicionado ao carrinho com sucesso!");
                } else {
                    alert("Erro ao adicionar livro ao carrinho.");
                }
            }
        };
        xhr.send();
    }

    // Adiciona evento de clique ao botão de comprar
    document.getElementById("comprar-btn").addEventListener("click", function () {
        var livroId = this.getAttribute("data-livro-id");
        var livroNome = this.getAttribute("data-livro-nome");
        var livroPreco = this.getAttribute("data-livro-preco");
        adicionarAoCarrinho(livroId, livroNome, livroPreco);
    });
    function loginWithRedirect() {
        var currentUrl = window.location.href;
        window.location.href = 'loginCliente.jsp?redirectUrl=' + encodeURIComponent(currentUrl);
    }

</script>
</body>

</html>