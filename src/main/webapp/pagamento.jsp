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
            <form id="payment-form" action="/CadastroPedido" method="POST">
                <input type="hidden" name="enderecoId" value="<%= request.getParameter("enderecoId") %>" />
                <div class="payment-options">
                    <label>
                        <input type="radio" name="payment" value="pix">
                        PIX
                    </label>
                    <label>
                        <input type="radio" name="payment" value="boleto">
                        Boleto Bancário
                    </label>
                    <label>
                        <input type="radio" name="payment" value="cartao">
                        Cartão de Crédito/Débito
                    </label>
                </div>
                <input type="hidden" name="formaPagamento" id="formaPagamento">
                <button type="button" id="select-payment">Selecionar Forma de Pagamento</button>
                <button type="submit" id="confirm-payment" style="display:none;">Confirmar Pagamento</button>
            </form>
            <div class="card-fields" style="display:none;">
                <!-- Campos de cartão omitidos para brevidade -->
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
        const confirmPaymentBtn = document.getElementById('confirm-payment');
        const hiddenInput = document.getElementById('formaPagamento');

        paymentOptions.addEventListener('change', function(event) {
            const selectedPayment = event.target.value;
            hiddenInput.value = selectedPayment;
            confirmPaymentBtn.style.display = 'block';

            if (selectedPayment === 'cartao') {
                cardFields.style.display = 'block';
                qrCodeDiv.style.display = 'none';
            } else {
                cardFields.style.display = 'none';
                qrCodeDiv.style.display = 'block';
                displayQRCode(selectedPayment);
            }
        });

        function displayQRCode(paymentType) {
            qrCodeDiv.innerHTML = ''; // Clear previous QR code
            let data = paymentType === 'pix' ? 'PIX123456789' : 'BOLETO987654321'; // Example data
            qrCodeDiv.innerHTML = `<img src="https://api.qrserver.com/v1/create-qr-code/?data=${data}&size=200x200" alt="QR Code for ${paymentType}">`;
        }
    });
</script>


</body>
</html>
