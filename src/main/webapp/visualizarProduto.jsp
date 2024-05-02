
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
        <img src="https://img.freepik.com/vetores-premium/elementos-de-modelo-de-design-de-icone-de-logotipo-de-livro-logotipo-criativo-com-livro-aberto-e-ramo-de-louros_968697-470.jpg?w=740" alt="Logo da Loja">
    </div>
    <div class="user-actions">
        <p>Login/Cadastre-se</p>

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
        <img src="Img/<%= request.getParameter("imagemPrincipal") %>" alt="Imagem 1">
        <img src="Img/<%= request.getParameter("imagem2") %>" alt="Imagem 2">
        <img src="Img/<%= request.getParameter("imagem3") %>" alt="Imagem 3">
        <img src="Img/<%= request.getParameter("imagem4") %>" alt="Imagem 4">
        <img src="Img/<%= request.getParameter("imagem5") %>" alt="Imagem 5">
    </div>
    <button class="prev">&#10094;</button>
    <button class="next">&#10095;</button>
</div>

<div class="detalhes-produto">
    <p><strong>Preço:</strong> <strong class="valor">R$ <%= request.getParameter("preco") %></strong></p>
    <p><strong>Avaliação:</strong><%= request.getParameter("avaliacao") %></strong></p>
    <p><strong>Descrição detalhada:</strong> <%= request.getParameter("descricao") %></p>
    <button class="comprar-btn">Comprar</button>
</div>

<script src="visualizarProduto.js"></script>

</body>

</html>