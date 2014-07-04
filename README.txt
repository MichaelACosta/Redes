Michael Alexandre Costa
Ciência da Computação

Trabalho de Redes 2 -- Web Crawler com conexão https

Este trabalho abre conexões http/1.1 com ssl atraves de sockets, em cada url entrada o crawler buscada outras e a arvore de visita acaba ao chegar na profundidade descrita na execução do código.

Para compilar o código possui um make, e para execução um executeme passando o valor de profundidade e a url.

make
executeme 100 https://www.google.com.br

Ao fazer o código para o primeiro trabalho encontrei problemas na conexão com socket http, tendo como respostas os erros 400 e 404.
Para o segundo trabalho resolvi os problemas encontardo no primeiro com uso do wireshark para acompanhar os protocols, e alterei o socket para conectar com https usando o sslsocket.
