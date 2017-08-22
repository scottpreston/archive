var Ssc = function () {
    return {
        move: function (pin, pos) {
            console.log("move requested: pin = " + pin + ",pos = " + pos);
            var url = "http://ubuntu-dev:8000/?pin=" + pin + "&pos=" + pos;
            $.get(url, function (data) {
                console.log("move complete.");
            });
        }
    }
};

var Eye = function (panPin, tiltPin) {
    var ssc = new Ssc();
    var panPos = 0;
    var tiltPos = 0;
    return {
        pan: function (pos) {
            panPos = pos;
            ssc.move(panPin, pos);
        },
        panRight: function () {
            panPos = panPos + 5;
            ssc.move(panPin, panPos);
        },
        panLeft: function () {
            panPos = panPos - 5;
            ssc.move(panPin, panPos);
        },
        tilt: function (pos) {
            tiltPos = pos;
            ssc.move(tiltPin, pos);
        },
        reset: function () {
            panPos = 127;
            tiltPos = 127;
            ssc.move(panPin, 127);
            ssc.move(tiltPin, 127);
        },
        getPan: function () {
            return panPos;
        },
        getTilt: function () {
            return tiltPos;
        }
    }
};

var Head = function (leftEye, rightEye) {
    return {
        pan: function (pos) {
            leftEye.pan(pos);
            rightEye.pan(pos);
        },
        tilt: function (pos) {
            leftEye.tilt(pos);
            rightEye.tilt(pos);
        },
        reset: function () {
            leftEye.reset();
            rightEye.pan(127);
            rightEye.tilt(115);
        }
    }
};

var load_right = function () {
    var right_canvas = document.querySelector("#right_eye");
    var right_ctx = right_canvas.getContext("2d");
    var right_img = new Image();

    right_img.src = "http://dev.misc.prestonresearch.net/jsrobotics/proxy.php?cam=192.168.5.50";
    right_img.onload = function () {
        right_ctx.drawImage(right_img, 0, 0);
        var tracked = $('#trackred_right').prop("checked");
        var follow = $('#followred_right').prop("checked");
        if (tracked || follow) {
            filterRed(right_ctx, rightEye, follow);
        }
    };
};

var load_left = function () {
    var left_canvas = document.querySelector("#left_eye");
    var left_ctx = left_canvas.getContext("2d");
    var left_img = new Image();

    left_img.src = "http://dev.misc.prestonresearch.net/jsrobotics/proxy.php?cam=192.168.5.60";
    left_img.onload = function () {
        left_ctx.drawImage(left_img, 0, 0);
        var tracked = $('#trackred_left').prop("checked");
        var follow = $('#follow_left').prop("checked");
        if (tracked || follow) {
            filterRed(left_ctx, leftEye, follow);
        }
    };
};


function filterRed(ctx, eye, follow) {
    var meanX = 0, meanY = 0, meanCount = 0;
    var imageData = ctx.getImageData(0, 0, 640, 480);
    var newImageData = ctx.createImageData(640, 480);
    var data = imageData.data;
    var newData = newImageData.data;
    for (var x = 0; x < 640; x++) {
        for (var y = 0; y < 480; y++) {
            //var offset = x * y * 4;
            var offset = x * 4 + y * 4 * imageData.width;
            var r = data[offset];
            var g = data[offset + 1];
            var b = data[offset + 2];
            var a = data[offset + 3];
            var gray = (r + g + b) / 3;
            if (isRed(r, g, b)) {
                //    newColor = 0;
                newData[offset] = 0;
                newData[offset + 1] = 0;
                newData[offset + 2] = 0;
                newData[offset + 3] = a;
                meanX = meanX + x;
                meanY = meanY + y;
                meanCount++;
            } else {
                newData[offset] = 255;
                newData[offset + 1] = 255;
                newData[offset + 2] = 255;
                newData[offset + 3] = a;
            }
        }
    }
    ctx.putImageData(newImageData, 0, 0);
    var avgX = meanX / meanCount;
    var avgY = meanY / meanCount;
    drawCenter(ctx, avgX, avgY);
    if (follow) {
        var curPos = eye.getPan();
        if (avgX > 380) {
            eye.panRight()
        }
        if (avgX < 260) {
            eye.panLeft();
        }
    }
}

function drawCenter(ctx, x, y) {
    console.log("center = " + x + "," + y)
    ctx.strokeStyle = 'rgb(0,255,0)';
    ctx.beginPath();
    ctx.moveTo(0, y);
    ctx.lineTo(640, y);
    ctx.closePath();
    ctx.stroke();
    ctx.beginPath();
    ctx.moveTo(x, 0);
    ctx.lineTo(x, 480);
    ctx.closePath();
    ctx.stroke();

}

function isBlue(r, g, b) {
    return (b > r + 75 && b > g + 75);
}

function isRed(r, g, b) {
    return (r > g + 75 && r > b + 75);
}

var leftEye = new Eye(0, 1);
var rightEye = new Eye(2, 3);
var feynmanHead = new Head(leftEye, rightEye);
feynmanHead.reset();
setInterval(load_left, 500);
setInterval(load_right, 500);
