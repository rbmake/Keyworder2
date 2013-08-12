<%@ include file="/includes/topDetails.jsp" %>
<title>Domains</title>
<%@ include file="/includes/topScript.jsp" %>

<bean:define id="menu" value="requests"/>
<body>
<%@include file="/includes/logo.jsp" %>
<%@include file="/includes/topMenu.jsp" %>
<%@include file="/includes/messaging.jsp" %>

<div class="pageTitle">
<bean:write name="SessionDomain" property="name" /><br>
</div>
<div class="pageSubTitle">
please select request
</div>

<div class="content">
     <html:form action="/secure/ViewRankingsByRequest" method="post" enctype="multipart/form-data">
    <table>
        <tr><td>

<html:select property="requestId" styleClass="selectboxLarge" styleId="requestId">
    <html:options collection="RequestRankingRequests" name="Request" property="id" labelProperty="date" />
</html:select>
        </td><td>
        <input type="submit" value="submit"></td></tr>

       </table>
       </html:form>
</div>

<%@include file="/includes/bottomDetails.jsp" %>