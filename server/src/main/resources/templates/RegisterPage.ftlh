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
            <form class="card-form" method="post" action="/register">
                <div class="input">
                    <input class="input-field" type="text" id="login" name="login" value="${registerForm.login!}" required/>
                    <label class="input-label" for="login">Login</label>
                    <@error 'registerForm.login'/>
                </div>
                <div class="input">
                    <input class="input-field" type="password" id="password" name="password" required/>
                    <label class="input-label" for="password">Password</label>
                </div>
                <div class="action">
                    <button class="action-button">Register</button>
                </div>
            </form>
        </div>
    </div>
</@c.page>