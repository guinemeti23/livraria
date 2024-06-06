<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pagamento</title>
    <style>
        * {
          margin: 0;
          padding: 0;
        }

        body {
          background: #f6f6f6;
          font-family: 'Lato', sans-serif;
        }

        header {
          background: #111;
          color: white;
          font-size: 24px;
          padding: 20px 0;
          display: flex;
          justify-content: center;
        }

        main {
          background: #fff;
          padding: 0 30px 30px;
        }

        .page-title {
          font-size: 40px;
          padding: 50px 0;
          text-align: center;
        }

        .content {
          display: flex;
        }

        section {
          flex: 1;
        }

        .payment-options {
          margin-bottom: 20px;
        }

        .payment-options label {
          display: block;
          margin-bottom: 10px;
        }

        .payment-options input[type="radio"] {
          margin-right: 10px;
        }

        .card-fields {
          display: none;
          margin-bottom: 20px;
        }

        .card-fields label {
          display: block;
          margin-bottom: 10px;
        }

        .card-fields input[type="text"] {
          width: 100%;
          padding: 10px;
          margin-top: 5px;
          box-sizing: border-box;
        }

        .generated-code {
          margin-top: 10px;
        }

        .qr-code {
          margin-top: 20px;
          text-align: center;
        }

        button {
          cursor: pointer;
          font-family: 'Lato', sans-serif;
          border: 0;
          padding: 15px 0;
          color: white;
          background: #28a745;
          display: block;
          width: 100%;
          text-transform: uppercase;
          letter-spacing: 1px;
          font-size: 16px;
        }

        button:hover {
          background: #3bc55b;
        }
        .hidden {
    display: none;
}
    </style>
</head>
<body>
<header>
    <span>Opções de Pagamento</span>
</header>
<main>
    <div class="page-title">Selecione uma opção de pagamento</div>
    <div class="content">
        <section>
            <form id="payment-form" action="ProcessarPagamento" method="POST">
                <input type="hidden" name="enderecoId" value="<%= request.getParameter("enderecoId") %>" />
                <input type="hidden" name="frete" id="inputFrete" /> <!-- Campo para armazenar o valor de frete -->
                <input type="hidden" name="formaPagamento" id="formaPagamento" /> <!-- Campo para armazenar a forma de pagamento -->
                <div class="payment-options">
                    <label><input type="radio" name="payment" value="pix">PIX</label>
                    <label><input type="radio" name="payment" value="boleto">Boleto</label>
                    <label><input type="radio" name="payment" value="cartao">Cartão de Crédito/Débito</label>
                </div>
                <button type="submit" id="confirm-payment">Confirmar Pagamento</button>
            </form>

            <div class="card-fields" style="display:none;">
                <label for="card-number">Número do Cartão:</label>
                <input type="text" id="card-number" placeholder="1234 5678 9012 3456">
                <label for="card-name">Nome do Titular:</label>
                <input type="text" id="card-name" placeholder="Nome Completo">
                <label for="card-expiry">Data de Validade:</label>
                <input type="text" id="card-expiry" placeholder="MM/YY">
                <label for="card-cvv">CVV:</label>
                <input type="text" id="card-cvv" placeholder="123">
            </div>
            <div class="generated-code" id="generated-code"></div>
            <div class="qr-code" id="qr-code"></div>
        </section>
    </div>
</main>



<script>
document.addEventListener("DOMContentLoaded", function () {
    const paymentOptions = document.querySelector('.payment-options');
    const cardFields = document.querySelector('.card-fields');
    const qrCodeDiv = document.getElementById('qr-code');
    const hiddenInputFormaPagamento = document.getElementById('formaPagamento');

    function displayQRCode(paymentType) {
        qrCodeDiv.innerHTML = ''; // Limpa o QR code anterior
        let data = paymentType === 'pix' ? 'PIX123456789' : 'BOLETO987654321'; // Dados de exemplo
        qrCodeDiv.innerHTML = `<img src="https://api.qrserver.com/v1/create-qr-code/?data=${data}&size=200x200" alt="QR Code para ${paymentType}">`;
    }

    paymentOptions.addEventListener('change', function(event) {
        if (event.target.type === 'radio') {
            const selectedPayment = event.target.value;
            hiddenInputFormaPagamento.value = selectedPayment; // Atualiza o campo oculto com o valor selecionado

            cardFields.style.display = 'none';
            qrCodeDiv.style.display = 'none';

            switch (selectedPayment) {
                case 'cartao':
                    cardFields.style.display = 'block';
                    break;
                case 'pix':
                case 'boleto':
                    qrCodeDiv.style.display = 'block';
                    displayQRCode(selectedPayment);
                    break;
            }
        }
    });
});
</script>




</body>
</html>
