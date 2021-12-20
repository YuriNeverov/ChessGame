<#-- @ftlvariable name="user" type="chessgame.web.server.domain.User" -->

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
    <aside>
        <div class="container">
            <div class="table">
                <div class="table-header">
                    <div class="header__item"><a id="name" class="filter__link">Users</a></div>
                </div>
                <div class="table-content">
                    <#list users as opponent>
                        <#if opponent.login != user.login>
                        <div class="table-row">
                            <div class="table-data">
                                <a class="link">
                                    ${opponent.login}
                                </a>
                            </div>
                        </div>
                        </#if>
                    </#list>
                </div>
            </div>
        </div>
    </aside>

    <div class="container" style="margin-right: 21rem">
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
                        <option value=1>White</option>
                        <option value=0>Black</option>
                    </select>
                    <label class="input-label" for="isWhiteColor">Choose your side</label>
                </div>
                <div class="action">
                    <button class="action-button">Invite</button>
                </div>
            </form>
        </div>
    </div>

    <script type="text/javascript">
        function inviteAndScroll(opponent){
            $("opponentLogin").text(opponent);
            scrollUp();
        }
    </script>
</@c.page>