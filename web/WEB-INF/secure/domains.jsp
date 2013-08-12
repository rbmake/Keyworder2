<%@ include file="/includes/topDetails.jsp" %>
<title>Domains</title>
<%@ include file="/includes/topScript.jsp" %>

<bean:define id="menu" value="domains"/>
<body>
<%@include file="/includes/logo.jsp" %>
<%@include file="/includes/topMenu.jsp" %>

<div class="pageTitle">
Domains
</div>

<div class="secondaryMenu">
	<ul id="secondaryMenu">
            <li> <a href="javascript:void(0);" onclick="window.location.href='/Keyworder2/secure/AddDomainRequest.do'">Add domain</a></li>

	</ul>
</div>


<%@include file="/includes/messaging.jsp" %>



<div class="content">
        <logic:present name = "SessionDomains" >
        <table border="1" cellspacing="0" cellpadding="5">


            <logic:iterate name="SessionDomains" id="Domain" >

                <tr>

                    <td class="info" style="color: #00FF00"><bean:write name="Domain" property="name" /></td>
                    <td class="info"><a href='/Keyworder2/secure/Keywords.do?domainId=<bean:write name="Domain" property="id" />'>Keywords & search</a></td>
                    <td class="info"><a href='/Keyworder2/secure/ViewRankingsByMonth.do?domainId=<bean:write name="Domain" property="id" />'>Rankings by month</a></td>
                    <td class="info"><a href='/Keyworder2/secure/SelectRequest.do?domainId=<bean:write name="Domain" property="id" />'>Rankings by request</a></td>


                </tr>
            </logic:iterate>

        </table>

    </logic:present>


</div>

<%@include file="/includes/bottomDetails.jsp" %>