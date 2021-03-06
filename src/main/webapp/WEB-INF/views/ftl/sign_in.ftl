<!doctype html>
<#import "spring.ftl" as spring />
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Login</title>
</head>
<body>
<div>
    <h1><@spring.message 'authorization.page.title'/></h1>
</div>
<div>
    <form action="/signIn" method="post">
        <input name="email" placeholder="Email">
        <input type="password" name="password" placeholder="<@spring.message 'registration.page.password'/>">
        <br>
        <label>
            <input type="checkbox" name="remember-me"><@spring.message 'authorization.page.remember-me'/>
        </label>
        <br>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        <input type="submit" value="Войти">
    </form>
    <br>
    <label>
        <a href="http://localhost:8080/login/oauth2/code/google">Войти с помощью gmail</a>
    </label>
    <#if exception??>
        ${exception.message}
    </#if>
</div>
</body>
</html>