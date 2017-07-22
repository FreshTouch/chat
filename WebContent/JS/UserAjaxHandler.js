"use strict";

class UserAjaxHandler extends CallAjax{
	
	update(form){
		let unindexed_array = form.serializeArray();
		unindexed_array.push({name:"userId",value:sessionStorage.uId});
	    let indexed_array = {};
	    $.map(unindexed_array, function(n){
	        indexed_array[n['name']] = n['value'];
	    });
	    this.method = "PUT";
		this.action = "update/" + sessionStorage.uToken;
		this.formData = JSON.stringify(indexed_array);
		super.update();
	};
	
	buttonsShow($this, fname){
		const indexURL = "http://iptv-comm.jelastic.regruhosting.ru";	
		const userURL = "http://iptv-comm.jelastic.regruhosting.ru/rest/UserService/";
		const messURL = "http://iptv-comm.jelastic.regruhosting.ru/rest/MessageService/";
		switch($this.title){
			case "logout":
				this.action = userURL + "logout/" + sessionStorage.uname;
				this.method = "PUT";
				this.success = ()=>{
					sessionStorage.clear();
					window.location = indexURL;
				};
				break;
			case "delete": 
				this.action = userURL + "delete/" + sessionStorage.uname;
				this.method = "DELETE";
				this.success = ()=>{
					sessionStorage.clear();
					window.location = indexURL;
				};
				break;
			case "friends":
				this.action = userURL + "frlist/" + sessionStorage.uId + "," + sessionStorage.uToken;
				this.method = "GET";
				this.success = (data)=>{
					Main.friends(data);
				};
				break;
			case "channels":
				this.action = userURL + "list/" + sessionStorage.uname + "," + sessionStorage.uToken;
				this.method = "GET";
				this.success = (data)=>{
					Main.channels(data);
				};
				break;
			case "find_friend":
				this.action = userURL + "searchuser/" + $(".fr input").val();
				this.method = "GET";
				this.success = (data)=>{
					Main.findFriend(data);
				};
				break;
			case "send_friend":
				this.action = userURL + "sendfr/"  + sessionStorage.uId +  "," + $this.id + "," + sessionStorage.uToken;
				this.method = "POST";
				this.success = (data)=>{
					alert("GOOD");
				};
				break;
			case "decline_friend":
				this.action = userURL + "declinefriend/" + sessionStorage.uId + "," + $this.id + "," + "false" + "," + sessionStorage.uToken;
				this.method = "PUT";
				this.success = ()=>{
					alert("GOOD");
					$(".newFriend").hide();
				};
				break;
			case "accept_friend":
				this.action = userURL + "accfr/" + sessionStorage.uId + "," + $this.id + "," + sessionStorage.uToken;
				this.method = "PUT";
				this.success = ()=>{
					alert("GOOD");
					$(".newFriend").hide();
				}
				break;
			case "delete_friend":
				this.action = userURL + "deleteFrd/" +  sessionStorage.uId + "," + $this.id + "," + sessionStorage.uToken;
				this.method = "DELETE";
				this.success = ()=>{
					alert("GOOD");
				}
				break;
			case "sendMess_friend":
				this.action = messURL + "message/" + sessionStorage.uId + "," + $this.id + "," + "alos" + "," + $("#sendie").val() + "," + sessionStorage.uToken;
				this.method = "PUT";
				this.success = ()=>{
					console.log($this.id)
					Main.sendMessage($this.id);
				}
				break;
			case "show_message":
				this.action = messURL + "showmessage/" + fname + "," + sessionStorage.uToken;
				this.method = "PUT";
				this.success = (data)=>{
					Main.showMessage($this.id, data);
				}
				break;
			case "goToMess_friend":
				if(localStorage.getItem($this.id)){
					$("#chat-area div").remove();
					$("#chat-area").append(localStorage.getItem($this.id));
				}
				$(".sendie").attr("id", $this.id);
				$(".clear_form").attr("id", $this.id);
				$(".blocFriend").hide();
				$("#mail").show();
				$(".blocFriend div").remove();
				break;
			case "clear_form":
					localStorage.clear();
					$("#chat-area div").remove();
				break;
		};
		super.buttonsShow();
	};
};

let user = new UserAjaxHandler();
