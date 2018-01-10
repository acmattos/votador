-- Insere Usuarios Iniciais
INSERT INTO funcionario (nome, email, senha) --, enabled, role)
VALUES ('Jorge Marques', 'jorge.pessoal@alterdata.com.br',
'$2a$10$gapKqcNnsrbD731EgcyfSOsUjZiJedRgGnwpOGSebGRg.4J7EJSI2');-- senha: jorge
INSERT INTO funcionario (nome, email, senha)
VALUES ('Ana Carmo', 'ana.pessoal@alterdata.com.br',
'$2a$10$Ut0eXlBcEmNnjT9eVKZxAeMYmogUBZpuochaKLDUuAWF3cDKPHB.m');-- senha: ana
INSERT INTO funcionario (nome, email, senha)
VALUES ('Paulo Otavio', 'paulo.producao@alterdata.com.br',
'$2a$10$XpZ.JcoQ29sl.Sb0a.7caeHvvYtqMtNaOyQlTJPltMccS.auCuheu');-- Senha: paulo
INSERT INTO funcionario (nome, email, senha)
VALUES ('Isabel Ferreira', 'isabel.producao@alterdata.com.br',
'$2a$10$GnBz5ViD/0lk/XKd2AayH.MOFH6dGIBaZj6cpfWXZaQ2GKtWbn0YO');-- senha: isabel
INSERT INTO funcionario (nome, email, senha)
VALUES ('Carlos Maia', 'carlos.financeiro@alterdata.com.br',
'$2a$10$VH6tkfIeSyKX62y90jap3.7CUyCkn5bB6jWtt6ctIHkW/zlRuh5l.');-- senha: carlos
INSERT INTO funcionario (nome, email, senha)
VALUES ('Rita Batista', 'rita.financeiro@alterdata.com.br',
'$2a$10$FnBPRwZ5NpJsV3mrO7UJ8eetHjQr93Ncqeac.Qt1Phub43TxzpMQm');-- senha: rita
INSERT INTO funcionario (nome, email, senha)
VALUES ('Angelo Nascimento', 'angelo.comercial@alterdata.com.br',
'$2a$10$/8Fp10BCGaPb1j0XZ/pK5O8Pkfmgt4h0hFZ4nTTljLyvmaEGLZqoa');-- senha: angelo
INSERT INTO funcionario (nome, email, senha)
VALUES ('Maria Bastos', 'maria.comercial@alterdata.com.br',
'$2a$10$jNK9lCy7JC9iiwsPRLhYheA4ndZrZ4EAyXCcNGP50C/IIHiMQCSbG');-- senha: maria
INSERT INTO funcionario (nome, email, senha)
VALUES ('Bruno Mota', 'bruno.desenvolvimento@alterdata.com.br',
'$2a$10$eukW4awAvYqXdQy7L0yNiO1ujhca2AX6xQM4lAduz3wWqgy3z3DHi');-- senha: bruno
INSERT INTO funcionario (nome, email, senha)
VALUES ('Julia Almeida', 'julia.desenvolvimento@alterdata.com.br',
'$2a$10$lAePJ2PzLip54wzfQLJPauWnYXFCi7dHBCx8Q1x5vRiXY7BtGbrum');-- senha: julia

-- Insere Recursos Iniciais
INSERT INTO recurso (descricao) VALUES ('Recurso 1');
INSERT INTO recurso (descricao) VALUES ('Recurso 2');
INSERT INTO recurso (descricao) VALUES ('Recurso 3');
INSERT INTO recurso (descricao) VALUES ('Recurso 4');
INSERT INTO recurso (descricao) VALUES ('Recurso 5');