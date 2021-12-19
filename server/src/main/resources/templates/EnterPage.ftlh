<#import "/spring.ftl" as spring>
<#import "./common.ftlh" as c>

<#macro error field>
<@spring.bind field/>
<#list spring.status.errorMessages as error>
<div class="error">${error}</div>
<#break/>
</#list>
</#macro>

<@c.page>
<div class="container">
    <div class="card">
        <div class="card-image">
        </div>
        <form class="card-form" method="post" action="/enter">
            <div class="input">
                <input class="input-field" type="text" id="login" name="login" value="${enterForm.login!}" required/>
                <label class="input-label" for="login">Login</label>
                <@error 'enterForm.login'/>
            </div>
            <div class="input">
                <input class="input-field" type="password" id="password" name="password" required/>
                <label class="input-label" for="password">Password</label>
            </div>
            <div class="action">
                <button class="action-button">Enter</button>
            </div>
        </form>
        <div class="card-info">
            <p><a href="/register">Register</a> if you don't have an account</p>
        </div>
    </div>
</div>
</@c.page>