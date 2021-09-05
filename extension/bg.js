var readsound;
var audioElement = new Audio();

reconnect();

function reconnect(){
	readsound = new WebSocket("ws://micky.ovh:9998/writesound");
	readsound.onmessage = readsoundOnMessage;
}

function readsoundOnMessage(event) {
	let audioElement = new Audio();
    audioElement.src = 'data:audio/mp3;base64, ' +event.data;
    audioElement.load();
    audioElement.play();
}