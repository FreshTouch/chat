class RegisterLoginAjaxHandler extends CallRegisterLoginAjax{
	
	register(form){
		let unindexed_array = form.serializeArray();
		unindexed_array.push({name:"avatar",value:"../Images/avatar.jpg"});
	    let indexed_array = {};
	    $.map(unindexed_array, function(n){
	        indexed_array[n['name']] = n['value'];
	    });
	    this.method = "POST";
		this.action = "register";
		this.formData = JSON.stringify(indexed_array);
		super.register();
	};
	
	login(form){
		let unindexed_array = form.serializeArray();
		let username = unindexed_array[0].value;
		let password = unindexed_array[1].value;
		this.action = "login/" + username + "," + password;
	    this.method = "GET";
		super.login();
	};
	
	
	regLogButton(name){
		let id = name;
		let opacity_bg = $(".opacity_bg");
		id.css({
            'margin-left':-id.outerWidth()/2-150,
            'margin-top':-id.outerWidth()/2
        });
		opacity_bg.show(10);
        id.show(300);
	};
	
	opacity_bg($this, sign_in, register){
		$this.hide(10);
		sign_in.hide(300);
		register.hide(300);
	};
};

let main = new RegisterLoginAjaxHandler();