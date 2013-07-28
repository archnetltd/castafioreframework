
	
	function sCall(params)
{	$('textarea').each(function(i){
		try{
			var ed = CKEDITOR.instances[jQuery(this).attr('id')];
			if(ed != undefined){
				params["casta_value_"+jQuery(this).attr("id")]=ed.getData();
			}
		}catch(err){
			//alert(err);
		}
		
	});
	$(':checkbox').each(function(i){
		if(jQuery(this).is(':checked')){
			params["casta_value_"+jQuery(this).attr("id")]= "checked";
		}else{
			params["casta_value_"+jQuery(this).attr("id")]= "";
		}

	});
	jQuery(" *[stf]").each(function(i){
		if(params["casta_value_"+jQuery(this).attr("id")] == undefined)
			params["casta_value_"+jQuery(this).attr("id")]=jQuery(this).attr("value");
	});
	params.requestId=requestId++;
	jQuery.post("castafiore/"+requestId+".jsp",params,function(data){
		jQuery(".loadmask, .loadmask-msg, .casta_tohide").hide();
		jQuery(".masked").removeClass("masked");
		try{
			jQuery("#script_"+params['casta_applicationid']).append(data);
		}catch(err){
			alert(err.description);
		}
	})
}

	
	
	
	
	
	
	
	
	
	
  