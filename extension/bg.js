var readsound;
var audioElement = new Audio();

setInterval(reconnect,1000 * 60 * 5);
reconnect();

function reconnect(){
	if (readsound != undefined) {
		readsound.close();
	}
	readsound = new WebSocket("ws://micky.ovh:9998/writesound");
	readsound.onmessage = readsoundOnMessage;
}

function readsoundOnMessage(event) {
    audioElement.src = 'data:audio/mp3;base64, ' +event.data;
    audioElement.load();
    audioElement.play();
}