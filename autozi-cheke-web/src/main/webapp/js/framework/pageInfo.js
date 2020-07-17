var PageInfo = new Object();
/**
 * @author lihf
 * 
 * pageNo:需要跳转到的页码
 * url:请求的url
 * data_container:数据返回时，需要填充的页面Id；格式为[container_id1;container_id2;container_id3]
 * search_condition:用于保存页面搜索条件的hidden项，需要后台组织好数据格式，格式为：key1=value1&key2=value2
 * 
 */
PageInfo.jumpToPage = function(pageNo){
	var data_container_list = $('#data_container').val().split(";");
	var url = $('#filter_form').attr("action");
	$.ajax({
		type:'POST',
		url:contextpath+url,
		data:"pageNo="+pageNo+"&"+$('#search_condition').val()+"&ajax=ajax",
		dataType:'html',
		error:function(XMLHttpRequest, textStatus, errorThrown){
			alert('系统出现错误，请稍候再试');
		},
		success:function(html){
			var html_array = html.split('\$JXC');
			var length = html_array.length;
			for(var i=0;i<length;i++){
				$('#'+data_container_list[i]).html('').html(html_array[i]);
			}
			//App.initDataGridForPage();
		}
	});
};

/**
 * 特殊分页
 * 
 * @param pageNo
 * @param url
 * @param dataContainerList
 * @param conditions
 */
PageInfo.jumpToPageSpecial = function(pageNo){
    var data_container_list = $('#data_container_dialog').val().split(";");
    var url = $('#filter_form_dialog').attr("action");
	$.ajax({
		type:'POST',
		url:contextpath+url,
		data:"pageNo="+pageNo+"&"+$('#search_condition_dialog').val()+"&ajax=ajax",
		dataType:'html',
		error:function(XMLHttpRequest, textStatus, errorThrown){
			alert('系统出现错误，请稍候再试');
		},
		success:function(html){
			var html_array = html.split('\$JXC');
			var length = html_array.length;
			for(var i=0;i<length;i++){
				$('#'+data_container_list[i]).html('').html(html_array[i]);
			}
			App.initDataGridForPage();
		}
	});
};

/**
 * 验证输入页码的合法性
 */
PageInfo.verify = function(curPage,totalPage){
	var number = /^\d+$/;
	if(!number.test(curPage)
			|| parseInt(curPage)>totalPage || parseInt(curPage)<=0){
		alert("页号必须为大于0的整数，并且不能大于总页数。");
		return false;
	}
	return true;
};

/**
 * 特殊分页
 * 主要为了一个页面有多个弹出页的情况
 * @param pageNo
 * @param url
 * @param dataContainerList
 * @param conditions
 */
PageInfo.jumpToPageSpecial2 = function (pageNo, url, dataContainerList, conditions) {
    var data_container_list;
    if (dataContainerList != undefined) {
        data_container_list = dataContainerList.split(";");
    } else {
        data_container_list = $('#data_container').val().split(";");
    }
    var searchConditions;
    if (conditions == undefined) {
        searchConditions = $('#search_condition').val();
    } else {
        searchConditions = conditions;
    }
    $.ajax({
        type:'POST',
        url:contextpath  + url,
        data:"pageNo=" + pageNo + "&" + searchConditions+"&ajax=ajax",
        dataType:'html',
        error:function (XMLHttpRequest, textStatus, errorThrown) {
            alert('系统出现错误，请稍候再试');
        },
        success:function (html) {
            var html_array = html.split('\$JXC');
            var length = html_array.length;
            for (var i = 0; i < length; i++) {
                $('#' + data_container_list[i]).html('').html(html_array[i]);
            }
            App.initDataGridForPage();
        }
    });
};