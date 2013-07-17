var requestId=0;
var loadingTimeout;
var loadingActive=-1;


$.fn.scrollable = function(options){
		var el = $(this);
		var h = el.attr('scrolheight');
		if(el.scrollTop < el.scrollHeight - h)
		    return;
		var params = {'casta_applicationid' : el.attr('appid'), 'casta_eventid': el.attr('eventid'), 'casta_componentid':el.attr('id')};
		sCall(params);
};


jQuery.getCSS = function( url ) {
    jQuery( document.createElement('link') ).attr({
        href: url,
        media:'screen',
        type: 'text/css',
        rel: 'stylesheet'
    }).appendTo('head');
};
	




$.fn.castafiore = function(params){
		
		var me = $(this);
		var app = me.attr('id');
		//jQuery.getCSS('castafiore/resource/classpath/org/castafiore/resource/css/1.css');
		//jQuery.getCSS('castafiore/resource/classpath/org/castafiore/resource/css/blueprint/screen.css');
		jQuery.getCSS('castafiore/resource/classpath/org/castafiore/resource/css/themes/aristo/theme.css');
		//jQuery.getCSS('castafiore/resource/classpath/org/castafiore/resource/css/themes/EXFinder.css');
		jQuery.getScript('castafiore/resource/classpath/org/castafiore/resource/js/jquery-ui-1.8.21.js');
		jQuery.getScript('castafiore/resource/classpath/org/castafiore/resource/js/jquery.maskedinput-1.3.js');
		jQuery.getScript('castafiore/resource/classpath/org/castafiore/resource/js/jquery.rightClick.js');
		jQuery.getScript('castafiore/resource/classpath/org/castafiore/resource/js/jquery.blockUI.js');
		//jQuery.getScript('http://68.68.109.26/upstage/ckeditor/ckeditor/ckeditor.js');
		jQuery.getScript('castafiore/resource/classpath/org/castafiore/resource/js/jquery.plugin.js',function(){
			me.append("<div id='root_"+app+"'>");
			me.append("<div id='script_"+app+"'>");
			var url = "castafiore/ui/?casta_applicationid=" + app;
			var curUrl = window.location.href;
			var p = curUrl.split('?')[1];
			if(p){
				url = url +'&' + p;
			}

			if(!params){
				params = {};
			}

			$.ajax({url: url,type: 'POST',dataType: 'text',data:params,success: function(data){$("#script_" + app).html(data);}});	
		});
			
};
	
function loading(){
	if(loadingActive==-1){
		//loadingTimeout = setTimeout('$.blockUI({message:"<h3 class=\'ui-widget-header\'>Please wait...</h3>"})', 1000); 
		loadingTimeout = setTimeout('$(".loader").fadeIn(100)', 10);
		loadingActive = 1;
	}
}

function hideloading(){
	$.unblockUI();
	$(".loader").fadeOut(100);
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

	$.post("castafiore/ui",params,function(data){
		$("#script_"+params['casta_applicationid']).append(data);
	}, "text")
}