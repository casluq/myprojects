const routes = require('express').Router();
const ProductType = require("./../modulos/ProductType");
const ProductTypeController = require("../controllers/ProductTypeController");

var productTypeController = new ProductTypeController();

routes.get('/', productTypeController.list);
routes.get('/:id', productTypeController.findById);
routes.post('/', productTypeController.create);
routes.put('/:id', productTypeController.update);
routes.delete('/:id', productTypeController.delete);

module.exports = routes;