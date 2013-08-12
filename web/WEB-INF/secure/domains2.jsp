<%@ include file="/includes/topDetails.jsp" %>
<title>Keyphrases</title>
<%@ include file="/includes/topScript.jsp" %>

<bean:define id="menu" value="domains"/>
<body>
<%@include file="/includes/logo.jsp" %>
<%@include file="/includes/topMenu.jsp" %>

<script>

    function uncheckAll()
    {
        var elem = document.getElementById('keyphraseForm').elements;
        for (var i=0; i<elem.length; i++)
            {
                elem[i].checked=false;
            }
    }

    function checkAll()
    {
        var elem = document.getElementById('keyphraseForm').elements;
        for (var i=0; i<elem.length; i++)
            {
                elem[i].checked=true;
            }
    }

</script>

<%@include file="/includes/messaging.jsp" %>

<div class="content">
  <logic:present name = "SessionDomains" >
        <table border="1" cellspacing="0" cellpadding="5">

            <th>Domain</th>
            <th></th>
            <th></th>
            <th></th>


            <logic:iterate name="SessionDomains" id="Domain" >

                <tr class="bodyVeryLightGrey">

                    <td class="info"><a href='/Keyworder2/secure/Keywords.do?domainId=<bean:write name="Domain" property="id" />'><bean:write name="Domain" property="name" /></a></td>
                    <td class="info"><a href='/Keyworder2/secure/ViewRankingsByMonth.do?domainId=<bean:write name="Domain" property="id" />'>Rankings by month</a></td>
                    <td class="info"><a href='/Keyworder2/secure/ViewAllRankings.do?domainId=<bean:write name="Domain" property="id" />'>All rankings</a></td>
                    <td class="info"><a href='/Keyworder2/secure/DoSearch.do?domainId=<bean:write name="Domain" property="id" />'>Do Search</a></td>

                </tr>
            </logic:iterate>

        </table>
        </logic:present>
</div>

<%@include file="/includes/bottomDetails.jsp" %>

