var casper = require('casper').create();
casper.start();
casper.thenOpen('http://www.casperjs.org/', function () {
    this.capture('casper.png');
});
casper.run();