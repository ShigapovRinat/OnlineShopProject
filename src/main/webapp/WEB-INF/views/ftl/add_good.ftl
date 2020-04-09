<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, person-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Add good</title>
</head>
<body>
<h1>Добавить продукт</h1>
<div>
    <form action="/addGood" method="post">
        <input name="title" placeholder="Название">
        <input type="number" name="price" placeholder="Цена">
        <input type="text" name="description" placeholder="Описание">
        <p>
            Категория:
            <label>
                <select name="type">
                    <option value="PHONE">Телефон</option>
                    <option value="NOTEBOOK">Ноутбук</option>
                    <option value="TABLET">Планшет</option>
                </select>
            </label>
        </p>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        <input type="submit" value="Добавить">
    </form>
    <#if message??>
        ${message}
    </#if>
</div>
</body>
</html>