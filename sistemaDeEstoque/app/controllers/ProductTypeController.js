const ProductType = require('../modulos/ProductType')

function ProductTypeController() { }

ProductTypeController.prototype.list = (req, res) => {
    let productType = new ProductType();

    productType.list(result => {
        if (result.error)
            res.status(400).send({ msg: "Ocorreu um erro ao listar os produtos." });

        res.send(result);
    });
};

ProductTypeController.prototype.findById = (req, res) => {
    let idTipo = req.params.id;

    if (isNaN(idTipo) || idTipo < 0) {
        res.send({ msg: "Parametro de rota invalido." })
    } else {
        let productType = new ProductType();

        productType.find(idTipo, result => {
            if (result.error)
                res.status(400).send({ msg: "Ocorreu um erro ao listar os tipos de produtos." });

            if (result.length > 0) {
                res.send(result);
            } else {
                res.send({ msg: "Produto não encontrado" })
            }
        });
    }
};

ProductTypeController.prototype.create = (req, res) => {

    if (Object.entries(req.body).length == 0) {

        res.status(400).send({
            message: "Falha ao criar produto. Nenhum dado foi enviado."
        });

    } else {
        //TODO validar dados da requisição
        let tipoProduto = req.body.tipo;

        if (tipoProduto) {
            if (!tipoProduto.name) {
                res.status(400).send({ msg: 'O atribudo name não foi especificado.' });
            } else {

                let productType = new ProductType();

                productType.create(tipoProduto, (result) => {
                    if (result.error)
                        res.status(400).send({ msg: "Ocorreu um erro ao cadastrar o tipo de produto." });

                    res.status(201).send({ msg: "Cadastro realizado com sucesso!" });
                });
            }
        } else {
            res.status(400).send({ msg: "Formato de dados inválido." });
        }
    }
};

ProductTypeController.prototype.update = (req, res) => {

    let idTipo = req.params.id;

    if (isNaN(idTipo) || idTipo < 0) {
        res.send({ msg: "Parametro de rota invalido." })
    } else {

        if (Object.entries(req.body).length == 0) {

            res.status(400).send({
                message: "Falha ao atualizar tipo de produto. Nenhum dado foi enviado."
            });

        } else {

            let tipoProduto = req.body.tipo;

            if (tipoProduto) {

                if (!tipoProduto.name) {
                    res.send({ msg: "O campo nome não foi informado." })
                    return;
                }

                let productType = new ProductType;
                productType.find(idTipo, result => {

                    if (result.length > 0) {

                        productType.update(tipoProduto, idTipo, result => {
                            if (result.error)
                                res.send({ msg: "Falha ao atualizar o Tipo de Produto" })

                            res.send({ msg: "Tipo de produto atualizado com sucesso." })
                        });
                    } else {
                        res.send({ msg: "Falha ao atualizar. Registro não encontrado" });
                    }
                });

            } else {
                res.status(400).send({ msg: "Formato de dados inválido." })
            }
        }
    }
};

ProductTypeController.prototype.delete = (req, res) => {
    let idTipo = req.params.id;

    if (isNaN(idTipo) || idTipo < 0) {
        res.send({ msg: "Parametro de rota invalido." })
    } else {

        let productType = new ProductType;
        productType.find(idTipo, result => {

            if (result.length > 0) {

                productType.delete(idTipo, result => {
                    if (result.error)
                        res.send({ msg: "Falha ao deletar registro." })

                    res.send({ msg: "Tipo de produto deletado com sucesso." })

                });
            } else {
                res.send({ msg: "Registro não encontrado" })
            }
        });
    }
};


module.exports = ProductTypeController;