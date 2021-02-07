const routes = require('express').Router();
const ProductController = require('../controllers/ProductController');

var productController = new ProductController();

routes.get('/', productController.list);
routes.get('/:id', productController.findById);
routes.post('/', productController.create);
routes.put('/:id', productController.update);
routes.delete('/:id', productController.delete);

module.exports = routes;