<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, person-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Registration</title>
</head>
<body>
<h1>Зарегестрироваться</h1>
<div>
    <form action="/signUp" method="post">
        <input name="name" placeholder="Имя">
        <input name="email" placeholder="Email">
        <input type="password" name="password" placeholder="Пароль">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        <input type="submit" value="Регистрация">
    </form>
    <#if exception??>
        ${exception.message}
    </#if>
</div>
</body>
</html>