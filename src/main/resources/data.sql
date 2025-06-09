CREATE TABLE IF NOT EXISTS qrcodes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome_arquivo VARCHAR(255) NOT NULL,
    url VARCHAR(255) NOT NULL
    );

INSERT INTO qrcodes (nome_arquivo, url) VALUES
 ('QrCode_101.png', 'https://exemplo.com/101'),
('QrCode_102.png', 'https://exemplo.com/102');