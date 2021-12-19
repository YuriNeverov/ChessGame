<#-- @ftlvariable name="user" type="chessgame.web.server.domain.User" -->
<#import "/spring.ftl" as spring/>
<#macro page>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Chess game</title>
        <link rel="icon" type="image/png" sizes="32x32" href="<@spring.url '/images/favicon32.png'/>">
        <link rel="stylesheet" type="text/css" href="<@spring.url '/css/normalize.css'/>">
        <link rel="stylesheet" type="text/css" href="<@spring.url '/css/style.css'/>">
        <link rel="stylesheet" type="text/css" href="<@spring.url '/css/form.css'/>">
    </head>
    <body>
    <header>
        <a href="/"><img src="<@spring.url '/images/logo.svg'/>" alt="Chess Game" title="Chess Game"/></a>
        <div class="enter-or-register-box">
            <#if user??>
                ${user.login}
                |
                <a href="/logout">Logout</a>
            <#else>
                <a href="/enter">Enter</a>
                |
                <a href="/register">Register</a>
            </#if>
        </div>
        <nav>
            <ul>
                <li><a href="/">Home</a></li>
                <#if user??>
                    <li><a href="/users">Users</a></li>
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