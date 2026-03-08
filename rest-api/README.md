# Modul 2 - REST API CRUD Produk

## Deskripsi
Pada modul ini dibuat sebuah **REST API sederhana** untuk mengelola data produk menggunakan **Node.js dan Express.js**. API ini mendukung operasi **CRUD (Create, Read, Update, Delete)** yang dapat diuji menggunakan Postman.

Praktikum ini bertujuan untuk memahami konsep dasar REST API, routing, controller, serta penggunaan HTTP method dalam pengembangan service.

---

## Tujuan Praktikum

- Memahami konsep dasar REST API
- Mengimplementasikan operasi CRUD
- Memahami penggunaan HTTP Method (GET, POST, PUT, DELETE)
- Membuat struktur project backend yang terorganisir
- Melakukan pengujian endpoint menggunakan Postman

---

## Teknologi yang Digunakan

- Node.js
- Express.js
- Nodemon
- Postman
- Git & GitHub


# Endpoint API
## 1. Get All Products
Mengambil semua data produk.
Method : GET 
URL : http://localhost:3000/products

## 2. Get Product by ID
Mengambil data produk berdasarkan ID.
Method: GET
URL :http://localhost:3000/products/:id

## 3. Create Product
Menambahkan produk baru.

Method: POST
URL :http://localhost:3000/products

## 3. Update Product
Memperbarui data produk.

Method: PUT
URL :http://localhost:3000/products/:id

## 3. Delete Product
Menghapus produk berdasarkan ID.

Method: DELETE
URL :http://localhost:3000/products/:id