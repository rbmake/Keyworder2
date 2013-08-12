<%@ include file="/includes/topDetails.jsp" %>
<title>Keyphrases</title>
<%@ include file="/includes/topScript.jsp" %>

<bean:define id="menu" value="domains"/>
<body>
<%@include file="/includes/logo.jsp" %>
<%@include file="/includes/topMenu.jsp" %>


<%@include file="/includes/messaging.jsp" %>


<div class="content">

    <html:form action='secure/AddKeyphrase'>
        <table>
            <tr><td class="info">        Enter one or more keyphrases. Separate each keyphrase with a comma.</td></tr>
            <tr><td height="10"></td></tr>
            <tr><td><html:text  styleClass="entryboxVeryLarge" name="AddKeyphrases" property="keyphrases"></html:text></td></tr>
            <tr><td align="left"><html:submit value="Add"></html:submit></td></tr>
        </table>

        
    </html:form>
</div>

<%@include file="/includes/bottomDetails.jsp" %>