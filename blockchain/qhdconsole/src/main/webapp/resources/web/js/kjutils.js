function toDecimal(x,y) {
    var f = parseFloat(x);
    if (isNaN(f)) {
        return;
    }
    if(y==0){
    	f = Math.round(x);
    }else if(y==1){
    	f = Math.round(x*10)/10;
    }else if (y==2){
    	f = Math.round(x*100)/100;
    }else if (y==3){
    	f = Math.round(x*100)/100;
    }else
    	f = Math.round(x*10000)/10000;
    return f;
}

function CalcDays(start,end,days){     
	var aDate, oDate1, oDate2, iDays   
	aDate = start.split("-")      
	oDate1 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0])   //转换为12-13-2008格式      
	aDate = end.split("-")      
	oDate2 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0])      
	iDays = parseInt(Math.abs(oDate1 - oDate2) / 1000 / 60 / 60 /24)+1;   //把相差的毫秒数转换为天数      
	//alert(iDays);    
	if(iDays >= days){
		alert('时间范围必须小于'+days);
		return false;
	}else{
		return true;
	}
}

function formatDate(x,l) {
	
	if(l==8){
		if(x.length >=8)
			return x.substr(4, 2)+'/'+x.substr(6, 2);
		else
			return x;
	}else if(l==10){
		if(x.length >=10)
			return x.substr(4, 2)+'/'+x.substr(6, 2)+' '+x.substr(8, 2)+":00";
		else
			return x;
	}else{
		return x;
	}		
}

//0-error,1-success
function showmsg(type,msg){
	if(type == 0){
		humane.error(msg);
	}
	if(type == 1){
		humane.timeout = 1500;
		humane.success(msg);
		humane.timeout = 2500;
	}
}

function ajax_post(the_url, the_param, succ_callback) {
	$.ajax({
		type : 'POST',
		url : the_url,
		data : the_param,
		cache : false,
		success : succ_callback
	});
}

function ajax_get(the_url, the_param, succ_callback) {
	$.ajax({
		type : 'GET',
		url : the_url,
		data : the_param,
		cache : false,
		success : succ_callback
	});
}

function ajax_post_load(the_url, the_param, div, btn, succ_callback) {
	$.ajax({
		url : the_url,
		data : the_param,
		beforeSend:function(){
			$('#'+div).html('<img src="'+CONTEXT_PATH+'/images/load.gif">');
			$('#'+btn).attr('disabled',true);
		},
		complete :function(){
			$('#'+div).html('');
			$('#'+btn).attr('disabled',false);
		},
		success : succ_callback

	});
}

function ajax_post_load2(the_url, the_param, succ_callback) {
	$.ajax({
		type : 'POST',
		url : the_url,
		data : the_param,
		beforeSend:function(){
			layer_load(0,false);
		},	
		complete :function(){
			layer_close();
		},
		success : succ_callback
	});
}

function stringToDate(dateStr){
	var d = new Date();
	var date = dateStr.substring(0,10).split('-');
	
	d.setYear(date[0]);
	d.setMonth(date[1]-1);
	d.setDate(date[2]);
	
	var time = dateStr.substring(11,19).split(':');
	if(time != ""){
		d.setHours(time[0]);
		d.setMinutes(time[1]);
		d.setSeconds(time[2]);
	}
	return d;
}

function checkPartTime(){
	var start = $('#start').val();
	var end = $('#end').val();
	if(start == "" || end == ""){
		showmsg(0,"请选择时间");
		return;
	}
	if(stringToDate(start) > stringToDate(end)){
		showmsg(0,"开始时间不能早于结束时间");
		return;
	}
}

function M(id){
	return document.getElementById(id);
}

function checkSelect(check, checks){
	 
	 var selected=check.checked;
	 if(!checks.length){
		 checks.checked=selected;
		 return ;
	}
	 for(var i=0; i<checks.length; i++){
		 checks[i].checked=selected;
	}
}
function selectInvert(check,checks){
	 var selected=check.checked;
	 if(!checks.length){
		 checks.checked=selected;
		 return ;
	}
	 for(var i=0; i<checks.length; i++){
		 checks[i].checked=!checks[i].checked;
	}
}
function getSelectIds(divId){
    var s = document.getElementsByName(divId);
    var id="";
    for(var i=0;i < s.length; i++){
 	   if(s[i].checked == true){
          id += s[i].value + ",";
        }
    }
    if(id != ""){
    	id = id.substr(0,id.length-1);
    }
    return id;
}

function trim(str){
	return str.replace(/(^\s*)|(\s*$)/g, "");
}
function showTime(type,day){
	var d = new Date();
	d.setDate(d.getDate()-1);
	$('#end').val(d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate());
	if(type==0){
		d.setDate(d.getDate()-day);
		$('#start').val(d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate());
	}
	if(type==1){
		d.setDate(d.getDate());
		d.setMonth(d.getMonth()-day);
		$('#start').val(d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate());
	}
}
function showTab(id,tab,total,current){
	for(i=1;i<=total;i++){
		if(i==current){
			$('#'+id+i).css('display',''); 
			$('#'+tab+i).attr('class','naviOnLong'); 
		}else{
			$('#'+id+i).css('display','none'); 
			$('#'+tab+i).attr('class','naviLong'); 
		}
	}
}