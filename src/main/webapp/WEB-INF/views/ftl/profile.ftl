<!doctype html>
<#import "spring.ftl" as spring />
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Профиль</title>
</head>
<style>
    .error {
        color: #ff0000;
    }
</style>
<body>
<h1>${user.name}</h1>
<h1>${user.email}</h1>
<@spring.bind "passwordDto"/>
<@spring.bind "nameDto"/>
<form action="/profile/changeName" method="post">
    Новое имя: <br>
        <@spring.formInput "nameDto.name"/>
        <@spring.showErrors "<br>","error"/>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <input type="submit" value="Изменить">
</form>
<form action="/profile/changePassword" method="post">
    Новый пароль: <br>
        <@spring.formInput "passwordDto.password"/>
        <@spring.showErrors "<br>","error"/>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <input type="submit" value="Изменить">
</form>
</body>
</html>