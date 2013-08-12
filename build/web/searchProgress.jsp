<%-- 
    Document   : searchProgress
    Created on : Apr 2, 2010, 1:17:46 PM
    Author     : robbrown
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Progress</title>
        <link href="/Keyworder2/includes/main.css" rel="stylesheet" type="text/css">
        <script language="JavaScript" src="/Keyworder2/includes/progress.js"></script>
    </head>
    <body>
        <div id="progressContainer">
            <div id="progress" style="width:3%;">3%</div>
        </div>

        <p>
            <input type="button" onclick="javascript:polling_start()" value="Start">
            <input type="button" onclick="javascript:polling_stop()" value="Stop">
        </p>
    </body>
</html>
