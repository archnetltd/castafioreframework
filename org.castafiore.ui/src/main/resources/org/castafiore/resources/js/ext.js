var requestId=0;
var loadingTimeout;
var loadingActive=-1;
castafiore = function(app,params){
	google.load("jquery", "1.7.0");
		
	google.setOnLoadCallback(function() {
		jQuery.fn.scrollable = function(options){
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
	
	
		var me = $('#' + app);
		jQuery.getCSS('castafiore/resource/classpath/org/castafiore/resources/css/themes/aristo/theme.css');
		jQuery.getScript('castafiore/resource/classpath/org/castafiore/resources/js/jquery-ui-1.8.21.js');
		jQuery.getScript('castafiore/resource/classpath/org/castafiore/resources/js/jquery.maskedinput-1.3.js');		
		jQuery.getScript('castafiore/resource/classpath/org/castafiore/resources/js/jquery.plugin.js',function(){
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
		
	 });
			
};
	
function loading(){
	
}

function hideloading(){
	
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
		}
	});
	
	$(" *[stf]").each(function(i){
		if(params["casta_value_"+$(this).attr("id")] == undefined)
			params["casta_value_"+$(this).attr("id")]=$(this).attr("value");
	});
	params.requestId=requestId++;

	$.post("castafiore/ui",params,function(data){
		$("#script_"+params['casta_applicationid']).append(data);
	}, "text");
}
function combo(elem, url){
	$.ajax({
			url: url,type: 'POST',
			dataType: 'json',
			success: function(data){
				var mm={};
				for(mm in data){
					var m = data[mm];
					var selected = m['selected'];
					var val = m['value'];
					var opt = $('<option>'+val+'</option>');
					opt.attr('value', m['index']);
					if(selected){
						opt.attr('selected','selected');
					}
					elem.append(opt);
				}
			}
		});	
}