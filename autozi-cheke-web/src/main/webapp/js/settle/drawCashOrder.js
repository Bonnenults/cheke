/**
 * Created by phoenix on 2017-12-13.
 */
var DrawCashOrder = {};

/**
 * 切换列表标签
 */
DrawCashOrder.changeListStatus = function (status) {
    if(status ==10){
        App.jump('/drawCashOrder/admin/list/listDrawCashOrder.action')
    }else if(status ==40){
        App.jump('/drawCashOrder/admin/list/listDrawCashOrderPayEnd.action')
    }
};

DrawCashOrder.confirmDrawCash=function(orderId){
    mizhu.confirm("提现确认","确认提现？",function(flag){
        if(flag){
            jQuery.ajax({
                type:"POST",
                url:contextpath+'/drawCashOrder/admin/list/confirmDrawCash.action',
                data:{"orderId":orderId},
                dataType:"json",
                success:function(data){
                    var msg = data.msg;
                    mizhu.confirmAlert("申请成功",function(flag){
                        window.location.href=contextpath+'/drawCashOrder/admin/list/listDrawCashOrder.action';
                    });
                }
            });
        }
    })

}