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
        //var url = window.location.href;

        // Add export=true on the end
        //url+="?export=true";

        // Redirect the user to the new page
        //window.location.href=url;
        document.requestForm.export.value="true";
        document.requestForm.submit();
    }



</script>

<div class="pageTitle">
<bean:write name="SessionDomain" property="name" /><br>

</div>
<div class="pageSubTitle">
rankings by request
</div>
<div class="secondaryMenu">
	<ul id="secondaryMenu">
            <li> <a href="javascript:void(0);" onclick="javascript:doExport()">Export</a></li>

	</ul>
</div>

<div class="content">

    <form name="requestForm" action="" enctype="multipart/form-data" method="post">
        <logic:present name = "RequestRankings" >
        <table border=0 cellspacing="0" cellpadding="5">

            <tr><th><a href="javascript:void(0)" onclick="document.requestForm.action='ViewRankingsByRequest.do'; document.requestForm.submit();">Keyphrase</a></th>
	    <th width="300"><a href="javascript:void(0)" onclick="document.requestForm.action='ViewRankingsByRequest.do?orderBy=ranking'; document.requestForm.submit();">Ranking at <bean:write name="RequestRequest" format='dd-MM-yyyy HH:mm:ss' property="date"/></a></th></tr>

            <logic:iterate name="RequestRankings" id="Ranking" >

                <tr >
                    <td class="info" width="200"><bean:write name="Ranking" property="keyword.sentenceName"/></td>
                    <td class="info" width="200"><bean:write name="Ranking" property="ranking" /></td>
                </tr>
            </logic:iterate>
            <input  name="requestId" type="hidden" value="<bean:write name="RequestRequest" property="id"/>">
	    <input name="export" value="export" type="hidden">

        </table>

        </logic:present>
    </form>
</div>
</body>
</html>