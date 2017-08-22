// Load the http module to create an http server.
var http = require('http');
var url = require('url');
var mac_serial = "/dev/cu.usbserial";
var linux_serial = "/dev/ttyUSB0";

var SerialPort = require("serialport").SerialPort;
var sp = new SerialPort(linux_serial, {
    baudrate:9600
});


function write(pin, pos) {
    if (pin >= 0 && pos >= 0) {
        var b = [255, pin, pos];
        sp.write(b);
        console.log("serial port writing on pin=" + pin + ", position = " + pos);
    }
}


// Configure our HTTP server to respond with Hello World to all requests.
var server = http.createServer(function (request, response) {
    response.writeHead(200, {"Content-Type":"text/plain"});
    var url_parts = url.parse(request.url, true);
    var pin = url_parts.query.pin;
    var pos = url_parts.query.pos;
    write(pin, pos);
    response.end("Pin = " + pin + ", Position = " + pos);
});

// Listen on port 8000, IP defaults to 127.0.0.1
server.listen(8000);

console.log("Server running at http://127.0.0.1:8000/");