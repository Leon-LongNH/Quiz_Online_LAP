<%-- 
    Document   : update-subject-page
    Created on : Mar 3, 2021, 1:38:06 PM
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
        <script type="text/javascript" src="//code.jquery.com/jquery-2.1.3.min.js"></script>
        <title>Create Question Page</title>
    </head>
    <body>
        <c:set var="userinfor" value="${sessionScope.USER_INFOR}" />
        <c:set var="subjects" value="${requestScope.SUBJECT}" />
        <div class="headinformation">
            <h1>Online Quiz</h1>
            <div class="welcome-and-login">
                <a href="Logout"><i class="fa fa-sign-out"> Welcome, ${userinfor.name} </i></a>
            </div>
        </div>
        <div class="container" >
            <form action="UpdateSubject" method="POST">
                <h1>Create New Subject</h1>
                <h3> Subject <select name="subjectList" id="subjectList">
                        <c:forEach var="subject" items="${subjects}" >
                            <option selected value="${subject.subjectID}" Subject-Name="${subject.subjectName}" Question-Amount="${subject.questionAmount}" timeTake="${subject.timeTakeQuiz}">${subject.subjectName}</option>
                        </c:forEach>
                    </select> </h3>
                <h3> Subject Name : <input id="subjectName" class="question-textbox" type="text" name="subjectName" value="<c:if test="${NAME != null}" >${NAME}</c:if>" required maxlength="50"/> </h3>
                <h3> Time take Quiz : <input id="hour" type="number" min="0" max="99" placeholder="00" name="hour" required>hour:
                    <input id="minute" type="number" min="0" max="59" placeholder="00" name="minute" required>minute </h3>
                <h3> Question Amount : <input id="questionAmount" type="number" name="questionAmount" value="" required max="100" min="10"/> </h3>
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
        .container select {
            border: 2px solid #008CBA;
            font-size: 25px;
            margin-left: 15px;
            text-align: left;
        }
        .Back-home {
            margin-left: 15px;
            text-align: left;
            color: tomato;
            text-decoration: none;
        }
    </style>
    <script type="text/javascript">
        $("#subjectList").on("change", function () {
            $("#subjectName").val($(this).find("option:selected").attr("Subject-Name"));
            $("#questionAmount").val($(this).find("option:selected").attr("Question-Amount"));
            var str = $(this).find("option:selected").attr("timeTake");
            var res = str.split(":");
            $("#hour").val(res[0]);
            $("#minute").val(res[1]);
        });
    </script>
</html>
