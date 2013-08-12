<div  style="position:absolute; left: 10px; top:115px;">
<table cellpadding="0" cellspacing="0">
<logic:present name="RequestErrorMessage" scope="request">

    <tr>

        <td class="error"><bean:write name="RequestErrorMessage" property="text" /></td>
    </tr>
</logic:present>
<logic:present name="RequestMessage" scope="request">

    <tr>

        <td class="info"><bean:write name="RequestMessage" property="text" /></td>
    </tr>
</logic:present>
</table>
</div>