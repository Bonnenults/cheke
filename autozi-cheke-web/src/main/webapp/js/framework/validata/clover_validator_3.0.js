/**
 * @fileOverview Clover Validator 表单验证工具
 * @author <a href="mailto:cloverzero@gmail.com">言の葉</a>
 * @version 3.0.1
 * @requires jQuery 1.5 or later
 */

/**
 * @namespace 从这版开始，命名空间首字母大写了
 */
var Clover = Clover || {};

/**
 * @namespace 验证器模块
 */
Clover.validator = {};

/**
 * @class 验证结果
 * @constructor
 * @param {Element} element DOM元素
 * @param {Boolean} valid 结果,true表示合法,false表示不合法
 * @param {String} [message] 不合法时的错误信息
 */
Clover.validator.ValidationResult = function (element, valid, message) {
    this.element = element;
    this.valid = valid;
    this.message = message;
};

/**
 * @class 验证结果列表
 * @constructor
 */
Clover.validator.ResultList = function () {
    this.valid = true;
    this.list = {};
    this.size = 0;
    this.firstErrorElement = null;
};

Clover.validator.ResultList.prototype = {
    /**
     * 将另一组ResultList的结果加入本list
     * @param {Clover.validator.ResultList} resultList
     */
    pushList: function (resultList) {
        var list = resultList.list;
        for (var jElId in list) {
            this.pushResult(list[jElId]);
        }
    },
    /**
     * 添加一个信息对象
     * @param {Clover.validator.ValidationResult} result
     */
    pushResult: function(result) {
        if (this.valid && !result.valid) {
            this.valid = false;
            this.firstErrorElement = result.element;
        }
        var jEl = $(result.element);
        var jElId = jEl.data("validationId");
        if (jElId == undefined) {
            jElId = Math.random();
            jEl.data("validationId", jElId);
        }
        var aResult = this.list[jElId];
        if (aResult) {
            if (aResult.valid && !result.valid) {
                this.list[jElId] = result;
            }
        } else {
            this.list[jElId] = result;
        }
        this.size++;
    }
};

/**
 * @class 表单验证者
 * @version 3.0
 * @constructor
 * @param {jQuery|String|Element} form 表单
 * @param {Object} [options] 选线
 * @see Clover.validator.Validator.defaultOptions
 */
Clover.validator.Validator = function (form, options) {
    this.context = $(form);
    this.validations = [];
    this.options = $.extend({}, Clover.validator.Validator.defaultOptions, options);
    this.messageRenderer = new this.options.rendererClass(this.options);
    if (this.options.autoBind && this.context.is("form")) {
        var validator = this;
        this.context.submit(function () {
            return validator.validate();
        });
    }
};

/** @constant */
Clover.validator.Validator.PATTERN_REG_EXP = /^(\*)?(!)?(~)?(\w+)(([=<>]{1}|>=|<=)([\w\-,#\.]+))?(\[(\d*)(,(\d*))?\])?$/;

Clover.validator.Validator.prototype = {
    /**
     * @description 执行验证
     * @return {Boolean}
     */
    validate: function () {
        var messages = new Clover.validator.ResultList();
        for (var i = 0; i < this.validations.length; i++) {
            var result = this.validations[i].validate();
            var hasExits = $(this.validations[i].target);
            if (hasExits && !result.valid) {
                messages.pushList(result);
            }
        }
        if (messages.firstErrorElement) {
            Clover.validator.autoScroll(messages.firstErrorElement);
        }
        this.messageRenderer.render(messages);
        return messages.valid;
    },

    /** @private */
    _addValidateOnBlur: function (target, validation, isLive) {
        if (this.options.validateOnBlur) {
            var messageRender = this.messageRenderer;
            if (isLive) {
                target.live("blur", function () {
                    messageRender.render(validation.validate($(this)));
                });
            } else {
                target.blur(function () {
                    messageRender.render(validation.validate($(this)));
                });
            }
        }
    },

    /**
     * 添加验证
     * @param {String} selector 目标的选择器
     * @param {String|RegExp|Function} pattern 验证模式
     * @param {String} [errorMessage] 错误信息
     */
    addValidation: function(selector, pattern, errorMessage) {
        var target = $(selector, this.context);
        var validation = this._createValidation(target, pattern, errorMessage);
        this.validations.push(validation);
        this._addValidateOnBlur(target, validation, pattern[0] == "*");
    },

    /**
     * 移除验证
     */
    removeValidation:function(selector,pattern,errorMessage){
        var target = $(selector, this.context);
        var validation = this._createValidation(target, pattern, errorMessage);
        this.validations.pop(validation);

    },

    /**
     * 添加带条件的的验证
     * @param {String} selector 目标的选择器
     * @param {String|RegExp} pattern 验证模式
     */
    addConditionalValidation: function (selector, pattern) {
        var target = $(selector, this.context);
        var thirdArgument = arguments[2];
        var validation, innerValidation;
        if (thirdArgument instanceof Function) {
            innerValidation = this._createValidation(target, pattern, arguments[3]);
            validation = new Clover.validator.ConditionValidation(innerValidation, thirdArgument);
        } else {
            innerValidation = this._createValidation(target, pattern, arguments[4]);
            var cValidation = this._createValidation($(thirdArgument), arguments[3]);
            validation = new Clover.validator.ConditionValidation(innerValidation, cValidation);
        }
        this.validations.push(validation);
        this._addValidateOnBlur(target, validation);
    },

    /**
     * 添加异步验证
     * @param selector
     * @param {Function} handler 处理函数
     * @param {String} errorMessage 错误信息
     */
    addAsyncValidation: function (selector, handler, errorMessage) {
        var target = $(selector);
        var asyncValidation = new Clover.validator.AsyncValidation(target, errorMessage , this.messageRenderer);
        this.validations.push(asyncValidation);
        var updateHandler = $.proxy(asyncValidation.updateResult, asyncValidation);
        target.blur(function () {
            handler(target, updateHandler);
        });
    },

    addCustomValidation: function(selector, handler, errorMessage, reload) {
        var target = $(selector);
        var validation = new Clover.validator.CustomerValidation(target, handler, errorMessage);
        if (reload) {
            validation = new Clover.validator.DynamicValidation(target, validation);
        }
        this.validations.push(validation);
        this._addValidateOnBlur(target, validation);
    },

    /**
     * @private
     */
    _createValidation: function (target, pattern, errorMessage) {
        if (typeof pattern == "string") {
            var matchData = Clover.validator.Validator.PATTERN_REG_EXP.exec(pattern);
            if (matchData) {
                var dynamic = matchData[1] == "*";
                var required = matchData[2] == "!";
                var reverse = matchData[3] == "~";
                var type = matchData[4];
                var operator = matchData[6];
                var value = matchData[7];
                var range = matchData[8];

                errorMessage = errorMessage || Clover.validator.errorMessageMap[operator ? type + operator : type];

                // 根据注册的Type构造validation,如果不存在则返回不验证对象
                var validationConstructor = Clover.validator.Validator.validationMap[type];
                var validation;
                if (validationConstructor && validationConstructor.getInstance) {
                    validation = validationConstructor.getInstance(type, operator, value, target, errorMessage);
                } else {
                    throw new Error("ValidatorAddValidationError: Illegal type: " + type);
                }

                if (reverse) {
                    validation = new Clover.validator.ReverseValidation(validation, errorMessage);
                }
                if (required) {
                    validation = new Clover.validator.AndValidation(new Clover.validator.RequireValidation(target, Clover.validator.errorMessageMap["required"]), validation);
                }
                if (range) {
                    var leftLength = matchData[9];
                    var rightLength = matchData[11];
                    var rangeValidation = new Clover.validator.LengthRangeValidation(target, leftLength, rightLength);
                    validation = new Clover.validator.AndValidation(validation, rangeValidation);
                }
                if (dynamic) {
                    validation = new Clover.validator.DynamicValidation(target, validation);
                }

                return validation;
            } else {
                throw new Error("ValidatorAddValidationError: Illegal pattern: " + pattern);
            }
        } else if (pattern instanceof RegExp) {
            return new Clover.validator.RegExpValidation(target, pattern, errorMessage);
        } else if (pattern instanceof Function) {
            return new Clover.validator.CustomerValidation(target, pattern, errorMessage);
        } else {
            throw new Error("Invalid Parameter.");
        }
    }
};

/**
 * @class Alert渲染器
 * @constructor
 */
Clover.validator.AlertMessageRenderer = function () {};
Clover.validator.AlertMessageRenderer.prototype.render = function (resultList) {
    var list = resultList.list;
    var info = [];
    for (var id in list) {
        var result = list[id];
        if (result.valid === false) {
            info.push(result.message);
        }
    }
    if (info.length > 0) {
        alert(info.join("\n"));
    }
};

/**
 * @class Tooltip渲染器
 * @constructor
 * @param options 选项
 */
Clover.validator.TipMessageRenderer = function (options) {
    this.showAllMessage = options.showAllMessage;
};

Clover.validator.TipMessageRenderer.prototype = {
    /**
     * @description 渲染
     * @param {Clover.validator.ResultList} resultList
     */
    render:function (resultList) {
        var list = resultList.list;
        for (var id in list) {
            var result = list[id];
            var target = $(result.element);
            var tip = target.data("validateTipEl");
            if (!tip) {
                tip = $("<div class='validator-tip'></div>").hide();
                target.data("validateTipEl", tip);
                tip.appendTo("body");
            }
            var updateTime = tip.data("updateTime") || 0;
            var now = new Date().getTime();
            var outOfDate = (now - updateTime) > 100;
            tip.data("updateTime", now);
            if (result.valid) {
                if (target.is(".validator-status-invalid") && outOfDate) {
                    target.removeClass("validator-status-invalid");
                    tip.hide();
                }
            } else {
                if (!target.is(".validator-status-invalid")) {
                    target.addClass("validator-status-invalid");
                }
                if (!(!tip.is(":hidden") && !outOfDate)) {
                    var targetOffset = target.offset();
                    var targetWidth = target.outerWidth();
                    tip.text(result.message);
                    tip.css("top", targetOffset.top + "px");
                    tip.css("left", targetOffset.left + targetWidth + "px");
                    this._show(tip)
                }
                if (!this.showAllMessage) { break; }
            }
        }
    },
    /** @private */
    _show: function (tip) {
        var timeoutId = tip.data("timeoutId");
        clearTimeout(timeoutId);
        timeoutId = setTimeout(function () {tip.fadeOut()}, 3000);
        tip.data("timeoutId", timeoutId);
        tip.fadeIn();
    }
};

/**
 * @class 自定义验证器
 * @constructor
 * @param target 验证目标
 * @param handler 自定义函数
 * @param errorMessage 错误信息
 */
Clover.validator.CustomerValidation = function (target, handler, errorMessage) {
    this.target = target;
    this.handler = handler;
    this.errorMessage = errorMessage;
};

Clover.validator.CustomerValidation.prototype.validate = function (target) {
    var elements = target || this.target;
    var messages = new Clover.validator.ResultList();
    for (var i = 0; i < elements.size(); i++) {
        var element = elements[i];
        messages.pushResult(new Clover.validator.ValidationResult(element, this.handler(element), this.errorMessage));
    }
    return messages;
};

/**
 * @class 与验证器
 * @constructor
 */
Clover.validator.AndValidation = function () {
    this.validations = arguments;
};

Clover.validator.AndValidation.prototype.validate = function (target) {
    var messages;
    for (var i = 0; i < this.validations.length; i++) {
        messages = this.validations[i].validate(target);
        if (!messages.valid) {
            return messages
        }
    }
    return messages;
};

Clover.validator.ReverseValidation = function (validation, errorMessage) {
    this.validation = validation;
    this.errorMessage = errorMessage;
};

Clover.validator.ReverseValidation.prototype.validate = function (target) {
    var reverseList = new Clover.validator.ResultList();
    var list = this.validation.validate(target).list;
    for (var id in list) {
        var message = list[id];
        if (message.element.value.length > 0) {
            message.valid = !message.valid;
            if (!message.valid) {
                message.message = this.errorMessage;
            }
        }
        reverseList.pushResult(message);
    }
    return reverseList;
};

Clover.validator.DynamicValidation = function (target, validation) {
    this.target = target;
    this.validation = validation;
    this.resultList = new Clover.validator.ResultList();
    this.resultList.pushResult(new Clover.validator.ValidationResult(target[0], true));
};

Clover.validator.DynamicValidation.prototype.validate = function (target) {
    var elements;
    if (target) { elements = target; }
    else {
        var selector = this.target.selector;
        if (selector.length > 0) {
            var context = this.target.context;
            elements = $(selector, context);
        } else {
            var isDeleted = this.target.parents("body").size() == 0;
            if (isDeleted) {
                return this.resultList;
            } else {
                elements = this.target;
            }
        }
    }
    return this.validation.validate(elements.filter(":enabled"));
};

/**
 * @class 条件验证器
 * @constructor
 * @param validation 验证器
 * @param condition 触发条件
 */
Clover.validator.ConditionValidation = function (validation, condition) {
    this.validation = validation;
    this.condition = condition;
};

Clover.validator.ConditionValidation.prototype.validate = function (target) {
    var elements = target || this.validation.target;
    var result;
    if (this.condition instanceof Function) {
        result = new Clover.validator.ResultList();
        for (var i = 0; i < elements.length; i++) {
            var element = $(elements[i]);
            if (this.condition(element)) {
                result.pushList(this.validation.validate(target));
            }
        }
        return result;
    } else {
        var conditionResult = this.condition.validate();
        if (conditionResult.valid) {
            return this.validation.validate(target);
        } else {
            result = new Clover.validator.ResultList();
            result.pushResult(new Clover.validator.ValidationResult(elements[0], true));
            return result;
        }
    }
};

/**
 * @class 必填验证器
 * @constructor
 * @param target 目标
 * @param errorMessage 错误信息
 */
Clover.validator.RequireValidation = function (target, errorMessage) {
    this.target = target;
    this.errorMessage = errorMessage;
};

Clover.validator.RequireValidation.prototype.validate = function (target) {
    var elements = target || this.target;
    var messages = new Clover.validator.ResultList();
    for (var i = 0; i < elements.size(); i++) {
        var element = elements[i];
        if (element.value.length == 0 || /^\s+$/.test(element.value)) {
            messages.pushResult(new Clover.validator.ValidationResult(element, false, this.errorMessage));
        } else {
            messages.pushResult(new Clover.validator.ValidationResult(element, true));
        }
    }
    return messages;
};

Clover.validator.RequireValidation.getInstance = function (type, operator, value, target, errorMessage) {
    return new Clover.validator.RequireValidation(target, errorMessage);
};

/**
 * @class 正则表达式验证器
 * @constructor
 * @param {jQuery} target
 * @param {RegExp} regexp
 * @param {String} errorMessage
 */
Clover.validator.RegExpValidation = function (target, regexp, errorMessage) {
    this.target= target;
    this.regexp = regexp;
    this.errorMessage = errorMessage;
};

Clover.validator.RegExpValidation.prototype.validate = function (target) {
    var elements = target || this.target;
    var messages = new Clover.validator.ResultList();
    for (var i = 0; i < elements.size(); i++) {
        var element = elements[i];
        if (element.value.length == 0 || this.regexp.test(element.value)) {
            messages.pushResult(new Clover.validator.ValidationResult(element, true));
        } else {
            messages.pushResult(new Clover.validator.ValidationResult(element, false, this.errorMessage));
        }
    }
    return messages;
};

Clover.validator.RegExpValidation.getInstance = function (type, operator, value, target, errorMessage) {
    var regExp;
    if (value && type == "integer") {
        regExp = new RegExp("^\\d{1," + value + "}$");
        return new Clover.validator.RegExpValidation(target, regExp, Clover.validator.generateMessage(errorMessage, [value]));
    } else if (value && type == "float") {
        var counts = value.split(".");
        // TODO 参数消息机制会有问题
        regExp = new RegExp("^\\d{1," + counts[0] + "}(\\.\\d{1," + (counts[1] || "") + "})?$");
        return new Clover.validator.RegExpValidation(target, regExp, Clover.validator.generateMessage(errorMessage, counts));
    } else {
        return new Clover.validator.RegExpValidation(target, Clover.validator.RegExpValidation.regexps[type], errorMessage);
    }
};

/** @constant */
Clover.validator.RegExpValidation.regexps = {
    "integer": /^\d+$/,
    "float": /^\d+(\.\d+)?$/,
    "email": /^[A-Z0-9._%+-]+@(?:[A-Z0-9-]+\.)+[A-Z]{2,4}$/i,
    "date": /^\d{4}-\d{1,2}-\d{1,2}$/,
    "datetime":  /^\d{4}-\d{1,2}-\d{1,2} \d{2}:\d{2}(:\d{2})?$/,
    "telephone": /^((0[1-9]{3})?(0[12][0-9])?[-])?\d{6,8}([-]\d+)?$/,
    "mobilePhone": /^0?1[34578][0-9]{9}$/,
    "idCard": /^(\d{17}(X|\d))|\d{15}$/,
    "ip": /^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/,
    "alpha": /^\w+$/,
    "zmkt": /^[a-zA-Z][a-zA-Z0-9_]/,
    "Chinese": /^[\u4e00-\u9fa5]+$/,
    "sqlSensitive": /^\s*[^%&\*'=\?\+]+\s*$/,
    "url": /^((http|https|ftp):\/\/)?(([A-Z0-9][A-Z0-9_-]*)(\.[A-Z0-9][A-Z0-9_-]*)+)(:(\d+))?\/?/i,
    "phone": /^((1[34578][0-9]{9})|((0\d{2}-\d{8})|(0\d{3}-\d{7,8})|(0\d{10,11}))|(((400)-(\d{3})-(\d{4}))|((400)-(\d{4})-(\d{3}))|((400)-(\d{5})-(\d{2}))|(400\d{7}))|(((800)-(\d{3})-(\d{4}))|((800)-(\d{4})-(\d{3}))|((800)-(\d{5})-(\d{2}))|(800\d{7})))$/
};

/**
 * @class 长度验证类,适用类型: text, textarea
 * @constructor
 * @param {jQuery} target 需要匹配的元素
 * @param {String} operator 操作符
 * @param {Number|String} length 要验证的长度值
 * @param {String} errorMessage 验证不通过时显示的信息
 */
Clover.validator.LengthValidation = function(target, operator, length, errorMessage) {
    this.target = target;
    this.comparator = Clover.validator.comparators[operator];
    this.length = length;
    this.errorMessage = Clover.validator.generateMessage(errorMessage, [length]);
};

Clover.validator.LengthValidation.getInstance = function (type, operator, value, target, errorMessage) {
    return new Clover.validator.LengthValidation(target, operator, value, errorMessage);
};

Clover.validator.LengthValidation.prototype.validate = function (target) {
    var elements = target || this.target;
    var resultList = new Clover.validator.ResultList();
    for (var i = 0; i < elements.size(); i++) {
        var element = elements[i];
        var valueLength = element.value.length;
        if (valueLength == 0 || this.comparator(valueLength, this.length)) {
            resultList.pushResult(new Clover.validator.ValidationResult(element, true));
        } else {
            resultList.pushResult(new Clover.validator.ValidationResult(element, false, this.errorMessage));
        }
    }
    return resultList;
};

/**
 * @class 长度区间验证
 * @constructor
 * @param target
 * @param {String} leftValue
 * @param {String} rightValue
 * @param {String} [errorMessage]
 */
Clover.validator.LengthRangeValidation = function (target, leftValue, rightValue, errorMessage) {
    this.target = target;

    if (leftValue.length > 0 && rightValue === undefined) {
        this.leftValue = leftValue - 0;
        this.compare = this.eq;
        this.errorMessage = Clover.validator.generateMessage(errorMessage || Clover.validator.errorMessageMap["length="], [leftValue]);
    } else if (leftValue.length > 0 && rightValue.length > 0) {
        this.leftValue = leftValue - 0;
        this.rightValue = rightValue - 0;
        this.compare = this.range;
        this.errorMessage = Clover.validator.generateMessage(errorMessage || Clover.validator.errorMessageMap["lengthRange"], [leftValue, rightValue]);
    } else if (leftValue.length > 0 && rightValue.length == 0) {
        this.leftValue = leftValue - 0;
        this.compare = this.ge;
        this.errorMessage = Clover.validator.generateMessage(errorMessage || Clover.validator.errorMessageMap["length>="], [leftValue]);
    } else if (leftValue.length == 0 && rightValue.length > 0) {
        this.rightValue = rightValue - 0;
        this.compare = this.le;
        this.errorMessage = Clover.validator.generateMessage(errorMessage || Clover.validator.errorMessageMap["length<="], [rightValue]);
    } else {
        throw Error("Illegal arguments error");
    }
};

Clover.validator.LengthRangeValidation.prototype = {
    validate: function (target) {
        var elements = target || this.target;
        var resultList = new Clover.validator.ResultList();
        for (var i = 0; i < elements.size(); i++) {
            var element = elements[i];
            var valueLength = element.value.length;
            if (this.compare(valueLength)) {
                resultList.pushResult(new Clover.validator.ValidationResult(element, true));
            } else {
                resultList.pushResult(new Clover.validator.ValidationResult(element, false, this.errorMessage));
            }
        }
        return resultList;
    },
    eq: function (length) {
        return this.leftValue == length;
    },
    le: function (length) {
        return length <= this.rightValue;
    },
    ge: function (length) {
        return length >= this.leftValue;
    },
    range: function (length) {
        return length >= this.leftValue && length <= this.rightValue;
    }
};

/**
 * @class 选择验证器
 * @description 主要用于checkbox和radio
 * @constructor
 * @param target
 * @param operator
 * @param matchNumber
 * @param errorMessage
 */
Clover.validator.SelectValidation = function (target, operator, matchNumber, errorMessage) {
    this.target = target;
    this.comparator = Clover.validator.comparators[operator];
    this.matchNumber = matchNumber;
    this.errorMessage = Clover.validator.generateMessage(errorMessage, [matchNumber]);
};

Clover.validator.SelectValidation.getInstance = function (type, operator, value, target, errorMessage) {
    operator = operator || ">";
    value = value || 0;
    return new Clover.validator.SelectValidation(target, operator, value, errorMessage);
};

Clover.validator.SelectValidation.prototype.validate = function (target) {
    var elements = target || this.target;
    var resultList = new Clover.validator.ResultList();
    if (elements.is(":checkbox")) {
        var selected = this.target.filter(":checked").size();
        if (this.comparator(selected, this.matchNumber)) {
            resultList.pushResult(new Clover.validator.ValidationResult(elements[0], true));
        } else {
            resultList.pushResult(new Clover.validator.ValidationResult(elements[0], false, this.errorMessage));
        }
    } else if (elements.is(":radio")) {
        if (this.target.filter(":checked").size() > 0) {
            resultList.pushResult(new Clover.validator.ValidationResult(elements[0], true));
        } else {
            resultList.pushResult(new Clover.validator.ValidationResult(elements[0], false, this.errorMessage));
        }
    }

    return resultList;
};

/**
 * @class 日期顺序验证器
 * @constructor
 * @param {jQuery} target
 * @param {String} operator
 * @param {String|Date} compareObject
 * @param {RegExp} format
 * @param {String} errorMessage
 */
Clover.validator.DateOrderValidation = function (target, operator, compareObject, format, errorMessage) {
    this.target = target;
    this.comparator = Clover.validator.comparators[operator];
    this.compareObject = compareObject;
    this.format = format;
    this.errorMessage = errorMessage;
};

Clover.validator.DateOrderValidation.prototype.validate = function (target) {
    var element = target || this.target;
    var resultList = new Clover.validator.ResultList();
    if (this.format.test(element.val())) {
        var compareDate;
        if (this.compareObject instanceof Date) {
            compareDate = this.compareObject;
        } else {
            var dateStr = this.compareObject.val();
            if (dateStr.length > 0 && this.format.test(dateStr)) {
                compareDate = new Date(dateStr.replace(/-/g, "/"));
            } else {
                resultList.pushResult(new Clover.validator.ValidationResult(this.target[0], true));
                return resultList;
            }
        }

        var targetDate = new Date(element.val().replace(/-/g, "/"));
        if (this.comparator(targetDate, compareDate)) {
            resultList.pushResult(new Clover.validator.ValidationResult(this.target[0], true));
        } else {
            var compareDateStr = compareDate.getFullYear() + "-" + (compareDate.getMonth() + 1) + "-" + compareDate.getDate();
            resultList.pushResult(new Clover.validator.ValidationResult(this.target[0], false, Clover.validator.generateMessage(this.errorMessage, [compareDateStr])));
        }
        return resultList;
    } else {
        resultList.pushResult(new Clover.validator.ValidationResult(this.target[0], true));
        return resultList;
    }
};

Clover.validator.DateOrderValidation.getInstance = function (type, operator, value, target, errorMessage) {
    var format = Clover.validator.RegExpValidation.regexps["date"];
    if (format.test(value)) {
        var compareDate = new Date(value.replace(/-/g, "/"));
        return new Clover.validator.DateOrderValidation(target, operator, compareDate, format, errorMessage);
    } else {
        return new Clover.validator.DateOrderValidation(target, operator, $(value), format, errorMessage);
    }
};

/**
 * @class 值验证器
 * @constructor
 * @param target
 * @param operator
 * @param value
 * @param errorMessage
 */
Clover.validator.ValueValidation = function (target, operator, value, errorMessage) {
    this.target = target;
    this.comparator = Clover.validator.comparators[operator];
    this.value = value;
    this.errorMessage = Clover.validator.generateMessage(errorMessage, [value]);
};

Clover.validator.ValueValidation.prototype.validate = function (target) {
    var resultList = new Clover.validator.ResultList();
    var elements = target || this.target;
    var i;
    if (elements.is(":checkbox") || elements.is(":radio")) {
        var checked = this.target.filter(":checked");
        var targetValues = checked.map(function () {
            return this.value;
        }).get();
        for (i = 0; i < targetValues.length; i++) {
            if (targetValues[i] == this.value) {
                resultList.pushResult(new Clover.validator.ValidationResult(elements[0], true));
            }
        }
        if (resultList.size == 0) {
            resultList.pushResult(new Clover.validator.ValidationResult(elements[0], false, this.errorMessage));
        }
        return resultList;
    } else {
        for (i = 0; i < elements.size(); i++) {
            var element = elements[i];
            if (element.value.length == 0 || this.comparator(element.value, this.value)) {
                resultList.pushResult(new Clover.validator.ValidationResult(elements[i], true));
            } else {
                resultList.pushResult(new Clover.validator.ValidationResult(elements[i], false, this.errorMessage));
            }
        }
        return resultList;
    }
};

Clover.validator.ValueValidation.getInstance = function (type, operator, value, target, errorMessage) {
    return new Clover.validator.ValueValidation(target, operator, value, errorMessage);
};

Clover.validator.AsyncValidation = function (target, errorMessage, messageRenderer) {
    this.resultList = new Clover.validator.ResultList();
    this.resultList.pushResult(new Clover.validator.ValidationResult(target[0], true));
    this.target = target;
    this.errorMessage = errorMessage;
    this.messageRenderer = messageRenderer;
};

Clover.validator.AsyncValidation.prototype = {
    validate: function() {
        return this.resultList;
    },
    updateResult: function (isValid) {
        var resultList = new Clover.validator.ResultList();
        resultList.pushResult(new Clover.validator.ValidationResult(this.target[0], isValid, this.errorMessage));
        this.messageRenderer.render(resultList);
        this.resultList = resultList;
    }
};

/**
 * @constant
 */
Clover.validator.Validator.validationMap = {
    "required": Clover.validator.RequireValidation,
    "integer": Clover.validator.RegExpValidation,
    "float": Clover.validator.RegExpValidation,
    "date": Clover.validator.RegExpValidation,
    "datetime": Clover.validator.RegExpValidation,
    "email": Clover.validator.RegExpValidation,
    "telephone": Clover.validator.RegExpValidation,
    "mobilePhone": Clover.validator.RegExpValidation,
    "idCard": Clover.validator.RegExpValidation,
    "ip": Clover.validator.RegExpValidation,
    "url": Clover.validator.RegExpValidation,
    "alpha": Clover.validator.RegExpValidation,
    "zmkt": Clover.validator.RegExpValidation,
    "Chinese": Clover.validator.RegExpValidation,
    "sqlSensitive": Clover.validator.RegExpValidation,
    "dateOrder": Clover.validator.DateOrderValidation,
    "length": Clover.validator.LengthValidation,
    "include": Clover.validator.ValueValidation,
    "value": Clover.validator.ValueValidation,
    "select": Clover.validator.SelectValidation,
    "phone": Clover.validator.RegExpValidation
};

/** @constant 中文默认验证信息 */
Clover.validator.errorMessageMap = {
    "required": "必填项",
    "integer": "只允许输入数字",
    "integer=": "只允许输入{0}位以下数字",
    "float": "只允许输入小数",
    "float=": "请输入合法的小数，整数位最多{0}位,小数位最多{1}位",
    "date": "请输入一个合法的日期",
    "datetime": "请输入一个合法的日期时间",
    "email": "请输入一个合法的电子邮件地址",
    "telephone": "请输入一个合法的电话号码",
    "mobilePhone": "请输入一个合法的手机号码",
    "idCard": "请输入一个合法的身份证号",
    "ip": "请输入一个合法的IP地址",
    "url": "请输入一个合法的url地址",
    "alpha": "只允许输入字母,数字与下划线",
    "zmkt":"以字母开头、数字组合",
    "Chinese": "只允许输入中文",
    "sqlSensitive": "包含敏感字符",
    "length": "长度不符合要求",
    "lengthRange": "长度应在{0}-{1}范围内",
    "length=": "长度应为{0}",
    "length>=": "长度应大于等于{0}",
    "length<=": "长度应小于等于{0}",
    "length>": "长度应大于{0}",
    "length<": "长度应小于{0}",
    "include=": "必须包含指定值",
    "select": "必选项",
    "select=": "必须选则{0}项",
    "select>": "必须选择大于{0}项",
    "select<": "必须选择小于{0}项",
    "select>=": "必须选择大于等于{0}项",
    "select<=": "必须选择小于等于{0}项",
    "value=": "值应等于{0}",
    "value>": "值应大于{0}",
    "value<": "值应小于{0}",
    "value>=": "值应大于等于{0}",
    "value<=": "值应小于等于{0}",
    "dateOrder=": "日期必须等于{0}",
    "dateOrder>": "日期必须大于{0}",
    "dateOrder<": "日期必须小于{0}",
    "dateOrder>=": "日期必须大于等于{0}",
    "dateOrder<=": "日期必须大于等于{0}",
    "phone": "请输入一个合法的手机号码、固定电话、400或800电话"
};

Clover.validator.generateMessage = function (messageTemplate, params) {
    return messageTemplate.replace(/\{(\d+)\}/g, function (s0, s1) {
        return params[s1 - 0];
    });
};

Clover.validator.autoScroll = function (selector) {
    var targetTop = $(selector).offset().top;
    var documentElementScroll = document.documentElement ? document.documentElement.scrollTop : 0;
    var scrollTop = documentElementScroll || document.body.scrollTop;
    if (targetTop < scrollTop || targetTop > (scrollTop + document.body.clientHeight)) {
        $(window).scrollTop(targetTop - 10);
    }
};

Clover.validator.comparators = {
    ">":  function (a, b) { return (a - 0) >  (b - 0) },
    ">=": function (a, b) { return (a - 0) >= (b - 0) },
    "<":  function (a, b) { return (a - 0) <  (b - 0) },
    "<=": function (a, b) { return (a - 0) <= (b - 0) },
    "=":  function (a, b) { return (a - 0) == (b - 0) }
};

/**
 * @default 表单验证者默认选项
 */
Clover.validator.Validator.defaultOptions = {
    autoBind: true,
    validateOnBlur: true,
    showAllMessage: true,
    rendererClass: Clover.validator.TipMessageRenderer
};