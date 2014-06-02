Michael Alexandre Costa
Ciência da Computação

Trabalho de Redes -- Web Crawler

Este trabalho abre conexões http/1.1 atraves de sockets, em cada url entrada o crawler buscada outras e a arvore de visita acaba ao chegar na profundidade descrita na execução do código.

Para compilar o código possui um make, e para execução um executeme passando o valor de profundidade e a url.

make
executeme 100 http://www.google.com.br

Ao fazer o código encontrei problemas na conexão com socket http, tendo como respostas os erros 400 e 404.
