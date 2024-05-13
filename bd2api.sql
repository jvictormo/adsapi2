CREATE TABLE Cliente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    sexo ENUM('M', 'F', 'Outro') NOT NULL
);

CREATE TABLE Produto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    preco INT NOT NULL 
);

CREATE TABLE Pedido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT NOT NULL,
    valor_total INT NOT NULL,
    data_pedido DATE NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES Cliente(id)
);

CREATE TABLE Item (
    id INT AUTO_INCREMENT PRIMARY KEY,
    pedido_id INT NOT NULL,
    produto_id INT NOT NULL,
    FOREIGN KEY (pedido_id) REFERENCES Pedido(id),
    FOREIGN KEY (produto_id) REFERENCES Produto(id)
);

INSERT INTO Cliente (nome, email, cidade, sexo) VALUES 
('João Silva', 'joao@gmail.com', 'São Paulo', 'M'),
('Maria Oliveira', 'maria@gmail.com', 'Rio de Janeiro', 'F'),
('Pedro Santos', 'pedro@gmail.com', 'São Paulo', 'M'),
('Ana Souza', 'ana@gmail.com', 'São Paulo', 'F'),
('Carlos Ferreira', 'carlos@gmail.com', 'Rio de Janeiro', 'M');

INSERT INTO Produto (nome, preco) VALUES 
('Camisa', 30),
('Calça', 40),
('Sapato', 60),
('Vestido', 50);

INSERT INTO Pedido (cliente_id, valor_total, data_pedido) VALUES 
(1, 100, '2024-05-13'),
(2, 150, '2024-05-12'),
(3, 200, '2024-05-11'),
(4, 80, '2024-05-10'),
(5, 300, '2024-05-09');

INSERT INTO Item (pedido_id, produto_id) VALUES 
(1, 1),
(1, 2),
(2, 3),
(2, 4),
(3, 1),
(3, 2),
(4, 3),
(4, 4),
(5, 1),
(5, 2);

SELECT 
    Cliente.nome AS Nome_Cliente,
    Cliente.email AS Email_Cliente,
    Cliente.cidade AS Cidade,
    Cliente.sexo AS Sexo,
    Pedido.valor_total AS Valor_Total,
    Pedido.data_pedido AS Data_Pedido
FROM 
    Cliente
INNER JOIN Pedido ON Cliente.id = Pedido.cliente_id;