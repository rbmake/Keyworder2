<%@ include file="/includes/topDetails.jsp" %>
<title>Domains</title>
<%@ include file="/includes/topScript.jsp" %>

<bean:define id="menu" value="domains"/>
<body>
<%@include file="/includes/logo.jsp" %>
<%@include file="/includes/notLoggedInMenu.jsp" %>
<div class="pageTitle">
Register
</div>




<%@include file="/includes/messaging.jsp" %>



<div class="content">
<table border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td width="766" height="1" align="left" valign="top"><table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="766" height="1"><img src="/media/line_dotted.gif" width="766" height="1" /></td>
                </tr>
                <tr>
                    <!-- Need to replace with an image -->
                    <td width="766" height="1" class="info">Please register your details below</td>
                </tr>

                <tr>
                    <td height="1"><table border="0" cellspacing="0" cellpadding="0">
                            <tr align="left" valign="top">

                                <!-- Set each of the following 4 widths to govern width of right hand table -->
                                <td width="300" height="18" class="bodyVeryLightGrey">
                                    <table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="300" height="1"><img src="/media/register.gif" width="255" height="26" /></td>
                                        </tr>
                                        <!--tr>
                                            <td class="info">
                                                Enter your details here if you have not registered with us before
                                            </td>
                                        </tr-->

                                        <tr>
                                            <td width="300" height="194" align="left" valign="top">
                                                <table border="0" cellspacing="0" cellpadding="0">
                                                    <tr>

                                                        <td height="18" align="left" valign="top" >
                                                            <!-- Register-->
                                                            <html:form action="/secure/Register" method="post" enctype="multipart/form-data">
                                                                <table border="0" cellspacing="0" cellpadding="0">
                                                                    <tr align="left" valign="middle">
                                                                        <td width="120" height="1" class="bodyWhite">Title</td>
                                                                        <td width="174">
                                                                            <html:select property="titleId" styleClass="selectbox" >
                                                                                <html:options collection="ServletTitles" property="id" labelProperty="name"/>
                                                                            </html:select>
                                                                        </td>
                                                                    </tr>
								    <tr><td class="spacer"></td></tr>
                                                                    <tr align="left" valign="middle">
                                                                        <td width="101" height="1" class="bodyWhite">First Name*</td>
                                                                        <td width="174">
                                                                            <html:text property="firstName"  title="First name" styleClass="entrybox"/>
                                                                        </td>
                                                                    </tr>
								     <tr><td class="spacer"></td></tr>
                                                                    <tr align="left" valign="middle">
                                                                        <td width="101" height="18" class="bodyWhite">Surname*</td>
                                                                        <td width="159">
                                                                            <html:text property="surname" title="Surname" styleClass="entrybox"/>
                                                                        </td>
                                                                    </tr>
								     <tr><td class="spacer"></td></tr>
                                                                    <tr align="left" valign="middle">
                                                                        <td width="101" height="18" class="bodyWhite" >Email*</td>
                                                                        <td width="159">
                                                                            <html:text property="registerEmail" title="Email" styleClass="entrybox"/>
                                                                        </td>
                                                                    </tr>
								     <tr><td class="spacer"></td></tr>
                                                                    <tr align="left" valign="middle">
                                                                        <td width="101" height="18" class="bodyWhite" >Password*</td>
                                                                        <td width="159">
                                                                            <html:password property="registerPassword" title="Password" styleClass="entrybox"/>
                                                                        </td>
                                                                    </tr>
								     <tr><td class="spacer"></td></tr>
                                                                    <tr align="left" valign="middle">
                                                                        <td width="101" height="18" class="bodyWhite" >Telephone Number</td>
                                                                        <td width="159">
                                                                            <html:text property="telephoneNumber" title="Telephone number" styleClass="entrybox"/>
                                                                        </td>
                                                                    </tr>
								     <tr><td class="spacer"></td></tr>
                                                                    <tr align="left" valign="middle">
                                                                        <td width="101" height="18" class="bodyWhite" >Receive newsletter?</td>
                                                                        <td width="159" align="left">
                                                                            <html:checkbox property="receiveEmailNewsletter" title="Receive email newsletter" styleClass="checkbox"/>
                                                                        </td>
                                                                    </tr>
                                                                  <tr><td class="spacer"></td></tr>
                                                                    <tr>
                                                                        <td align=right colspan=3><input type="image" value="submitname" src="/MG/media/general/registerSubmitTransparentCropped.gif" border="0" alt="register" name="registerSubmit">
                                                                    </tr>
                                                                </table>

                                                            </html:form>
                                                        </td>
                                                    </tr>
                                            </table></td>
                                        </tr>
                                </table></td>

                            </tr>
                    </table></td>
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
    <tr><td height=100>&nbsp;</td></tr>
<%@ include file="/includes/bottomDetails.jsp" %>