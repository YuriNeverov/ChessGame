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
            <form class="card-form" method="post" action="/create_game">
                <div class="input">
                    <input class="input-field" type="text" id="opponentLogin" name="opponentLogin" value="${gameForm.opponentLogin!}" required/>
                    <label class="input-label" for="opponentLogin">Opponent login</label>
                    <@error 'gameForm.opponentLogin'/>
                </div>
                <div class="input">
                    <select class="input-field" type="isWhiteColor" id="isWhiteColor" name="isWhiteColor" >
                        <option value=true>White</option>
                        <option value=false>Black</option>
                    </select>
                    <label class="input-label" for="isWhiteColor">Choose your side</label>
                </div>
                <div class="action">
                    <button class="action-button">Invite</button>
                </div>
            </form>
        </div>
    </div>
</@c.page>