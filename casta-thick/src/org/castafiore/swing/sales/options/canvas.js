function roundRect(ctx, x, y, width, height, radiusx,radiusy) {
	if (typeof stroke == "undefined") {
		stroke = true;
	}
	if (typeof radiusx === "undefined") {
		radiusx = 5;
	}
	
	if (typeof radiusy === "undefined") {
		radiusy = 5;
	}
	ctx.beginPath();
	ctx.moveTo(x + radiusx, y);
	ctx.lineTo(x + width - radiusx, y);
	ctx.quadraticCurveTo(x + width, y, x + width, y + radiusy);
	ctx.lineTo(x + width, y + height - radiusy);
	ctx.quadraticCurveTo(x + width, y + height, x + width - radiusx, y + height);
	ctx.lineTo(x + radiusx, y + height);
	ctx.quadraticCurveTo(x, y + height, x, y + height - radiusy);
	ctx.lineTo(x, y + radiusy);
	ctx.quadraticCurveTo(x, y, x + radiusx, y);
	ctx.closePath();
	
}


function drawEllipse(ctx, x, y, w, h) {
	  var kappa = .5522848,
	      ox = (w / 2) * kappa, // control point offset horizontal
	      oy = (h / 2) * kappa, // control point offset vertical
	      xe = x + w,           // x-end
	      ye = y + h,           // y-end
	      xm = x + w / 2,       // x-middle
	      ym = y + h / 2;       // y-middle

	  ctx.beginPath();
	  ctx.moveTo(x, ym);
	  ctx.bezierCurveTo(x, ym - oy, xm - ox, y, xm, y);
	  ctx.bezierCurveTo(xm + ox, y, xe, ym - oy, xe, ym);
	  ctx.bezierCurveTo(xe, ym + oy, xm + ox, ye, xm, ye);
	  ctx.bezierCurveTo(xm - ox, ye, x, ym + oy, x, ym);
	  ctx.closePath();
	  //ctx.stroke();
}
