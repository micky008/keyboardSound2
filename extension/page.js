var readlist;
var readinput;

reconnect();

 document.addEventListener("DOMContentLoaded", function(event) {
	 document.getElementById("reco").addEventListener("click", reconnect); 
	 document.getElementById("sendthis").addEventListener("click", mysend); 	 
})
	 
function reconnect(){
	readlist = new WebSocket("ws://micky.ovh:9998/readlist");
	readinput = new WebSocket("ws://micky.ovh:9998/inputsound");
	readlist.onmessage = readlistOnMessage;
	readlist.onclose = onClose;    
	readlist.onopen = onOpen;
}

function mysend(i){        
	//console.log(i);
	readinput.send(i);
}


function readlistOnMessage(event) {
	clearUl();
	let json = JSON.parse(event.data);
	let keys = Object.keys(json);
	let table = document.getElementById('myTable');
	for (let key of keys){
		let tr = document.createElement("tr");
		let td1 = document.createElement("td");	
		let td2 = document.createElement("td");	
		let mybutton = document.createElement("button");	
		mybutton.type='button';
		mybutton.innerHTML='GO';
		mybutton.addEventListener('click',mysend.bind(null,key));
		td1.innerHTML = `${json[key]}`;	
		td2.appendChild(mybutton);
		tr.appendChild(td1);
		tr.appendChild(td2);
		table.appendChild(tr);
	}
}
	
function onClose(event) {
	document.getElementById('reco').hidden = false;
}

function onOpen(event) {
	document.getElementById('reco').hidden = true;
}

function clearUl(){
	let ul = document.getElementById('myTable');
	while( ul.firstChild) {
		ul.removeChild( ul.firstChild);
	}
}
