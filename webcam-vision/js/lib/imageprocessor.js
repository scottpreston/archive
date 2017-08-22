var ImageProcessor = function() {
    this.oldImageData = null;
    this.imageData = null;
};

ImageProcessor.prototype.toGrey = function(ctx,ctx2) {
    var imageData = ctx.getImageData(0, 0, 640, 480);
    var newImageData = ctx2.createImageData(640, 480);
    var data = imageData.data;
    var newData = newImageData.data;
    for (var x = 0; x < 640; x++) {
        for (var y = 0; y < 480; y++) {
            var offset = x * 4 + y * 4 * imageData.width;
            var r = data[offset]
            var g = data[offset + 1];
            var b = data[offset + 2];
            var a = data[offset + 3];
            var gray = (r + g + b) / 3;
            newData[offset] = gray;
            newData[offset + 1] = gray;
            newData[offset + 2] = gray;
            newData[offset + 3] = a;
        }
    }
    ctx2.putImageData(newImageData, 0, 0);
};

ImageProcessor.prototype.toRed = function(ctx,ctx2) {
    var imageData = ctx.getImageData(0, 0, 640, 480);
    var newImageData = ctx2.createImageData(640, 480);
    ctx2.clearRect (0,0,640,480);
    var data = imageData.data;
    var newData = newImageData.data;
    var avgX=0, avgY=0, hits=0;
    for (var x = 0; x < 640; x++) {
        for (var y = 0; y < 480; y++) {
            var offset = x * 4 + y * 4 * imageData.width;
            var r = data[offset]
            var g = data[offset + 1];
            var b = data[offset + 2];
            var a = data[offset + 3];
            var gray = (r + g + b) / 3;
            if (isRed(r,g,b)) {
                newData[offset] = 0;
                newData[offset + 1] = 0;
                newData[offset + 2] = 0;
                newData[offset + 3] = a;
                avgX=avgX+x;
                avgY=avgY+y;
                hits++;
            }
        }
    } 

    var ax = (avgX/hits), ay = (avgY/hits);
ctx2.arc(ax, ay, 20, 0, 2 * Math.PI, false);
      ctx2.fillStyle = 'green';
      ctx2.fill();
    console.log("averages:",ax,ay);
    //ctx2.putImageData(newImageData, 0, 0);
    
      

      
};

ImageProcessor.prototype.toBlue = function(ctx,ctx2) {
    var imageData = ctx.getImageData(0, 0, 640, 480);
    var newImageData = ctx2.createImageData(640, 480);
    var data = imageData.data;
    var newData = newImageData.data;
    for (var x = 0; x < 640; x++) {
        for (var y = 0; y < 480; y++) {
            var offset = x * 4 + y * 4 * imageData.width;
            var r = data[offset]
            var g = data[offset + 1];
            var b = data[offset + 2];
            var a = data[offset + 3];
            var gray = (r + g + b) / 3;
            if (isBlue(r,g,b)) {
            newData[offset] = r;
            newData[offset + 1] = g;
            newData[offset + 2] = b;
            newData[offset + 3] = a;
            }
        }
    }
    ctx2.putImageData(newImageData, 0, 0);
};


function  isRed(r, g, b) {
        return (r > g + 75 && r > b + 75);
    }

function isBlue(r, g, b) {
        return (b > r + 75 && b > g + 75);
    }