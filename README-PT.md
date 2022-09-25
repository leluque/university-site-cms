# Website e sistema administrativo de site para faculdades

Este projeto implementa um site universitário (da Fatec Mogi das Cruzes) e um sistema administrativo - gerenciamento de notícias, eventos etc. - usando Spring Boot. Uma versão executável deste projeto pode ser encontrada em: www.fatecmogidascruzes.com.br

## Iniciando

Estas instruções te auxilirão a copiar e executar o projeto em sua máquina local para testes e desenvolvimento.

### Pré-requisitos

Você precisará dos sistemas de software descritos a seguir para executar o projeto:

Compilador
```
JDK 8 ou mais recente
```

Servidor HTTP
```
Nginx, Apache ou equivalente para servir arquivos estáticos (imagens e PDFs).
```

IDE
```
Qualquer uma de sua preferência
```

LOMBOK
```
Deve ser instalado na sua IDE: https://projectlombok.org/
```

DBMS
```
MySQL 5.8 ou mais recente (pode rodar em versões mais antigas também)
Outro DBMS pode ser usado, dado que usamos JPA.
```

Google ReCAPTCHA
```
Uma conta Google ReCAPTCHA
```

Conta SMTP
```
Uma conta SMTP para enviar e receber e-mails
```

### Instalando

Para instalar o projeto, o importe como projeto maven no STS.
Ainda, você deve definir as seguintes variáveis de ambiente:
* SITE_FATEC_DATABASE_HOST: O nome do host ou o IP do SGBD.
* SITE_FATEC_DATABASE_PORT: A porta do SGBD.
* SITE_FATEC_DATABASE_DATABASE: O nome do banco de dados.
* SITE_FATEC_DATABASE_USER: O usuário do banco de dados.
* SITE_FATEC_DATABASE_PASSWORD: A senha do banco de dados.
* SITE_FATEC_STATIC_FILES_PATH: A pasta onde arquivos estáticos devem ser gravados.
* SITE_FATEC_TEMP_FILES_PATH: A pasta onde arquivos temporários podem ser gravados.
* SITE_FATEC_RECAPTCHA_SITE: O site reCAPTCHA.
* SITE_FATEC_RECAPTCHA_SECRET: A chave secreta reCAPTCHA.
* SITE_FATEC_MAIL_FROM: O e-mail do qual as mensagens serão enviadas (from).
* SITE_FATEC_MAIL_ACCOUNT_ID: Um id de conta Zoho SMTP API (Veja https://www.zoho.com/crm/help/api/using-authentication-token.html).
* SITE_FATEC_MAIL_AUTHTOKEN: Um token de autorização Zoho SMTP API (Veja https://www.zoho.com/crm/help/api/using-authentication-token.html).

## Autores

* **Leandro Luque**

Veja também uma lista de [contribuidores](https://github.com/your/project/contributors), que participaram deste projeto.

## Licença

Este projeto é lienciado de acordo com MIT - veja a [LICENÇA](LICENSE) para detalhes.
