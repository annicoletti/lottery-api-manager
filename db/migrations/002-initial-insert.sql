-- liquibase formatted sql

-- changeset nicoletti:1734468722-1
INSERT INTO tb_tipos_jogo (nome, quantidade_numeros_disponiveis, quantidade_numeros_minimo, quantidade_numeros_maximo, quantidade_numeros_minimo_para_ganhar, quantidade_numeros_maximo_para_ganhar) VALUES('LOTOFACIL', 25, 15, 20, 11, 15);

-- changeset nicoletti:1734468722-2
INSERT INTO tb_tipos_jogo (nome, quantidade_numeros_disponiveis, quantidade_numeros_minimo, quantidade_numeros_maximo, quantidade_numeros_minimo_para_ganhar, quantidade_numeros_maximo_para_ganhar) VALUES('MEGASENA', 60, 6, 20, 4, 6);
