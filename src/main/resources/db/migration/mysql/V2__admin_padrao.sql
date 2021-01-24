INSERT INTO `empresa` (`id`, `cnpj`, `data_atualizacao`, `data_criacao`, `razao_social`) 
VALUES (NULL, '82198127000121', CURRENT_DATE(), CURRENT_DATE(), 'Kazale IT');

insert into `funcionario` (`id`, `cpf`, `data_atualizacao`, `data_criacao`, `email`, `nome`, `perfil`, 
`qtd_horas_almoco`, `qtd_horas_trabalho`, `senha`, `valor_hora`, `empresa_id`) VALUES (NULL, 
'16248890935' , CURRENT_DATE(), CURRENT_DATE(), 'admin@kazale.com', 'ADMIN', 'ROLE_ADMIN', NULL, NULL,
 '$2a$10$Q.cpKmjECb9cGfaRroNUIeOKXgwpTzuqEHG6utQiSMxw2fvxsaLYm' , NULL, 
 (select `id` from `empresa` where `cnpj` = '82198127000121'));