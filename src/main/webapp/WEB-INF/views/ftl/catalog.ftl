<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Catalog</title>
</head>
<body>
<h1>Каталог</h1>
<div>
    <#list goods as good>
        <table>
            <tr>
                 <#if good.path??>
                    <img src='/images/${good.path}'>
                 </#if>
                <th>${good.id}</th>
                <th>${good.title}</th>
                <th>${good.type}</th>
                <th>${good.price}</th>
                <th>${good.description}</th>

        <#if auth??>
            <form action="/catalog" method="post">
                <th><input type="number" name="quantity" value="1"></th>
                <input type="hidden" name="id" value="${good.id}">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                <th><input type="submit" value="Добавить"></th>
            </form>
        </#if>
            </tr>
        </table>
    </#list>
    <#if message??>
        ${message}
    </#if>
</div>
</body>
</html>