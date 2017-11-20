function Cloud(request){
	var url = request.url;
	var successCallback = request.successCallback;
	
	return{
		subscribe: subscribe,
		publish: publish
	}
	
	function subscribe(){
		fetch(successCallback)
	}
	
	function fetch(successCallback){
		$.ajax({
			url: url + "?rnd=" + new Date().getTime(),
			type: "GET",
			success: function(data){
				successCallback(data);
				fetch(successCallback);
			},
			error: function(data, response){
				console.log("Could not fetch data due to:" + response);
			}
		});
	}
	
	function publish(data){
		$.ajax({
			url: url,
			type: "POST",
			data: data,
			success: function(data){
				console.log(data);
			},
			error: function(data, response){
				console.log("Could not publish data due to:" + response);
			}
		});
	}
}