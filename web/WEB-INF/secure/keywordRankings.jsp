<%@ include file="/includes/topDetails.jsp" %>
<title>Keyword rankings</title>


</head>
    <body>
    	<bean:define id="domainName" name="SessionDomain" property="name" type="java.lang.String"/>
        <div style="position:absolute; top:20px; background-color: #CCCCCC"; >

        <table width="730px" border="1" cellspacing="0" cellpadding="1">
		<th style="background-color: #5e93e6;">Ranking</th>
		<th style="background-color: #5e93e6;">URL</th>
            <logic:iterate name="RequestKeywordRankings" id="Ranking">

	    	<tr
             <logic:match name="Ranking" property="url" value="${domainName}">
           style="background-color: #009b41;"</logic:match>
	   >
                    <td class="smallBlack">
                        <bean:write name="Ranking" property="ranking" />
                    </td>
                    <td class="smallBlack">
                        <bean:write name="Ranking" property="url" />
                    </td>
                </tr>


            </logic:iterate>
        </table>
        </div>
    </body>
</html>	