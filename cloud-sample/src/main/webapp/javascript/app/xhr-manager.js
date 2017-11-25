function XHRManager(request){
	var url = request.url;
	var method = request.method;
	var onConnect = sanitizeFunction(request.onConnect);
	var onSuccess = sanitizeFunction(request.onSuccess);
	var onError = sanitizeFunction(request.onError);
	
	return{
		send: send
	}
	
	function send(data){
		var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function(){
			if (this.readyState == 1)
				onConnect("Connection established");
			else if (this.readyState == 4 && this.status == 200)
				onSuccess(xhttp.responseText);
			else if (this.readyState == 4 & this.status != 200)
				onError(xhttp.responseText)
		}
		xhttp.open(method, url, true);
		xhttp.send(data);
	}
	
	function sanitizeFunction(f){
		if (f != undefined && typeof f === "function")
			return f;
		return console.log;
	}
	
	
}