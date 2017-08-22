var CamCapture = function () {
    var self = this;
    this.video = document.querySelector("#vid");
    this.canvas = document.querySelector('#canvas');
    this.canvas2 = document.querySelector('#canvas2');
    this.ctx = canvas.getContext('2d');
    this.ctx2 = canvas2.getContext('2d');
    this.localMediaStream = null;
    this.fps = 1000 / 4;
    this.interval = null;
    this.processors = [];

    navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia || navigator.msGetUserMedia;
    window.URL = window.URL || window.webkitURL;
    navigator.getUserMedia({video: true}, function (stream) {
        self.video.src = window.URL.createObjectURL(stream);
        self.localMediaStream = stream;

    }, this.onCameraFail);

};

CamCapture.prototype.onCameraFail = function (e) {
    alert('Your browser does not support this, download Chrome!');
};

CamCapture.prototype.start = function () {
    var self = this;
    this.interval = setInterval(function () {
        
        self.ctx.drawImage(self.video, 0, 0);
        canvas2.width = canvas2.width;
        self.processors.forEach(function(processor) {
            processor(self.ctx, self.ctx2);
        });
        
        console.log({w:self.video.videoWidth, h: self.video.videoHeight});

    }, self.fps);
};

CamCapture.prototype.stop = function () {
    clearInterval(this.interval);
};

CamCapture.prototype.addProcessor = function (processor) {
    this.processors.push(processor);
};

