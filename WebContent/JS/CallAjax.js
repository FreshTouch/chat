"use strict";

class CallAjax{
	
	constructor() {
	    this.rootURL = "http://iptv-comm.jelastic.regruhosting.ru/rest/UserService/";
	    this.method;
	    this.action;
	    this.formData;
	    this.success;
	};
	
	update(){
		$.ajax({
			type: this.method,
			url: this.rootURL + this.action,
			data: this.formData,
			contentType: "application/json",
			dataType: "json",
			success: ()=>{
				$("#update").hide();
				$(".opacity_bg").hide();
			}
		});
	};
	
	buttonsShow() {
		$.ajax({
			type: this.method,
			dataType: "json",
        	contentType: "application/json",
			url: this.action,
			success: this.success
		});
	};
	
	static newMessage(){
		$.ajax({
			type: "GET",
			url: "http://iptv-comm.jelastic.regruhosting.ru/rest/MessageService/messagelist/" + sessionStorage.uId + "," + sessionStorage.uToken,
			dataType: "json",
			contentType: "application/json",
			success: (data)=>{
				Main.createMess(data);
			}
		});
	};
	
	static newFriend(){
		$.ajax({
			type: "GET",
			url: "http://iptv-comm.jelastic.regruhosting.ru/rest/UserService/frreqlist/"  + sessionStorage.uId,
			dataType: "json",
			contentType: "application/json",
			success: (data)=>{
				Main.newFriend(data);
			}
		});
	};
};
CallAjax.newFriend();
CallAjax.newMessage()