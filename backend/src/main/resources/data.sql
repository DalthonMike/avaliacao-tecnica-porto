-- Inserir dados na tabela TBL_USUARIO
INSERT INTO TBL_USUARIO (ID, NOME, EMAIL, VER_SPOILERS, STATUS) VALUES
                                                                    (1, 'Fulano', 'fulano@email.com', TRUE, 'ATIVO'),
                                                                    (2, 'Ciclano', 'ciclano@email.com', FALSE, 'ATIVO'),
                                                                    (3, 'Dalthon', 'dalthon@email.com', TRUE, 'INATIVO');

-- Inserir dados na tabela TBL_LIVRO
INSERT INTO TBL_LIVRO (ID, TITULO, AUTOR, RESUMO, ANO_PUBLICACAO, ISBN, STATUS) VALUES
                                                                                    (1, 'O Pequeno Príncipe', 'Antoine de Saint-Exupéry', 'Um clássico sobre a infância e amizade', 1943, '1234567890123', 'ATIVO'),
                                                                                    (2, 'Dom Quixote', 'Miguel de Cervantes', 'A história de um cavaleiro sonhador', 1605, '9876543210987', 'ATIVO');

-- Inserir dados na tabela TBL_PROGRESO_LEITURA
INSERT INTO TBL_PROGRESO_LEITURA (ID, USUARIO_ID, LIVRO_ID, PAGINA_ATUAL, DATA_REGISTRO) VALUES
                                                                                             (1, 1, 1, 50, '2025-09-07 12:00:00'),
                                                                                             (2, 2, 1, 30, '2025-09-07 12:30:00'),
                                                                                             (3, 1, 2, 100, '2025-09-07 13:00:00');

-- Inserir dados na tabela TBL_COMENTARIO
INSERT INTO TBL_COMENTARIO (ID, LIVRO_ID, USUARIO_ID, MENSAGEM, DATA_COMENTARIO, SPOILER) VALUES
                                                                                              (1, 1, 1, 'Amei o livro, muito profundo!', '2025-09-07 14:00:00', FALSE),
                                                                                              (2, 1, 2, 'Cuidado, spoilers daqui pra frente!', '2025-09-07 14:30:00', TRUE),
                                                                                              (3, 2, 1, 'Muito interessante a abordagem do autor', '2025-09-07 15:00:00', FALSE);