
-- Tabela de Alunos
CREATE TABLE Alunos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    data_nascimento DATE NOT NULL
);

-- Tabela de Cursos
CREATE TABLE Cursos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT
);

-- Tabela de Turmas
CREATE TABLE Turmas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    curso_id INT,
    semestre VARCHAR(20) NOT NULL,
    FOREIGN KEY (curso_id) REFERENCES Cursos(id)
);

-- Inserindo dados de exemplo na tabela de Alunos
INSERT INTO Alunos (nome, email, data_nascimento) VALUES
('João Silva', 'joao@example.com', '2000-05-15'),
('Maria Santos', 'maria@example.com', '2001-09-20'),
('Carlos Oliveira', 'carlos@example.com', '1999-03-10');

-- Inserindo dados de exemplo na tabela de Cursos
INSERT INTO Cursos (nome, descricao) VALUES
('Ciência da Computação', 'Bacharelado em Ciência da Computação'),
('Administração', 'Bacharelado em Administração de Empresas'),
('Engenharia Civil', 'Bacharelado em Engenharia Civil');

-- Inserindo dados de exemplo na tabela de Turmas
INSERT INTO Turmas (curso_id, semestre) VALUES
(1, '2023/1'),
(2, '2023/1'),
(1, '2023/2');

SELECT 
    Alunos.nome AS Nome_Aluno,
    Alunos.email AS Email_Aluno,
    Alunos.data_nascimento AS Data_Nascimento,
    Cursos.nome AS Nome_Curso,
    Cursos.descricao AS Descricao_Curso,
    Turmas.semestre AS Semestre
FROM 
    Alunos
INNER JOIN Turmas ON Alunos.id = Turmas.id
INNER JOIN Cursos ON Turmas.curso_id = Cursos.id;
