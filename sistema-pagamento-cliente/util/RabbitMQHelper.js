const amqp = require("amqplib/callback_api");

exports.getChannel = address => {
  return new Promise((resolve, reject) => {
    amqp.connect(address, (errorConection, connection) => {
      if (errorConection) reject(errorConection);
      return connection.createChannel((errorChannel, channel) => {
        if (errorChannel) reject(errorChannel);
        resolve(channel);
      });
    });
  });
};
