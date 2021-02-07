const express = require('express');
const productRoutes = require('./app/routes/product')
const productTypeRoutes = require('./app/routes/productType')

const port = 3000;
const app = express();
app.use(express.urlencoded({ extended: true }));
app.use(express.json());

app.use('/produtos', productRoutes)
app.use('/tiposDeProdutos', productTypeRoutes)

app.listen(port, () =>{});