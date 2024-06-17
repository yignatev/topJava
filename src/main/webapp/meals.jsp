<%@ page import="java.time.format.DateTimeFormatter" %>
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
  <c:forEach items="${mealsList}" var="mealsList">
    <c:if test="${mealsList.isExcess()}">
      <tr style="color: red">
        <td>${mealsList.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))}</td>
        <td>${mealsList.getDescription()}</td>
        <td>${mealsList.getCalories()}</td>
        <td></td>
        <td></td>
      </tr>
    </c:if>
    <c:if test="${not mealsList.isExcess()}">
      <tr style="color: green">
        <td>${mealsList.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))}</td>
        <td>${mealsList.getDescription()}</td>
        <td>${mealsList.getCalories()}</td>
        <td></td>
        <td></td>
      </tr>
    </c:if>
  </c:forEach>
</table>

</body>
</html>
