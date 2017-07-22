"use strict";

class CallRegisterLoginAjax{
	
	constructor() {
	    this.rootURL = "http://localhost:8080/IptvCommRest/rest/UserService/";
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
				window.location = "http://localhost:8080/IptvCommRest/Pages/main.html";
			}
		});
	};
};
