<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Main</title>
</head>
<body>
<h1>Главное меню</h1>
<div>
    <#if auth??>
        <a href="/profile">Профиль</a>
        <a href="/basket">Корзина</a>
        <a href="/orders">Заказы</a>
        <a href="/support_chat">Тех поддержка</a>
        <#if auth == "ADMIN">
            <a href="/addGood">Добавить продукт</a>
            <a href="/allUsers">Список пользователей</a>
        </#if>
    <#else>
        <a href="/signIn">Войти</a>
        <a href="/signUp">Зарегистрироваться</a>
    </#if>
    <a href="/catalog">Каталог товаров</a>

</div>
</body>
</html>