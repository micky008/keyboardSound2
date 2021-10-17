var readlist;
var readinput;

reconnect();

document.addEventListener("DOMContentLoaded", function (event) {
	document.getElementById("reco").addEventListener("click", reconnect);
	document.getElementById("myselect").addEventListener("change", setStorage);
});

function reconnect() {
	let channelsWs = new WebSocket("ws://micky.ovh:9998/readchan");
	channelsWs.onmessage = readChannelName;
}

function setStorage(event) {
	let chan = event.target[event.target.selectedIndex].value; //OPS ou DEV	
	chrome.storage.local.set({ 'chan': chan });
	readSongList(chan);
	chrome.runtime.sendMessage({ "changeChan": chan });
}

function readChannelName(event) {
	chrome.storage.local.get('chan', (store) => {
		let json = JSON.parse(event.data); // data = ["DEV","OPS"]
		let select = document.getElementById("myselect");
		clearUl('myselect');
		if (!store['chan'] || store['chan'].length == 0) {
			store['chan'] = json[0];
		}
		for (let chan of json) {
			let opt = document.createElement("option");
			opt.text = chan;
			opt.selected = chan == store['chan'];
			if (opt.selected) {
				readSongList(chan);
			}
			select.appendChild(opt);
		}
	});
}

function readSongList(channelName) {
	readlist = new WebSocket("ws://micky.ovh:9998/readlist/" + channelName);
	readinput = new WebSocket("ws://micky.ovh:9998/inputsound/" + channelName);
	readlist.onmessage = readlistOnMessage;
	readlist.onclose = onClose;
	readlist.onopen = onOpen;
}


function mysend(i) {
	readinput.send(i);
}


function readlistOnMessage(event) {
	clearUl('myTable');
	let json = JSON.parse(event.data);
	console.log(json)
	let keys = Object.keys(json);
	let table = document.getElementById('myTable');
	for (let key of keys) {
		let tr = document.createElement("tr");
		let td1 = document.createElement("td");
		let td2 = document.createElement("td");
		let mybutton = document.createElement("button");
		mybutton.type = 'button';
		mybutton.innerHTML = 'GO';
		mybutton.addEventListener('click', mysend.bind(null, key));
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

function clearUl(what) {
	let ul = document.getElementById(what);
	while (ul.firstChild) {
		ul.removeChild(ul.firstChild);
	}
}
