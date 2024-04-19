<!DOCTYPE html>
<html lang="pt-br">
<head>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cadastrar </title>
    <link rel="stylesheet" href="css/cadastro.css">
</head>
<body>
<div>
    <h1>Cadastrar </h1>
    <form action="CadastroEndereco" method="post">
        <label>

                    <input type="text" name="cep" placeholder="CEP" required>
                </label>
                <br><br>
                <label>

                    <input type="text" name="logradouro" placeholder="Logradouro" required>
                </label>
                <br><br>
                <label>

                    <input type="text" name="numero" placeholder="Número" required>
                </label>
                <br><br>
                <label>

                    <input type="text" name="complemento" placeholder="Complemento">
                </label>
                <br><br>
                <label>

                    <input type="text" name="bairro" placeholder="Bairro" required>
                </label>
                <br><br>
                <label>

                    <input type="text" name="cidade" placeholder="Cidade" required>
                </label>
                <br><br>
                <label>

                    <input type="text" name="uf" placeholder="UF" required>
                </label>
        <br><br>
        <span class="error-message" style="color: red;"></span>
        <button type="submit" class="btn-ok">Ok</button>
        <button type="button" class="btn-cancelar">Cancelar</button>
    </form>
</div>
</body>
</html>
