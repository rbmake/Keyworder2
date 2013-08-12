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
        var elem = document.Keyphrase.elements;
        for (var i=0; i<elem.length; i++)
            {
                elem[i].checked=false;
            }
    }

    function checkAll()
    {
        var elem = document.Keyphrase.elements;
        for (var i=0; i<elem.length; i++)
            {
                elem[i].checked=true;
            }
    }

</script>

<%@include file="/includes/messaging.jsp" %>

<div class="pageTitle">
Domain: <bean:write name="SessionDomain" property="name" />
</div>

<div class="secondaryMenu">
	<ul id="secondaryMenu">
            <li> <a href="javascript:void(0);" onclick="document.Keyphrase.submit();">Search</a></li>
            <li> <a href="javascript:void(0);" onclick="javascript:uncheckAll()">Uncheck all</a></li>
            <li> <a href="javascript:void(0);"  onclick="javascript:checkAll()">Check all</a></li>
            <li> <a href="AddKeyphraseRequest.do" >Add keywords</a></li>
	</ul>
</div>

<div class="content">

   

    <html:form action='secure/DoSearch'>
        <input type="hidden" name="domainId" value='<bean:write name="SessionDomain" property="id" />'>
        <logic:present name = "RequestKeyphrases" >

        <table class="kTable" border=0 cellspacing="0" cellpadding="0">


            <logic:iterate name="RequestKeyphrases" id="Keyword" >

               <tr>
                   <td><input type="checkbox" value='<bean:write name="Keyword" property="id" />' name='keyphrase'
                   <logic:equal name="Keyword" property="doSearch" value="true">
                       checked="checked"
                   </logic:equal>

                   /></td>
                    <td width="200"><bean:write name="Keyword" property="sentenceName" /></td>
                    <td width="50"><a href="/Keyworder2/secureEditKeyphrase.do?keyphraseId=<bean:write name="Keyword" property="id" />">Edit</a></td>
                    <td width="50"><a href="/Keyworder2/secure/DeleteKeyphrase.do?keyphraseId=<bean:write name="Keyword" property="id" />">Delete</a></td>
                </tr>
            </logic:iterate>

        </table>

        </logic:present>
    </html:form>
</div>

<%@include file="/includes/bottomDetails.jsp" %>