<div class="primaryMenu">
<ul id="menu">
	<li><a href="/Keyworder2/secure/Domains.do" onmouseover="mopen()" onmouseout="mclosetime()">Domains</a>

	</li>

	<li><a href="#" onmouseover="mopen('settings')" onmouseout="mclosetime()">Settings</a>
		<div id="settings" onmouseover="mcancelclosetime()" onmouseout="mclosetime()">
                 <a href="/Keyworder2/ShowSettings.do">Refresh</a>
		</div>
	</li>
    <li><a href="/Keyworder2/secure/Logout.do" onmouseover="mopen()" onmouseout="mclosetime()">Log out</a>

	</li>

</ul>
</div>
<div class="loggedIn">
    <logic:present name="SessionUser">
        <span class="info">
        <bean:write name="SessionUser" property="firstName"/>&nbsp;<bean:write name="SessionUser" property="surname"/>&nbsp;logged in
        </span>
    </logic:present>
</div>