/* author: yangjunhua
 * time  : 2017/11/24
**/
(function($){
    var jsonParse = window.JSON && JSON.parse ? JSON.parse : eval;

    var addEvent;

    if (document.body.addEventListener) {
        addEvent = function(elem, type, eventHandler) {
            elem.addEventListener(type, eventHandler);
        };
    } else if (document.body.attachEvent) {
        addEvent = function(elem, type, eventHandler) {
            elem.attachEvent('on' + type, eventHandler);
        };
    } else {
        addEvent = function(elem, type, eventHandler) {
            elem['on' + type] = eventHandler;
        };
    }

    function CustomSelect(options) {
        this.options = $.extend({}, options || {});
        this.init();
    }

    // 原型
    CustomSelect.prototype = {
        constructor: CustomSelect,
        keywords: '',
        init: function() {
            if (!this.options || !this.options.container) return;
            this.initContainer();
            this.listenFocus();
            this.listenBlur();
            this.listenSearch();
            this.listenTrigger();
            this.listenSelect();
            this.listenMouseenter();
            this.listenBodyClick();
            this.listenPaste();
        },
        initContainer: function() {
            this.$container = $(this.options.container).addClass('custom-select-container');
            var tpl = '<div class="custom-select-input-wrap">' +
                '<input type="text" class="custom-select-input" value="' + (this.$container.data('default-value')) +
                '" placeholder="' + (this.$container.data('placeholder')) + '">' +
                '<div class="list-toggle-trigger"><i></i></div>' +
                '</div>' +
                '<div class="custom-select-list"></div>';

            this.dataList = jsonParse(this.$container.find('textarea')[0].value);
            this.$container.html(tpl);
            this.$input = this.$container.find('.custom-select-input');
            this.$list = this.$container.find('.custom-select-list');
            this.$filterList = $();
            this.$trigger = this.$container.find('.list-toggle-trigger');

            this.defaltValue = this.$container.data('default-value');
            this.$container.data({
                'customSelect': this,
                'value': ''
            });
        },

        _isRended: false,
        _isResetSize: false,
        _highlightIndex: -1,
        _seletedIndex: -1,

        highlight: function(idx) {
            idx = idx !== undefined && idx > -1 ? idx : this._highlightIndex;
            idx >= 0 && this.$filterList.children().removeClass('hover').eq(idx).addClass('hover');
        },
        renderList: function(list) {
            var listTpl = '',
                len = list.length;
            if (len > 0) {
                for (var i = 0; i < len; i++) {
                    listTpl += '<span data-value="' + list[i].id + '">' + list[i].name + '</span>';
                }
                this.$list.html(listTpl).slideDown('fast');
            } else {
                this.$list.html(listTpl).hide();
            }
            this.filterDataList = list;
            this._isRended = true;
            if (!this._isResetSize) {
                this._isResetSize = true;
                /*this.$list.css({
                    width: this.$list[0].scrollWidth + 25 + 'px'
                });*/
            }
        },
        search: function() {
            if (this.keywords === '' || this.keywords === this.defaltValue) {
                this.$input.val('');
                this.renderList(this.dataList);
                this.$filterList = this.$list;
                return;
            }
            var searchList = [];
            var len = this.dataList.length;
            var reg = new RegExp(this.keywords, 'i');

            for (var i = 0; i < len; i++) {
                var dataItem = this.dataList[i];
                dataItem.name.match(reg) && (searchList.push(dataItem));
                this.$filterList = this.$filterList.add(this.$list.eq(i));
            }
            this.renderList(searchList);
        },
        listenFocus: function() {
            var self = this;
            this.$input.on('focus', function() {
                if (self._isRended && self.filterDataList.length > 0) {
                    self.highlight(self._seletedIndex);
                    self.$list.slideDown('fast');
                    self.keywords === '' && self.$input.val('');
                    return;
                }
                self.search();
            });
        },
        listenBlur: function() {
            var self = this;
            this.$input.on('blur', function() {
                if (self.filterDataList.length === 0) {
                    self.$input.val(self.defaltValue);
                    self.keywords = '';
                } else if ($.trim(self.$input.val()) === '') {
                    self.$input.val(self.defaltValue);
                }
            });
        },
        keyboardSelect: function(code) {
            if (code === 38) {
                this._highlightIndex--;
                this._highlightIndex = this._highlightIndex < 0 ? 0 : this._highlightIndex;
                this.highlight();
            } else if (code === 40) {
                this._highlightIndex++;
                this._highlightIndex = this._highlightIndex > (this.filterDataList.length - 1) ? (this.filterDataList.length - 1) : this._highlightIndex;
                this.highlight();
            }
            this._seletedIndex = this._highlightIndex;
        },
        listenSearch: function() {
            var self = this;
            this.$input.on('keyup', function(e) {
                var code = e.keyCode || e.which;
                self.keywords = $.trim(self.$input.val());

                if (code === 38 || code === 40) { // up down select
                    self.keyboardSelect(code);
                } else if (code === 13 && self._highlightIndex >= 0) { // enter
                    var selectObj = self.filterDataList[self._highlightIndex];
                    self.$input.val(selectObj.name);
                    self.$container.data('value', selectObj.id);

                    self.options.change && self.options.change(self.$container.data('value'));
                    self.$list.hide();
                    self.$input.trigger('blur');
                } else {
                    self.search();
                }
            });
        },
        listenTrigger: function() {
            var self = this;
            this.$trigger.on('click', function() {
                var $this = $(this);
                if (self._isRended && self.filterDataList.length > 0) {
                    self.$list.slideToggle('fast');
                } else {
                    self.$input.trigger('focus');
                }
            });
        },
        listenSelect: function() {
            var self = this;
            this.$container.on('click', '[data-value]', function() {
                var $this = $(this),
                    value = $this.data('value');

                self.$input.val($this.text());
                self.keywords = $this.text();

                self.$list.hide();
                self.$container.data('value', value);
                self.options.change && self.options.change(value);
                self._seletedIndex = self.$filterList.children().index(this);
            });
        },
        listenMouseenter: function() {
            var self = this;
            this.$container
                .on('mouseenter', '[data-value]', function() {
                    var $childs = self.$filterList.children();
                    var i = self._highlightIndex = $childs.index(this);
                    $childs.removeClass('hover').eq(i).addClass('hover');
                });
        },
        listenBodyClick: function() {
            var self = this;
            $('body').on('click', function(e) {
                if ($(e.target).parents('.custom-select-container')[0] !== self.$container[0]) {
                    self.$list.hide();
                }
            });
        },
        listenPaste: function() {
            var self = this;
            addEvent(this.$input[0], 'paste', function(e) {
                var clipboardData = e.clipboardData || window.clipboardData;
                var clipValue = clipboardData.getData('text');

                self.keywords = self.getValueAsPaste(clipValue);
                self.search();
            });
        },
        getValueAsPaste: function(pasteText) {
            var existingVal = this.$input.val();
            var length = existingVal.length;
            var start = this.getSelectionStart(this.$input[0]);
            var value = '';

            if (start === undefined) return existingVal;

            if (start > 0) {
                if (start < length) {
                    value = existingVal.substring(0, start) + pasteText + existingVal.substring(start, length);
                } else if (start === length) {
                    value = existingVal.substring(0, start) + pasteText;
                }
            } else {
                value = pasteText + existingVal.substring(0, length);
            }

            return value;
        },
        getSelectionStart: function(el) {
            if (el.selectionStart) {
                return el.selectionStart;
            } else if (document.selection) {
                el.focus();

                var r = document.selection.createRange();
                if (!r) return 0;

                var re = el.createTextRange(),
                    rc = re.duplicate();

                re.moveToBookmark(r.getBookmark());
                rc.setEndPoint('EndToStart', re);

                return rc.text.length;
            }
            return 0;
        }
    };

    window.CustomSelect = CustomSelect;
    
}(jQuery));