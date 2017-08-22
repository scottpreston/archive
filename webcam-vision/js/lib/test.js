// setup

var mockController = {};
var mockSession = {user:"none"};

// act

mockController("user1");

// assert

assertEquals(mockSession.getUser(),"user1");




