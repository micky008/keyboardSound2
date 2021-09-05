var readlist;
var readinput;

reconnect();

 document.addEventListener("DOMContentLoaded", function(event) {
	 document.getElementById("reco").addEventListener("click", reconnect); 
	 document.getElementById("sendthis").addEventListener("click", mysend); 	 
})
	 
function reconnect(){
	readlist = new WebSocket("ws://localhost:9998/readlist");
	readinput = new WebSocket("ws://localhost:9998/inputsound");
	readlist.onmessage = readlistOnMessage;
	readlist.onclose = onClose;    
	readlist.onopen = onOpen;
}

function mysend(){        
	readinput.send(document.getElementById('myText').value);
}


function readlistOnMessage(event) {
	clearUl();
	let json = JSON.parse(event.data);
	let keys = Object.keys(json);
	let ul = document.getElementById('myUl');
	for (let key of keys){
		li = document.createElement("li");
		li.innerHTML = key+" - "+json[key];
		ul.appendChild(li);
	}
}
	
function onClose(event) {
	document.getElementById('reco').hidden = false;
}

function onOpen(event) {
	document.getElementById('reco').hidden = true;
	console.log("server started")
}

function clearUl(){
	let ul = document.getElementById('myUl');
	while( ul.firstChild) {
		ul.removeChild( ul.firstChild);
	}
}
