<%-- 
    Document   : history-page
    Created on : Mar 5, 2021, 1:25:34 PM
    Author     : LongNH
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="https://kit.fontawesome.com/ff02cdde28.js" crossorigin="anonymous"></script>
        <title>JSP Page</title>
    </head>
    <body>
        <c:set var="userinfor" value="${sessionScope.USER_INFOR}" />
        <c:set var="quizs" value="${requestScope.LIST_QUIZ}" />
        <c:set var="endpage" value="${requestScope.END_PAGE}" />
        <c:set var="subjectK" value="${requestScope.SUBJECT_KEY}" />
        <c:set var="subjects" value="${requestScope.SUBJECT}" />
        <div class="headinformation">
            <h1>Online Quiz</h1>
            <div class="welcome-and-login">
                <a href="Logout"><i class="fa fa-sign-out"> Welcome, ${userinfor.name} </i></a>
            </div>
        </div>
        <div class="container">
            <h1>Your Quiz History</h1>
            <form action="LoadHistoryPage">
                <div class="search">
                    <div class="center2">
                        <select name="subjectList">
                            <option>Subject:</option>
                            <c:forEach var="subject" items="${subjects}" >
                                <option value="${subject.subjectID}" <c:if test="${subjectK == subject.subjectID}">selected</c:if> >${subject.subjectName}</option>
                            </c:forEach>
                        </select>
                        <button type="submit">
                            <i class="fa fa-search"></i>
                        </button>
                    </div>
                </div>
            </form>
            <form action="LoadSubject">
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

                        <c:forEach var="quiz" items="${quizs}" >
                            <tr>
                                <td>${quiz.subjectID}</td>
                                <td>${quiz.dateTakeQuiz}</td>
                                <td>${quiz.timeTakeQuiz}</td>
                                <td>${quiz.point}</td>
                                <c:set var="correct" value="${quiz.point * (10 / quiz.questionAmount)}" />
                                <td>${correct}/${quiz.questionAmount}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <input name="btAction" class="Timeout" type="submit" value="Back To Home Page" />
            </form>
            <div class="pagging">
                <div class="center">
                    <c:forEach begin="1" end="${endpage}" var="index">
                        <c:url var="urlRewrite" value="LoadHistoryPage" >
                            <c:param name="index" value="${index}" />
                            <c:param name="subjectList" value="${subjectK}" />
                        </c:url>
                        <a href="${urlRewrite}">${index}</a>
                    </c:forEach>
                </div>
            </div>
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
        .pagging {
            display: inline-block;
            position: relative;
            height: 65px;
            width: 100%
        }
        .pagging a {
            float: left;
            display: inline-block;
            background-color: #008CBA;
            height: 25px;
            width: 25px;
            color: white;
            transition-duration: 0.4s;
            font-size: 20px;
            border: 2px solid #fff;
            text-decoration: none;
            text-align: center;
        }
        .pagging a:hover {
            cursor: pointer;
            border: 2px solid #008CBA;
            background-color: white;
            color: black
        }
        .center {
            margin: 0;
            position: absolute;
            top: 50%;
            left: 50%;
            -ms-transform: translate(-50%, -50%);
            transform: translate(-50%, -50%);
        }
        .center2 {
            margin: 0;
            position: absolute;
            left: 50%;
            -ms-transform: translate(-50%, -50%);
            transform: translate(-50%, -50%);
        }
        .search {
            padding-left: 30%;
            border: none;
            margin: 5px
        }
        .search button {
            border-radius: 20%;
            display: inline-block;
            background-color: #008CBA;
            width: 30px;
            height: 30px;
            color: white;
            transition-duration: 0.4s;
            border: 2px solid #008CBA;
            position: absolute;
            margin: 3px;
        }
        .search button:hover {
            cursor: pointer;
            border: 2px solid #008CBA;
            background-color: white;
            color: black
        }
        .search select, input[type="text"] {
            border: 2px solid #008CBA;
            font-size: 25px;
        }
    </style>

</html>
