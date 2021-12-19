<#-- @ftlvariable name="user" type="chessgame.web.server.domain.User" -->

<#import "/spring.ftl" as spring/>
<#macro page>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Chess game</title>
        <script type="text/javascript" src="<@spring.url '/js/chess.js'/>"></script>

        <link rel="icon" type="image/png" sizes="32x32" href="<@spring.url '/images/favicon.png'/>">
        <link rel="stylesheet" type="text/css" href="<@spring.url '/css/normalize.css'/>">
        <link rel="stylesheet" type="text/css" href="<@spring.url '/css/style.css'/>">
        <link rel="stylesheet" type="text/css" href="<@spring.url '/css/form.css'/>">
        <link rel="stylesheet" type="text/css" href="<@spring.url '/css/chess.css'/>">
        <link rel="stylesheet" type="text/css" href="<@spring.url '/css/list.css'/>">
        <link rel="stylesheet" type="text/css" href="<@spring.url '/css/table.css'/>">
    </head>
    <body>
    <header>
        <a href="/"><img src="<@spring.url '/images/logo.png'/>" alt="Chess Game" title="Chess Game"/></a>
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