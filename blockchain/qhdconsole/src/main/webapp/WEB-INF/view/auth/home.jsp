<%@page language="java" contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<TITLE>管理平台</TITLE>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@include file="/resources/inc/inc.jsp"%>
<link href="${contextPath}/resources/assets/css/bootstrap/bootstrap.css" rel="stylesheet" />
<link href="${contextPath}/resources/assets/css/bootstrap/bootstrap-responsive.css" rel="stylesheet" />
<link href="${contextPath}/resources/assets/css/bootstrap/bootstrap-overrides.css" type="text/css" rel="stylesheet" />
<link href="${contextPath}/resources/assets/css/lib/jquery-ui-1.10.2.custom.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/resources/assets/css/layout.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/resources/assets/css/elements.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/resources/assets/css/icons.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/resources/assets/css/compiled/index.css" type="text/css" media="screen" />
<link href="${contextPath}/resources/assets/css/compiled/index.css" type="text/css" media="screen" rel="stylesheet" />
<!--[if lt IE 9]>
<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
</head>
<body>
	<div class="content">
		<div class="container-fluid">
			<div id="main-stats">
				<div class="row-fluid stats-row">
					<div class="span3 stat">
						<div class="data">
							<span class="number">2457</span> visits
						</div>
						<span class="date">Today</span>
					</div>
					<div class="span3 stat">
						<div class="data">
							<span class="number">3240</span> users
						</div>
						<span class="date">February 2014</span>
					</div>
					<div class="span3 stat">
						<div class="data">
							<span class="number">322</span> orders
						</div>
						<span class="date">This week</span>
					</div>
					<div class="span3 stat last">
						<div class="data">
							<span class="number">$2,340</span> sales
						</div>
						<span class="date">last 30 days</span>
					</div>
				</div>
			</div>
			<div id="pad-wrapper">
				<div class="row-fluid chart">
					<h4>
						Statistics
						<div class="btn-group pull-right">
							<button class="glow left">DAY</button>
							<button class="glow middle active">MONTH</button>
							<button class="glow right">YEAR</button>
						</div>
					</h4>
					<div class="span12">
						<div id="statsChart"></div>
					</div>
				</div>
				<!-- <div class="row-fluid section ui-elements">
					<h4>UI Elements</h4>
					<div class="span5 knobs">
						<div class="knob-wrapper">
							<input type="text" value="50" class="knob" data-thickness=".3" data-inputcolor="#333" data-fgcolor="#30a1ec" data-bgcolor="#d4ecfd"
								data-width="150" />
							<div class="info">
								<div class="param">
									<span class="line blue"></span> Active users
								</div>
							</div>
						</div>
						<div class="knob-wrapper">
							<input type="text" value="75" class="knob second" data-thickness=".3" data-inputcolor="#333" data-fgcolor="#3d88ba" data-bgcolor="#d4ecfd"
								data-width="150" />
							<div class="info">
								<div class="param">
									<span class="line blue"></span> % disk space usage
								</div>
							</div>
						</div>
					</div>
				</div> -->
			</div>
		</div>
	</div>
	<script src="${contextPath}/resources/assets/js/jquery-ui-1.10.2.custom.min.js"></script>
	<script src="${contextPath}/resources/assets/js/jquery.knob.js"></script>
	<script src="${contextPath}/resources/assets/js/jquery.flot.js"></script>
	<script src="${contextPath}/resources/assets/js/jquery.flot.stack.js"></script>
	<script src="${contextPath}/resources/assets/js/jquery.flot.resize.js"></script>
	<script type="text/javascript">
		$(function() {
			$(".knob").knob();
			$(".slider-sample1").slider({
				value : 100,
				min : 1,
				max : 500
			});
			$(".slider-sample2").slider({
				range : "min",
				value : 130,
				min : 1,
				max : 500
			});
			$(".slider-sample3").slider({
				range : true,
				min : 0,
				max : 500,
				values : [ 40, 170 ],
			});
			var visits = [ [ 1, 50 ], [ 2, 40 ], [ 3, 45 ], [ 4, 23 ], [ 5, 55 ], [ 6, 65 ], [ 7, 61 ], [ 8, 70 ], [ 9, 65 ], [ 10, 75 ], [ 11, 57 ],
					[ 12, 59 ] ];
			var visitors = [ [ 1, 25 ], [ 2, 50 ], [ 3, 23 ], [ 4, 48 ], [ 5, 38 ], [ 6, 40 ], [ 7, 47 ], [ 8, 55 ], [ 9, 43 ], [ 10, 50 ],
					[ 11, 47 ], [ 12, 39 ] ];
			var plot = $.plot($("#statsChart"), [ {
				data : visits,
				label : "Signups"
			}, {
				data : visitors,
				label : "Visits"
			} ], {
				series : {
					lines : {
						show : true,
						lineWidth : 1,
						fill : true,
						fillColor : {
							colors : [ {
								opacity : 0.1
							}, {
								opacity : 0.13
							} ]
						}
					},
					points : {
						show : true,
						lineWidth : 2,
						radius : 3
					},
					shadowSize : 0,
					stack : true
				},
				grid : {
					hoverable : true,
					clickable : true,
					tickColor : "#f9f9f9",
					borderWidth : 0
				},
				legend : {
					// show: false
					labelBoxBorderColor : "#fff"
				},
				colors : [ "#a7b5c5", "#30a0eb" ],
				xaxis : {
					ticks : [ [ 1, "JAN" ], [ 2, "FEB" ], [ 3, "MAR" ], [ 4, "APR" ], [ 5, "MAY" ], [ 6, "JUN" ], [ 7, "JUL" ], [ 8, "AUG" ],
							[ 9, "SEP" ], [ 10, "OCT" ], [ 11, "NOV" ], [ 12, "DEC" ] ],
					font : {
						size : 12,
						family : "Open Sans, Arial",
						variant : "small-caps",
						color : "#697695"
					}
				},
				yaxis : {
					ticks : 3,
					tickDecimals : 0,
					font : {
						size : 12,
						color : "#9da3a9"
					}
				}
			});
			function showTooltip(x, y, contents) {
				$('<div id="tooltip">' + contents + '</div>').css({
					position : 'absolute',
					display : 'none',
					top : y - 30,
					left : x - 50,
					color : "#fff",
					padding : '2px 5px',
					'border-radius' : '6px',
					'background-color' : '#000',
					opacity : 0.80
				}).appendTo("body").fadeIn(200);
			}
			var previousPoint = null;
			$("#statsChart").bind("plothover", function(event, pos, item) {
				if (item) {
					if (previousPoint != item.dataIndex) {
						previousPoint = item.dataIndex;
						$("#tooltip").remove();
						var x = item.datapoint[0].toFixed(0), y = item.datapoint[1].toFixed(0);
						var month = item.series.xaxis.ticks[item.dataIndex].label;
						showTooltip(item.pageX, item.pageY, item.series.label + " of " + month + ": " + y);
					}
				} else {
					$("#tooltip").remove();
					previousPoint = null;
				}
			});
		});
	</script>
</body>
</html>