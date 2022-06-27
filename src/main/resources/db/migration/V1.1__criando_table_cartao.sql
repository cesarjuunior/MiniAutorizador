CREATE TABLE cartao (
        id INTEGER NOT NULL AUTO_INCREMENT,
        numero_cartao VARCHAR(16),
        senha VARCHAR(8),
        saldo NUMERIC DEFAULT(500),
        PRIMARY KEY(id)
    );
