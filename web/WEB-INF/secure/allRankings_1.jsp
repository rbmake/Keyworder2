<%@ include file="/includes/topDetails.jsp" %>
<title>Rankings for domain <bean:write name='RequestDomain' property='name'/></title>
<bean:define id="menu" value="domains"/>
<script>
    <logic:present name="refresh">
    <bean:write name="refresh"/>
    </logic:present>
</script>
</head>
<body onMouseMove="mouser(event, 'keywordRankings');">
<%@include file="/includes/topMenu.jsp" %>


<div  style="position:absolute; left:10px; top:50px;">
<table cellpadding="0" cellspacing="5">
<logic:present name="RequestErrorMessage" scope="request">

    <tr>
        <td align="left" valign="top"><img src="/Keyworder2/media/pixelTransparent.gif" alt="sp" width="10" height="40" /></td>

        <td class="info"><bean:write name="RequestErrorMessage" property="text" /></td>
    </tr>
</logic:present>
</table>
</div>


<div style="position:absolute; left:10px; top:100px; z-index:1;">
    <form action="">
        <logic:present name = "RequestRankingHistories" >
        <table border=0 cellspacing="0" cellpadding="5">
            <tr class="bodyVeryLightGrey">
                <th></th>
                <th align="left">
                    Keyphrase
                </th>
                <logic:iterate name="RequestRankingRequests" id="Request">
                    <th>
                        <bean:write name="Request" property="date" format="dd MMM yy"/>
                    </th>
                </logic:iterate>

            </tr>

            <logic:iterate name="RequestRankingHistories" id="RankingHistory" >

                <tr class="bodyVeryLightGrey">
                    <td><input type="checkbox" name='<bean:write name="RankingHistory" property="keyword.id" />'></td>
                    <td class="info" width="200"><bean:write name="RankingHistory" property="keyword.name" /></td>

                    <!-- Iterate through each ranking in the RankingHistory -->
                    <logic:iterate name="RankingHistory" property="rankings" id="Ranking" >
                    <td class="info" width="80"><a href="javascript:showKeywordRankings('Rankings for &quot;<bean:write name='Ranking' property='keyword.name' />&quot;&nbsp;on&nbsp;<bean:write name="Request" property="date" format="dd MMM yy"/>'  , <bean:write name="Ranking" property="keyword.id" />,<bean:write name="Ranking" property="request.id" />)"><bean:write name="Ranking" property="ranking" /></a></td>
                    </logic:iterate>
                </tr>
            </logic:iterate>

        </table>

        </logic:present>
    </form>
</div>
<div id="keywordRankings" class="rounded" style="position:absolute; top:100px; left: 300px; visibility: hidden; width:750px; height:850px; opacity:0.6; filter:alpha(opacity=60); z-index:100; background-color: #1553ee; padding: 20px;  cursor:move;" onMouseDown="mouse_down('keywordRankings')" onMouseUp="mouse_up()">

</div>

</body>
</html> 