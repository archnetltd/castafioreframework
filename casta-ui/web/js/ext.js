var requestId=0;
var loadingTimeout;
var loadingActive=-1;
function getBrowserWidth(){
	var myWidth=0;
	if(typeof (window.innerWidth)=="number"){myWidth=window.innerWidth
}else{if(document.documentElement&&(document.documentElement.clientWidth||document.documentElement.clientHeight)){myWidth=document.documentElement.clientWidth
}else{if(document.body&&(document.body.clientWidth||document.body.clientHeight)){myWidth=document.body.clientWidth
}}}return myWidth
}function getBrowserHeight(){var myHeight=0;
if(typeof (window.innerWidth)=="number"){myHeight=window.innerHeight
}else{if(document.documentElement&&(document.documentElement.clientWidth||document.documentElement.clientHeight)){myHeight=document.documentElement.clientHeight-37
}else{if(document.body&&(document.body.clientWidth||document.body.clientHeight)){myHeight=document.body.clientHeight
}}}return myHeight
}	

$.fn.scrollable = function(options){
		var el = $(this);
		var h = el.attr('scrolheight');
		if(el.scrollTop < el.scrollHeight - h)
		    return;
		var params = {'casta_applicationid' : el.attr('appid'), 'casta_eventid': el.attr('eventid'), 'casta_componentid':el.attr('id')};
		sCall(params);
};
	
	
$.fn.castafiore = function(app){
		var me = $(this);
		me.append("<div id='root_"+app+"'>");
		me.append("<div id='script_"+app+"'>");
		$("#script_" + app).mask('Please wait....');
		var url = "castafiore/?casta_applicationid=" + app;
		var curUrl = window.location.href;
		var p = curUrl.split('?')[1];
		if(p){
			url = url +'&' + p;
		}
		$.ajax({url: url,type: 'POST',dataType: 'text',success: function(data){$("#script_" + app).html(data);}});		
};
	
function loading(){
	if(loadingActive==-1){
		loadingTimeout = setTimeout('$.blockUI({message:"<h3 class=\'ui-widget-header\'>Please wait...</h3>"})', 1000); 
		loadingActive = 1;
	}
}

function hideloading(){
	$.unblockUI();
	if(loadingActive==1){
		loadingActive = -1;
		if (loadingTimeout) clearTimeout(loadingTimeout);
	}
}

function sCall(params)
{	
	loading();
	$('textarea').each(function(i){
		try{
			var ed = CKEDITOR.instances[$(this).attr('id')];
			if(ed != undefined){
				params["casta_value_"+$(this).attr("id")]=ed.getData();
			}
		}catch(err){
			//alert(err);
		}
	});
	$(':checkbox').each(function(i){
		if($(this).is(':checked')){
			params["casta_value_"+$(this).attr("id")]= "checked";
		}else{
			params["casta_value_"+$(this).attr("id")]= "";
		}

	});
	$(" *[stf]").each(function(i){
		if(params["casta_value_"+$(this).attr("id")] == undefined)
			params["casta_value_"+$(this).attr("id")]=$(this).attr("value");
	});
	params.requestId=requestId++;

	$.post("castafiore/"+requestId+".jsp",params,function(data){
		$("#script_"+params['casta_applicationid']).append(data);
	}, "text")
}