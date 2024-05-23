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
                                    <label><input type="radio" name="frete" value="15.00" onchange="atualizarFrete(this.value)"> Normal - R$ 15,00</label>
                                    <label><input type="radio" name="frete" value="30.00" onchange="atualizarFrete(this.value)"> Expresso - R$ 30,00</label>
                                    <label><input type="radio" name="frete" value="50.00" onchange="atualizarFrete(this.value)"> Premium - R$ 50,00</label>
                                </div>
            </div>

        </aside>
    </div>
    <div class="addresses">

        <% if(session.getAttribute("cliente") == null) { %>

            <form id="addressForm" action="loginCliente.jsp" method="POST">
                                    <% List<Endereco> enderecos = (List<Endereco>) request.getAttribute("enderecos");
                                    if (enderecos != null && !enderecos.isEmpty()) {
                                        for (Endereco endereco : enderecos) { %>
                                    <div class="address-option">
                                        <input type="radio" id="endereco<%= endereco.getEnderecoId() %>" name="enderecoId" value="<%= endereco.getEnderecoId() %>" required>
                                        <label for="endereco<%= endereco.getEnderecoId() %>">
                                            CEP: <%= endereco.getCep() %>, Rua: <%= endereco.getLogradouro() %>, Número: <%= endereco.getNumero() %>
                                        </label>
                                    </div>
                                    <% }
                                    } %>
                                    <button type="submit" id="checkout">Finalizar Compra</button>
                                </form>

        <% } else { %>
            <form id="addressForm" action="pagamento.jsp" method="POST">
                        <% List<Endereco> enderecos = (List<Endereco>) request.getAttribute("enderecos");
                        if (enderecos != null && !enderecos.isEmpty()) {
                            for (Endereco endereco : enderecos) { %>
                        <div class="address-option">
                            <input type="radio" id="endereco<%= endereco.getEnderecoId() %>" name="enderecoId" value="<%= endereco.getEnderecoId() %>" required>
                            <label for="endereco<%= endereco.getEnderecoId() %>">
                                CEP: <%= endereco.getCep() %>, Rua: <%= endereco.getLogradouro() %>, Número: <%= endereco.getNumero() %>
                            </label>
                        </div>
                        <% }
                        } %>
                        <button type="submit" id="checkout">Finalizar Compra</button>
                    </form>
        <% } %>


    </div>
</main>

<script>
    document.addEventListener("DOMContentLoaded", function () {
      const cartBody = document.getElementById("cart-body");
      const summary = document.getElementById("summary");
      const total = document.getElementById("total");

      // Função para buscar o carrinho do servidor
      function fetchCart() {
        fetch('/carregarItensCarrinho')
          .then(response => {
            if (!response.ok) {
                throw new Error('Falha ao buscar dados');
            }
            return response.json();
          })
          .then(data => {
            updateCart(data);  // Atualiza o carrinho com os itens recebidos
          })
          .catch(error => console.error('Erro ao buscar dados do carrinho:', error));
      }

      // Função para atualizar o carrinho com os itens
      function updateCart(cartItems) {
        cartBody.innerHTML = "";
        let subtotal = 0;

        cartItems.forEach((item, index) => {
          const totalPrice = item.livroPreco * item.quantidade;
          subtotal += totalPrice;

          const row = document.createElement("tr");
          row.innerHTML = `
            <td>
              <div class="product">
                <div class="info">
                  <div class="name">${item.livroNome}</div>
                </div>
              </div>
            </td>
            <td>R$ ${item.livroPreco.toFixed(2)}</td>
            <td>
              <div class="qty">
                <button onclick="alterarQuantidade(${item.livroId}, -1)"><i class="bx bx-minus"></i></button>
                <span>${item.quantidade}</span>
                <button onclick="alterarQuantidade(${item.livroId}, 1)"><i class="bx bx-plus"></i></button>
              </div>
            </td>
            <td>R$ ${totalPrice.toFixed(2)}</td>
            <td>
              <button onclick="removerItem(${item.livroId})" class="remove"><i class="bx bx-x"></i></button>
            </td>
          `;
          cartBody.appendChild(row);
        });

        const shipping = "Gratuito";
        const totalAmount = subtotal;

        summary.innerHTML = `
          <div><span>Sub-total</span><span>R$ ${subtotal.toFixed(2)}</span></div>
          <div><span>Frete</span><span>${shipping}</span></div>
        `;
        total.textContent = `Total: R$ ${totalAmount.toFixed(2)}`;
      }

      // Funções para modificar a quantidade do item no carrinho
window.alterarQuantidade = function (id, delta) {
    fetch(`/updateCart?id=${id}&action=check`, { method: 'POST' })
      .then(response => response.json())
      .then(data => {
        if (data.newQuantity <= 0) {
            removerItem(id);
        } else {
            let action = delta === 1 ? "increase" : "decrease";
            fetch(`/updateCart?id=${id}&action=${action}`, { method: 'POST' })
            .then(() => {
                fetchCart();  // Recarrega o carrinho após a alteração
            });
        }
      });
};


// Função para remover um item do carrinho
window.removerItem = function (id) {
    fetch(`/updateCart?id=${id}&action=remove`, { method: 'POST' })
      .then(() => {
        fetchCart();
      });
};

      // Carregar o carrinho ao carregar a página
      fetchCart();
    });
    document.getElementById("checkout").addEventListener("click", function() {
    window.location.href = "pagamento.jsp"; // Redireciona para a página de pagamento
});

window.selecionarEndereco = function(endereco) {
    alert("Endereço selecionado: " + endereco);
};

window.atualizarFrete = function(valorFrete) {
    const subtotalElement = document.getElementById('summary').querySelector('div span:last-child');
    if (!subtotalElement) {
        console.error('Elemento do subtotal não encontrado');
        return;
    }
    const subtotalText = subtotalElement.textContent.replace('R$', '').trim();
    const subtotal = parseFloat(subtotalText);
    if (isNaN(subtotal)) {
        console.error('Subtotal inválido:', subtotalText);
        return;
    }
    const valorFreteNumber = parseFloat(valorFrete);
    if (isNaN(valorFreteNumber)) {
        console.error('Custo de frete inválido:', valorFrete);
        return;
    }
    const total = subtotal + valorFreteNumber;
    document.getElementById('total').textContent = `Total: R$ ${total.toFixed(2)}`;
};
document.getElementById('addressForm').onsubmit = function(event) {
    const selectedAddress = document.querySelector('input[name="enderecoId"]:checked');
    if (!selectedAddress) {
        event.preventDefault();  // Impede o envio do formulário se nenhum endereço estiver selecionado
        alert("Por favor, selecione um endereço para a entrega.");
    }
};


</script>
</body>
</html>
