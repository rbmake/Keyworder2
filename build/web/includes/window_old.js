var WinNum=0;
function WindowOpen2(link,x,y)
{
var String;
String = "toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=0,resizable=0,copyhistory=0,";
String += ",width=";
String += x;
String += ",height=";
String += y;

WinPic=window.open(link,WinNum++,String);
}
//--->

function WindowOpen(link,x,y)
{
var String;
String = "toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=1,resizable=1,copyhistory=0,";
String += "width=";
String += x;
String += ",height=";
String += y;

newWindow=window.open(link,"subWind",String);
newWindow.focus();
}

function showKeywordRankings(keywordId, requestId)
{
    var el=document.getElementById("keywordRankings");
    el.innerHTML="<iframe src=\"/Keyworder2/secure/ShowKeywordRankings.do?keywordId="+keywordId+"&requestId="+requestId+"\"></iframe>";
    el.style.visibility= 'Visible';
}


