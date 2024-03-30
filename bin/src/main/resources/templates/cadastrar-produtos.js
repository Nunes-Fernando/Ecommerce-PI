const express = require('express');
const multer = require('multer');
const mysql = require('mysql');
const path = require('path');

const app = express();

// Configuração do multer para fazer o upload de arquivos
const storage = multer.diskStorage({
  destination: function (req, file, cb) {
    cb(null, 'uploads/');
  },
  filename: function (req, file, cb) {
    cb(null, file.fieldname + '-' + Date.now() + path.extname(file.originalname));
  }
});

const upload = multer({ storage: storage });

// Configuração do banco de dados MySQL
const connection = mysql.createConnection({
  host: 'localhost',
  user: 'root',
  password: '',
  database: 'produtos'
});

connection.connect(err => {
  if (err) {
    console.error('Erro ao conectar ao banco de dados:', err);
    return;
  }
  console.log('Conexão com o banco de dados MySQL estabelecida.');
});

// Rota para cadastrar um produto
app.post('/cadastrarProduto', upload.array('productImage', 5), (req, res) => {
  const { productName, productPrice, productQuantity, productDescription, productRating } = req.body;
  const productImages = req.files.map(file => file.filename);
  const mainImage = productImages.shift(); // Remove a primeira imagem da lista para ser a imagem principal

  const sql = 'INSERT INTO produtos (nome, preco, quantidade, descricao, imagem_principal, imagens_secundarias, avaliacao) VALUES (?, ?, ?, ?, ?, ?, ?)';
  connection.query(sql, [productName, productPrice, productQuantity, productDescription, mainImage, JSON.stringify(productImages), productRating], (err, result) => {
    if (err) {
      console.error('Erro ao inserir produto no banco de dados:', err);
      return res.status(500).send('Erro ao cadastrar produto.');
    }
    console.log('Produto cadastrado com sucesso.');
    res.send('Produto cadastrado com sucesso.');
  });
});

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`Servidor rodando na porta ${PORT}`);
});
