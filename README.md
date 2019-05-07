## Aplicação OctoEvent

### Bibliotecas utilizadas:

 - [Ktor](https://github.com/ktorio/ktor) - Kotlin async web framework
 - [Netty](https://github.com/netty/netty) - Async web server
 - [Exposed](https://github.com/JetBrains/Exposed) - Kotlin SQL framework
 - [H2](https://github.com/h2database/h2database) - Embeddable database
 - [HikariCP](https://github.com/brettwooldridge/HikariCP) - High performance JDBC connection pooling
 - [Jackson](https://github.com/FasterXML/jackson) - JSON serialization/deserialization

Este projeto cria um banco H2 em memória com uma tabela para instâncias de `Event`.

Como o ktor é assíncrono e baseado em corrotinas, o padrão blocking do JDBC pode causar problemas de
desempenho quando usado diretamente na main thread pool (threads devem ser reutilizados para outras
solicitações). Dessa forma, outro pool de threads foi criado para todas as consultas do banco de dados,
juntamente com o pool de conexões do HikariCP.


### Rotas:

 - `GET /issues/{issueNumber}/events`: recupera todos eventos filtrados por número da issue
 - `POST /payload` : cria um evento


### Rodar os testes:

 - Primeiro clone este repositório e vá para a pasta onde o mesmo foi clonado
 - Para rodar os testes da aplicação execute `./gradlew clean test` na pasta raiz do projeto


### Executar a aplicação:

 - Primeiro clone este repositório e vá para a pasta onde o mesmo foi clonado
 - Para rodar a aplicação execute os seguintes comandos na pasta raiz do projeto
   - `./gradlew clean build`
   - `./gradlew run`

### Testando o POST para criar um evento

 - Usando `curl` execute o comando abaixo:
 `curl -d '{ "action":"opened", "issue":{ "number":5, "created_at":"2019-03-24T21:40:18Z"} }' -H "Content-Type: application/json" -X POST http://localhost:8080/payload`


### Testando o get para obter o evento pelo número da issue

 - No browser acesse: http://localhost:8080/issues/5/events
 - A resposta deve ser algo como o json abaixo:

 `
 [
    {
        id: 1,
        action: "opened",
        createdAt: "2019-03-24T21:40:18Z",
        issueNumber: 5
    }
 ]
 `