<div>
<script type="text/javascript">
	
function init() 
	{
		document.getElementById('file_upload_form_${component.id}').onsubmit=function() {
			
			
			
			document.getElementById('file_upload_form_${component.id}').target = 'upload_target_${component.id}';
			
			document.getElementById('inputelement_${component.id}').style.display = 'none';
			
			document.getElementById('progress_${component.id}').style.display = 'block';
			
			document.getElementById('name_${component.id}').value = document.getElementById('file_${component.id}').value;
			
			jQuery('#upload_target_${component.id}').load( function(){
				document.getElementById('progress_${component.id}').innerHTML= 'Upload completed';
			});
			
			
		}
	}
jQuery(document).ready(function(){
	init();
	
	
	
});
   
</script>
<form id="file_upload_form_${component.id}" method="post" enctype="multipart/form-data" action="castafiore">
		
		<div id="inputelement_${component.id}">
			<input type="hidden" name="fileName" id="name_${component.id}">
			<input name="file" id="file_${component.id}" size="27" type="file" multiple="multiple" />
			<input type="submit" name="action" value="Upload" />
		</div>
		<div id="progress_${component.id}" style="display :none">Uploading...</div>
			<iframe id="upload_target_${component.id}" name="upload_target_${component.id}" src="" style="width:0;height:0;border:0px solid #fff;"></iframe>
			
			<input type="hidden" name="casta_applicationid">
			<input type="hidden" name="casta_componentid">
	</form>
</div>