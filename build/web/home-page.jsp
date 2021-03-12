<%-- 
    Document   : home-page
    Created on : Feb 23, 2021, 3:34:33 PM
    Author     : LongNH
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="https://kit.fontawesome.com/ff02cdde28.js" crossorigin="anonymous"></script>
        <title>Home Page</title>
    </head>
    <body>
        <c:set var="subjects" value="${requestScope.SUBJECT}" />
        <c:set var="userinfor" value="${sessionScope.USER_INFOR}" />
        <div class="headinformation" >
            <h1>Online Quiz</h1>
            <div class="welcome-and-login">
                <a href="Logout"><i class="fa fa-sign-out"> Welcome, ${userinfor.name} </i></a>
            </div>
        </div>
        <div class="container">
            <h1>Welcome to quiz online</h1>
            <h3>Please choose one subject that you want to take quiz</h3>
            <form class="boxed" action="StartAQuiz">
                <c:forEach var="subject" items="${subjects}" >
                    <input type="radio" id="${subject.subjectID}" name="SubjectID" value="${subject.subjectID}">
                    <label for="${subject.subjectID}">${subject.subjectName}</label>
                </c:forEach>
                <input class="Timeout" type="submit" value="Star A Quiz">
            </form>
            <a href="LoadHistoryPage">Show Your Quiz History<i class="fas fa-history"></i></a>
        </div>
    </body>
    <style>
        .headinformation {
            background-color: #008CBA;
            position: sticky;
            top: 0;
        }
        .headinformation h1 {
            margin-left: 15px;
            color: white;
            display: inline-block;
        }
        .welcome-and-login {
            display: inline-block;
            position: absolute;
            bottom: 27px;
            right: 15px;
            font-size: 20px;
        }
        .welcome-and-login i {
            color: white;
        }
        .welcome-and-login i:hover {
            color: #a6a6aa;
        }
        .container
        {
            font-size: 120%;
            color: black;
            border-style: double;
            border-color: #008CBA;
            margin: auto;
        }
        .container h1, h3
        {
            text-align: center;
            color: #008CBA;
        }
        .container a {
            margin-left: 15px;
            text-align: left;
            color: tomato;
            text-decoration: none;
            margin-bottom: 15px;
            display: inline-block;
            font-size: 20px;
        }
        .container a:hover {
            color: #008CBA;
        }
        .boxed {
            padding-left: 25%;
            padding-right: 15%;
        }
        .boxed label {
            display: inline-block;
            width: 200px;
            padding: 10px;
            border: solid 2px #008CBA;
            transition: all 0.3s;
            color: white;
            background-color: #008CBA;
            margin: 15px;
        }

        .boxed input[type="radio"] {
            display: none;
        }

        .boxed input[type="radio"]:checked + label {
            border: solid 2px #008CBA;
            background-color: white;
            color: black
        }
        .boxed input[type="radio"]:hover + label {
            cursor: pointer;
            border: 2px solid #008CBA;
            background-color: white;
            color: black
        }
        .Timeout {
            border: none;
            border-radius: 12px;
            display: inline-block;
            background-color: #008CBA;
            width: 50%;
            height: 50px;
            color: white;
            margin-top: 20px;
            margin-bottom: 20px;
            margin-left: 17%;
            margin-right: 25%;
            transition-duration: 0.4s;
            font-size: 20px;
            bottom: 20px;
            right: 20px;
        }
        .Timeout:hover {
            cursor: pointer;
            border: 2px solid #008CBA;
            background-color: white;
            color: black
        }
    </style>
</html>
