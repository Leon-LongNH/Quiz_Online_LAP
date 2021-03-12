<%-- 
    Document   : admin-home-page
    Created on : Feb 26, 2021, 9:14:01 PM
    Author     : LongNH
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="https://kit.fontawesome.com/ff02cdde28.js" crossorigin="anonymous"></script>
        <title>Admin Home Page</title>
    </head>
    <body>
        <c:set var="userinfor" value="${sessionScope.USER_INFOR}" />
        <c:set var="subjects" value="${requestScope.SUBJECT}" />
        <c:set var="questions" value="${requestScope.LIST_ALL_QUESTION}" />
        <c:set var="endpage" value="${requestScope.END_PAGE}" />
        <c:set var="searchK" value="${requestScope.SEARCH_KEY}" />
        <c:set var="subjectK" value="${requestScope.SUBJECT_KEY}" />
        <c:set var="statusK" value="${requestScope.STATUS_KEY}" />
        <div class="headinformation">
            <h1>Online Quiz</h1>
            <div class="welcome-and-login">
                <a href="Logout"><i class="fa fa-sign-out"> Welcome, ${userinfor.name} </i></a>
            </div>
        </div>
        <div class="container">
            <form action="SearchQuestion" >
                <div class="search">
                    <select name="subjectList">
                        <option>Subject:</option>
                        <c:forEach var="subject" items="${subjects}" >
                            <option value="${subject.subjectID}" <c:if test="${subjectK == subject.subjectID}">selected</c:if> >${subject.subjectName}</option>
                        </c:forEach>
                    </select>
                    <select name="status">
                        <option <c:if test="${statusK == 'Status:'}"> selected </c:if> >Status:</option>
                        <option <c:if test="${statusK == 'Avaliable'}"> selected </c:if> >Avaliable</option>
                        <option <c:if test="${statusK == 'Deleted'}"> selected </c:if> >Deleted</option>
                        </select>
                        <span>Search</span>
                        <input type="text" name="searchQuiz" value="<c:if test="${searchK != null and searchK != 'NO'}">${searchK}</c:if>" placeholder="Enter question ....."/>
                        <button type="submit">
                            <i class="fa fa-search"></i>
                        </button>
                    </div>
                    <hr>
                </form>
            <c:forEach var="question" items="${questions}" >
                <div class="question">
                    <form action="QuestionDetail" method="POST">
                        <input type="text" name="questionID" value="${question.questionID}" readonly/>
                        <div>
                            <span>${question.question}</span>
                        </div>
                        <input type="submit" value="Edit" name="btAction"/>
                        <input type="submit" value="Delete" name="btAction"/>
                        <hr>
                    </form>
                </div>
            </c:forEach>
            <div class="create-button">
                <div class="center">
                    <a href="LoadSubjectForCreate">Create new question</a>
                </div>
            </div>
            <div class="create-button">
                <div class="center">
                    <a href="create-subject-page.jsp">Create new subject</a>
                </div>
            </div>
            <div class="create-button">
                <div class="center">
                    <a href="LoadSubjectForUpdate">Update subject</a>
                </div>
            </div>
            <div class="pagging">
                <div class="center">
                    <c:forEach begin="1" end="${endpage}" var="index">
                        <c:url var="urlRewrite" value="SearchQuestion" >
                            <c:param name="subjectList" value="${subjectK}" />
                            <c:param name="status" value="${statusK}" />
                            <c:param name="index" value="${index}" />
                            <c:param name="searchQuiz" value="${searchK}" />
                        </c:url>
                        <a href="${urlRewrite}">${index}</a>
                    </c:forEach>
                </div>
            </div>
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
        .search {
            padding-left: 30%;
            border: none;
            margin: 5px
        }
        .search select, input[type="text"] {
            border: 2px solid #008CBA;
            font-size: 25px;
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
        .search span {
            font-size: 25px;
        }
        .question span {
            word-break: break-all;
            font-size: 20px;
            display: inline-block;
        }
        .question div {
            width: 50%;
            display: inline-block;
        }
        .question input[type="text"] {
            display: inline-block;
            border: none; 
            font-size: 25px;
        }
        .question input[type="submit"] {
            border: none;
            border-radius: 12px;
            margin: 20px;
            display: inline-block;
            background-color: #008CBA;
            width: 10%;
            height: 25px;
            color: white;
            transition-duration: 0.4s;
            font-size: 20px;
            border: 2px solid #008CBA;
        }
        .question input[type="submit"]:hover {
            cursor: pointer;
            border: 2px solid #008CBA;
            background-color: white;
            color: black
        }
        hr {
            background-color: #008CBA;
        }
        .create-button a:link, a:visited {
            border-radius: 12px;
            margin: 20px;
            display: inline-block;
            background-color: #008CBA;
            height: 21px;
            color: white;
            transition-duration: 0.4s;
            font-size: 20px;
            border: 2px solid #008CBA;
            text-decoration: none;
            padding-left: 20px;
            padding-right: 20px;

        }
        .create-button a:hover {
            cursor: pointer;
            border: 2px solid #008CBA;
            background-color: white;
            color: black
        }
        .create-button {
            height: 65px;
            position: relative;
        }
        .center {
            margin: 0;
            position: absolute;
            top: 50%;
            left: 50%;
            -ms-transform: translate(-50%, -50%);
            transform: translate(-50%, -50%);
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
    </style>
</html>
