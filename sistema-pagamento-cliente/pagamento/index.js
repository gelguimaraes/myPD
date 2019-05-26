var bankBroker = require("../util/broker_tools");

exports.processPayment = transaction => {
  validateTransaction(transaction);
  bankBroker.sendMsg(transaction);
};

var validateTransaction = transaction => {
  if (!"ag" in transaction || transaction.ag == "")
    throw new Error("ag is a required field in Transaction");
  if (!"conta" in transaction || transaction.conta == "")
    throw new Error("conta is a required field in Transaction");
  if (!"senha" in transaction || transaction.senha == "")
    throw new Error("senha is a required field in Transaction");
  if (!"numero_cartao" in transaction || transaction.numero_cartao == "")
    throw new Error("numero_cartao is a required field in Transaction");
  if (!"valor" in transaction || transaction.valor == "")
    throw new Error("valor is a required field in Transaction");
};
