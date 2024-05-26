<%@ page import="br.com.livraria.model.Livro" %>
<%@ page import="br.com.livraria.model.Cliente" %>
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
            <a href="alternarCliente.jsp?id=<%= ((Cliente) session.getAttribute("cliente")).getId() %>&nome=<%= ((Cliente) session.getAttribute("cliente")).getNomeCompleto() %>&cpf=<%= ((Cliente) session.getAttribute("cliente")).getCpf() %>&email=<%= ((Cliente) session.getAttribute("cliente")).getEmail() %>&senha=<%= ((Cliente) session.getAttribute("cliente")).getSenha() %>&dataNascimento=<%= ((Cliente) session.getAttribute("cliente")).getDataNascimento() %>&genero=<%= ((Cliente) session.getAttribute("cliente")).getGenero() %>">Editar</a>/
            <a href="cadasdroEndereco.jsp">Adicionar Endereço</a>/
            <a href="LogoutServlet">Deslogar</a>/
            <a href="ListarPedidos">meus pedidos</a>
        <% } %>
        <div class="cart-icon">
            <a href="ListarEnderecoServlet">
                <i class="fas fa-shopping-cart"></i>
            </a>
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

            <button class="comprar-btn" data-livro-id="<%= livro.getId() %>" data-livro-nome="<%= livro.getNome() %>" data-livro-preco="<%= livro.getPreco() %>">Comprar</button>
        </div>
    <% } %>

</div>


<script>
    function adicionarAoCarrinho(livroId, livroNome, livroPreco) {
        var livro = {
            livroId: livroId,
            livroNome: livroNome,
            livroPreco: livroPreco
        };

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

    var botoesComprar = document.querySelectorAll(".comprar-btn");
    botoesComprar.forEach(function (botao) {
        botao.addEventListener("click", function (event) {
            var livroId = event.target.getAttribute("data-livro-id");
            var livroNome = event.target.getAttribute("data-livro-nome");
            var livroPreco = event.target.getAttribute("data-livro-preco");
            adicionarAoCarrinho(livroId, livroNome, livroPreco);
        });
    });
    function loginWithRedirect() {
            var currentUrl = window.location.href;
            window.location.href = 'loginCliente.jsp?redirectUrl=' + encodeURIComponent(currentUrl);
        }


</script>

</body>

</html>
