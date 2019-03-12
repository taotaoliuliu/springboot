$(function () {
    $("#jqGrid").jqGrid({
        url: '/sys/menu/getListPage',
        datatype: "json",
        colModel: [
            {label: '任务ID', name: 'uuid', width: 60, key: true},
            {label: '菜单名称', name: 'powername', width: 100},
            {label: '菜单图片', name: 'imgurl', width: 100},
            {label: '菜单表达式', name: 'powerkey', width: 100},
            {label: '按钮 ', name: 'isbutton', width: 100},
            {
                label: '状态', name: 'status', width: 60, formatter: function (value, options, row) {
                return value === 1 ?
                    '<span class="label label-success">是</span>' :
                    '<span class="label label-danger">否</span>';
            }
            },
            {label: '操作', name: 'process', width: 100}
            

            
        ],
        viewrecords: true,
        height: 485,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "page.list",
            page: "page.pageNum",
            total: "page.pages",
            records: "page.total"
        },
        prmNames: {
            page: "pageNum",
            rows: "pageSize",
            order: "order"
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
            
            var ids = $("#jqGrid").jqGrid('getDataIDs');
            
            for (var i = 0; i < ids.length; i++) {
            	var cl = ids[i];
            	update = "<input class='btn btn-primary' type='button' value='编辑' onclick='update2()'/>&nbsp;";
            	copy = "<input type='button' value='复制' onclick='deleteStu("
            	+ cl + ")'/>&nbsp;";
            	delet = "<input type='button' value='删除' onclick='viewStu("
            	+ cl + ")'/>";
            	
            	jQuery("#jqGrid").jqGrid('setRowData',
                		ids[i], {
                		process: update + copy + delet
                		});
            }

            
            
            
            
            
        }
    });
});

var vm = new Vue({
    el: '#rrapp',
    data: { 
    	showList:true,
    
    	powername:"",
    },
    methods: {
      
    	reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'powername': vm.powername},
                page: page
            }).trigger("reloadGrid");
        },
        search: function () {
            vm.reload();
        },
        handleadd :function(){
        	location.href="/sys/menu/toAdd";
        },
        update: function () {
            //var jobId = getSelectedRow();
            console.log("aaaa");

            var jobIds = getSelectedRows();
            console.log(jobIds+"aaaa");

            if (jobIds == null) {
                return;
            }
            
          //  console.log(jobId);
            console.log(jobIds);
           

            
        },
    }
});


function update(){
	alert('aa');
	
}