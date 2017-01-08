function check_required(ids) {
	if (ids == null || ids.length == 0) {
		return false;
	}
	for (var i = 0; i < ids.length; i++) {
		if ($("#" + ids[i]).val() == null || trim($("#" + ids[i]).val()) == '') {
			return true;
		}
	}
}
function str_isblank(s) {
	if (s == null || s == '' || s == 'undefined') {
		return true;
	} else
		return false;
}
function getTrolleyNums() {
	var ids = "";
	var s = document.getElementsByName("trolleynum");
	for (var i = 0; i < s.length; i++) {
		ids += s[i].value + ",";
	}
	ids = ids.substr(0, ids.length - 1);
	return ids;
}

function limitlen(s, len) {
	if (s.length <= len)
		return s;
	else {
		return s.substr(0, len - 3) + "...";
	}
}

function clearNoNum(obj) {
	// 先把非数字的都替换掉，除了数字和.
	obj.value = obj.value.replace(/[^\d.]/g, "");
	// 必须保证第一个为数字而不是.
	obj.value = obj.value.replace(/^\./g, "");
	// 保证只有出现一个.而没有多个.
	obj.value = obj.value.replace(/\.{2,}/g, ".");
	// 保证.只出现一次，而不能出现两次以上
	obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
	
	var x = obj.value;
	var pos_decimal = x.indexOf('.');
	if (pos_decimal < 0) {
		pos_decimal = x.length;
	} else {
		if (x.length >= pos_decimal + 3) {
			var len = pos_decimal + 3;
			x = x.substr(0, len);
		}
	}
	
	// var f_x = Math.round(x*100)/100;
	// var s_x = f_x.toString();
	// var pos_decimal = s_x.indexOf('.');
	// if (pos_decimal < 0)
	// {
	// pos_decimal = s_x.length;
	// s_x += '.';
	// }
	// alert(s_x);
	// while (s_x.length <= pos_decimal + 2)
	// {
	// s_x += '0';
	// }
	// alert(s_x);
	obj.value = x;
	// return s_x;
}
