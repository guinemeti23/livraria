<!DOCTYPE html>
<html lang="pt-br">
<head>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cadastrar</title>
    <link rel="stylesheet" href="css/cadastro.css">
</head>
<body>
<div>
    <h1>Cadastrar</h1>
    <form action="CadastroEndereco" method="post">
        <label>
            <input type="text" id="cep" name="cep" placeholder="CEP" required onblur="buscarCep()">
        </label>
        <br><br>
        <label>
            <input type="text" id="logradouro" name="logradouro" placeholder="Logradouro" required>
        </label>
        <br><br>
        <label>
            <input type="text" id="numero" name="numero" placeholder="Número" required>
        </label>
        <br><br>
        <label>
            <input type="text" id="complemento" name="complemento" placeholder="Complemento">
        </label>
        <br><br>
        <label>
            <input type="text" id="bairro" name="bairro" placeholder="Bairro" required>
        </label>
        <br><br>
        <label>
            <input type="text" id="cidade" name="cidade" placeholder="Cidade" required>
        </label>
        <br><br>
        <label>
            <input type="text" id="uf" name="uf" placeholder="UF" required>
        </label>
        <br><br>
        <span class="error-message" style="color: red;"></span>
        <button type="submit" class="btn-ok">Ok</button>
        <button type="button" class="btn-cancelar">Cancelar</button>
    </form>
</div>
<script>
function buscarCep() {
    var cepInput = document.getElementById('cep').value;
    var cep = cepInput.replace(/-/g, '');

    if (cep.length !== 8) {
        console.error('CEP deve conter 8 dígitos.');
        return;
    }

    var url = `https://viacep.com.br/ws/${cep}/json/`;


    fetch(url)
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error('Falha na resposta da API');
            }
        })
        .then(data => {


            if (data.erro) {
                console.error('CEP não encontrado.');
            } else {
                document.getElementById('logradouro').value = data.logradouro || '';
                document.getElementById('complemento').value = data.complemento || '';
                document.getElementById('bairro').value = data.bairro || '';
                document.getElementById('cidade').value = data.localidade || '';
                document.getElementById('uf').value = data.uf || '';
            }
        })
        .catch(error => console.error('Erro ao buscar CEP:', error));
}
</script>
</body>
</html>
