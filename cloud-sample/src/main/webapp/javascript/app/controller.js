function appController(){
	var config = new Config();
	var DIV_ID = "message-content";
	
	return{
		launch: launch
	}
	
	function launch(){
		$.get(config.EVENT_URL, {}, render);
	}
	
	function render(response){
		var placeholder = $('#' + DIV_ID);
		placeholder.html(response);
	}
}

$(document).ready(function(){
	appController().launch()
});


