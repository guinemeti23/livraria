<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tela de login</title>
    <link rel="stylesheet" href="css/inicio.css">

    <style>
        .overlay {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.7); /* Menos transparente */
            align-items: center;
            justify-content: center;
        }

        .message-box {
            background-color: #000; /* Fundo preto */
            padding: 20px;
            border-radius: 10px; /* Menor raio de borda */
            color: #fff;
            text-align: center;
            max-width: 300px; /* Largura máxima da mensagem */
            position: relative; /* Para posicionar o botão de fechar em relação a este elemento */
            margin: auto; /* Centraliza horizontalmente */
        }

        .close-btn {
            position: absolute;
            top: 5px;
            right: 5px;
            cursor: pointer;
            color: #fff;
            font-size: 24px; /* Tamanho maior */
        }

        .message {
            font-size: 18px; /* Tamanho maior */
            margin-bottom: 15px; /* Espaçamento inferior */
        }
    </style>
</head>
<body style="font-family: Arial, Helvetica, sans-serif; background-image: linear-gradient(45deg, rgb(0, 0, 0), rgb(98, 66, 241));">
<div>
    <h1>Principal</h1>
    <a href="ListaProduto.jsp" class="btn-list">Listar Produtos</a>
    <a href="#" id="listarUsuariosBtn" class="btn-list">Listar Usuários</a>
    <a href="ListarPedidosEstoque" class="btn-list">Listar Pedidos</a>
</div>

<div class="overlay" id="overlay">
    <div class="message-box">
        <span class="close-btn" id="closeBtn">&times;</span>
        <p class="message">Somente administradores podem acessar esta função.</p>
    </div>
</div>

<script>
    document.getElementById('listarUsuariosBtn').addEventListener('click', function(event) {
        event.preventDefault(); // Previne a ação padrão do link
        document.getElementById('overlay').style.display = 'flex'; // Mostra a mensagem
    });

    document.getElementById('closeBtn').addEventListener('click', function() {
        document.getElementById('overlay').style.display = 'none'; // Fecha a mensagem ao clicar no botão 'x'
    });
</script>
</body>
</html>
