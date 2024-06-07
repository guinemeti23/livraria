<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tela de login</title>
    <link rel="stylesheet" href="css/login.css">
</head>
<body>
<div id="container">
    <h1>Login de Cliente</h1>
    <form action="LoginClienteServlet" method="post">

        <input type="hidden" name="redirectUrl" value="${fn:escapeXml(param.redirectUrl)}" />
        <input type="text" name="email" placeholder="Email" required style="margin-bottom: 10px;"><br>
        <input type="password" name="senha" placeholder="Senha" required style="margin-bottom: 10px;"><br>
        <button type="submit" class="btn-ok" id="btn-ok">Ok</button>
        <button type="button" class="btn-cancelar">Cancelar</button>

    </form>
        <a href="cadastroCliente.html">Cadastre-se</a>


    <div id="alert" style="display: none;"></div>
</div>

<script>
function redirectToLogin() {
    var currentUrl = window.location.href;
    var loginUrl = 'loginCliente.jsp?redirectUrl=' + encodeURIComponent(currentUrl);
    window.location.href = loginUrl;
}
</script>
</body>
</html>
