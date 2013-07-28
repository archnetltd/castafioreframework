$.fn.designer = function() {

	var picker;

	var positionSelector = function(evt) {
		evt.stopPropagation();
		var dd = $(this);
		$('.designer').attr('selitem', dd.attr('id'));

	
		
		var height = dd.outerHeight(true);
		var width = dd.outerWidth(true);
		var pos = dd.offset();
		
		var marginTop = dd.css('margin-top');
		var marginRight = dd.css('margin-right');
		var marginBottom = dd.css('margin-bottom');
		var marginLeft = dd.css('margin-left');
		
		$("#slider-vertical").css('display', 'none');
		$(".colorpickerholder").css('display', 'none');
		$('#selector').stop().animate( {
			'height' : height + 'px',
			'width' : width + 'px',
			'top' : pos.top + 'px',
			'left' : pos.left + 'px',
			'opacity' : 0.3
		}, function() {

			$('#innerselector').css({'margin-top': dd.css('margin-top'), 'height' : dd.outerHeight(false), 'width' : dd.outerWidth(false)});
			var selid = getSelectedItem();
			var designer = $('div[name=EXDragAndDropDemo]');
			
		
			var params = {
				'casta_applicationid' : designer.attr('appid'),
				'casta_eventid' : designer.attr('eventid'),
				'casta_componentid' : designer.attr('id'),
				'selItem' : selid
			};
			params['style'] = 'selectitem';
			sCall(params);
		});
		
		$('#selector').draggable('destroy');
		$('#selector').draggable({"revert":false,
			
		
			"start":function(event,ui){
				
				//var offset = selector.offset();
				$(this).attr('top', ui.position.top).attr('left', ui.position.left);

			},
			"stop": function(event,ui) {
				
				
				
				var xpos = $(this).attr('left');
				var ypos = $(this).attr('top');
				
				
				var xmove = ui.position.left - xpos;
				var ymove = ui.position.top - ypos;
				
				var sel = $('#' + getSelectedItem());
				sel.css('position', 'relative');
				alert(sel.css('top'));
				var cy = parseFloat( sel.css('top').replace('px', ''));
				var cx =parseFloat( sel.css('left').replace('px', ''));
				
				//alert(cy);
				sel.css('top', (cy + ymove) + 'px');
				sel.css('left', (cx + xmove) + 'px');
				//sel.css('position', 'relative').css('top', diffVertical + 'px').css('left', diffHoriz + 'px');


				

 
			}});


	};

	var positionSelectorFromBar = function() {
		
		var id = $(this).attr('name').replace('_c-item', '').trim();
		var dd = $('*[__oid=' + id + ']');
		$('.designer').attr('selitem', dd.attr("id"));
		var height = dd.outerHeight(true);
		var width = dd.outerWidth(true);
		var pos = dd.offset();
		
		var marginTop = dd.css('margin-top');
		var marginRight = dd.css('margin-right');
		var marginBottom = dd.css('margin-bottom');
		var marginLeft = dd.css('margin-left');
		$("#slider-vertical").css('display', 'none');
		$(".colorpickerholder").css('display', 'none');
		$('#selector').stop().animate( {
			'height' : height + 'px',
			'width' : width + 'px',
			'top' : pos.top + 'px',
			'left' : pos.left + 'px',
			'opacity' : 0.3
		}, function() {
			$('#innerselector').show().css({'margin-top': dd.css('margin-top'), 'height' : dd.outerHeight(false), 'width' : dd.outerWidth(false)});
			var selid = getSelectedItem();
			var designer = $('div[name=EXDragAndDropDemo]');
			var params = {
				'casta_applicationid' : designer.attr('appid'),
				'casta_eventid' : designer.attr('eventid'),
				'casta_componentid' : designer.attr('id'),
				'selItem' : selid
			};
			params['style'] = 'selectitem';
			sCall(params);
		});

		$('#selector').draggable('destroy');
		$('#selector').draggable({"revert":false,
			
		
			"start":function(event,ui){
				
				//var offset = selector.offset();
				$(this).attr('top', ui.position.top).attr('left', ui.position.left);

			},
			"stop": function(event,ui) {
				
				
				
				var xpos = $(this).attr('left');
				var ypos = $(this).attr('top');
				
				
				var xmove = ui.position.left - xpos;
				var ymove = ui.position.top - ypos;
				
				var sel = $('#' + getSelectedItem());
				sel.css('position', 'relative');
				alert(sel.css('top'));
				var cy = parseFloat( sel.css('top').replace('px', ''));
				var cx =parseFloat( sel.css('left').replace('px', ''));
				
				//alert(cy);
				sel.css('top', (cy + ymove) + 'px');
				sel.css('left', (cx + xmove) + 'px');
				//sel.css('position', 'relative').css('top', diffVertical + 'px').css('left', diffHoriz + 'px');


				

 
			}});
	};

	var hideSelector = function() {
		$('.designer').removeAttr('selitem');
		$("#slider-vertical").css('display', 'none');
		$(".colorpickerholder").css('display', 'none');
		$('#selector').stop().animate( {
			'height' : '0px',
			'width' : '0px',
			'top' : '0px',
			'left' : '0px',
			'opacity' : 0
		});
		$('#innerselector').hide();
	};

	function prepareComponentToolbar() {
		$('.designer-left-toolbar .dtb').draggable( {
			'revert' : true
		});
	}

	function getSelectedItem() {
		return $('.designer').attr('selitem');
	}

	function buttonateToolbar() {
		$('.designer .top .toolbar li, .fg-toolbar .components').mouseover(
				function() {
					$(this).addClass('ui-state-default');
				}).mouseout(
				function() {
					$(this).removeClass('ui-state-default').removeClass(
							'ui-state-active');
				}).mousedown(function() {
			$(this).addClass('ui-state-active');
		});

		$('.designer .designer-top-toolbar .uts').mouseover(function() {
			$(this).css('background-color', 'gray');
		}).mouseout(function() {
			$(this).css('background-color', 'transparent');
		}).mousedown(function() {
			$(this).css('background-color', 'green');
		});
	}

	function prepareVerticalSlider() {
		$("#slider-vertical").slider(
				{
					orientation : "vertical",
					range : "min",
					min : 0,
					max : 100,

					slide : function(event, ui) {
						var selid = getSelectedItem();
						var sel = $('#' + selid);
						var val = ui.value + 'px';
						sel.css($("#slider-vertical").attr('stname'), val);
						var handleOffset = $(
								'#slider-vertical .ui-slider-handle').offset();
						$('.slider-value').show().css( {
							'top' : handleOffset.top - 15 + 'px',
							'left' : handleOffset.left + 20 + 'px',
							'display' : 'block'
						}).text(val);
						resizeSelector();
					},
					stop : function(event, ui) {

						setTimeout("$('.slider-value').hide()", 1000);
						var selid = getSelectedItem();

						var val = ui.value + 'px';

						var designer = $('div[name=EXDragAndDropDemo]');
						var params = {
							'casta_applicationid' : designer.attr('appid'),
							'casta_eventid' : designer.attr('eventid'),
							'casta_componentid' : designer.attr('id'),
							'selItem' : selid
						};
						params['style'] = $("#slider-vertical").attr('stname');
						params['value'] = val;

						sCall(params);

						// sel.css($("#slider-vertical").attr('stname'),val);
				}
				}).css('display', 'none');
		
	}

	function portalWrapper(){
		
		setTimeout("$('div[name=portalWrapper]').scroll(function(){remakeSelector($('.designer').attr('selitem'))});",1000);
	}

	function prepareMisc() {

	//setTimeout("portalWrapper();",1000);
	portalWrapper();


		$('div[name=EXDragAndDropDemo]').css('width', $(document).width() + 'px');

		$('#selector').live('click', hideSelector);

		$('.designer .top .toolbar .tgl-grid').toggle(function() {
			$('.designer-workspace').removeClass('showgrid');
		}, function() {
			$('.designer-workspace').addClass('showgrid');
		});

		picker = $('.colorpickerholder').ColorPicker( {
			flat : true,
			onChange : function(hsb, hex, rgb) {
				var selid = $('.designer').attr('selitem');
				var sel = $('#' + selid);
				var val = '#' + hex;
				sel.css($(".colorpickerholder").attr('stname'), val);
			},
			onSubmit : function(hsb, hex, rgb) {
				$(".colorpickerholder").hide();
				var selid = $('.designer').attr('selitem');
				var sel = $('#' + selid);
				var val = '#' + hex;
				var designer = $('div[name=EXDragAndDropDemo]');
				var params = {
					'casta_applicationid' : designer.attr('appid'),
					'casta_eventid' : designer.attr('eventid'),
					'casta_componentid' : designer.attr('id'),
					'selItem' : selid
				};
				params['style'] = $(".colorpickerholder").attr('stname');
				params['value'] = val;

				sCall(params);

			},
			onBeforeShow : function() {
				var selid = $('.designer').attr('selitem');
				var sel = $('#' + selid);
				var val = sel.css('color');
				// picker.ColorPickerSetColor(val);
		}
		});
	}

	function prepareSiteStructure() {
		$('.designer .left ul li').addClass('ui-state-default');
		$('.designer .left ul h3').addClass('ui-widget-header');
		$('.designer .left ul li').live('mouseover', function() {
			$(this).addClass("ui-state-active");
			resizeSelector();
		}).live('mouseout', function() {
			$(this).removeClass("ui-state-active");
		});

		$('.SiteStructure').resizable( {
			handles : 'e'
		});

		$('.SiteStructure .ui-resizable-e').addClass('ui-widget-header').css(
				'background-position', '0px 0px').width('2px');
	}

	function resizeSelector() {
		
		var selid = getSelectedItem();
		var dd = $('#' + selid);
		var height = dd.outerHeight(true);
		var width = dd.outerWidth(true);
		var pos = dd.offset();
		
		var marginTop = dd.css('margin-top');
		var marginRight = dd.css('margin-right');
		var marginBottom = dd.css('margin-bottom');
		var marginLeft = dd.css('margin-left');
		//var pos = findPos(sel);
		$('#selector').stop().css( {
			'height' : height + 'px',
			'width' : width + 'px',
			'top' : pos.top + 'px',
			'left' : pos.left + 'px',
			'opacity' : 0.3
		});
		$('#innerselector').show().css({'margin-top': dd.css('margin-top'), 'height' : dd.outerHeight(false), 'width' : dd.outerWidth(false)});
	}

	function useColorPicker(dd, sel, params) {
		var pos = dd.offset();
		var val = sel.css(dd.attr('stname'));
		var min = parseInt(dd.attr('stmin'));
		var max = parseInt(dd.attr('stmax'));
		$('.colorpickerholder').attr('stname', dd.attr('stname')).stop().css( {
			'top' : pos.top - 20 + 'px',
			'left' : pos.left + 20 + 'px',
			'display' : 'block'
		});
		// val = val.replace('rgb(', '').replace(')', '');
		// var colors = val.split(',');
		var hexColor = RGBtoHex(val);
		picker.ColorPickerSetColor(hexColor);
	}

	function RGBtoHex(val) {
		val = val.replace('rgb(', '').replace(')', '');
		var colors = val.split(',');
		return toHex(colors[0]) + toHex(colors[1]) + toHex(colors[2])
	}
	function toHex(N) {
		if (N == null)
			return "00";
		N = parseInt(N);
		if (N == 0 || isNaN(N))
			return "00";
		N = Math.max(0, N);
		N = Math.min(N, 255);
		N = Math.round(N);
		return "0123456789ABCDEF".charAt((N - N % 16) / 16)
				+ "0123456789ABCDEF".charAt(N % 16);
	}

	function useSlider(dd, sel, params) {
		var pos = dd.offset();
		var val = sel.css(dd.attr('stname')).replace('px', '');
		var min = parseInt(dd.attr('stmin'));
		var max = parseInt(dd.attr('stmax'));
		$('#slider-vertical').attr('stname', dd.attr('stname')).stop().css( {
			'top' : pos.top - 20 + 'px',
			'left' : pos.left + 20 + 'px',
			'display' : 'block'
		}).slider('option', 'value', val).slider('option', 'min', min).slider(
				'option', 'max', max);
	}

	function applySimpleStyle(dd, sel, params) {

		params['style'] = dd.attr('stname');
		if (dd.attr('stvalue')) {
			params['value'] = dd.attr('stvalue');

		} else {

			params['value'] = dd.val();
		}
		if (dd.attr('stoff')) {
			if (sel.css(dd.attr('stname')) != dd.attr('stvalue')) {
				sel.css(dd.attr('stname'), dd.attr('stvalue'));

			} else {
				sel.css(dd.attr('stname'), dd.attr('stoff'));
				params['value'] = dd.attr('stoff');
			}
		} else {
			sel.css(dd.attr('stname'), params['value']);

		}

		sCall(params);
	}

	function useExplorer(dd, sel, params) {

		params['style'] = dd.attr('stname');
		params['value'] = sel.css(dd.attr('stname'));
		sCall(params);

	}

	function prepareMenu() {
		$('.menuitem').live('click', function() {

			var designer = $('div[name=EXDragAndDropDemo]');
			var params = {
				'casta_applicationid' : designer.attr('appid'),
				'casta_eventid' : designer.attr('eventid'),
				'casta_componentid' : designer.attr('id')
			};
			params['ismenu'] = 'true';
			params['menuitem'] = $(this).attr('menuitem');

			if ($(this).attr('confmsg')) {
				if (confirm($(this).attr('confmsg'))) {
					sCall(params);
				}
			} else {
				sCall(params);
			}

		});
	}
	// end of function declaration.

	buttonateToolbar();
	prepareVerticalSlider();
	prepareMisc();
	// prepareSiteStructure();
	prepareComponentToolbar();

	prepareMenu();
	$('#selector').draggable({snap:true,scroll:true});
	$('#selector').resizable({
		handles: 'n, e, s, w', 
		autoHide: true, 
		alsoResize :'#innserselector',
		stop:function(event, ui){
		
			var selid = getSelectedItem();
			var sel= $('#' + selid);
			
			var width = $('#selector').width() - sel.css('border-left').replace('px', '') - sel.css('border-right').replace('px', '') - sel.css('padding-left').replace('px', '') - sel.css('padding-right').replace('px', '') - sel.css('margin-left').replace('px', '') - sel.css('margin-right').replace('px', '');
			var height = $('#selector').height() - sel.css('border-top').replace('px', '') - sel.css('border-bottom').replace('px', '') - sel.css('padding-top').replace('px', '') - sel.css('padding-bottom').replace('px', '') - sel.css('margin-top').replace('px', '') - sel.css('margin-bottom').replace('px', '');
			
			sel.css('width',width + 'px' ).css('height',height + 'px');
			resizeSelector();
			var designer = $('div[name=EXDragAndDropDemo]');
			var params = {
					'casta_applicationid' : designer.attr('appid'),
					'casta_eventid' : designer.attr('eventid'),
					'casta_componentid' : designer.attr('id'),
					'selItem' : selid
				};
				params['style'] = 'dimension';
				params['width'] = width;
				params['height'] = height;

				sCall(params);
		}
		
	});

	// add live click on any component that is in workspace with class .des
	// ADD the class .des
	$('.designer .designer-workspace [des-id]').live('click', positionSelector);

	$('.vertical-bar li div').live('click', positionSelectorFromBar);

	$('.designer-toolbar-section-header').click(
			function() {
				if (!$(this).next().hasClass('open')) {

					$('.designer-left-toolbar .designer-toolbar-section.open')
							.removeClass('open').hide();
					$(this).next().addClass('open').show();
				}
			});

	// configure all style buttons
	$('.designer .uts, .designer .ui-text-style').live('click', function() {
		var selid = getSelectedItem();
		var designer = $('div[name=EXDragAndDropDemo]');
		var params = {
			'casta_applicationid' : designer.attr('appid'),
			'casta_eventid' : designer.attr('eventid'),
			'casta_componentid' : designer.attr('id'),
			'selItem' : selid
		};

		var sel = $('#' + selid);
		var dd = $(this);
		if (dd.attr('slider')) {
			useSlider(dd, sel, params);
		} else if (dd.attr('colorpicker')) {
			useColorPicker(dd, sel, params);
		} else if (dd.attr('explorer')) {
			useExplorer(dd, sel, params);
		} else {
			applySimpleStyle(dd, sel, params);
		}
		resizeSelector();
	});

	$('.designer .text-style-select').live('change', function() {

		var selid = getSelectedItem();
		var designer = $('div[name=EXDragAndDropDemo]');
		var params = {
			'casta_applicationid' : designer.attr('appid'),
			'casta_eventid' : designer.attr('eventid'),
			'casta_componentid' : designer.attr('id'),
			'selItem' : selid
		};

		var sel = $('#' + selid);
		var dd = $(this);
		applySimpleStyle(dd, sel, params);
		resizeSelector();

	});

};

function remakeSelector(selid) {
	try{
	var sel = $('#' + selid);
	var height = sel.outerHeight(true);
	var width = sel.outerWidth(true);
	var pos = sel.offset();
	var marginTop = sel.css('margin-top');
	var marginRight = sel.css('margin-right');
	var marginBottom = sel.css('margin-bottom');
	var marginLeft = sel.css('margin-left');
	// var pos = findPos(sel);
	$('#selector').stop().css( {
		'height' : height + 'px',
		'width' : width + 'px',
		'top' : pos.top + 'px',
		'left' : pos.left + 'px',
		'opacity' : 0.3
	});
	
	$('#innerselector').show().css({'margin-top': sel.css('margin-top'), 'height' : sel.outerHeight(false), 'width' : sel.outerWidth(false)});
	}catch(err){
	}
}
