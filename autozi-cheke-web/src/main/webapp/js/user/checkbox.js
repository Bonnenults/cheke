$(function(){
	//新增角色全选多选
		$("body").on("click",".all_checkbox input", function () {
			var checkChild = $(this).parent().parent().next().children().find(".clarity");
        	checkChild.prop("checked", this.checked);
   		});
	    $(".checkbox_child li input").each(function(index,element){
	    	var _that = $(this);
	    	var num = _that.parent().parent().parent().parent().attr('data_num');
	    	$(this).on("click",function(){
	    		checkedAll(num,_that);
	    	});
	    })
	    function checkedAll(num,_that){
	    	var $subs = $("div[data_num='"+num+"']").find('li input');//$(".checkbox_child").eq(num).find('li input');
	        var checkParent = _that.parent().parent().parent().prev().children().find(".clarity");
	        checkParent.prop("checked",$subs.filter(":checked").length>0 ? true : false);
//	        checkParent.prop("checked",$subs.length == $subs.filter(":checked").length ? true : false);
	    }
})
