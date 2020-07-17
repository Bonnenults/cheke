/**
 * 自动扫描模块
 */
Clover.validator.container = {};

$(function () {
    $("form.validation-required").each(function () {
        var jForm = $(this);
        var formId = jForm.attr("id");
        if (!formId) {
            formId = Math.random();
            jForm.attr("id", formId);
        }
        var validator = new Clover.validator.Validator(jForm);
        Clover.validator.container[formId] = validator;
        jForm.find(":input[data-validation]").each(function () {
            var inputType = this.type;
            var jField = $(this);
            var pattern = jField.data("validation");
            var message = jField.data("message");
            if (inputType && (inputType == "checkbox" || inputType == "radio")) {
                validator.addValidation("[name='" + this.name + "']", pattern, message);
            } else {
                validator.addValidation(this, pattern, message);
            }
        });
    })
});