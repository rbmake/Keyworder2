
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
function MM_preloadImages() { //v3.0
var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_swapImgRestore() { //v3.0
var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_findObj(n, d) { //v4.01
var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}

var timeout	= 2500;
var closetimer	= 0;
var ddmenuitem	= 0;

// open hidden layer
function mopen(id)
{
	// cancel close timer
	mcancelclosetime();

	// close old layer
	if(ddmenuitem) ddmenuitem.style.visibility = 'hidden';

	// get new layer and show it
	ddmenuitem = document.getElementById(id);
	ddmenuitem.style.visibility = 'visible';

}
// close showed layer
function mclose()
{
	if(ddmenuitem) ddmenuitem.style.visibility = 'hidden';
}

// go close timer
function mclosetime()
{
	closetimer = window.setTimeout(mclose, timeout);
}

// cancel close timer
function mcancelclosetime()
{
	if(closetimer)
	{
		window.clearTimeout(closetimer);
		closetimer = null;
	}
}

// close layer when click-out
document.onclick = mclose;


////////////// DRAGGABLE DIV //////////////////


var x;
var y;
var element;
var being_dragged = false;
function mouser(event, divElement){
	if(event.offsetX || event.offsetY) {
		x=event.offsetX;
		y=event.offsetY;
	}
	else {
		x=event.pageX;
		y=event.pageY;
	}
	//document.getElementById('X').innerHTML = x +'px';
	//document.getElementById('Y').innerHTML = y +'px';
	//document.getElementById('X-coord').innerHTML = x +'px';
	//document.getElementById('Y-coord').innerHTML = y +'px';
	if(being_dragged == true) {
	document.getElementById(divElement).style.left = x +'px';
	document.getElementById(divElement).style.top = y +'px';
	}
}

function mouse_down(ele_name) {
being_dragged = true;
element = ele_name;
document.getElementById(element).style.cursor = 'move';
}

function setCursorXY(event)
{
    if(event.offsetX || event.offsetY) {
		x=event.offsetX;
		y=event.offsetY;
	}
	else {
		x=event.pageX;
		y=event.pageY;
	}
}


function mouse_up() {
being_dragged = false;
document.getElementById(element).style.top = y +'px';
document.getElementById(element).style.left = x +'px';
document.getElementById(element).style.cursor = 'auto';
}


function showKeywordRankings(title, keywordId, requestId)
{
    var el=document.getElementById("keywordRankings");
    el.style.left=(x + 50) + 'px';
    el.style.top=(y - 50) + 'px';
    // First add the close DIV button
    el.innerHTML="<div style=\"width:20px; height:20px; position:absolute; top:0px; left:0px; border: 0px solid; text-align:right;\">" +
         "<a href=\"#\" onclick=\"getElementById('keywordRankings').style.visibility='Hidden'\">X</a></div>";
    // Add the title
    el.innerHTML+="<div class='title' style=\"width:500px; position:absolute; top:0px; left:20px;\">&nbsp;&nbsp;"+title+"</div>";
    // Show the keyword rankings div
    el.innerHTML+="<div style=\"height:10px\"></div>"  +
         "<iframe style=\"width:750px; height:650px; margin:0; padding:0; border:0px; color:black;\"  src=\"/Keyworder2/secure/ShowKeywordRankings.do?keywordId="+
         keywordId+"&requestId="+requestId+"\"></iframe>";
    el.style.visibility= 'Visible';
}

function showKeywordRankingsByMonth(title, keywordId, monthYear)
{
    var el=document.getElementById("keywordRankings");
    el.style.left=(x + 50) + 'px';
    el.style.top=(y - 50) + 'px';
    // First add the close DIV button
    el.innerHTML="<div style=\"width:20px; height:20px; position:absolute; top:0px; left:0px; border: 0px solid; text-align:right;\">" +
         "<a href=\"#\" onclick=\"getElementById('keywordRankings').style.visibility='Hidden'\">X</a></div>";
    // Add the title
    el.innerHTML+="<div class='title' style=\"width:500px; position:absolute; top:0px; left:20px;\">"+title+"</div>";
    // Show the keyword rankings div
    el.innerHTML+="<div style=\"height:10px\"></div>"  +
         "<iframe style=\"width:750px; height:850px; margin:0; padding:0; border:0px;\"  src=\"/Keyworder2/secure/ShowKeywordRankingsByMonth.do?keywordId="+
         keywordId+"&monthYear="+monthYear+"\"></iframe>";
    el.style.visibility= 'Visible';
}


function hideKeywordRankings()
{
    var el = getElementById('keywordRankings');
    el.style.visibility='Hidden';

}