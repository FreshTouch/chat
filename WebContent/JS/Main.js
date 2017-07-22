"use strict";

class Main{
	
	static login(){	//APPEND USER INFORMATION
		let data = JSON.parse(sessionStorage.data, function(key, value) {
			return value;
		});
		$(".top-block h1").append(data.username);
		$(".flname").append(data.firstName + " " + data.lastName);
		$(".eMail").append(data.eMail);
		$(".info img").attr("src", data.avatar);
	};
	
	static friends(friends){  //APPEND USER FRIENDS
		$("#friends_button").removeAttr("onclick");
		for(let i in friends){
			let friend = 
				"<div class = 'friends'>"+
					"<img src =" + friends[i].avatar+">"+
					"<span class = 'fname'>"+ friends[i].firstName+"</span>"+
					"<span class = 'lname'>"+friends[i].lastName+"</span>"+
					"<span class='button_type  button del' title='delete_friend' onclick='user.buttonsShow(this); return false;'" + 'id=' +"'"+ friends[i].userId +"'" +">" +  "Delete" + "</span>"+
					"<span class='button_type  button sendMess' title='goToMess_friend'" +  "onclick=" + '"' + "user.buttonsShow(this"+','+ "'" + friends[i].firstName + "'" + '); return false;' + '"' + "id=" +"'"+ friends[i].userId + "'" +">" +  "Messages" + "</span>"+
				"</div>";
			$(".blocFriend").append(friend);
			/*onclick='user.buttonsShow(this); return false;'*/
		};
	};
	
	static channels(channels){  //APPEND USER CHANNELS
		for(let i in channels){
			let channel = 
				"<div class = 'channels'>"+
					"<img src = "+channels[i].channelLogo+" " + "class = 'channels_logo' >"+
					"<span class = 'channels_name'>"+channels[i].channelName+"</span>"+
				"</div>";
			$("#channels").append(channel);
			$("#channels_button").removeAttr("onclick");
		};
	};
	
	static findFriend(findFriend){  //APPEND FRIEND SEARCH RESULT
		$(".img img ").attr("src", findFriend.avatar);
		$(".name").html(findFriend.username);
		$(".frfname").html(findFriend.firstName + " " + findFriend.lastName);
		$(".controls button").attr("id", findFriend.userId);
		$("#search_result").css("display", "block");
		$(".find_friends").val("");
	};
	
	static newFriend(newFriend){  //APPEND NEW FRIEND REQUSET
		if(newFriend.length > 0){
			for(let i in newFriend){
				let friend = 
					"<div class = 'newFriend'>" +
						"<div class='img'>" +
							"<img class='search_img'" + "src =" + "'" +newFriend[i].avatar + "'" +">"+
						"</div>"+
						"<div class='controls'>"+
							"<button class='button_type button' title = 'accept_friend' onclick = 'user.buttonsShow(this); return false;'"+ "id =" + "'" + newFriend[i].userId + "'" +">" +'Accept' + "</button>" +
							"<button class='button_type  button' title = 'decline_friend' onclick = 'user.buttonsShow(this); return false;'" + "id =" + "'" + newFriend[i].userId + "'" +">"+'Decline' + "</button>"+
						"</div>"+
						"<div class='infos'>"+
							"<div class='labeled name lk'>"+ newFriend[i].username +"</div>" +
							"<div class = 'frfname lk'>"+ newFriend[i].firstName + " " + newFriend[i].lastName +"</div>"+
						"</div>" +
					"</div>";
				$(".acc").append(friend);
				$(".acc").show();
			};
			$(".page1").show();
		};
	};
	
	static sendMessage(id){	 // SEND MESSAGE
		$("#div").append("<div class = 'mess'>"+'You: '+ $('#sendie').val() + "</div>");
		var tag_arr = $("#div");
		for(let i=0;i<tag_arr.length;i++) {
			if (tag_arr[i].className == 'show') {
				tag_arr = tag_arr[i]; 
				break;
			}
		}
		
		localStorage.setItem(id,tag_arr.outerHTML); 
		
		setInterval(() => {	// STORACVAC KOD
			CallAjax.newMessage();
			$("#newMess div").remove();
		}, 5000);
	}
	
	static showMessage(id, userMessage){ // SHOW MESSAGE
		if(localStorage.getItem(id)){
			$("#chat-area div").remove();
			$("#chat-area").append(localStorage.getItem(id));
		}
		$("#newMess").show();
		$(".fr").hide();
		$(".page1").show();
		$("#account").hide();
		$("#channels").hide();
		$(".blocFriend").hide();
		$("#mail").show();
		$(".acc").show();
		$(".sendie").attr("id", id);
		$(".clear_form").attr("id", id);
		$("#div").append("<div class = 'mess'>"+userMessage.firstName+': '+userMessage.body + "</div>")
		var tag_arr = $("#div");
		for(let i=0;i<tag_arr.length;i++) {
			if (tag_arr[i].className == 'show') {
				tag_arr = tag_arr[i]; 
				break;
			}
		}
		localStorage.setItem(id,tag_arr.outerHTML);
		$(".newMessage").remove();
	}

	static createMess(messagelist){  // SHOW USER MESSAGE LIST
		$(".fr").hide();
		$(".page1").show();
		$("#newMess").show();
		$(".acc").show();
		for(let i in messagelist){
			$(".page1 #newMess").append("<div class='button newMessage' title = 'show_message'" + "onclick=" + '"' + "user.buttonsShow(this"+','+  messagelist[i].messageId  + '); return false;' + '"' + "id =" + "'" + messagelist[i].sender + "'" + ">"+'new message' + "</div>")
		};
		
	};
	
	static menu(){	//LEFT NAVIGATION MENU
		let active = true;
		let icon_menu = $(".icon_menu");
		let nav = $(".nav");
	    icon_menu.click(()=>{
	    	if(active == true){
	    		nav.css("display", "block");
	            $("#mainMenu").css("width", "8em");
	            active = false;
	        } else{
	            nav.css("display", "none")
	            $("#mainMenu").css("width", "3.3em");
	            active = true;
	        }
	    }); 
	};
	
	static navButtons(){  //NAVIGATION BUTTONS
		let active = true;
	    let sign_in = $("#update");
	    let nav = $("#nav");
	    let button = $("#update_my_account");
	    let opacity_bg = $(".opacity_bg");
	    let newMess = $("#newMess");
	    let cancel = $(".cancel");
	    let buttons = $(".active");
	    let account = $("#account");
	    let page1 = $(".page1");
	    let fr = $(".fr");
	    let acc = $(".acc");
	    let friends = $(".blocFriend");
	    let channels = $("#channels");
	    let mail = $("#mail");
	    opacity_bg.click(()=>{
	        show_hide_sign_in();
	    });
	    button.click(()=>{
	        show_hide_sign_in();
	    });
	    cancel.click(()=>{
	        show_hide_sign_in();
	    });
	    let show_hide_sign_in = ()=>{
	        if (active == true){
	        	opacity_bg.show(10);
	            sign_in.show(300);
	            nav.css("z-index", 0)
	            active = false;
	        }
	        else{
	            sign_in.hide(300);
	            opacity_bg.hide(10);
	            nav.css("z-index", 1000)
	            active = true;
	        }; 
	    };
	    $("._1").click(()=>{
	    	newMess.hide();
	    	buttons.hide();
	    	fr.hide();
	    	mail.hide();
	    	page1.hide();
	    	account.show();
	    	acc.show();
	    });
	    $("._2").click(()=>{
	    	newMess.hide();
	    	buttons.hide();
	    	acc.hide();
	    	mail.hide();
	    	page1.show();
	    	friends.show();
	    	fr.show();
	    });
	    $("._3").click(()=>{
	    	newMess.hide();
	    	buttons.hide();
	    	page1.hide();
	    	mail.hide();
	    	fr.hide();
	    	acc.hide();
	    	channels.show();
	    });
	    $("._4").click(()=>{
	    	newMess.show();
	    	page1.show();
	    	fr.hide();
	    	friends.hide();
	    	channels.hide();
	    	account.hide();
	    	mail.show();
	    });
	};
};

$(document).ready(()=>{
	let q = Main;
	q.login();
	q.menu();
	q.navButtons();
})