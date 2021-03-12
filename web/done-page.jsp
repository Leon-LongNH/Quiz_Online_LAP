<%-- 
    Document   : done-page
    Created on : Feb 21, 2021, 7:56:06 PM
    Author     : LongNH
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="https://kit.fontawesome.com/ff02cdde28.js" crossorigin="anonymous"></script>
        <title>Result Page</title>
    </head>
    <body>
        <c:set var="userinfor" value="${sessionScope.USER_INFOR}" />
        <c:set var="quiz" value="${requestScope.QUIZ}" />
        <c:set var="correct" value="${requestScope.QUIZ_CORRECT}" />
        <c:set var="subject" value="${requestScope.QUIZ_SUBJECT}" />
        <div class="headinformation">
            <h1>Online Quiz</h1>
            <div class="welcome-and-login">
                <a href="Logout"><i class="fa fa-sign-out"> Welcome, ${userinfor.name} </i></a>
            </div>
        </div>
        <div class="container">
            <form action="LoadSubject">
                <h1>Your Quiz is Done</h1>
                <table class="DesignTable">
                    <thead>
                        <tr>
                            <th>Quiz Subject</th>
                            <th>Date Take</th>
                            <th>Time Take</th>
                            <th>Point</th>
                            <th>Correct Answers</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>${quiz.subjectID}</td>
                            <td>${quiz.dateTakeQuiz}</td>
                            <td>${quiz.timeTakeQuiz}</td>
                            <td>${quiz.point}</td>
                            <td>${correct}/${quiz.questionAmount}</td>
                        </tr>
                    </tbody>
                </table>
                <input name="btAction" class="Timeout" type="submit" value="Back To Home Page" />
            </form>
        </div>
    </body>
    <style>
        .container
        {
            font-size: 120%;
            color: black;
            border-style: double;
            border-color: #008CBA;
            margin: auto;
        }
        .container h1
        {
            text-align: center;
            color: #008CBA;
        }
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
        table {
            border-collapse: collapse;
            width: 90%;
            margin: 5%;
        }

        th, td {
            text-align: left;
            padding: 8px;
        }

        tr:nth-child(even){background-color: #f2f2f2}

        th {
            background-color: #008CBA;
            color: white;
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
            margin-left: 25%;
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
