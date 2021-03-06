<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
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
            <form action="/basket" method="post">
                <input type="hidden" name="id" value="${basketCol.id}">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                <input type="submit" value="Удалить">
            </form>
        <br>
    </#list>
        <form action="/createOrder" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            <input type="submit" value="Сделать заказ">
        </form>
    <br>
    <br>
   <h3>Вам может понравится</h3>
    <#list recommendations as good>
         <table>
             <tr>
                 <th>${good.id}</th>
                 <th>${good.title}</th>
                 <th>${good.type}</th>
                 <th>${good.price}</th>
                 <th>${good.description}</th>
            <form action="/catalog" method="post">
                <th><input type="number" name="quantity" value="1"></th>
                <input type="hidden" name="id" value="${good.id}">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                <th><input type="submit" value="Добавить"></th>
            </form>
             </tr>
         </table>
    </#list>
    <#else>
        Корзина пуста
    </#if>

</div>
</body>
</html>