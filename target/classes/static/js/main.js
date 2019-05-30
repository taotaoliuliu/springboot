var utils = {};


utils.getQueryString=function(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}


utils.daterangepicker = function(){
   
    //初始化显示当前时间

   //日期控件初始化
    $('.form_time').each(function(){
        var singleDate = $(this).attr("data-single") && true || false;
        var timeShow = $(this).attr("data-time") && true || false;
        var secondsShow = $(this).attr("data-second") && true || false;
        var formatstr = timeShow?(secondsShow?'YYYY-MM-DD HH:mm:ss':'YYYY-MM-DD HH:mm'):'YYYY-MM-DD';

    	$(this).daterangepicker(
    	        {'locale': {
    	                "format": formatstr,
    	                "separator": " 至 ",
    	                "applyLabel": "确定",
    	                "cancelLabel": "取消",
    	                "fromLabel": "起始时间",
    	                "toLabel": "结束时间'",
    	                "customRangeLabel": "自定义",
    	                "weekLabel": "W",
    	                "daysOfWeek": ["日", "一", "二", "三", "四", "五", "六"],
    	                "monthNames": ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
    	                "firstDay": 2
    	            },
    	            "opens":"right",
                    "singleDatePicker": singleDate,
    	            timePickerSeconds:secondsShow,
    	            timePicker:timeShow,
    	            timePicker24Hour:true,
    	            //汉化按钮部分
    	            ranges: {
    	            	'本周': [moment().startOf("week"),moment().endOf("week")],
                        '本月': [moment().startOf("month"),moment().endOf("month")],
                        '近三个月': [moment().subtract(90, 'days'), moment()],
                      //  '近六个月': [moment().subtract(180, 'days'), moment()],
    	           },
    	            //startDate: moment().subtract(29, 'days'),
    	            //endDate: moment()
    	           startDate: false,
    	           endDate: false
    	        },
    	        function (start, end) {
    	        	/* console.log(start);
    	        	console.log(start.format('YYYY-MM-DD HH:mm:ss'));
    	            $('.form_time').val(start.format('YYYY-MM-DD HH:mm:ss') + ' - ' + end.format('YYYY-MM-DD')); */
    	           // alert($("#startDate").val());
    	            console.log(this.startDate);
    	            
    	            
    	            $("#startTime").val(start.format('YYYY-MM-DD HH:mm:ss'));
    	            $("#endTime").val(end.format('YYYY-MM-DD HH:mm:ss'));
    	           /* if(!this.startDate){
    	                this.element.val('');
    	            }else{
    	                this.element.val(this.startDate.format(this.locale.format));
    	            }*/
    	        }
    	      
    	   );
    	
    	 $(this).on('apply.daterangepicker', function(ev, picker) {
             if (singleDate) {
                 $(this).val(picker.startDate.format(formatstr));
             }else{
                 $(this).val(picker.startDate.format(formatstr) + ' 至 ' + picker.endDate.format(formatstr));
             }
             $("#startTime").val(picker.startDate.format(formatstr));
             $("#endTime").val(picker.endDate.format(formatstr));
             // 页面中有多个时间区间选择插件时，将开始、结束时间保存到input同级的开始、结束时间框内
             $(this).siblings(".startTime").val(picker.startDate.format(formatstr));
             $(this).siblings(".endTime").val(picker.endDate.format(formatstr));
         });
    	 
    	 
    	 $(this).on('cancel.daterangepicker', function(ev, picker) {
     	    $(".form_time").val('');

         });
    	    
    	    $(".form_time").val('');
    	
    })
	
	
}