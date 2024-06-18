<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table border="3px">
    <tr>
        <td><b>Время/Дата</b></td>
        <td><b>Описание</b></td>
        <td><b>Калории</b></td>
        <td><b>Update</b></td>
        <td><b>Delete</b></td>
    </tr>
    <c:forEach items="${mealsList}" var="meal">
        <tr style="color:${meal.excess ? 'red' : 'green'}">
            <td>${meal.dateTime}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td></td>
            <td></td>
        </tr>
    </c:forEach>


</table>

</body>
</html>
