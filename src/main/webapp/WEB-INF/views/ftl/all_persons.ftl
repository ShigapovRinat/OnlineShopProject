<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, person-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Persons</title>
</head>
<body>
<h1>Пользователи</h1>
<div>
    <#list persons as person>
        ${person.email}
        ${person.name}
        ${person.role}
        <#if person.confirmed>
            confirmed
        <#else>
            not confirmed
        </#if>
        <br>
    </#list>
</div>
</body>
</html>