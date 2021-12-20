<#-- @ftlvariable name="user" type="chessgame.web.server.domain.User" -->

<#import "/spring.ftl" as spring/>
<#macro page>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Chess game</title>
        <script src="<@spring.url '/js/jquery-3.3.1.js'/>"></script>
        <script src="https://unpkg.com/@chrisoakman/chessboardjs@1.0.0/dist/chessboard-1.0.0.min.js"
                integrity="sha384-8Vi8VHwn3vjQ9eUHUxex3JSN/NFqUg3QbPyX8kWyb93+8AC/pPWTzj+nHtbC5bxD"
                crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/chess.js/0.10.2/chess.js"
                integrity="sha384-s3XgLpvmHyscVpijnseAmye819Ee3yaGa8NxstkJVyA6nuDFjt59u1QvuEl/mecz"
                crossorigin="anonymous"></script>
        <script src="<@spring.url '/js/chess.js'/>"></script>

        <link rel="icon" type="image/png" sizes="32x32" href="<@spring.url '/img    /favicon.png'/>">
        <link rel="stylesheet" type="text/css" href="<@spring.url '/css/normalize.css'/>">
        <link rel="stylesheet" type="text/css" href="<@spring.url '/css/style.css'/>">
        <link rel="stylesheet" type="text/css" href="<@spring.url '/css/form.css'/>">
        <link rel="stylesheet" type="text/css" href="<@spring.url '/css/chess.css'/>">
        <link rel="stylesheet" type="text/css" href="<@spring.url '/css/list.css'/>">
        <link rel="stylesheet" type="text/css" href="<@spring.url '/css/table.css'/>">
        <link rel="stylesheet" type="text/css" href="<@spring.url '/css/chessboard-1.0.0.css'/>">
    </head>
    <body>
    <header>
        <a href="/"><img src="<@spring.url '/img/logo.png'/>" alt="Chess Game" title="Chess Game"/></a>
        <div class="enter-or-register-box">
            <#if user??>
                <a class="link">${user.login}</a>
                |
                <a class="link" href="/logout">Logout</a>
            <#else>
                <a class="link" href="/enter">Enter</a>
                |
                <a class="action-button, link" href="/register">Register</a>
            </#if>
        </div>
        <nav>
            <ul>
                <li><a href="/">Home</a></li>
                <#if user??>
                    <li><a href="/create_game">Game</a></li>
                </#if>
            </ul>
        </nav>
    </header>
    <div class="middle">
        <main>
            <#nested/>
        </main>
    </div>
    <footer>
        <a href="https://github.com/YuriNeverov/ChessGame">Chess Game</a> &copy; 2021 ITMO Software Design
    </footer>
    </body>
    </html>
</#macro>