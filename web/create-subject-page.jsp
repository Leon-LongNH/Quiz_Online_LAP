<%-- 
    Document   : create-subject-page
    Created on : Mar 3, 2021, 12:46:13 PM
    Author     : LongNH
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <script src="https://kit.fontawesome.com/ff02cdde28.js" crossorigin="anonymous"></script>
        <title>Create Question Page</title>
    </head>
    <body>
        <c:set var="userinfor" value="${sessionScope.USER_INFOR}" />
        <c:set var="ID" value="${requestScope.ID}" />
        <c:set var="NAME" value="${requestScope.NAME}" />
        <c:set var="ERROR" value="${requestScope.ERRORS}" />
        <div class="headinformation">
            <h1>Online Quiz</h1>
            <div class="welcome-and-login">
                <a href="Logout"><i class="fa fa-sign-out"> Welcome, ${userinfor.name} </i></a>
            </div>
        </div>
        <div class="container" >
            <form action="CreateSubject" method="POST">
                <h1>Create New Subject</h1>
                <h3> Subject ID : <input class="question-textbox" type="text" name="subjectID" value="<c:if test="${ID != null}" >${ID}</c:if>" required maxlength="10"/>
                    <c:if test="${ERROR != null}" >
                        <p style="color:red">${ERROR}</p>
                    </c:if> </h3>
                <h3> Subject Name : <input class="question-textbox" type="text" name="subjectName" value="<c:if test="${NAME != null}" >${NAME}</c:if>" required maxlength="50"/> </h3>
                <h3> Time take Quiz : <input type="number" min="0" max="99" placeholder="00" name="hour" required>hour:
                    <input type="number" min="0" max="59" placeholder="00" name="minute" required>minute </h3>
                <h3> Question Amount : <input type="number" name="questionAmount" value="" required max="100" min="10"/> </h3>
                <input type="submit" value="Submit" />
            </form>
            <h3><a href="LoadAdminPage" class="Back-home"><i class="far fa-hand-point-left"></i> Back to Home</a></h3>
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
        .container {
            font-size: 120%;
            color: black;
            border-style: double;
            border-color: #008CBA;
            margin: auto;
        }
        .container input[type=number] {
            height: 2em;
            font-size: 20px;
            border-color: #008CBA;
        }
        .container h3 {
            margin-left: 15px;
            text-align: left;
            color: tomato;
        }
        .container h1 {
            text-align: center;
            color: #008CBA;
        }
        .question-textbox {
            width: 84%;
            height: 2em;
            font-size: 20px;
            border-color: #008CBA;
            display: inline-block;
            margin: 14px;
        }
        .container input[type=submit] {
            border: none;
            border-radius: 12px;
            display: inline-block;
            background-color: #008CBA;
            width: 50%;
            height: 50px;
            color: white;
            margin-top: 20px;
            margin-bottom: 20px;
            margin-left: 25%;
            margin-right: 25%;
            transition-duration: 0.4s;
            font-size: 20px;
            bottom: 20px;
            right: 20px;
        }
        .container input[type=submit]:hover {
            cursor: pointer;
            border: 2px solid #008CBA;
            background-color: white;
            color: black
        }
        .Back-home {
            margin-left: 15px;
            text-align: left;
            color: tomato;
            text-decoration: none;
        }
    </style>
</html>
