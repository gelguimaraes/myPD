var express = require("express");
var routes = require("./routes/index");
var bodyParser = require("body-parser");
var broker = require("./util/broker_tools");
var config = require("./config.json");

start = async function() {
  var app = express();
  app.use(bodyParser.json());
  app.use(bodyParser.urlencoded({ extended: true }));
  routes(app);

  await broker.brokerConnect(config.broker, config.queeue);

  app.listen(config.server_port, function() {
    console.log("Hello Assert listening on port " + config.server_port);
  });
};

start();
