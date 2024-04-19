# Documentação dos Endpoints da API

Este documento fornece informações sobre os endpoints disponíveis na API do IMDb.

## Endpoints de Autenticação

### Login

- **URL:** `/api/auth/login`
- **Método:** `POST`
- **Descrição:** Autentica um usuário e gera um token JWT.
- **Corpo da Requisição:**
  ```json
  {
    "login": "nome_de_usuário",
    "senha": "senha"
  }
  ```
- **Resposta:** 
  ```json
  {
    "userId": "id_do_usuário",
    "token": "token_jwt",
    "cargo": "cargo_do_usuário"
  }
  ```

### Registro

- **URL:** `/api/auth/register`
- **Método:** `POST`
- **Descrição:** Registra um novo usuário.
- **Corpo da Requisição:**
  ```json
  {
    "login": "novo_nome_de_usuário",
    "senha": "nova_senha",
    "cargo": "cargo_do_usuário"
  }
  ```
- **Resposta:** `200 OK` se bem-sucedido, `400 Bad Request` se o usuário já existir.

### Ativar Usuário

- **URL:** `/api/auth/activate`
- **Método:** `POST`
- **Descrição:** Ativa uma conta de usuário.
- **Corpo da Requisição:**
  ```json
  {
    "login": "nome_de_usuário_para_ativar"
  }
  ```
- **Resposta:** `200 OK` se bem-sucedido, `404 Not Found` se o usuário não for encontrado.

### Desativar Usuário

- **URL:** `/api/auth/deactivate`
- **Método:** `POST`
- **Descrição:** Desativa uma conta de usuário.
- **Corpo da Requisição:**
  ```json
  {
    "login": "nome_de_usuário_para_desativar"
  }
  ```
- **Resposta:** `200 OK` se bem-sucedido, `404 Not Found` se o usuário não for encontrado.

## Endpoints de Filmes

### Obter Todos os Filmes

- **URL:** `/api/filmes`
- **Método:** `GET`
- **Descrição:** Recupera todos os filmes.
- **Resposta:** Lista de objetos de filme.

### Obter Filme por ID

- **URL:** `/api/filmes/{id}`
- **Método:** `GET`
- **Descrição:** Recupera um filme pelo seu ID.
- **Resposta:** Objeto de filme.

### Pesquisar Filmes

- **URL:** `/api/filmes/search`
- **Método:** `GET`
- **Descrição:** Pesquisa filmes com base em uma palavra-chave.
- **Parâmetro de Consulta:** `search` (palavra-chave)
- **Resposta:** Lista de objetos de filme que correspondem à pesquisa.

### Adicionar Filme

- **URL:** `/api/filmes`
- **Método:** `POST`
- **Descrição:** Adiciona um novo filme.
- **Corpo da Requisição:** Objeto de filme.
  ```json
  {
    "nome": "Nome do Filme",
    "descricao": "Descrição do Filme",
    "votos": [],
    "diretor": "Nome do Diretor",
    "genero": "Gênero do Filme",
    "atores": "Lista de Atores"
  }
  ```
- **Resposta:** Objeto de filme recém-adicionado.

### Deletar Filme por ID

- **URL:** `/api/filmes/{id}`
- **Método:** `DELETE`
- **Descrição:** Exclui um filme pelo seu ID.
- **Resposta:** `204 No Content` se bem-sucedido.

### Atualizar Filme por ID

- **URL:** `/api/filmes/{id}`
- **Método:** `PUT`
- **Descrição:** Atualiza um filme pelo seu ID.
- **Corpo da Requisição:**
 ```json
  {
    "id": 1,
    "nome": "Nome do Filme",
    "descricao": "Descrição do Filme",
    "votos": [{"id":168,"valor":3.0,"idFilme":1,"idUsuario":1}],
    "mediaVotos": 0,
    "diretor": "Nome do Diretor",
    "genero": "Gênero do Filme",
    "atores": "Lista de Atores"
}
```
- **Resposta:
 ```json
 {
	"id": 1,
	"nome": "Nome do Filme",
	"descricao": "Descrição do Filme",
	"votos": [
		{
			"id": 168,
			"valor": 3.0,
			"idFilme": 1,
			"idUsuario": 1
		}
	],
	"mediaVotos": 3,
	"diretor": "Nome do Diretor",
	"genero": "Gênero do Filme",
	"atores": "Lista de Atores"
}
```
