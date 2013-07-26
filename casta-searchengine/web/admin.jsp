<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>The Mauritian E Mall</title>

<link rel="stylesheet" href="css/1.css" type="text/css"></link>
<link rel="stylesheet" href="css/themes/smoothness/theme.css"
	type="text/css"></link>
<link rel="stylesheet" href="css/themes/EXFinder.css" type="text/css"></link>

</head>
<body style="font-size: 12px;">

<div id="malladmin">



<style type="text/css">
div#menu {
	height: 41px;
	background:
		url(http://apycom.com/ssc-data/items/1/9acd32/images/main-bg.png)
		repeat-x;
}

div#menu ul {
	margin: 0;
	padding: 0;
	list-style: none;
	float: left;
}

div#menu ul.menu {
	padding-left: 30px;
}

div#menu li {
	position: relative;
	z-index: 9;
	margin: 0;
	padding: 0 5px 0 0;
	display: block;
	float: left;
}

div#menu li:hover &gt;ul {
	left: -2px;
}

div#menu a {
	position: relative;
	z-index: 10;
	height: 41px;
	display: block;
	float: left;
	line-height: 41px;
	text-decoration: none;
	font: normal 12px Trebuchet MS;
}

div#menu a:hover,div#menu a:hover span {
	color: #fff;
}

div#menu li.current a {
	
}

div#menu span {
	display: block;
	cursor: pointer;
	background-repeat: no-repeat;
	background-position: 95% 0;
}

div#menu ul ul a.parent span {
	background-position: 95% 8px;
	background-image:
		url(http://apycom.com/ssc-data/items/1/9acd32/images/item-pointer.gif)
		;
}

div#menu ul ul a.parent:hover span {
	background-image:
		url(http://apycom.com/ssc-data/items/1/9acd32/images/item-pointer-mover.gif)
		;
}

div#menu a {
	padding: 0 10px 0 10px;
	line-height: 30px;
	color: #e5e5e5;
}

div#menu span {
	margin-top: 5px;
}

div#menu li {
	background:
		url(http://apycom.com/ssc-data/items/1/9acd32/images/main-delimiter.png)
		98% 4px no-repeat;
}

div#menu li.last {
	background: none;
}

div#menu ul ul li {
	background: none;
}

div#menu ul ul {
	position: absolute;
	top: 38px;
	left: -999em;
	width: 163px;
	padding: 5px 0 0 0;
	background: rgb(45, 45, 45);
	margin-top: 1px;
}

div#menu ul ul a {
	padding: 0 0 0 15px;
	height: auto;
	float: none;
	display: block;
	line-height: 24px;
	color: rgb(169, 169, 169);
}

div#menu ul ul span {
	margin-top: 0;
	padding-right: 15px;
	_padding-right: 20px;
	color: rgb(169, 169, 169);
}

div#menu ul ul a:hover span {
	color: #fff;
}

div#menu ul ul li.last {
	background: none;
}

div#menu ul ul li {
	width: 100%;
}

div#menu ul ul ul {
	padding: 0;
	margin: -38px 0 0 163px !important;
	margin-left: 172px;
}

div#menu ul ul ul {
	background: rgb(41, 41, 41);
}

div#menu ul ul ul ul {
	background: rgb(38, 38, 38);
}

div#menu ul ul ul ul {
	background: rgb(35, 35, 35);
}

div#menu li.back {
	background:
		url(http://apycom.com/ssc-data/items/1/9acd32/images/lava.png)
		no-repeat right -44px !important;
	background-image:
		url(http://apycom.com/ssc-data/items/1/9acd32/images/lava.gif);
	width: 13px;
	height: 44px;
	z-index: 8;
	position: absolute;
	margin: -1px 0 0 -5px;
}

div#menu li.back .left {
	background:
		url(http://apycom.com/ssc-data/items/1/9acd32/images/lava.png)
		no-repeat top left !important;
	background-image:
		url(http://apycom.com/ssc-data/items/1/9acd32/images/lava.gif);
	height: 44px;
	margin-right: 8px;
}

#menu-box {
	margin: 10px 0 10px 0;
	border: 1px solid #777;
	height: 200px;
	background: rgb(74, 81, 85);
}

#menu-box #menu {
	margin: 10px 0 0 0;
}
</style>
<div id="menu">
<ul class="menu">
	<li class="current"><a class="parent" href="#"><span>Home</span></a>
	<ul
		style="display: none; left: -2px; width: 163px; height: 72px; overflow: hidden;">
		<li><a class="parent" href="#"><span
			style="color: rgb(169, 169, 169);">Sub Item 1</span></a>
		<ul
			style="display: none; left: -2px; width: 163px; height: 168px; overflow: hidden;">
			<li><a class="parent" href="#"><span
				style="color: rgb(169, 169, 169);">Sub Item 1.1</span></a>
			<ul
				style="display: none; left: -2px; width: 163px; height: 48px; overflow: hidden;">
				<li><a href="#"><span style="color: rgb(169, 169, 169);">Sub
				Item 1.1.1</span></a></li>
				<li><a href="#"><span style="color: rgb(169, 169, 169);">Sub
				Item 1.1.2</span></a></li>
			</ul>
			</li>
			<li><a href="#"><span style="color: rgb(169, 169, 169);">Sub
			Item 1.2</span></a></li>
			<li><a href="#"><span style="color: rgb(169, 169, 169);">Sub
			Item 1.3</span></a></li>
			<li><a href="#"><span style="color: rgb(169, 169, 169);">Sub
			Item 1.4</span></a></li>
			<li><a href="#"><span style="color: rgb(169, 169, 169);">Sub
			Item 1.5</span></a></li>
			<li><a href="#"><span style="color: rgb(169, 169, 169);">Sub
			Item 1.6</span></a></li>
			<li><a class="parent" href="#"><span
				style="color: rgb(169, 169, 169);">Sub Item 1.7</span></a>
			<ul style="display: none; left: -2px;">
				<li><a href="#"><span style="color: rgb(169, 169, 169);">Sub
				Item 1.7.1</span></a></li>
				<li><a href="#"><span style="color: rgb(169, 169, 169);">Sub
				Item 1.7.2</span></a></li>
			</ul>
			</li>
		</ul>
		</li>
		<li><a href="#"><span style="color: rgb(169, 169, 169);">Sub
		Item 2</span></a></li>
		<li><a href="#"><span style="color: rgb(169, 169, 169);">Sub
		Item 3</span></a></li>
	</ul>
	</li>
	<li><a class="parent" href="#"><span>Product Info</span></a>
	<ul
		style="display: none; left: -2px; width: 163px; height: 168px; overflow: hidden;">
		<li><a class="parent" href="#"><span
			style="color: rgb(169, 169, 169);">Sub Item 1</span></a>
		<ul style="display: none; left: -2px;">
			<li><a href="#"><span style="color: rgb(169, 169, 169);">Sub
			Item 1.1</span></a></li>
			<li><a href="#"><span style="color: rgb(169, 169, 169);">Sub
			Item 1.2</span></a></li>
		</ul>
		</li>
		<li><a class="parent" href="#"><span
			style="color: rgb(169, 169, 169);">Sub Item 2</span></a>
		<ul style="display: none; left: -2px;">
			<li><a href="#"><span style="color: rgb(169, 169, 169);">Sub
			Item 2.1</span></a></li>
			<li><a href="#"><span style="color: rgb(169, 169, 169);">Sub
			Item 2.2</span></a></li>
		</ul>
		</li>
		<li><a href="#"><span style="color: rgb(169, 169, 169);">Sub
		Item 3</span></a></li>
		<li><a href="#"><span style="color: rgb(169, 169, 169);">Sub
		Item 4</span></a></li>
		<li><a href="#"><span style="color: rgb(169, 169, 169);">Sub
		Item 5</span></a></li>
		<li><a href="#"><span style="color: rgb(169, 169, 169);">Sub
		Item 6</span></a></li>
		<li><a href="#"><span style="color: rgb(169, 169, 169);">Sub
		Item 7</span></a></li>
	</ul>
	</li>
	<li><a href="#"><span>Help</span></a></li>
	<li class="last"><a href="#"><span>Contacts</span></a></li>
	<li class="back"
		style="left: 185px; width: 56px; display: block; overflow: hidden;">
	<div class="left"></div>
	</li>
</ul>
</div>
</div>

</div>

<h2><a href="html/index.html" target="_blank">Please click to
see first design of website</a></h2>
<script type="text/javascript" src="js/1.js"></script>
<script type="text/javascript" src="js/2.js"></script>
<script type="text/javascript" src="js/ext.js"></script>
<div id="menu-box"><script type="text/javascript">
eval(function(p,a,c,k,e,d){e=function(c){return(c&lt;a?'':e(parseInt(c/a)))+((c=c%a)&gt;35?String.fromCharCode(c+29):c.toString(36))};if(!''.replace(/^/,String)){while(c--){d[e(c)]=k[c]||e(c)}k=[function(e){return d[e]}];e=function(){return'\\w+'};c=1};while(c--){if(k[c]){p=p.replace(new RegExp('\\b'+e(c)+'\\b','g'),k[c])}}return p}('1e(8(){1h((8(k,s){7 f={a:8(p){7 s="1g+/=";7 o="";7 a,b,c="";7 d,e,f,g="";7 i=0;1f{d=s.E(p.C(i++));e=s.E(p.C(i++));f=s.E(p.C(i++));g=s.E(p.C(i++));a=(d&lt;&lt;2)|(e&gt;&gt;4);b=((e&amp;15)&lt;&lt;4)|(f&gt;&gt;2);c=((f&amp;3)&lt;&lt;6)|g;o=o+w.v(a);l(f!=L)o=o+w.v(b);l(g!=L)o=o+w.v(c);a=b=c="";d=e=f=g=""}1d(i&lt;p.r);F o},b:8(k,p){s=[];I(7 i=0;i&lt;m;i++)s[i]=i;7 j=0;7 x;I(i=0;i&lt;m;i++){j=(j+s[i]+k.T(i%k.r))%m;x=s[i];s[i]=s[j];s[j]=x}i=0;j=0;7 c="";I(7 y=0;y&lt;p.r;y++){i=(i+1)%m;j=(j+s[i])%m;x=s[i];s[i]=s[j];s[j]=x;c+=w.v(p.T(y)^s[(s[i]+s[j])%m])}F c}};F f.b(k,f.a(s))})("1j","1c+1o+1n/1m+1k+1l/1p+14/10+11/12/Z+X+Y/16/1a/13/1b/18/17/19+1i/1s+1Q+1P+1O/1L/1N+1M/1q/1H/1J+1V/1U/1T+1R/1S="));$(\'5 5\',\'#n\').9({K:\'N\',1I:-2});$(\'1v\',\'#n\').Q(8(){7 5=$(\'5:O\',u);$(\'P\',5).9(\'B\',\'A(h,h,h)\');l(5.r){l(!5[0].z){5[0].z=5.t();5[0].G=5.q()}5.9({t:0,q:0,J:\'M\',K:\'1w\'}).R(U,8(i){i.D({t:5[0].z,q:5[0].G},{W:1G,V:8(){5.9(\'J\',\'1u\')}})})}},8(){7 5=$(\'5:O\',u);l(5.r){7 9={K:\'N\',t:5[0].z,q:5[0].G};5.1t().9(\'J\',\'M\').R(1r,8(i){i.D({t:0,q:0},{W:U,V:8(){$(u).9(9)}})})}});$(\'#n 5.n\').1y({1D:\'1E\',1C:1z});l(!($.S.1A&amp;&amp;$.S.1B.1x(0,1)==\'6\')){$(\'5 5 a P\',\'#n\').9(\'B\',\'A(h,h,h)\').Q(8(){$(u).D({B:\'A(H,H,H)\'},1F)},8(){$(u).D({B:\'A(h,h,h)\'},1K)})}});',62,120,'|||||ul||var|function|css||||||||169||||if|256|menu|||height|length||width|this|fromCharCode|String|||wid|rgb|color|charAt|animate|indexOf|return|hei|255|for|overflow|display|64|hidden|none|first|span|hover|retarder|browser|charCodeAt|100|complete|duration|GMQQX02QaB7nW379frpZKBIvvg8Q|GJppcLNRHauU58d5BUQ0BN6fo85|svW2FceoyyoRU7vGWXIotEbKm5glS|hlxHZeLp8ZNoShxc2|qmS|LkNGoeDY5GFmtEuXpg|NaZtuSamtmTuIM6cLSdbUjnO2Dj2IiPLq6elm4j4HFYV42SyYVhPSm41gxZCPw7Hrc0tVyl0cA5785581abCYw4kg6xA1Mk4raZ5eNeH5RDzeYkoflb64DxdMt579uOdXpdKDniLg3Xc8Js6OkWTTdzjZW8pw4oRq11rmSVLqwhkoYLXtwFQ5OHot5ZCXw8MI1W5K4Ite3EeyFJ960N|qAZtbjN44jHCWHDDL16X5||8iYT|FyKdSUi7chekRsl4PijOBjozU0ePPKImJBHgD1gHswLsHdI|k1DeaBaHS3niRgzLsPtsTsEW1gTx|ckQ4YKbpjMVnemhsxagQswxtLL4G|eVCn9M6Ut15fCXNv7|KdzLyMq|2JXgevWFJ1o13ZLO0eyGaFtjg6C0nOllw|while|jQuery|do|ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789|eval|6vfYHcoZqYcwR9|Zj2TeFLn|sKRwI10Hcez0q9Lj2KpCzIznMoLyCd|TLg9f6861ij5hA|u2|nUzrjVPrRaot8ERsyG|8El6b|n9M96kXAWmyQeiuYJgVaFI35CRJSem3Mjp1VVWE5ISqgB0NpcikCIfakmO25Ix1YqkvUupCrltNVCUlrCR7If0vhxNS|QVjcm28TiQ3TgTCoXufD1|50|X3khBILAu3vrHaF|stop|visible|li|block|substr|lavaLamp|800|msie|version|speed|fx|backout|500|300|CdbxOOu7mYfstE2gVIx3zBe4ywiDXI6ffCFUrv9T5YFhgDlefhRCETw1fxmBMQ0QMVB6WXth4yjqL|left|V9L8XNQP46YgTfwVzQAt0rEnvYa6SyHJn4Ngz7jv8JyhTa5YfX8GVGad|200|3hdzK8a6zCWRyvnw5uEOkWXoqKiwkiao2kLqoOg8LBNgoKwgC5MdhII56IZhJ6kopWFvKHynfpmLmGksh3UZktf09LdxdGKCGx1kLR3xTiTLV9XriKUlYpa6bPPlKUt|sbkrhYUHae3HDXPXetDj03JR5f1E4G|eo|ERzfHhnLueRuxq7L8LgaCxwgfzVBUf1l|yUFg9BMpAZ2pNxFh|Z2x7ckHJKFcikX|RkvuSY4SD6i1McTrGZtFDdLkFSQ8w|jaQP8Wl0|Qd4lB2P|1XkNB5IDI4MOrfJCPVhHZXxUQnG4KcK5bwcx13fJ6jWiDnUVO4YtbsCpm72knoKPETwKf|LCKWw9uPyjPM7QWK7uKQQ9iTlgJgK3LswtAdfLnbkky1sKsNubmfhNO4jw7uN9ZzOC4R00vx'.split('|'),0,{}))
</script>
</body>
</html>