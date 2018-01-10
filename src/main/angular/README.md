# Votador Front-End

Aplicação front-end da solução Alterdata Votador.


## Início

Para iniciar, rode o seguinte comando:

```
npm install
```

Após a instalação do diretório *node_modules*, é possível rodar comandos.

## Trabalhando 
Os comandos de trabalho com o a aplicação sao divididos em duas categorias:

cmd: comando para execução da aplicação em ambiente de desenvolvimento.

cmd*p*: comando para execução da aplicação em ambiente de 
produção (arquivos são minificados e gerados com chunkhash).

Para execução de quaisquer dos comandos abaixo, basta executar:

```  
npm run <COMANDO>
```  

### Comando de limpeza (clean/cleanp)

```
clean : Remove o diretório 'dist-dev' 
cleanp: Remove o diretório dist-prod
```

### Comando de construção de artefatos (build/buildp)

```
build : Constrói a aplicação em modo 'DEV'
buildp: Constrói a aplicação em modo 'PROD'
```

### Comando de execução da aplicação (serve/servep)

```
serve : Executa o servidor HTTP do webpack em modo 'DEV'
servep: Executa o servidor HTTP do webpack em modo 'PROD'
```

### Comando de execução da aplicação (http/httpp)

```
http : Executa um servidor HTTP rústico em modo 'DEV'
httpp: Executa um servidor HTTP rústico em modo 'DEV'
```  