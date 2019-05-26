var pagamentoController = require("../pagamento");

var appRouter = app => {
  app.get("/", (req, res) => {
    res.status(200).send("Hello Pag");
  });

  app.get("/count", (req, res) => {
    res.status(200).send("Total de mensagens: XX");
  });

  app.post("/pagar", (req, res) => {
    console.log("Processando pagamento");
    let transcation = req.body;
    console.log("Transação:", transcation);
    try {
      pagamentoController.processPayment(transcation);
    } catch (e) {
      console.error(e);
      res.status(400).send(e.message);
      return;
    }
    res.status(200).send("Seu pagamento foi enviado para processamento");
  });
};

module.exports = appRouter;
