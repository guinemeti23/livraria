<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="br.com.livraria.model.Endereco" %>
<%@ page import="java.util.List" %>
<%@ page import="br.com.livraria.model.Cliente" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Selecionar Endereço</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        .address-option {
            margin: 10px 0;
        }
        label {
            display: block;
            margin: 5px;
        }
        button {
            padding: 10px;
            background-color: #4CAF50;
            color: white;
            border: none;
            cursor: pointer;
            margin-top: 20px;
        }
        button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>

    <a href="cadasdroEndereco.jsp"><button type="button">Cadastrar Novo Endereco</button></a>
    <form id="addressForm" action="ProcessarEndereco" method="POST">
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
</body>
</html>

