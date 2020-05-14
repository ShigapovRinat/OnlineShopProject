<!doctype html>
<#import "spring.ftl" as spring />
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">

    <title>Registration</title>
</head>
<style>
    .error {
        color: #ff0000;
    }
</style>
<body>
<div>
    <h1><@spring.message 'registration.page.title'/></h1>
</div>
<div>
    <@spring.bind "signUpDto"/>
    <form action="/signUp" method="post">
        Email: <br>
        <@spring.formInput "signUpDto.email"/>
        <br>
        <@spring.showErrors "<br>", "error"/>
        <br><br>
        <@spring.message 'registration.page.name'/>: <br>
        <@spring.formInput "signUpDto.name"/>
        <br>
        <@spring.showErrors "<br>","error"/>
        <br><br>
        <@spring.message 'registration.page.password'/>: <br>
        <@spring.formPasswordInput "signUpDto.password"/>
        <br>
        <@spring.showErrors "<br>","error"/>

        <br>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        <input type="submit" value="Регистрация">
    </form>
    <#if exception??>
        <p style="color: red">${exception.message}</p>
    </#if>
</div>
</body>
</html>