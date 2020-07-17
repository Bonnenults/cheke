var Invoice = {};
var InvoiceAdmin = {};

Invoice.initCheckBox=function(){
    $("body").on('click','#06',function () {
        $(".individual").prop("checked", this.checked);
    });
    $("body").on('click','.individual',function() {
        var $subs = $(".individual");
        $("#06").prop("checked",$subs.length == $subs.filter(":checked").length ? true : false);
    });
}

Invoice.changeStatus=function(status){
    $("#invoiceStatus").val(status);
    if(status==2){
        $("#invoiceTimeSpan").show();
    }else{
        $("#invoiceTimeSpan").hide();
    }
    $(".qy_center_seach input").val("");
    App.commonFormSubmit('filter_form');
}

InvoiceAdmin.changeStatus=function(status){
    $("#status").val(status);
    $(".qy_center_seach input").val("");
    App.commonFormSubmit('filter_form');
}

Invoice.singleApply=function(orderId){
    Invoice.applyInvoice(orderId);
}

Invoice.mutipleApply=function(){
    var orderIds = "";
    $(":input[name='orderId']:checked").each(function(){
        var orderId = $(this).val();
        orderIds += orderId+ ",";
    });

    if(orderIds.length > 0) {
        orderIds = orderIds.substring(0, orderIds.length - 1);
    }else {
        mizhu.alert("请选择要开票的订单");
        return;
    }
    Invoice.applyInvoice(orderIds);
}

Invoice.applyInvoice=function(orderIds){
    $(".mask_float").show();
    $("#invoice_details").show();
    jQuery.ajax({
        type: "POST",
        url: contextpath + '/invoice/cheke/list/getInvoiceInfo.action',
        dataType: "json",
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            mizhu.alert('系统出现错误，请稍候再试' + errorThrown.toString());
        },
        success: function (data) {
            var party = data.party;
            $("#orderIds").val(orderIds);
            if(party.partyClass!=4){
                $("#invoiceTitle").attr("readonly","readonly");
                $("#invoiceNumber").attr("readonly","readonly");
            }else{
                $("#invoiceTitle").removeAttr("readonly");
                $("#invoiceNumber").removeAttr("readonly");
            }
            $("#invoiceTitle").val(party.invoiceTitle);
            $("#invoiceNumber").val(party.invoiceNumber);
            $("#invoiceAddress").val(party.invoiceAddress);
            $("#invoiceBank").val(party.invoiceBank);
        }
    });
}

Invoice.viewInvoice=function(invoiceId){
    $(".mask_float").show();
    $("#invoice_details_view").show();
    jQuery.ajax({
        type: "POST",
        data:{"invoiceId":invoiceId},
        url: contextpath + '/invoice/admin/list/getInvoice.action',
        dataType: "json",
        success: function (data) {
            var invoice = data.invoice;
            var money = invoice.money;
            $("#invoiceIdDialog").val(invoice.id);
            $("#invoiceTitle_view").val(invoice.invoiceTitle);
            $("#invoiceNumber_view").val(invoice.invoiceNumber);
            $("#money_view").val(money.toFixed(2)+"元");
        }
    });
}

Invoice.closeSubmitBox=function(){
    $(".mask_float").hide();
    $("#invoice_details").hide();
}

Invoice.closeViewBox=function(){
    $(".mask_float").hide();
    $("#invoice_details_view").hide();
}

Invoice.submitInvoice = function () {
    var invoiceTitle = $("#invoiceTitle").val();
    var invoiceNumber = $("#invoiceNumber").val();
    var invoiceAddress = $("#invoiceAddress").val();
    var invoiceBank = $("#invoiceBank").val();

    if(!invoiceTitle||invoiceTitle.length>50){
        mizhu.alert('发票抬头不能为空且要小于等于50字');
        return;
    }
    if(!invoiceNumber||invoiceNumber.length<15){
        mizhu.alert('纳税人识别号不能为空且要大于等于15字');
        return;
    }
    if(!invoiceNumber||invoiceNumber.length>20){
        mizhu.alert('纳税人识别号不能为空且要小于等于20字');
        return;
    }
    if(invoiceAddress&&invoiceAddress.length>150){
        mizhu.alert('地址、电话要小于等于150字');
        return;
    }
    if(invoiceBank&&invoiceBank.length>150){
        mizhu.alert('开户行及银行账号要小于等于150字');
        return;
    }

    var datas = $("#apply_invoice_form").serialize();
    jQuery.ajax({
        type: "POST",
        url: contextpath + '/invoice/cheke/list/applyInvoice.action',
        data: datas,
        dataType: "json",
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            mizhu.alert('系统出现错误，请稍候再试' + errorThrown.toString());
        },
        success: function (data) {
            var msg = data.msg;
            if ("ok" == msg) {
                mizhu.alert("申请成功！");
                App.commonFormSubmit('filter_form');
                Invoice.closeSubmitBox();
            } else {
                mizhu.alert(msg);
            }
        }
    });
}

Invoice.submitInvoicePerson = function () {
    var invoiceTitle = $("#invoiceTitle").val();
    var invoiceNumber = $("#invoiceNumber").val();
    var invoiceAddress = $("#invoiceAddress").val();
    var invoiceBank = $("#invoiceBank").val();

    //if(!invoiceTitle||invoiceTitle.length>50){
    //    mizhu.alert('发票抬头不能为空且要少于50字');
    //    return;
    //}
    //if(invoiceNumber&&invoiceNumber.length<15){
    //    mizhu.alert('纳税人识别号要大于等于15字');
    //    return;
    //}
    //if(invoiceNumber&&invoiceNumber.length>20){
    //    mizhu.alert('纳税人识别号要小于等于20字');
    //    return;
    //}
    if(invoiceAddress&&invoiceAddress.length>150){
        mizhu.alert('地址、电话要小于等于150字');
        return;
    }
    if(invoiceBank&&invoiceBank.length>150){
        mizhu.alert('开户行及银行账号要小于等于150字');
        return;
    }

    var datas = $("#apply_invoice_form").serialize();
    jQuery.ajax({
        type: "POST",
        url: contextpath + '/invoice/cheke/list/applyInvoice.action',
        data: datas,
        dataType: "json",
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            mizhu.alert('系统出现错误，请稍候再试' + errorThrown.toString());
        },
        success: function (data) {
            var msg = data.msg;
            if ("ok" == msg) {
                mizhu.alert("申请成功！");
                App.commonFormSubmit('filter_form');
                Invoice.closeSubmitBox();
            } else {
                mizhu.alert(msg);
            }
        }
    });
}

Invoice.confirmInvoice=function(){
    var invoiceId = $("#invoiceIdDialog").val();
    jQuery.ajax({
        type: "POST",
        url: contextpath + '/invoice/admin/list/confirmInvoice.action',
        data: {"invoiceId":invoiceId},
        dataType: "json",
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            mizhu.alert('系统出现错误，请稍候再试' + errorThrown.toString());
        },
        success: function (data) {
            var msg = data.msg;
            if ("ok" == msg) {
                mizhu.confirmAlert("确认已开票！",function(flag){
                    Invoice.closeViewBox();
                    App.commonFormSubmit('filter_form');
                });
            } else {
                mizhu.alert(msg);
            }
        }
    });
}
