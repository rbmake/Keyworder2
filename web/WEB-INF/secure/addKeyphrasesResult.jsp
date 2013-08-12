<%@ include file="/includes/topDetails.jsp" %>
<title>Keyphrases</title>
<%@ include file="/includes/topScript.jsp" %>

<bean:define id="menu" value="domains"/>
<body>
<%@include file="/includes/logo.jsp" %>
<%@include file="/includes/topMenu.jsp" %>


<%@include file="/includes/messaging.jsp" %>


<div class="content">
    <span class="info">
         <bean:write name="RequestNumberKeyphrasesAdded"/>
         <logic:equal name="RequestNumberKeyphrasesAdded" value="1">
            keyphrase was added.
        </logic:equal>
        <logic:notEqual name="RequestNumberKeyphrasesAdded" value="1">
            keyphrases were added.
        </logic:notEqual>
    </span><p>
    <logic:present name="RequestRejectedKeyphrases">
      
        <span class="error">
            The following keyphrases were already present for this domain<p>
                <logic:iterate name="RequestRejectedKeyphrases" id="Keyphrase">
                    <bean:write name="Keyphrase" /><br>
                </logic:iterate>
        </span>
    </logic:present>
    <input type="button" value="Return to keyphrases" onclick="document.location='Keywords.do'">
</div>

<%@include file="/includes/bottomDetails.jsp" %>