<%@ include file="/includes/topDetails.jsp" %>
<title>Keyphrases</title>
<%@ include file="/includes/topScript.jsp" %>

<bean:define id="menu" value="domains"/>
<body>
<%@include file="/includes/logo.jsp" %>
<%@include file="/includes/topMenu.jsp" %>


<%@include file="/includes/messaging.jsp" %>


<div class="content">

    <html:form action='secure/AddDomain'>
        <table>
            <tr><td class="info">        Enter the domain that you wish to add. This should be in the format of mydomain.com <br>Please do not add prefix it with http://www or similar</td></tr>
            <tr><td height="10"></td></tr>
            <tr><td><html:text  styleClass="entryboxVeryLarge" name="Domain" property="name"></html:text></td></tr>
            <tr><td align="left"><html:submit value="Add"></html:submit></td></tr>
        </table>

        
    </html:form>
</div>

<%@include file="/includes/bottomDetails.jsp" %>