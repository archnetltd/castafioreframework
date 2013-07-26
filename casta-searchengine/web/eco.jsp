<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>The Mauritian E Mall</title>
		<link rel="stylesheet" href="css/1.css" type="text/css"></link>
		<link rel="stylesheet" href="blueprint/screen.css" type="text/css" media="screen, projection"></link>
		<link rel="stylesheet" href="css/themes/smoothness/theme.css" type="text/css"></link>
		<link rel="stylesheet" href="css/themes/EXFinder.css" type="text/css"></link>
	<style>
				.toolbar .pager { height: 20px }
				.toolbar .sorter {}
				.toolbar-bottom {}
				.toolbar a { color: #808184; }
				.toolbar a:hover { text-decoration: underline; }
				.pager { padding: 5px 10px;  font-size: 8pt; vertical-align: middle; display: block; line-height: 17px; }
				.pager .amount, .pager .view-mode { float: left; margin: 1px 20px 0 0; }
				.pager .sort-by { float: left; margin-top: 1px; }
				.pager .limiter { float: right; height: 17px; }
				.pager .limiter label { vertical-align:middle; }
				.pager .limiter select { padding:0; margin:0; vertical-align: middle; font-size: 7pt; border: 1px solid #ddd; }
				.pager .pages {  float: right; margin: 1px 0 0 0px; }
				.pager .pages ol { display:inline; }
				.pager .pages li { display:inline; margin:0 2px; }
				.pager .pages li.current { text-decoration: underline; font-weight: bold; }
				.pager .pages .current {}
				.pager .delimiter { margin: 0 5px; }
				/* View Type: Grid */
				.products-grid { position:relative; margin: 20px;list-style: none;}
				.products-grid.last { border-bottom:0; }
				.products-grid li.itema { float:left; width:125px; padding:0 0 74px; margin-right: 8px; text-align: center;cursor: pointer; }
				.products-grid .product-image:hover {-moz-box-shadow:5px 5px 5px #333;-webkit-box-shadow:5px 5px 5px #333;box-shadow:5px 5px 5px #333}
				.products-grid li.itema.last { margin-right: 0; }
				.products-grid .product-image { display:block; width:124px; height:124px; border:1px solid #ddd; margin:0 0 10px; background-color: #fff; }
				
				.products-grid .product-name {font-size: 10px;font-weight: bold;height: 28px;overflow: hidden;}
				
				
				.products-grid .product-name a { color:#000; text-decoration: none; }
				.products-grid .product-name a:hover { text-decoration: underline; }
				.products-grid .price-box { margin: 0; }
				.products-grid .price-box .price { color: #000; font-size:10px; }
				.products-grid .price-box .price .sign { margin-right: 3px; }
				.products-grid .availability { line-height:21px; }
				.products-grid .actions { position:absolute; bottom:20px; width:124px; }
				.cart-widget{padding:0;-moz-box-shadow:5px 5px 5px #333;-webkit-box-shadow:5px 5px 5px #333;box-shadow:5px 5px 5px #333;}
				.cart-widget h4.ui-widget-header{padding: 3px;margin: 0;border-bottom:none;}
				.cart-widget .product-categories{padding:10px 15px;margin: 0;min-height: 50px;list-style: none;}
				.cart-widget .product-categories li{padding: 3px 5px;margin: 3px 0}
				.cart-widget .product-categories li span{float: right;}
				.search-bar input{border: 0 none;color: #929497;float: left;font: 8pt/19px Tahoma,Geneva,sans-serif;height: 19px;outline: medium none;padding: 0 10px;width: 425px;margin: 3px 5px 3px 30px}
				.search-bar button{margin: 3px;float: left;};
				
				/* Product Images */
.product-view .product-img-box { float:left; width:225px; }
.col3-layout .product-view .product-img-box { float:none; margin:0 auto; }
.product-view .product-img-box .product-image { margin:0 0 10px; border:1px solid #cacaca; }
.product-view .product-img-box .product-image-zoom { position:relative; width:225px; height:225px; overflow:hidden; z-index:9; }
.product-view .product-img-box .product-image-zoom img { position:absolute; left:0; top:0; cursor:move; }
.product-view .product-img-box .zoom-notice { margin:0 0 10px; text-align:center; }
.product-view .product-img-box .zoom { position:relative; z-index:9; height:24px; margin:0 auto 13px; padding:0 28px; background:url(../images/slider_bg.gif) 50% 50% no-repeat; cursor:pointer; }
.product-view .product-img-box .zoom.disabled { -moz-opacity:.3; -webkit-opacity:.3; -ms-filter:"progid:DXImageTransform.Microsoft.Alpha(Opacity=30)";/*IE8*/ opacity:.3; }
.product-view .product-img-box .zoom #track { position:relative; height:18px; }
.product-view .product-img-box .zoom #handle { position:absolute; left:0; top:3px; width:9px; height:18px; background:url(../images/magnifier_handle.gif) 0 0 no-repeat;  }
.product-view .product-img-box .zoom .btn-zoom-out { position:absolute; left:10px; top:7px; }
.product-view .product-img-box .zoom .btn-zoom-in { position:absolute; right:10px; top:7px; }
.product-view .product-img-box .more-views { border-bottom: 1px solid #ddd; }
.product-view .product-img-box .more-views h2 { font-size:10px; font-weight:normal; padding:0 0 2px; border-bottom:1px solid #ccc; margin:0 0 8px; text-transform:uppercase; }
.product-view .product-img-box .more-views ul { margin-left:-10px }
.product-view .product-img-box .more-views li { float:left; margin:0 0 8px 10px; }
.product-view .product-img-box .more-views li a { float:left; width:66px; height:66px; border:1px solid #ddd; overflow:hidden; }

.product-image-popup { margin:0 auto; }
.product-image-popup .buttons-set { float:right; clear:none; border:0; margin:0; padding:0; }
.product-image-popup .nav { font-weight:bold; margin:0 100px; text-align:center; }
.product-image-popup .image { display:block; margin:10px 0; }
.product-image-popup .image-label { font-size:12px; font-weight:bold; margin:0 0 10px; color:#2f2f2f; }

/* Product Shop */
.product-view { margin-top: 38px; }
.product-view .product-shop { float:right; width:394px; padding-left: 34px; }
.col1-layout .product-view .product-shop { float:right; width:430px; }
.col3-layout .product-view .product-shop { float:none; width:auto; }
.product-view .product-shop .product-name { margin:0 0 5px; }
.product-view .product-shop .product-name h1 { font-weight: bold; font-size: 14pt; }
.product-view .product-shop .availability { margin:10px 0; color: #008142; }
.product-view .product-shop .short-description { margin: 20px 0 10px 0; padding: 20px 0 10px 0; border-top: 1px solid #808184; }
.product-view .product-shop .short-description h2 { color: #008142; font: normal 11pt Verdana, Arial, Helvetica; margin-bottom: 10px; }
.product-view .product-shop .price-box { margin:10px 0; }
.product-view .product-shop .add-to-links { margin:0; font-size: 8pt; }
.product-view .product-shop .add-to-links { text-align: left; }
.product-view .product-shop .add-to-links li {  }
.product-view .product-shop .add-to-links li .separator { display:none !important; }
.product-view .product-shop .product-options-bottom .paypal-logo { float:left; }
.product-view .product-img-box .more-views { border-bottom: 1px solid #ddd; }
.product-view .product-img-box .more-views h2 { font-size:10px; font-weight:normal; padding:0 0 2px; border-bottom:1px solid #ccc; margin:0 0 8px; text-transform:uppercase; }
.product-view .product-img-box .more-views ul { margin-left:-10px;list-style: none; }
.product-view .product-img-box .more-views li { float:left; margin:0 0 8px 10px; }
.product-view .product-img-box .more-views li a { float:left; width:66px; height:66px; border:1px solid #ddd; overflow:hidden; }
.product-view .product-img-box .product-image { margin:0 0 10px; border:1px solid #cacaca; }
.product-view .product-img-box .more-views ul:after,
.product-view .box-tags .form-add:after,
.product-view .product-shop .short-description:after,
.product-view .box-description:after{display:block; content:"."; clear:both; font-size:0; line-height:0; height:0; overflow:hidden; }
.product-view .product-img-box .more-views h4{font-size: 1.05em;line-height: 1.35;margin-bottom: 0.45em;font-weight: bold;}

.form-list li {margin: 0 0 6px;list-style: none;}
.form-list .field.name-firstname, .form-list .field.left-field {margin-right: 23px;}
.form-list label {color: #808184;font-weight: bold;position: relative;top: 9px;}
.form-list label.required em {color: #EB340A;float: right;font-style: normal;line-height: 12px;position: absolute;right: -7px;top: 0;}
.form-list .input-box {float: right;width: 199px;}
.form-list input.input-text {width: 189px;}
.form-list li.self-wide .input-box {float: right;left: -18px; position: relative;width: 510px;}
.form-list li.self-wide input.input-text {width: 500px;}
.form-list .field {float: left;width: 288px;}
</style>
	</head>
	<body style="margin: 0;padding: 0;">
	<div id="switcher"></div>
		<div id="ECommerceApp" style="margin: auto"></div>
		
		<script type="text/javascript" src="js/1.js"></script>
		<script type="text/javascript" src="js/2.js"></script>
		<script type="text/javascript" src="js/ext.js"></script>
		<script type="text/javascript" src="http://jqueryui.com/themeroller/themeswitchertool/"></script>

		
		<script>
			$(document).ready(function(){
				$('#ECommerceApp').castafiore('ECommerceApp');
				$('#switcher').themeswitcher();
			});
		</script>
	</body>
</html>