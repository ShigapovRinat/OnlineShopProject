<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, person-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Basket</title>
</head>
<body>
<h1>Корзина</h1>
<div>
    <#if basket??>
    <#list basket as basketCol>
        ${basketCol.quantityGood}
        ${basketCol.goodDto.id}
        ${basketCol.goodDto.title}
        ${basketCol.goodDto.type}
        ${basketCol.goodDto.price}
        ${basketCol.goodDto.description}
        <br>
    </#list>
        <form action="/basket" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            <input type="submit" value="Сделать заказ">
        </form>
    <#else>
        Корзина пуста
    </#if>

</div>
</body>
</html>