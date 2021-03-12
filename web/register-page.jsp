<%-- 
    Document   : register-page
    Created on : Feb 26, 2021, 9:29:38 AM
    Author     : LongNH
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta charset="UTF-8">
        <title>Register Page</title>
        <style>
            body {
                font-family: Arial, Helvetica, sans-serif;
                background-color: black;
            }

            * {
                box-sizing: border-box;
            }

            /* Add padding to containers */
            .container {
                padding: 16px;
                background-color: white;
            }

            /* Full-width input fields */
            input[type=text], input[type=password] {
                width: 100%;
                padding: 15px;
                margin: 5px 0 22px 0;
                display: inline-block;
                border: none;
                background: #f1f1f1;
            }

            input[type=text]:focus, input[type=password]:focus {
                background-color: #ddd;
                outline: none;
            }

            /* Overwrite default styles of hr */
            hr {
                border: 1px solid #f1f1f1;
                margin-bottom: 25px;
            }

            /* Set a style for the submit button */
            .registerbtn {
                display: inline-block;
                background-color: #008CBA;
                width: 100%;
                height: 45px;
                color: white;
                transition-duration: 0.4s;
                border: 2px solid #008CBA;
                margin: 3px;
                font-size: 20px;
            }

            .registerbtn:hover {
                cursor: pointer;
                border: 2px solid #008CBA;
                background-color: white;
                color: black
            }

            /* Add a blue text color to links */
            a {
                color: dodgerblue;
            }

            /* Set a grey background color and center the text of the "sign in" section */
            .signin {
                background-color: #f1f1f1;
                text-align: center;
            }
        </style>
    </head>
    <body>
        <c:set var="emailC" value="${requestScope.EMAIL}" />
        <c:set var="nameC" value="${requestScope.NAME}" />
        <c:set var="pwdC" value="${requestScope.PASS}" />
        <c:set var="pwdreC" value="${requestScope.PASSRE}" />
        <c:set var="emailErr" value="${requestScope.EMAIL_ERROR}" />
        <c:set var="nameErr" value="${requestScope.NAME_ERROR}" />
        <c:set var="pwdErr" value="${requestScope.PASS_ERROR}" />
        <c:set var="pwdreErr" value="${requestScope.PASSRE_ERROR}" />
        <form action="Register" method="POST">
            <div class="container">
                <h1>Register</h1>
                <p>Please fill in this form to create an account.</p>
                <hr>

                <label for="email"><b>Email</b></label>
                <input type="text" placeholder="Enter Email" name="email" id="email" required value="${emailC}">
                <c:if test="${emailErr != null}" >
                    <p style="color:red"> ${emailErr}. </p>
                </c:if>
                <label for="name"><b>Full Name</b></label>
                <input type="text" placeholder="Enter Name" name="name" id="name" required value="${nameC}">
                <c:if test="${nameErr != null}" >
                    <p style="color:red"> ${nameErr}. </p>
                </c:if>
                <label for="psw"><b>Password</b></label>
                <input type="password" placeholder="Enter Password" name="psw" id="psw" required value="${pwdC}">
                <c:if test="${pwdErr != null}" >
                    <p style="color:red"> ${pwdErr}. </p>
                </c:if>
                <label for="psw-repeat"><b>Repeat Password</b></label>
                <input type="password" placeholder="Repeat Password" name="psw-repeat" id="psw-repeat" required value="${pwdreC}">
                <c:if test="${pwdreErr != null}" >
                    <p style="color:red"> ${pwdreErr}. </p>
                </c:if>
                <hr>

                <button type="submit" class="registerbtn">Register</button>
            </div>

            <div class="container signin">
                <p>Already have an account? <a href="login-page_1.html">Sign in</a>.</p>
            </div>
        </form>

    </body>
</html>
