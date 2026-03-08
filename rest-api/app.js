const express = require("express");
const productRoutes = require("./routes/productRoutes");

const app = express();
const PORT = 3000;

// middleware
app.use(express.json());

// route utama
app.get("/", (req, res) => {
  res.json({
    message: "Welcome to Product API",
  });
});

// route products
app.use("/products", productRoutes);

// handler jika route tidak ditemukan
app.use((req, res) => {
  res.status(404).json({
    message: "Route not found",
  });
});

app.listen(PORT, () => {
  console.log(`Server running on http://localhost:${PORT}`);
});