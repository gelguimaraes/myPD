var mqHelper = require("./RabbitMQHelper");

var channel;
var queeue;

exports.brokerConnect = async function(address, queeueName) {
  if (!queeue) queeue = queeueName;
  if (channel) return channel;

  try {
    channel = await mqHelper.getChannel(address);

    channel.assertQueue(queeue, {
      durable: true
    });

    return channel;
  } catch (e) {
    console.log("Error creating channel or connection: " + e);
    return e;
  }
};

exports.getChannel = function() {
  if (channel) return channel;

  console.log("There is no channel");
  return null;
};

exports.sendMsg = msg => {
  channel.sendToQueue(queeue, Buffer.from(JSON.stringify(msg)));
  console.log("[X] Sent(" + new Date() + ")");
  console.table(msg);
};
