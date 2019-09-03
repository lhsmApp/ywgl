//it causes some flicker when reloading or navigating grid
//it may be possible to have some custom formatter to do this as the grid is being created to prevent this
//or go back to default browser checkbox styles for the grid
function styleCheckbox(table) {
	/**
	 * $(table).find('input:checkbox').addClass('ace') .wrap('<label />')
	 * .after('<span class="lbl align-top" />')
	 * 
	 * 
	 * $('.ui-jqgrid-labels th[id*="_cb"]:first-child')
	 * .find('input.cbox[type=checkbox]').addClass('ace') .wrap('<label
	 * />').after('<span class="lbl align-top" />');
	 */
}

// unlike navButtons icons, action icons in rows seem to be hard-coded
// you can change them like this in here if you want
function updateActionIcons(table) {
	/**
	 * var replacement = { 'ui-ace-icon fa fa-pencil' : 'ace-icon fa fa-pencil
	 * blue', 'ui-ace-icon fa fa-trash-o' : 'ace-icon fa fa-trash-o red',
	 * 'ui-icon-disk' : 'ace-icon fa fa-check green', 'ui-icon-cancel' :
	 * 'ace-icon fa fa-times red' }; $(table).find('.ui-pg-div
	 * span.ui-icon').each(function(){ var icon = $(this); var $class =
	 * $.trim(icon.attr('class').replace('ui-icon', '')); if($class in
	 * replacement) icon.attr('class', 'ui-icon '+replacement[$class]); })
	 */
}

// replace icons with FontAwesome icons like above
function updatePagerIcons(table) {
	var replacement = {
		'ui-icon-seek-first' : 'ace-icon fa fa-angle-double-left bigger-140',
		'ui-icon-seek-prev' : 'ace-icon fa fa-angle-left bigger-140',
		'ui-icon-seek-next' : 'ace-icon fa fa-angle-right bigger-140',
		'ui-icon-seek-end' : 'ace-icon fa fa-angle-double-right bigger-140'
	};
	$('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon')
			.each(function() {
				var icon = $(this);
				var $class = $.trim(icon.attr('class').replace('ui-icon', ''));

				if ($class in replacement)
					icon.attr('class', 'ui-icon ' + replacement[$class]);
			})
}

function enableTooltips(table) {
	$('.navtable .ui-pg-button').tooltip({
		container : 'body'
	});
	$(table).find('.ui-pg-div').tooltip({
		container : 'body'
	});
}

function style_delete_form(form) {
	var buttons = form.next().find('.EditButton .fm-button');
	buttons.addClass('btn btn-sm btn-white btn-round').find('[class*="-icon"]')
			.hide();// ui-icon, s-icon
	buttons.eq(0).addClass('btn-danger').prepend(
			'<i class="ace-icon fa fa-trash-o"></i>');
	buttons.eq(1).addClass('btn-default').prepend(
			'<i class="ace-icon fa fa-times"></i>')
}

function style_edit_form(form) {
	// enable datepicker on "sdate" field and switches for "stock" field
	/*form.find('input[name=sdate]').datepicker({
		format : 'yyyy-mm-dd',
		autoclose : true
	})

	form.find('input[name=stock]').addClass('ace ace-switch ace-switch-5')
			.after('<span class="lbl"></span>');*/
	// don't wrap inside a label element, the checkbox value won't be submitted
	// (POST'ed)
	// .addClass('ace ace-switch ace-switch-5').wrap('<label class="inline"
	// />').after('<span class="lbl"></span>');

	// update buttons classes
	var buttons = form.next().find('.EditButton .fm-button');
	buttons.addClass('btn btn-sm').find('[class*="-icon"]').hide();// ui-icon,
	// s-icon
	buttons.eq(0).addClass('btn-primary').prepend(
			'<i class="ace-icon fa fa-check"></i>');
	buttons.eq(1).prepend('<i class="ace-icon fa fa-times"></i>')

	buttons = form.next().find('.navButton a');
	buttons.find('.ui-icon').hide();
	buttons.eq(0).append('<i class="ace-icon fa fa-chevron-left"></i>');
	buttons.eq(1).append('<i class="ace-icon fa fa-chevron-right"></i>');
}

function style_search_filters(form) {
	form.find('.delete-rule').val('X');
	form.find('.add-rule').addClass('btn btn-xs btn-primary');
	form.find('.add-group').addClass('btn btn-xs btn-success');
	form.find('.delete-group').addClass('btn btn-xs btn-danger');
}

function style_search_form(form) {
	var dialog = form.closest('.ui-jqdialog');
	var buttons = dialog.find('.EditTable')
	buttons.find('.EditButton a[id*="_reset"]').addClass('btn btn-sm btn-info')
			.find('.ui-icon').attr('class', 'ace-icon fa fa-retweet');
	buttons.find('.EditButton a[id*="_query"]').addClass(
			'btn btn-sm btn-inverse').find('.ui-icon').attr('class',
			'ace-icon fa fa-comment-o');
	buttons.find('.EditButton a[id*="_search"]').addClass(
			'btn btn-sm btn-purple').find('.ui-icon').attr('class',
			'ace-icon fa fa-search');
}

function beforeDeleteCallback(e) {
	var form = $(e[0]);
	if (form.data('styled'))
		return false;
	form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner(
			'<div class="widget-header" />')
	style_delete_form(form);
	form.data('styled', true);
}

function beforeEditOrAddCallback(e) {
	var form = $(e[0]);
	form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner(
			'<div class="widget-header" />');
	/*
	 * $("tr", form).each(function() { var inputs =
	 * $(">td.DataTD:has(input,select)",this); if (inputs.length == 1) { var tds =
	 * $(">td", this); tds.eq(1).attr("colSpan", tds.length - 1);
	 * tds.slice(2).hide(); } });
	 */
	style_edit_form(form);
}

function beforeSearchCallback(e) {
	var form = $(e[0]);
	form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap(
			'<div class="widget-header" />')
	style_search_form(form);
}

/**
 * 增加成功
 * 
 * @param response
 * @param postdata
 * @returns
 */
function fn_addSubmit(response, postdata) {
	// console.log("Add Success Execute");
	// var json=response.responseText;
	// alert(json);//显示返回值

	if (response.responseJSON.code == 0) {
		// console.log("Add Success");
		$("#subTitle").tips({
			side : 3,
			msg : '保存成功',
			bg : '#009933',
			time : 3
		});
		return [ true ];
	} else {
		// console.log("Add Failed"+response.responseJSON.message);
		$("#subTitle").tips({
			side : 3,
			msg : '保存失败,' + response.responseJSON.message,
			bg : '#cc0033',
			time : 3
		});
		return [ false, response.responseJSON.message ];
	}
}

// 显示隐藏查询 标准高度统一定为192（含底行），如果不含底行高度定为155.
function showQueryCondi(jqgrid, gridHeight,withBottom) {
	if (gridHeight=="undefined"||gridHeight == null || gridHeight == "" || gridHeight == 0) {
		if (withBottom) {
			//gridHeight = 192;
			gridHeight = 177;
		} else {
			//gridHeight = 155;
			gridHeight = 140;
		}
	}
	if ($(".widget-box").is(':visible')) {
		$("#btnQuery").find("i").removeClass('fa-chevron-up').addClass(
				'fa-chevron-down');
		$("#btnQuery").find("span").text("显示查询");
		$(".widget-box").hide();
		$(jqgrid).jqGrid('setGridHeight', $(window).height() - gridHeight);
	} else {
		$("#btnQuery").find("i").removeClass('fa-chevron-down').addClass(
				'fa-chevron-up');
		$("#btnQuery").find("span").text("隐藏查询");
		$(".widget-box").show();
		$(jqgrid).jqGrid('setGridHeight', $(window).height() - gridHeight - 48);
	}
	//$(".widget-box").toggle("fast");
}

//重设GridHeight
function resizeGridHeight(jqgrid, gridHeight, withBottom) {
	if (gridHeight=="undefined"||gridHeight == null || gridHeight == "" || gridHeight == 0) {
		if (withBottom) {
			//gridHeight = 192;
			gridHeight = 177;
		} else {
			//gridHeight = 155;
			gridHeight = 140;
		}
	}
	
	if ($(".widget-box").is(':visible')) {
		$(jqgrid).jqGrid('setGridHeight', $(window).height() - gridHeight - 65);
	} else {
		$(jqgrid).jqGrid('setGridHeight', $(window).height() - gridHeight);
	}
}

//显示隐藏查询 标准高度统一定为192（含底行），如果不含底行高度定为155.
function showQueryCondiFour(jqGridBase, jqGridDetail, gridHeight,withBottom) {
	if (gridHeight=="undefined"||gridHeight == null || gridHeight == "" || gridHeight == 0) {
		gridHeight = 279;
	}
	$(jqGridBase).jqGrid( 'setGridWidth', $(".page-content").width());
	$(jqGridDetail).jqGrid( 'setGridWidth', $(".page-content").width());
	if ($(".widget-box").is(':visible')) {
		$("#btnQuery").find("i").removeClass('fa-chevron-up').addClass('fa-chevron-down');
		$("#btnQuery").find("span").text("显示查询");
		$(".widget-box").hide();
		$(jqGridBase).jqGrid('setGridHeight', ($(window).height() - gridHeight) * (2/5));
		$(jqGridDetail).jqGrid('setGridHeight', ($(window).height() - gridHeight) * (3/5));
	} else {
		$("#btnQuery").find("i").removeClass('fa-chevron-down').addClass('fa-chevron-up');
		$("#btnQuery").find("span").text("隐藏查询");
		$(".widget-box").show();
		$(jqGridBase).jqGrid('setGridHeight', ($(window).height() - gridHeight - 65) * (2/5));
		$(jqGridDetail).jqGrid('setGridHeight', ($(window).height() - gridHeight - 65) * (3/5));
	}
	//$(".widget-box").toggle("fast");
}

function resizeGridHeightFour(jqGridBase, jqGridDetail, gridHeight,withBottom){
	if (gridHeight=="undefined"||gridHeight == null || gridHeight == "" || gridHeight == 0) {
		gridHeight = 279;
	}
	$(jqGridBase).jqGrid( 'setGridWidth', $(".page-content").width());
	$(jqGridDetail).jqGrid( 'setGridWidth', $(".page-content").width());
	if ($(".widget-box").is(':visible')) {
		$(jqGridBase).jqGrid('setGridHeight', ($(window).height() - gridHeight - 65) * (2/5));
		$(jqGridDetail).jqGrid('setGridHeight', ($(window).height() - gridHeight - 65) * (3/5));
	} else {
		$(jqGridBase).jqGrid('setGridHeight', ($(window).height() - gridHeight) * (2/5));
		$(jqGridDetail).jqGrid('setGridHeight', ($(window).height() - gridHeight) * (3/5));
	}
}