<%--
  Created by IntelliJ IDEA.
  User: veve
  Date: 2020/7/28
  Time: 5:47 PM
  To change this template use File | Settings | File Templates.
  全局导航栏
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav class="navbar" role="navigation" aria-label="main navigation">
    <div class="navbar-brand">
        <a class="navbar-item">
            <!--            <img src="https://bulma.io/images/bulma-logo.png" width="112" height="28">-->
            iBoard
        </a>

        <!--        <a role="button" class="navbar-burger burger" aria-label="menu" aria-expanded="false" data-target="navbarBasicExample">-->
        <!--            <span aria-hidden="true"></span>-->
        <!--            <span aria-hidden="true"></span>-->
        <!--            <span aria-hidden="true"></span>-->
        <!--        </a>-->
    </div>

    <div id="navbarBasicExample" class="navbar-menu">
        <div class="navbar-start">
        </div>


        <div class="navbar-end">
            <c:if test="${sessionScope.user==null}">
                <div class="navbar-item">
                    <div class="buttons">
                        <a class="button is-primary" href="${pageContext.request.contextPath}/user/register">
                            <strong>Sign up</strong>
                        </a>
                        <a class="button is-light" href="${pageContext.request.contextPath}/user/login">
                            Log in
                        </a>
                    </div>
                </div>
            </c:if>

            <c:if test="${sessionScope.user!=null}">
                <%--        已经登陆--%>
                <%--        ${sessionScope.user.name}--%>

                <div class="navbar-item has-dropdown is-hoverable">
                    <a class="navbar-link">
                            ${sessionScope.user.name}
                    </a>

                    <div class="navbar-dropdown">
                        <a class="navbar-item">
                            Profile
                        </a>
                        <a class="navbar-item" href="${pageContext.request.contextPath}/user/signout">
                            Sign out
                        </a>
                        <!--                        <a class="navbar-item">-->
                        <!--                            Components-->
                        <!--                        </a>-->
                        <!--                        <hr class="navbar-divider">-->
                        <!--                        <div class="navbar-item">-->
                        <!--                            Version 0.9.0-->
                        <!--                        </div>-->
                    </div>
                </div>
            </c:if>
        </div>
</nav>
