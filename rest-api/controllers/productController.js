let products = require("../data/products");

// GET semua produk
const getAllProducts = (req, res) => {
  res.status(200).json({
    message: "Success get all products",
    data: products,
  });
};

// GET produk by id
const getProductById = (req, res) => {
  const id = parseInt(req.params.id);
  const product = products.find((p) => p.id === id);

  if (!product) {
    return res.status(404).json({
      message: "Product not found",
    });
  }

  res.status(200).json({
    message: "Success get product by id",
    data: product,
  });
};

// CREATE produk
const createProduct = (req, res) => {
  const { name, price, stock } = req.body;

  if (!name || price === undefined || stock === undefined) {
    return res.status(400).json({
      message: "Name, price, and stock are required",
    });
  }

  const newProduct = {
    id: products.length ? products[products.length - 1].id + 1 : 1,
    name,
    price,
    stock,
  };

  products.push(newProduct);

  res.status(201).json({
    message: "Product created successfully",
    data: newProduct,
  });
};

// UPDATE produk
const updateProduct = (req, res) => {
  const id = parseInt(req.params.id);
  const { name, price, stock } = req.body;

  const productIndex = products.findIndex((p) => p.id === id);

  if (productIndex === -1) {
    return res.status(404).json({
      message: "Product not found",
    });
  }

  products[productIndex] = {
    ...products[productIndex],
    name: name ?? products[productIndex].name,
    price: price ?? products[productIndex].price,
    stock: stock ?? products[productIndex].stock,
  };

  res.status(200).json({
    message: "Product updated successfully",
    data: products[productIndex],
  });
};

// DELETE produk
const deleteProduct = (req, res) => {
  const id = parseInt(req.params.id);

  const product = products.find((p) => p.id === id);

  if (!product) {
    return res.status(404).json({
      message: "Product not found",
    });
  }

  products = products.filter((p) => p.id !== id);

  res.status(200).json({
    message: "Product deleted successfully",
    data: product,
  });
};

module.exports = {
  getAllProducts,
  getProductById,
  createProduct,
  updateProduct,
  deleteProduct,
};