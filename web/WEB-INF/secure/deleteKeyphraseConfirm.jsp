<%@ include file="/includes/topDetails.jsp" %>
<title>Domains</title>
<%@ include file="/includes/topScript.jsp" %>

<bean:define id="menu" value="domains"/>
<body>
<%@include file="/includes/logo.jsp" %>
<%@include file="/includes/topMenu.jsp" %>
<%@include file="/includes/messaging.jsp" %>


<div class="content">
    <span class="info">
Are you sure you want to delete keyphrase <bean:write name="RequestKeyphrase" property="name" /></span>
<html:form action='/secure/DeleteKeyphraseConfirm' method="post">

        <input type="hidden" name="id" value='<bean:write name="RequestKeyphrase" property="id" />'>
        <input type="hidden" name="yesNo" value="">
        <table border=0 cellspacing="0" cellpadding="5">
            
            <tr>
                <td><input type="submit" value="Yes" name="Yes" onclick="this.form.yesNo.value='yes'"></td>
                <td width="20"></td>
                <td>
                <input type="submit" name="No" value="No" onclick="this.form.yesNo.value='no'">
                </td>
               

            </tr>
           
            
        </table>
    
        
</html:form>
</div>
</body>
</html>
