<%@ include file="/includes/topDetails.jsp" %>
<title>Keyphrases</title>
<%@ include file="/includes/topScript.jsp" %>

<bean:define id="menu" value="domains"/>
<body>
<%@include file="/includes/logo.jsp" %>
<%@include file="/includes/topMenu.jsp" %>

<%@include file="/includes/messaging.jsp" %>



<div class="content">

    
        <input type="hidden" name="domainId" value='<bean:write name="SessionDomain" property="id" />'>
        <logic:present name = "RequestKeyphrases" >

        <table class="kTable" border=0 cellspacing="0" cellpadding="0">


            <logic:iterate name="RequestRankings" id="Ranking" >

               <tr>
                   
                    <td width="200"><bean:write name="Ranking" property="keyphrase.name" /></td>
                    <td width="50>"><bean:write name="Ranking" property="ranking" /></td>
                </tr>
            </logic:iterate>

        </table>

        </logic:present>
</div>

<%@include file="/includes/bottomDetails.jsp" %>