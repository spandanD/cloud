function Cloud(request){
	var url = request.url;
	var successCallback = request.successCallback;
	var connectionCallback = request.connectionCallback;
	
	return{
		subscribe: subscribe,
		publish: publish
	}
	
	function subscribe(){
		fetch(successCallback)
	}
	
	function fetch(successCallback){
		var getRequest = {url: url + "?rnd=" + new Date().getTime(),
						  method: "GET",
						  onSuccess: reFetch,
				          onConnect: connectionCallback};
		var xhr = new XHRManager(getRequest);	
		xhr.send();
		
		function reFetch(data){
			successCallback(data);
			xhr.send();
		}
	}
	
	function publish(data){
		var postRequest = {url: url,
				  		  method: "POST",
				  		  onSuccess: function(){},
				          onError: publishError};
		var xhr = new XHRManager(postRequest);	
		xhr.send(data);

		function publishError(responseText){
			console.log("Could not publish data due to:" + responseText);
		}
	}
}