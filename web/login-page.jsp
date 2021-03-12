<%-- 
    Document   : login-page
    Created on : Feb 24, 2021, 3:05:58 PM
    Author     : LongNH
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    <body>
        <c:set var="loginRe" value="${requestScope.loginResult}" />
        <form action="CheckLogin" method="post">
            <div class="imgcontainer">
                <img src="avatar.png" alt="Avatar" class="avatar">
            </div>

            <div class="container">
                <label for="uname"><b>Username</b></label>
                <input type="text" placeholder="Enter Username" name="username" required>

                <label for="psw"><b>Password</b></label>
                <input type="password" placeholder="Enter Password" name="password" required>
                
                <c:if test="${loginRe != null and loginRe == false}" >
                    <p style="color:red"> Login Failed. Please try again. </p>
                </c:if>
                    
                <button type="submit">Login</button>

            </div>
            <div class="container" >
                <span> Create new <a href="register-page.html">account?</a></span>
                <span class="psw">Forgot <a href="#">password?</a></span>
            </div>
        </form>
    </body>
    <style>
        body {font-family: Arial, Helvetica, sans-serif;}
        form {border: 3px solid #f1f1f1;padding: 100px}

        input[type=text], input[type=password] {
            width: 100%;
            padding: 12px 20px;
            margin: 8px 0;
            display: inline-block;
            border: 1px solid #ccc;
            box-sizing: border-box;
        }

        button {
            background-color: #008CBA;
            color: white;
            padding: 14px 20px;
            margin: 8px 25%;
            border: 2px solid #008CBA;
            cursor: pointer;
            width: 50%;

        }

        button:hover {
            cursor: pointer;
            border: 2px solid #008CBA;
            background-color: white;
            color: black
        }

        .cancelbtn {
            width: auto;
            padding: 10px 18px;
            background-color: #f44336;
        }

        .imgcontainer {
            text-align: center;
            margin: 24px 0 12px 0;

        }

        img.avatar {
            border-radius: 50%;
            width: 10%;
            height: auto;
        }

        .container {
            padding-left: 100px;
            padding-right: 100px;
        }

        span.psw {
            float: right;
            padding-top: 16px;
        }

        /* Change styles for span and cancel button on extra small screens */
        @media screen and (max-width: 300px) {
            span.psw {
                display: block;
                float: none;
            }
            .cancelbtn {
                width: 100%;
            }
        }
    </style>
</html>
