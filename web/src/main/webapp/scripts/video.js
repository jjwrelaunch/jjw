
var downloadURL = document.getElementById('download-url');

var webcamstream;
var screenStream;

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
	this.opener.window.screenRecorder.startRecording();
	
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
			true==true;
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


function jjw_startRecordingScreen() {

	captureUserMediaScreen(function(stream) {
		this.opener.window.screenRecorder = window.RecordRTC(stream, {
			type : 'video/mp4' // don't forget this; otherwise you'll get
		// video/webm instead of audio/ogg
		});
		//this.opener.window.screenRecorder.startRecording();
		
		screenStream = stream;
	});
	
};

function jjw_stopRecordingScreen() {
				
    this.opener.window.screenRecorder
			.stopRecording(function(url) {
				
				videoElementScreen.onended = function() {
                   // videoElement.pause();
                    // dirty workaround for: "firefox seems unable to playback"
                    videoElementScreen.src = URL.createObjectURL(this.opener.window.screenRecorder.getBlob());
                };
			});	
			
			true==true;
};


function captureUserMediaScreen(callback) {
	navigator.getUserMedia = navigator.mozGetUserMedia
			|| navigator.webkitGetUserMedia;
	navigator.getUserMedia({
		audio : false,
		video: {
			mediaSource : 'screen',
			width: { min: 640, ideal: 1920, max: 1920 },
			height: { min: 480, ideal: 1080, max: 1080 },
			frameRate: { max: 3 },
		}
	}, function(stream) {
		if (videoElementScreen == null) videoElementScreen = document.getElementById('videoScreen');
		videoElementScreen.src = URL.createObjectURL(stream);
		videoElementScreen.muted = true;
		videoElementScreen.controls = false;
		

		callback(stream);
	}, function(error) {
		console.error(error);
	});
}

