"use strict";

class CallRegisterLoginAjax{
	
	constructor() {
	    this.rootURL = "http://iptv-comm.jelastic.regruhosting.ru/rest/UserService/";
	    this.method;
	    this.action;
	    this.formData;
	};
	
	register() {
		$.ajax({
			type: this.method,
			url: this.rootURL + this.action,
			data: this.formData,
			contentType: "application/json",
			success: function(){
				main.regLogButton($('#sign_in'));
				$("#register").hide();
				
			}
		});
	};
	
	login(){
		$.ajax({
			type: this.method,
			url: this.rootURL + this.action,
			contentType: "application/json",
			dataType: "json",
			success: function(data){
				sessionStorage.data = JSON.stringify(data);
				sessionStorage.uname = data.username;
				sessionStorage.uId = data.userId;
				sessionStorage.uToken = data.token;
				window.location = "http://iptv-comm.jelastic.regruhosting.ru/Pages/main.html";
			}
		});
	};
};
