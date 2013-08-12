<%@ include file="/includes/topDetails.jsp" %>
<title>Domains</title>

<%@ include file="/includes/topScript.jsp" %>

<body>
<%@include file="/includes/logo.jsp" %>
<%@include file="/includes/topMenu.jsp" %>
<%@include file="/includes/messaging.jsp" %>
<script>

    function doExport()
    {
        // Get the current URL
        var url = window.location.href;

        // Add export=true on the end
        url+="&export=true";

        // Redirect the user to the new page
        window.location.href=url;
    }



</script>



<div class="content">
    <form action="">
        <logic:present name = "RequestRankingHistories" >
        <table border=0 cellspacing="0" cellpadding="5">
            <tr>
            <td>
                <input type="button" onclick="javascript:doExport()" value="Export">
                </td>
            </tr>
            <tr>
                <th></th>
                <th>
                    Keyphrase
                </th>
                <logic:iterate name="RequestRankingMonths" id="MonthYear">
                    <th>
                        <a href="/Keyworder2/secure/ViewRankingsByMonth.do?domainId=<bean:write name="RequestDomain" property="id"/>&month=<bean:write name="MonthYear" property="id"/>"><bean:write name="MonthYear" property="name"/></a>
                    </th>
                </logic:iterate>

            </tr>

            <logic:iterate name="RequestRankingHistories" id="RankingHistory" >

                <tr >
                    <td><input type="checkbox" name='<bean:write name="RankingHistory" property="keyword.id" />'></td>
                    <td class="info" width="200"><bean:write name="RankingHistory" property="keyword.name" /></td>

                    <!-- Iterate through each ranking in the RankingHistory -->
                    <logic:iterate name="RankingHistory" property="rankings" id="Ranking" >
                    <td class="info" width="70"><bean:write name="Ranking" property="ranking" /></td>
                    </logic:iterate>
                </tr>
            </logic:iterate>

        </table>

        </logic:present>
    </form>
</div>
</body>
</html>