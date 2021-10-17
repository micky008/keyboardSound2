var readsound;


setInterval(reconnect, 1000 * 60 * 5);
reconnect();
chrome.runtime.onMessage.addListener(notify);


function notify(message, sender, sendResponse) {
	if (message['changeChan']) {
		reconnect();
	}

}

function reconnect() {
	if (readsound) {
		readsound.close();
	}
	chrome.storage.local.get('chan', (store) => {
		if (!store['chan']) {
			let channelsWs = new WebSocket("ws://micky.ovh:9998/readchan");
			channelsWs.onmessage = (event) => {
				let json = JSON.parse(event.data); // data = ["DEV","OPS"]
				readsound = new WebSocket("ws://micky.ovh:9998/writesound/" + json[0]);
				readsound.onmessage = readsoundOnMessage;
			};
		} else {
			readsound = new WebSocket("ws://micky.ovh:9998/writesound/" + store['chan']);
			readsound.onmessage = readsoundOnMessage;
		}
	});

}

function readsoundOnMessage(event) {
	var audioElement = new Audio();
	audioElement.src = 'data:audio/mp3;base64, ' + event.data;
	audioElement.load();
	audioElement.play();
}