function appController(){
	var config = new Config();
	var DIV_ID = "message-content";
	var request = {url: config.EVENT_URL , 
			       successCallback: render};
	
	var cloudResource = new Cloud(request);
	
	return{
		launch: launch
	}
	
	function launch(){
		cloudResource.subscribe();
		keyListenerOnTextArea();
	}
	
	function render(response){
		var placeholder = $('#' + DIV_ID);
		placeholder.append("<br/>")
		placeholder.append(response);
	}
	
	function keyListenerOnTextArea(){
		var that = $('#text_area');
		that.keypress(function(e){
			if (e.which == 13 && !e.shiftKey){
				e.preventDefault();
				cloudResource.publish(that.val());
				that.val("");
			}
				
		});
	}
}

$(document).ready(function(){
	appController().launch();
	
});


