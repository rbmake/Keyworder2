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
 <html:form action='secure/TestCheckbox'>


        <table class="kTable" border=0 cellspacing="0" cellpadding="0">
            <tr><td><html:checkbox property="mountains" value="1"/></td><td>Everest</td></tr>
            <tr><td><html:checkbox property="mountains" value="2"/></td><td>K2</td></tr>

        </table>

        <input type="submit">
    </html:form>
</div>

<%@include file="/includes/bottomDetails.jsp" %>

