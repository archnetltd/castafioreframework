//============================= START OF CLASS ==============================//
// CLASS: HtmlBox                                                            //
//===========================================================================//
   /**
    * HtmlBox is a cross-browser widget that replaces the normal textboxes with
    * rich text area like in OpenOffice Text. It has Ajax support out of the box.
    * PS. It requires JQuery in order to function.
    * TODO: Smilies, Undo/Redo, CSS for Safari
    *<code>
    * </code>
    * Copyright@2007-2008 Remiya Solutions All rights reserved! 
	* @author Remiya Solutions
	* @version 2.8
    */
$.fn.htmlbox=function(){
    // START: Settings
    if(undefined===window.glob_ha){glob_ha=[];}
	// Are more colors set?
	var colors = (typeof document.htmlbox_colors === 'function')?document.htmlbox_colors():['silver','silver','white','white','yellow','yellow','orange','orange','red','red','green','green','blue','blue','brown','brown','black','black'];
	var styles = (typeof document.htmlbox_styles === 'function')?document.htmlbox_styles():[['No Styles','','']];
	var syntax = (typeof document.htmlbox_syntax === 'function')?document.htmlbox_syntax():[['No Syntax','','']];
	var d={
	    buttons:[],      // Buttons
		//dir:".",         // HtmlBox Directory, This is needed for the all components and images to work
		idir:"htmlBox/images/",// HtmlBox Image Directory, This is needed for the images to work
		images:[],       // Images
		output:"xhtml",  // Output
		css:"body{margin:3px;font-family:verdana;font-size:11px;}p{margin:0px;}",
		rows:[],         // Button rows 
		success:function(data){alert(data);}, // AJAX on success
		error:function(a,b,c){return this;}   // AJAX on error
		};
    if(!$(this).attr("id")){$(this).attr("id","jqha_"+glob_ha.length);d.id="jqha_"+glob_ha.length;glob_ha[glob_ha]=glob_ha;}else{d.id=$(this).attr("id");}
	if(undefined === glob_ha[d.id]){glob_ha[d.id]=this;}
	// END: Settings
	
// ------------- PRIVATE METHODS -----------------//

    //========================= START OF METHOD ===========================//
    //  METHOD: get_selection                                              //
    //=====================================================================//
	   /**
	    * Returns the selected (X)HTML code
	    * @access private
	    */
	var get_selection = function(){
	    var range;
		if($.browser.msie){
	       range = d.iframe.contentWindow.document.selection.createRange();
		   if (range.htmlText && range.text){return range.htmlText;}
	    }else{
		   if (d.iframe.contentWindow.getSelection) {
		       var selection = d.iframe.contentWindow.getSelection();
		       if (selection.rangeCount>0&&window.XMLSerializer){
                   range=selection.getRangeAt(0);
                   var html=new XMLSerializer().serializeToString(range.cloneContents());
			       return html;
               }if (selection.rangeCount > 0) {
		           range = selection.getRangeAt(0);
			       var clonedSelection = range.cloneContents();
			       var div = document.createElement('div');
			       div.appendChild(clonedSelection);
			       return div.innerHTML;
		       }
			}
		}
	};
    //=====================================================================//
    //  METHOD: get_selection                                              //
    //========================== END OF METHOD ============================//
	
	//========================= START OF METHOD ===========================//
    //  METHOD: in_array                                                   //
    //=====================================================================//
	 /**
	    * Coppies the PHP in_array function. This is useful for Objects.
	    * @access private
	    */
	var in_array=function(o,a){
	   for (var i in a){ if((i===o)){return true;} }
       return false;
	};
    //=====================================================================//
    //  METHOD: in_array                                                   //
    //========================== END OF METHOD ============================//
	
    //========================= START OF METHOD ===========================//
    //  METHOD: insert_text                                                //
    //=====================================================================//
	   /**
	    * Inserts text at the cursor position or selection
	    * @access private
	    */
	var insert_text = function(text,start,end){
	    if($.browser.msie){
	        if(typeof d.idoc.selection !== "undefined" && d.idoc.selection.type !== "Text" && d.idoc.selection.type !== "None"){start = false;d.idoc.selection.clear();}
		    var sel = d.idoc.selection.createRange();sel.pasteHTML(text);
			if (text.indexOf("\n") === -1) {
			    if (start === false) {} else {
                    if (typeof start !== "undefined") {
                        sel.moveStart("character", - text.length + start);
                        sel.moveEnd("character", - end);
                    } else {
                        sel.moveStart("character", - text.length);
                    }
                }
                sel.select();
            }
		}else{
		    d.idoc.execCommand("insertHTML", false, text);
		}
	};
    //=====================================================================//
    //  METHOD: insert_text                                                //
    //========================== END OF METHOD ============================//

	//========================= START OF METHOD ===========================//
    //  METHOD: keyup                                                      //
    //=====================================================================//
	   /**
	    * Keyup event.
	    * @access private 
	    */
	var keyup = function(e){
	    // Updating the textarea component, so whenever it is posted it will send all the data
		var html = $("#1"+d.id).is(":visible")?$("#"+d.id).val():html = d.iframe.contentWindow.document.body.innerHTML;
	    html = (typeof getXHTML === 'function')?getXHTML(html):html;
		$("#"+d.id).val(html);
		if(undefined!==d.change){d.change();}
	};
    //=====================================================================//
    //  METHOD: keyup                                                      //
    //========================== END OF METHOD ============================//
	
	//========================= START OF METHOD ===========================//
    //  METHOD: keypress                                                   //
    //=====================================================================//
	   /**
	    * Keypress event.
	    * @access private
	    */
	var keypress = function(e){
	    //var code = e.keyCode ? e.keyCode : e.which ? e.which : e.charCode;
		//if (code == 13){}
	};
    //=====================================================================//
    //  METHOD: keypress                                                   //
    //========================== END OF METHOD ============================//
	
    //========================= START OF METHOD ===========================//
    //  METHOD: nobubble                                                   //
    //=====================================================================//
	   /**
	    * Stops event bubbling
	    * ATTN: Not used now, but is to be used for code editting in the
	    *       Next versions
	    * @access private
	    */
	/*
	var nobubble = function(e){
	    var evt=window.event? event : e;
	    if($.browser.msie){
		    evt.returnValue = false;evt.cancelBubble = true;return evt;
		}else{
		    evt.stopPropagation();evt.preventDefault();return e;
		}
	};
	*/
    //=====================================================================//
    //  METHOD: nobubble                                                   //
    //========================== END OF METHOD ============================//
	
    //========================= START OF METHOD ===========================//
    //  METHOD: style                                                      //
    //=====================================================================//
	   /**
	    * Sets the CSS style to the HtmlBox iframe
	    * @access private
	    */
    var style = function(){
	    // START: HtmlBox Style
		if( d.idoc.createStyleSheet) { 
		  d.idoc.createStyleSheet().cssText=d.css;
		}else {
		  var css=d.idoc.createElement('link');
		  css.rel='stylesheet'; css.href='data:text/css,'+escape(d.css);
		  if($.browser.opera){
			 d.idoc.documentElement.appendChild(css);
		  }else{
			 d.idoc.getElementsByTagName("head")[0].appendChild(css);
		  }
		}
		// END: HtmlBox Style
	};
    //=====================================================================//
    //  METHOD: style                                                      //
    //========================== END OF METHOD ============================//
	
    //========================= START OF METHOD ===========================//
    //  METHOD: wrap_tags                                                  //
    //=====================================================================//
	   /**
	    * Wraps tags around the cursor position or selection
	    * @access private
	    */
	var wrap_tags = function(start,end){
	   var sel = get_selection(); if(undefined===sel){sel="";}
	   insert_text(start+sel+end,start.length,end.length);
	};
    //=====================================================================//
    //  METHOD: wrap_tags                                                  //
    //========================== END OF METHOD ============================//
	
// ------------- PRIVATE METHODS -----------------//
	
    //========================= START OF METHOD ===========================//
    //  METHOD: init                                                       //
    //=====================================================================//
	/**
	  * Draws the HtmlBox on the screen
	  * @return this
	  */
	this.init = function(is_init){
	    // START: Timeout to allow creation of DesignMode
	    if(undefined===is_init){setTimeout("glob_ha['"+d.id+"'].init(true)",250);return false;}
		// END: Timeout to allow creation of DesignMode
		var w=$(this).css("width");var h=$(this).css("height");
		$(this).wrap("<table __path='" + $(this).attr('__path') + "' id='"+d.id+"_wrap' width='"+w+"' style='height:"+h+";border:2px solid #E9EAEF;' cellspacing='0' cellpadding='0'><tr><td></td></tr></table>");
		// START: Appending toolbar
		$(this).parent().parent().parent().parent().prepend(toolbar());
		$("."+d.id+"_tb").css("background-image","url("+d.idir+"bg_silver.jpg)").css("height","30px");
		$("."+d.id+"_tb").find("button").each(function(){
		    $(this).css("border","1px solid #E9EAEF").css("background","transparent").css("margin","1px 1px 1px 1px").css("padding","1px");
		    $(this).mouseover(function(){$(this).css("border","1px solid #BFCAFF").css("background","#EFF2FF");});
			$(this).mouseout(function(){$(this).css("border","1px solid #E9EAEF").css("background","transparent");});
			}
		);
		$("."+d.id+"_tb").find("select").each(function(){
		    $(this).css("border","1px solid #E9EAEF").css("background","transparent").css("margin","2px 2px 3px 2px");
			if($.browser.mozilla){$(this).css("padding","0").css("position","relative").css("top","-2px");}
		    }
		);
		// END: Appending toolbar
		try {
		   var iframe=document.createElement("IFRAME");// var doc=null;
		   $(iframe).css("width",w).css("height",h).attr("id",d.id+"_html").css("border","0");
		   $(this).parent().prepend(iframe);
		   // START: Shortcuts for less code
		   d.iframe = iframe;
		   d.idoc = iframe.contentWindow.document;
		   // END: Shortcuts
		   
		   d.idoc.designMode="on";
		   // START: Insert text
		      // Is there text in the textbox?
		   var text = ($(this).val()==="")?"":$(this).val();
		   if($.browser.mozilla||$.browser.safari){
			   //if(text===""){text="&nbsp;";}
			   d.idoc.open('text/html', 'replace');
			   d.idoc.write(text);
			   d.idoc.close();
		   }else{
	           if(text!==""){d.idoc.write(text);}
		   }
		   // Needed by browsers other than MSIE to become editable
		   if($.browser.msie===false){iframe.contentWindow.document.body.contentEditable = true;}
		   // END: Insert text
		   
		   // START: HtmlBox Style
		   if(d.css.indexOf("background:")===-1){d.css+="body{background:#EFF4FF;}";}
		   if(d.css.indexOf("background-image:")===-1){
		       d.css=d.css+"body{background-image:url("+d.idir+"/logo.gif);background-position:top right;background-repeat:no-repeat;}";
		   }
		   if(d.idoc.createStyleSheet) {
		       setTimeout("glob_ha['"+d.id+"'].set_text(glob_ha['"+d.id+"'].get_html())",10);
		   }else {
		     var css=d.idoc.createElement('link');
			 css.rel='stylesheet';
			 css.href='data:text/css,'+escape(d.css);
			 if($.browser.opera){
			     d.idoc.documentElement.appendChild(css);
			 }else if($.browser.safari){
                 // No solution. Looking for it
			 }else{
			     d.idoc.getElementsByTagName("head")[0].appendChild(css);
			 }
		   }
		   // END: HtmlBox Style
		   
		   // START: Adding events
		   if(iframe.contentWindow.document.attachEvent){
		       iframe.contentWindow.document.attachEvent("onkeyup", keyup);
			   iframe.contentWindow.document.attachEvent("onkeypress", keypress);
		   }else{
			   iframe.contentWindow.document.addEventListener("keyup",keyup,false);
			   iframe.contentWindow.document.addEventListener("keypress",keypress,false);
		   }
		   $(this).hide();
	    }catch(e){
	       alert("This rich text component is not supported by your browser.\n"+e);
		   $(this).show();
	    }
		return this;
	};
    //=====================================================================//
    //  METHOD: init                                                       //
    //========================== END OF METHOD ============================//
	
    //========================= START OF METHOD ===========================//
    //  METHOD: change                                                     //
    //=====================================================================//
       /**
        * Specifies a function to be executed on text change in the HtmlBox
        */
	this.change=function(fn){d.change=fn;return this;};
    //=====================================================================//
    //  METHOD: change                                                     //
    //========================== END OF METHOD ============================//
	
    //========================= START OF METHOD ===========================//
    //  METHOD: remove                                                     //
    //=====================================================================//
       /**
        * Removes the HtmlBox instance from the DOM and the globalspace
        */
	this.remove = function(){
	    glob_ha[d.id]=undefined;
	    $(d.id+"_wrap").remove();
	};
    //=====================================================================//
    //  METHOD: remove                                                     //
    //========================== END OF METHOD ============================//
	
    //========================= START OF METHOD ===========================//
    //  METHOD: toolbar                                                    //
    //=====================================================================//
	   /**
	    * The toolbar of HtmlBox
	    * @return this
	    */
	var toolbar=function(){
	    var h = "";
		for(var k=1;k<d.rows.length;k++){
		    if(undefined===d.rows[k]){continue;}
		    var buttons = d.rows[k].split(",");
			h += "<tr><td class='"+d.id+"_tb' valign='middle' style='background:silver;border-bottom:1px outset white'>";
			for(var i=0;i<(buttons.length+1);i++){
			    if(undefined===d.buttons[buttons[i]]){continue;}
				else if(d.buttons[buttons[i]]==="separator_dots"){h += "<image src='"+d.idir+"separator_dots.gif' style='margin:2px 3px 2px 5px;'>";}
				else if(d.buttons[buttons[i]]==="separator_basic"){h += "<image src='"+d.idir+"separator_basic.gif' style='margin:0px' height='24'>";}
				else if(d.buttons[buttons[i]]==="fontsize"){
				    h += "<select id='"+d.id+"_fontsize' onchange='glob_ha[\""+d.id+"\"].cmd(\"fontsize\",this.options[this.selectedIndex].value)' style='font-size:14px;'><option value='' selected>- SIZE -</option><option value='1'>1</option><option value='2'>2</option><option value='3'>3</option><option value='4'>4</option><option value='5'>5</option><option value='6'>6</option><option value='7'>7</option></select>";
			    }else if(d.buttons[buttons[i]]==="fontfamily"){
				    h += "<select id='"+d.id+"_fontfamily' onchange='glob_ha[\""+d.id+"\"].cmd(\"fontname\",this.options[this.selectedIndex].value)' style='font-size:14px;'><option value='' selected>- FONT -</option><option value='arial' style='font-family:arial;'>Arial</option><option value='courier' style='font-family:courier;'>Courier</option><option value='cursive' style='font-family:cursive;'>Cursive</option><option value='georgia' style='font-family:georgia;'>Georgia</option><option value='monospace' style='font-family:monospace;'>Monospace</option><option value='tahoma' style='font-family:tahoma;'>Tahoma</option><option value='verdana' style='font-family:verdana;'>Verdana</option></select>";
				}else if(d.buttons[buttons[i]]==="formats"){
				    h += "<select id='"+d.id+"_formats' onchange='glob_ha[\""+d.id+"\"].cmd(\"format\",this.options[this.selectedIndex].value)'><option value='' selected>- FORMATS -</option><option value='h1'>Heading 1</option><option value='h2'>Heading 2</option><option value='h3'>Heading 3</option><option value='h4'>Heading 4</option><option value='h5'>Heading 5</option><option value='h6'>Heading 6</option><option value='p'>Paragraph</option><option value='pindent'>First Indent</option><option value='pre'>Preformatted</option></select>";
				}else if(d.buttons[buttons[i]]==="fontcolor"){
				    h += "<select id='"+d.id+"_fontcolor' onchange='glob_ha[\""+d.id+"\"].cmd(\"fontcolor\",this.options[this.selectedIndex].value)' style='font-size:14px;'><option value='' selected>-COLOR-</option>";
					//alert(colors)
					
					for(var m=0;m<colors.length;m++){ if(m%2){continue;}
					   h+="<option value='"+colors[m]+"' style='background:"+colors[m]+";color:"+colors[m]+";'>"+colors[m]+"</option>";
					}
					h += "</select>";
				}else if(d.buttons[buttons[i]]==="highlight"){
				    h += "<select id='"+d.id+"_highlight' onchange='glob_ha[\""+d.id+"\"].cmd(\"backcolor\",this.options[this.selectedIndex].value)' style='font-size:14px;'><option value='' selected>-HIGHLIGHT-</option>";
					for(var n=0;n<colors.length;n++){ if(n%2){continue;}
					   h+="<option value='"+colors[n]+"' style='background:"+colors[n]+";color:"+colors[n]+";'>"+colors[n]+"</option>";
					}
					h += "</select>";
				}else if(d.buttons[buttons[i]]==="styles"){
				    h += "<select id='"+d.id+"_styles' onchange='glob_ha[\""+d.id+"\"].cmd(\"styles\",this.options[this.selectedIndex].value);this.options[0].selected=\"true\";' style='font-size:14px;'><option value='' selected>-STYLES-</option>";
					for(var o=0;o<styles.length;o++){ if(n%2){continue;}
					   h+="<option value='"+o+"' style='background:white;color:red;'>"+styles[o][0]+"</option>";
					}
					h += "</select>";
				}else if(d.buttons[buttons[i]]==="syntax"){
				    h += "<select id='"+d.id+"_styles' onchange='glob_ha[\""+d.id+"\"].cmd(\"syntax\",this.options[this.selectedIndex].value);this.options[0].selected=\"true\";' style='font-size:14px;'><option value='' selected>-SYNTAX-</option>";
					for(var p=0;p<syntax.length;p++){ if(n%2){continue;}
					   h+="<option value='"+p+"' style='background:white;color:red;'>"+syntax[p][0]+"</option>";
					}
					h += "</select>";
				}
				/*
				else if(d.buttons[buttons[i]]==="images"){
				    $().mousemove(function(e){document.x=e.pageX;document.y=e.pageY;});
				    $(document.body).append("<div id='images' style='position:absolute;top:0px;z-index:500;background:white;width:200px;height:100px;display:none;'><image src='"+idir+"image.gif''></div>");
					$("#images").blur(function(){$(this).hide();});
					
				    h += "<button type='button' onclick='$(\"#images\").css(\"top\",document.y-10).css(\"left\",document.x-10).show().focus();' title='Images'><image src='"+idir+"image.gif'></button>";
				}*/
				// Commands
				var cmds = {"bold":"Bold","center":"Center","code":"View Code","copy":"Copy","cut":"Cut","hr":"Insert Line","hyperlink":"Insert Link","image":"Insert Image","indent":"Indent","italic":"Italic","justify":"Justify","left":"Left","ol":"Numbered List","outdent":"Outdent","paragraph":"Insert Paragraph","paste":"Paste","quote":"Quote","redo":"Redo","removeformat":"Remove Format","right":"Right","strike":"Strikethrough","striptags":"Strip Tags","sub":"Subscript","sup":"Superscript","ul":"Bulleted List","underline":"Underline","undo":"Undo","unlink":"Remove Links"};
				if(in_array(d.buttons[buttons[i]],cmds)){h += "<button type='button' onclick='glob_ha[\""+d.id+"\"].cmd(\""+d.buttons[buttons[i]]+"\")' title='"+cmds[d.buttons[buttons[i]]]+"'><image src='"+d.idir+d.buttons[buttons[i]]+".gif'></button>";}
		    }
			h += "</td></tr>";
		}
		return h;
	};
    //=====================================================================//
    //  METHOD: toolbar                                                    //
    //========================== END OF METHOD ============================//
	
    //========================= START OF METHOD ===========================//
    //  METHOD: cmd                                                        //
    //=====================================================================//
	   /**
	    * Executes a user-specified command
	    * @return this
	    */
	this.cmd = function(cmd,arg1){
	   // When user clicks toolbar button make sure it always targets its respective WYSIWYG
       d.iframe.contentWindow.focus();
	   // START: Prepare commands
	   if(cmd==="paragraph"){cmd="format";arg1="p";}
	   var cmds = {"center":"justifycenter","hr":"inserthorizontalrule","justify":"justifyfull","left":"justifyleft","ol":"insertorderedlist","right":"justifyright","strike":"strikethrough","sub":"subscript","sup":"superscript","ul":"insertunorderedlist"};
	   if(in_array(cmd,cmds)){cmd=cmds[cmd];}
       // END: Prepare commands
	   if(cmd==="code"){
	       var text = this.get_html();
	       if($("#"+d.id).is(":visible")){		       
		       $("#"+d.id).hide();		   
		       $("#"+d.id+"_html").show();
			   this.set_text(text);
		   }else{
		       $("#"+d.id).show();
		       $("#"+d.id+"_html").hide();
			   this.set_text(text);
			   $("#"+d.id).focus();
		   }
		   
	   }else if(cmd==="hyperlink"){
		   d.idoc.execCommand("createlink", false, prompt("Paste Web Address URL Here:"));
	   }else if(cmd==="image"){
		   d.idoc.execCommand("insertimage", false, prompt("Paste Image URL Here:"));
	   }else if(cmd==="fontsize"){
		   d.idoc.execCommand(cmd, false,arg1);
	   }else if(cmd==="backcolor"){
	       if($.browser.msie){
		   d.idoc.execCommand("backcolor", false,arg1);
		   }else{
		   d.idoc.execCommand("hilitecolor", false,arg1);
		   }
	   }else if(cmd==="fontcolor"){
	       d.idoc.execCommand("forecolor", false,arg1);
	   }else if(cmd==="fontname"){
		   d.idoc.execCommand(cmd, false, arg1);
	   }else if(cmd==="cut"){
	       if($.browser.msie === false){
		       alert("Available in IExplore only.\nUse CTRL+X to cut text!");
		   }else{
	           d.idoc.execCommand('Cut');
	       }
	   }else if(cmd==="copy"){
	       if($.browser.msie === false){
		       alert("Available in IExplore only.\nUse CTRL+C to copy text!");
		   }else{
	           d.idoc.execCommand('Copy');
	       }
	   }else if(cmd==="paste"){
	       if($.browser.msie === false){
		       alert("Available in IExplore only.\nUse CTRL+V to paste text!");
		   }else{
	           d.idoc.execCommand('Paste');
	       }
	   }else if(cmd==="format"){
	       if(arg1==="pindent"){wrap_tags('<p style="text-indent:20px;">','</p>');}
		   else if(arg1!==""){d.idoc.execCommand('formatBlock', false, "<"+arg1+">");}
	   }else if(cmd==="striptags"){
	       var sel = get_selection();
		   sel = sel.replace(/(<([^>]+)>)/ig,"");
		   insert_text(sel); 
	   }else if(cmd==="quote"){
	       wrap_tags('<br /><div style="position:relative;top:10px;left:11px;font-size:11px;font-family:verdana;">Quote</div><div class="quote" contenteditable="true" style="border:1px inset silver;margin:10px;padding:5px;background:#EFF7FF;">','</div><br />');
	   }else if(cmd==="styles"){
	       wrap_tags(styles[arg1][1],styles[arg1][2]);
	   }else if(cmd==="syntax"){
	       wrap_tags(syntax[arg1][1],syntax[arg1][2]);
	   }else{
	       d.idoc.execCommand(cmd, false, null);
	   }
	   //Setting the changed text to textearea
	   if($("#"+d.id).is(":visible")===false){
	      $("#"+d.id).val(this.get_html());
	   }
	};
    //=====================================================================//
    //  METHOD: cmd                                                        //
    //========================== END OF METHOD ============================//
		
    //========================= START OF METHOD ===========================//
    //  METHOD: get_text                                                   //
    //=====================================================================//
	   /**
	    * Returns the text without tags of the HtmlBox
	    * @return this
	    */
	this.get_text = function(){
	   // Is textbox visible?
	   if($("#"+d.id).is(":visible")){ return $("#"+d.id).val(); }
	   // Iframe is visible
	   var text;
	   if($.browser.msie){
	       text = d.iframe.contentWindow.document.body.innerText;
	   }else{
	       var html = d.iframe.contentWindow.document.body.ownerDocument.createRange();
		   html.selectNodeContents(d.iframe.contentWindow.document.body);
		   text = html;
	   }
	   return text;
	};
    //=====================================================================//
    //  METHOD: get_text                                                   //
    //========================== END OF METHOD ============================//
	
    //========================= START OF METHOD ===========================//
    //  METHOD: set_text                                                  //
    //=====================================================================//
	   /**
	    * Sets the text as a content of the HtmlBox
	    * @return this
	    */
	this.set_text = function(txt){
	   var text = (undefined===txt)?"":txt;
	   // Is textarea visible? Writing to it.
	   if($("#"+d.id).is(":visible")){
	       $("#"+d.id).val(text);
	   }else{
	     // Textarea not visible. write to iframe
	     if($.browser.mozilla||$.browser.safari){
		   //if($.trim(text)===""){text="&nbsp;";}
		   d.idoc.open('text/html', 'replace'); d.idoc.write(text); d.idoc.close();
	     }else{
		   d.idoc.body.innerHTML = "";
	       if(text!==""){d.idoc.write(text);}
	     }
		 d.idoc.body.contentEditable = true;
		 style(); // Setting the CSS style for the iframe
	   }
	   
	   return this;
	};
    //=====================================================================//
    //  METHOD: set_text                                                   //
    //========================== END OF METHOD ============================//
	
    //=====================================================================//
    //  METHOD: dir                                                        //
    //========================== END OF METHOD ============================//
	   /**
	    * Sets the image directory of the HtmlBox
	    */
	//this.dir = function(dir){d.dir=$.trim(dir);return this;};
    //=====================================================================//
    //  METHOD: dir                                                        //
    //========================== END OF METHOD ============================//
	
    //=====================================================================//
    //  METHOD: idir                                                       //
    //========================== END OF METHOD ============================//
	   /**
	    * Sets the image directory of the HtmlBox
	    */
	this.idir = function(dir){d.idir=$.trim(dir);return this;};
    //=====================================================================//
    //  METHOD: idir                                                       //
    //========================== END OF METHOD ============================//
	
    //=====================================================================//
    //  METHOD: set_style                                                  //
    //========================== END OF METHOD ============================//
	   /**
	    * Sets the CSS style to be used by the HtmlBox
	    */
	this.set_style = function(css){d.css=css;return this;};
    //=====================================================================//
    //  METHOD: set_style                                                  //
    //========================== END OF METHOD ============================//

    //========================= START OF METHOD ===========================//
    //  METHOD: get_html                                                   //
    //=====================================================================//
	   /**
	    * Returns the (X)HTML content of the HtmlBox
	    * @return this
	    */
	this.get_html = function(){
	   var html;
	   if($("#"+d.id).is(":visible")){
	      html = $("#"+d.id).val();
	   }else{
	      html = d.iframe.contentWindow.document.body.innerHTML;
	   }
	   if(typeof getXHTML === 'function'){ return getXHTML(html); }else{return html;}
	};
    //=====================================================================//
    //  METHOD: get_html                                                  //
    //========================== END OF METHOD ============================//
	
    //========================= START OF METHOD ===========================//
    //  METHOD: button                                                   //
    //=====================================================================//
	   /**
	    * Sets which button to be shown at the top of HtmlBox
        * @param String the name of the button
	    * @param Integer the row at whic the button is to be placed
	    * @return this
	    */
	this.button = function(name,row){
	    d.buttons[d.buttons.length]=name;
		if(undefined===row){
		    if(undefined===d.rows[1]){d.rows[1]="";}
			d.rows[1]=d.rows[1]+","+(d.buttons.length-1);
		}else{
		    if(undefined===d.rows[row]){d.rows[row]="";}
			d.rows[row]=d.rows[row]+","+(d.buttons.length-1);
		}
		return this;
	};
    //=====================================================================//
    //  METHOD: button                                                     //
    //========================== END OF METHOD ============================//
	
    //========================= START OF METHOD ===========================//
    //  METHOD: separator                                                  //
    //=====================================================================//
	   /**
	    * Adds a separator image to the toolbar
	    * @param String "basic" or "dots" 	  
	    * @return this;
	    */
	this.separator = function(type,row){
	    if(undefined===type){type="basic";}
	    this.button("separator_"+type,row);
		return this;
	};
    //=====================================================================//
    //  METHOD: separator                                                  //
    //========================== END OF METHOD ============================//
	
    //========================= START OF METHOD ===========================//
    //  METHOD: post                                                       //
    //=====================================================================//
	   /**
	    * Posts the form data to the specified URL using Ajax
        * @param String the URL to post to
	    * @param String the text to be posted, default the (X)HTML text
	    * @return this;
	    */
	this.post=function(url,data){
	    if(undefined===data){data=this.get_html();} data=(d.id+"="+data);
		$.ajax({type: "POST", data: data,url: url,dataType: "html",error:d.error,success:d.success});
	};
    //=====================================================================//
    //  METHOD: post                                                       //
    //========================== END OF METHOD ============================//
	
    //========================= START OF METHOD ===========================//
    //  METHOD: get                                                        //
    //=====================================================================//
	   /**
	    * Gets the form data to the specified URL using Ajax
        * @param String the URL to get to
	    * @param String the text to be posted, default the (X)HTML text
	    * @return this;
	    */
	this.get=function(url,data){
	    if(undefined===data){data=this.get_html();} data=(d.id+"="+data);
		$.ajax({type: "GET", data: data,url: url,dataType: "html",error:d.error,success:d.success});
	};
    //=====================================================================//
    //  METHOD: get                                                        //
    //========================== END OF METHOD ============================//
	
    //========================= START OF METHOD ===========================//
    //  METHOD: success                                                    //
    //=====================================================================//
       /**
        * Specifies what is to be executed on successful Ajax POST or GET
        */
	this.success=function(fn){d.success=fn;return this;};
    //=====================================================================//
    //  METHOD: success                                                    //
    //========================== END OF METHOD ============================//

    //========================= START OF METHOD ===========================//
    //  METHOD: error                                                      //
    //=====================================================================//
       /**
        * Specifies what is to be executed on error Ajax POST or GET
        */
	this.error=function(fn){d.error=fn;return this;};
    //=====================================================================//
    //  METHOD: error                                                      //
    //========================== END OF METHOD ============================//

	return this;
};
//===========================================================================//
// CLASS: HtmlBox                                                            //
//============================== END OF CLASS ===============================//
