/**
 * 初始化下来查询框样式
 * 
 * @returns
 */
function dropDownStyle() {
	// 下拉框
	if (!ace.vars['touch']) {
		$('.chosen-select').chosen({
			allow_single_deselect : true
		});
		$(window).off('resize.chosen').on('resize.chosen', function() {
			$('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({
					'width' : $this.parent().width()
				});
			});
		}).trigger('resize.chosen');
		$(document).on('settings.ace.chosen',
				function(e, event_name, event_val) {
					if (event_name != 'sidebar_collapsed')
						return;
					$('.chosen-select').each(function() {
						var $this = $(this);
						$this.next().css({
							'width' : $this.parent().width()
						});
					});
				});
	}
}

/**
 * 日期框样式
 * 
 * @returns
 */
function datePickerStyle() {
	// 日期框
	$('.date-picker').datepicker({
		autoclose : true,
		todayHighlight : true
	});
}