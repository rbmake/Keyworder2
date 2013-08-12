<%@ include file="/includes/topDetails.jsp" %>
<title>Keyworder</title>
<%@ include file="/includes/topScript.jsp" %>

<%@include file="/includes/logo.jsp" %>

<div style="position:absolute; left:10px; top:150px;">
<table border="0" cellspacing="0" cellpadding="0">
<tr> 
    <td width="766" height="1" align="left" valign="top"><table border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td width="766" height="1"><img src="/Keyworder2/media/line_dotted.gif" width="766" height="1" /></td>
            </tr>
            <tr>
                <!-- Need to replace with an image -->
                <td width="766" height="1" class="statement">Login</td>
            </tr>
            <tr>
                <td width="766" height="1"><img src="/Keyworder2/media/line_dotted.gif" width="766" height="1" /></td>
            </tr>
            <tr>
                <td height="1"><img src="/Keyworder2/media/pixelTransparent.gif" alt="sp" width="766" height="4" /></td>
            </tr>
            <tr><td class="error">
                    <logic:present name="RequestErrorMessage" scope="request">
                        <bean:write name="RequestErrorMessage" property="text" filter="false"/>
                    </logic:present>
                    <html:errors/>
            </td></tr>
            <tr>
                <td height="1"><table border="0" cellspacing="0" cellpadding="0">
                        <tr align="left" valign="top">
                            <td width="300" height="18" class="bodyVeryLightGrey">
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="300" height="1"><img src="/Keyworder2/media/login.gif" width="255" height="26" /></td>
                                    </tr>

                                    <tr>
                                        <td width="300" height="1"><img src="/Keyworder2/media/pixelTransparent.gif" alt="sp" width="255" height="9" /></td>
                                    </tr>
                                    <tr>
                                        <td width="300" height="194" align="left" valign="top"><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="6" height="18"><img src="/Keyworder2/media/pixelTransparent.gif" alt="sp" width="6" height="194" /></td>
                                                    <td height="18" align="left" valign="top" >
                                                        <!-- Login -->
                                                        <html:form action="/secure/Login" method="post" enctype="multipart/form-data">
                                                            <table border="0" cellspacing="0" cellpadding="0">
                                                                <tr align="left" valign="middle">
                                                                    <td width="120" height="1" class="bodyWhite">Email</td>
                                                                    <td width="174">
                                                                        <html:text property="email" styleClass="entrybox" />
                                                                    </td>
                                                                </tr>

                                                                <tr align="left" valign="middle">
                                                                    <td width="101" height="18" class="bodyWhite">Password</td>
                                                                    <td width="159">
                                                                        <html:password property="password" styleClass="entrybox" />
                                                                    </td>
                                                                </tr>
                                                                <tr align="left" valign="middle">
                                                                    <td height="18" colspan="2"><img src="/Keyworder2/media/pixelTransparent.gif" alt="sp" width="242" height="24" /></td>
                                                                    <td>&nbsp;</td>
                                                                </tr>
                                                                <tr>
                                                                    <td align=left colspan=3 class="bodyWhite"><a href="/Keyworder2/secure/PasswordReminderRequest.do">Forgotten password?</a></td>
                                                                </tr>

                                                                <tr height=100></tr>
                                                                <tr>
                                                                    <td align=right colspan=3><input type="image" value="submitname" src="/Keyworder2/media/loginSubmitTransparentCropped.gif" border="0" alt="login" name="loginSubmit">
                                                                </tr>

                                                            </table>

                                                        </html:form>
                                                        <!-- End login -->
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                           
                        </tr>
                </table></td>
            </tr>
            <tr>
                <td height="1"><img src="/Keyworder2/media/pixelTransparent.gif" alt="sp" width="766" height="4" /></td>
            </tr>
            <tr>
                <td width="766" height="1"><img src="/Keyworder2/media/line_dotted.gif" width="766" height="1" /></td>
            </tr>
            <tr>
                <td height="1">&nbsp;</td>
            </tr>
            <tr>
                <td height="1">&nbsp;</td>
            </tr>
            <tr>
                <td height="1">&nbsp;</td>
            </tr>
    </table></td>
</tr>
</table>
</div>
<%@ include file="/includes/bottomDetails.jsp" %>
