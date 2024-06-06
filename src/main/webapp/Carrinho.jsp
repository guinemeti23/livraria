<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="br.com.livraria.model.Endereco" %>
<%@ page import="java.util.List" %>
<%@ page import="br.com.livraria.model.Cliente" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Carrinho de Compras</title>
    <link rel="stylesheet" href="css/carrinho.css" />
    <link href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css" rel="stylesheet" />
</head>
<body>
<header>
    <span>Carrinho de compras</span>
</header>
<main>
    <div class="page-title">Seu Carrinho</div>
    <div class="content">
        <section>
            <table id="cart-table">
                <thead>
                <tr>
                    <th>Produto</th>
                    <th>Preço</th>
                    <th>Quantidade</th>
                    <th>Total</th>
                    <th></th>
                </tr>
                </thead>
                <tbody id="cart-body">
                <!-- Itens do carrinho serão inseridos aqui pelo JavaScript -->
                </tbody>
            </table>
        </section>
        <aside>
            <div class="box">
                <header>Resumo da compra</header>
                <div class="info" id="summary">
                    <!-- Resumo da compra será inserido aqui pelo JavaScript -->
                </div>
                <footer id="total">
                    <!-- Total da compra será inserido aqui pelo JavaScript -->
                </footer>
                <div class="shipping-options">
                    <label><input type="radio" name="frete" value="15.00" data-label="Normal - R$ 15,00" onchange="atualizarFrete(this.value, this.getAttribute('data-label'))"> Normal - R$ 15,00</label>
                    <label><input type="radio" name="frete" value="30.00" data-label="Expresso - R$ 30,00" onchange="atualizarFrete(this.value, this.getAttribute('data-label'))"> Expresso - R$ 30,00</label>
                    <label><input type="radio" name="frete" value="50.00" data-label="Premium - R$ 50,00" onchange="atualizarFrete(this.value, this.getAttribute('data-label'))"> Premium - R$ 50,00</label>
                </div>

            </div>
            <button id="checkout">Finalizar Compra</button>
        </aside>
    </div>
</main>

<script>
document.addEventListener("DOMContentLoaded", function () {
    const cartBody = document.getElementById("cart-body");
    const summary = document.getElementById("summary");
    const total = document.getElementById("total");

    // Carregar dados do carrinho do servidor
    function fetchCart() {
        fetch('/carregarItensCarrinho')
            .then(response => response.json())
            .then(data => updateCart(data))
            .catch(error => console.error('Erro ao buscar dados do carrinho:', error));
    }

    // Atualizar o carrinho com os itens recebidos
    function updateCart(cartItems) {
        cartBody.innerHTML = "";
        let subtotal = 0;

        cartItems.forEach(item => {
            const totalPrice = item.livroPreco * item.quantidade;
            subtotal += totalPrice;

            const row = `<tr>
                <td><div class="product"><div class="info"><div class="name">${item.livroNome}</div></div></div></td>
                <td>R$ ${item.livroPreco.toFixed(2)}</td>
                <td>
                    <div class="qty">
                        <button onclick="alterarQuantidade(${item.livroId}, -1)"><i class="bx bx-minus"></i></button>
                        <span>${item.quantidade}</span>
                        <button onclick="alterarQuantidade(${item.livroId}, 1)"><i class="bx bx-plus"></i></button>
                    </div>
                </td>
                <td>R$ ${totalPrice.toFixed(2)}</td>
                <td><button onclick="removerItem(${item.livroId})" class="remove"><i class="bx bx-x"></i></button></td>
            </tr>`;
            cartBody.insertAdjacentHTML('beforeend', row);
        });

        summary.innerHTML = `<div><span>Sub-total</span><span>R$ ${subtotal.toFixed(2)}</span></div>`;
        total.textContent = `Total: R$ ${subtotal.toFixed(2)}`;
    }

    // Atualizar frete e recalcular o total
    window.atualizarFrete = function(valorFrete, labelFrete) {
        const subtotalText = summary.querySelector('div span:last-child').textContent.replace('R$', '').trim();
        const subtotal = parseFloat(subtotalText);
        const valorFreteNumber = parseFloat(valorFrete);
        const totalComFrete = subtotal + valorFreteNumber;
        total.textContent = `Total: R$ ${totalComFrete.toFixed(2)}`;
        // Armazenar o rótulo do frete para uso posterior
        document.getElementById("checkout").setAttribute("data-frete-label", labelFrete);
    };

    document.getElementById("checkout").addEventListener("click", function() {
        const freteSelecionado = this.getAttribute("data-frete-label");  // Pega o rótulo completo do frete
        const valorTotalText = total.textContent.replace('Total: R$', '').trim();
        fetch('/FinalizarCompra', {
            method: 'POST',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            body: `frete=${encodeURIComponent(freteSelecionado)}&valorTotal=${encodeURIComponent(valorTotalText)}`
        }).then(response => {
            if (response.ok) window.location.href = response.url;
            else throw new Error('Falha ao processar pedido');
        }).catch(error => console.error('Erro ao finalizar a compra:', error));
    });


    fetchCart();  // Carregar o carrinho ao carregar a página
});
</script>
</body>
</html>
