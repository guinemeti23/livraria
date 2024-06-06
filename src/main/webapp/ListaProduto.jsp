<%@ page import="br.com.livraria.model.Livro" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Listar Produtos</title>
    <link rel="stylesheet" href="css/listarProdutos.css">
</head>

<body>
    <h1>Listar Produtos</h1>

    <form action="/ListarProdutoServlet" method="GET">
        <label for="nomeProduto">Nome do Produto:</label>
        <input type="text" id="nomeProduto" name="nomeProduto">
        <button type="submit">Buscar</button>
    </form>

    <table>
        <thead>
            <tr>
                <th>Código</th>
                <th>Nome</th>
                <th>Quantidade</th>
                <th>Valor</th>
                <th>Ações</th>
            </tr>
        </thead>
        <tbody>
            <% for (Livro livro : (List<Livro>) request.getAttribute("livros")) { %>
                <tr>
                    <td><%= livro.getId() %></td>
                    <td><%= livro.getNome() %></td>
                    <td><%= livro.getQuantidade() %></td>
                    <td>R$ <%= livro.getPreco() %></td>
                    <td>
                        <a href="visualizarProduto.jsp?id=<%= livro.getId() %>&nome=<%= livro.getNome() %>&preco=<%= livro.getPreco() %>&descricao=<%= livro.getDescricao() %>&avaliacao=<%= livro.getAvaliacao() %>&imagens=<%= String.join(",", livro.getImagens()) %>">Visualizar</a>
                        <form action="/EditarProdutoServlet" method="GET">
                            <input type="hidden" name="productId" value="<%= livro.getId() %>">
                            <button type="submit">Editar</button>
                        </form>
                    </td>
                </tr>
            <% } %>

        </tbody>
    </table>
</body>

</html>

