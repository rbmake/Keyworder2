<%@ include file="/includes/topDetails.jsp" %>
<title>Domains</title>

<%@ include file="/includes/topScript.jsp" %>

<body onMouseMove="mouser(event, 'keywordRankings');">
<%@include file="/includes/logo.jsp" %>
<%@include file="/includes/topMenu.jsp" %>
<%@include file="/includes/messaging.jsp" %>

<div class="pageTitle">
<bean:write name="SessionDomain" property="name" /><br>

</div>
<div class="pageSubTitle">
rankings by month
</div>

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

<logic:present  name="RequestRankingHistories">
<div class="secondaryMenu">
	<ul id="secondaryMenu">
            <li> <a href="javascript:void(0);" onclick="javascript:doExport()">Export</a></li>

	</ul>
</div>
</logic:present>


<div class="content">
    <form action="">
        <logic:present name = "RequestRankingHistories" >
        <table border=0 cellspacing="0" cellpadding="5">


            <tr>

                <th>
                    Keyphrase
                </th>
                <logic:iterate name="RequestRankingMonths" id="MonthYear">
                    <th>
                        <a href="/Keyworder2/secure/ViewRankingsByMonth.do?domainId=<bean:write name="SessionDomain" property="id"/>&month=<bean:write name="MonthYear" property="id"/>"><bean:write name="MonthYear" property="name"/></a>
                    </th>
                </logic:iterate>

            </tr>
            <logic:iterate name="RequestRankingHistories" id="RankingHistory" >

                <tr >
                    <td class="info" width="200"><bean:write name="RankingHistory" property="keyword.sentenceName" /></td>
                    
                    <!-- Iterate through each ranking in the RankingHistory -->
                    <logic:iterate name="RankingHistory" property="rankings" id="Ranking" >
                    <td class="info" width="70">
                        <a href="javascript:showKeywordRankings('Rankings for &quot;<bean:write name='Ranking' property='keyword.name' />&quot;&nbsp;on&nbsp;<bean:write name="Ranking" property="timestamp" format="MMM yy"/>'  , <bean:write name="Ranking" property="keyword.id" />,<bean:write name="Ranking" property="request.id" />)">
                        <bean:write name="Ranking" property="ranking" />
                        </a>
                     </td>
                    </logic:iterate>
                </tr>
            </logic:iterate>

        </table>

        </logic:present>
    </form>
</div>
<div id="keywordRankings" class="rounded" style="position:absolute; top:100px; left: 300px; visibility: hidden; width:750px; height:650px; z-index:100; background-color: #1553ee; padding: 20px;  cursor:move;" onMouseDown="mouse_down('keywordRankings')" onMouseUp="mouse_up()">

</div>
</body>
</html>