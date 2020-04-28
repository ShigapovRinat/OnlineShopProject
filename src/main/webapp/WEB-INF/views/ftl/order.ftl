<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Orders</title>
</head>
<body>
<h1>Заказы</h1>
<div>
    <#if orders??>
    <#list orders as order>
        ${order.idOrder}
        ${order.createAt}
        <br>
        <#list order.goodsBasket as good>
            ${good.quantityGood}
            ${good.goodDto.title}
            ${good.goodDto.price}
            <br>
        </#list>
        <br>
        <br>
    </#list>
    <#else>
        Вы еще ничего не заказали
    </#if>

</div>
</body>
</html>