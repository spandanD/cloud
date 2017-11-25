function appController(){
	var config = new Config();
	var DIV_ID = "message-content";
	var MODAL_ID = "usernameModal";
	var USER_NAME_ID = "username";
	var TEXT_ID = "text_area";
	var username;
	var request = {url: config.EVENT_URL , 
			       successCallback: render,
			       connectionCallback: displayConnectionBanner};
	
	var cloudResource = new Cloud(request);
	
	return{
		launch: launch,
		readUserName: readUserName
	}
	
	function displayConnectionBanner(){
		$("#connection-banner").css("display" , "block");
	}
	function launch(){
		cloudResource.subscribe();
		overrideEnterKeyForInputBox(TEXT_ID, publishToCloud);
		overrideEnterKeyForInputBox(USER_NAME_ID, readUserName);
		$("#" + MODAL_ID).modal();
	}
	
	function render(response){
		var response = JSON.parse(response);
		var message = response.message;
		var messageAuthor = response.author;
		
		var placeholder = $('#' + DIV_ID);
		
		var row = $("<div></div>").addClass("row");
		row.append(getUsernameDOM(messageAuthor));
		row.append(getMessageDOM(message, messageAuthor));
				
		placeholder.append(row);
	}
	
	function getUsernameDOM(author){
		if (author == username) return $("<div></div>");
		var userHTML = $("<div></div>").addClass("col-md-1 h3").text(author);
		return userHTML;
	}
	
	function getMessageDOM(message, author){
		var messageHTML = $("<div></div>").addClass("alert alert-info col-md-4").text(message);
		if (author == username){
			messageHTML.addClass("col-md-offset-8");
		}
		return messageHTML;
	}
	
	function overrideEnterKeyForInputBox(divIdentifier, callback){
		console.log(divIdentifier);
		var element = $('#' + divIdentifier);
		element.keypress(function(e){
			if (e.which == 13 && !e.shiftKey){
				e.preventDefault();
				callback(element.val());
				element.val("");
			}
		});
	}

	function publishToCloud(value){
		cloudResource.publish(composeMessage(value));
	}
	
	function readUserName(value){
		$("#" + MODAL_ID).modal("hide");
		username = value;
	}
	
	function composeMessage(rawContent){
		var message = {author:username, message:rawContent}
		return JSON.stringify(message);
	}
}

$(document).ready(function(){
	controller = appController();
	controller.launch();
	
});


