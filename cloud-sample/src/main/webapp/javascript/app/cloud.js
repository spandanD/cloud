function Cloud(url, successCallback){
	var url = url;
	var successCallback;
	
	return{
		subscribe: subscribe,
		publish: publish
	}
	
	function subscribe(){
		fetch(successCallback)
	}
	
	function fetch(successCallback){
		$.ajax({
			url: url + "?rnd=1" + new Date().getTime(),
			type: "GET",
			success: function(data){
				successCallback(data);
				fetch(successCallback);
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
			}
		});
	}
}