# language: pt
Funcionalidade: Usuario

  Cenario: Criar usuario com sucesso
    Quando criar um novo usuario
    Entao deve retornar sucesso

  Cenario: Obter usuario com sucesso
    Dado um usuario que existe
    Quando obter o usuario
    Entao deve retornar sucesso
    E deve retornar os dados do usuario

  Cenario: Obter usuario não existente
    Dado um usuario que não existe
    Quando obter o usuario
    Entao deve retornar não encontrado
