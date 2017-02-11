
var downloadURL = document.getElementById('download-url');

var webcamstream;

function jjw_startRecording() {

	captureUserMedia(function(stream) {
		this.opener.window.audioVideoRecorder = window.RecordRTC(stream, {
			type : 'video/mp4' // don't forget this; otherwise you'll get
		// video/webm instead of audio/ogg
		});
		this.opener.window.audioVideoRecorder.startRecording();
		videoElement.play();
		webcamstream = stream;
	});
};

function jjw_pauseRecording() {
	this.opener.window.audioVideoRecorder.pauseRecording();
}

function jjw_resumeRecording() {
	this.opener.window.audioVideoRecorder.resumeRecording();
}

function jjw_stopRecording() {
	this.opener.window.audioVideoRecorder
			.stopRecording(function(url) {
				
				videoElement.onended = function() {
                    videoElement.pause();
                    // dirty workaround for: "firefox seems unable to playback"
                    videoElement.src = URL.createObjectURL(this.opener.window.audioVideoRecorder.getBlob());
                };
			});	
};

function captureUserMedia(callback) {
	navigator.getUserMedia = navigator.mozGetUserMedia
			|| navigator.webkitGetUserMedia;
	navigator.getUserMedia({
		audio : true,
		video: {
			width: { min: 640, ideal: 1920, max: 1920 },
			height: { min: 480, ideal: 1080, max: 1080 },
		}
	}, function(stream) {
		if (videoElement == null) videoElement = document.getElementById('video');
		videoElement.src = URL.createObjectURL(stream);
		videoElement.muted = true;
		videoElement.controls = false;
		//videoElement.play();

		callback(stream);
	}, function(error) {
		console.error(error);
	});
}