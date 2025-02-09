# language: pt
Funcionalidade: Video

  Cenario: Carregar um novo video com sucesso
    Quando carregar um novo video
    Entao deve retornar sucesso

  Cenario: Obter video com sucesso
    Dado um video que existe
    Quando obter o video
    Entao deve retornar sucesso
    E deve retornar os dados do video

  Cenario: Obter video não existente
    Dado um video que não existe
    Quando obter o video
    Entao deve retornar não encontrado
