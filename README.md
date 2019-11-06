# University Website and CMS (Website e Sistema Administrativo do Site da Fatec)

This project implements a university (Fatec Mogi das Cruzes) website and CMS using Spring Boot. A running version of this project may be found at: www.fatecmogidascruzes.com.br.

*Este projeto implementa um site universitário (da Fatec Mogi das Cruzes) e um sistema administrativo - gerenciamento de notícias, eventos etc. - usando Spring Boot. Uma versão executável deste projeto pode ser encontrada em: www.fatecmogidascruzes.com.br*

## Getting Started (Iniciando)

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

*Estas instruções te auxilirão a copiar e executar o projeto em sua máquina local para testes e desenvolvimento.*

### Prerequisites (Pré-requisitos)

You'll need the software described next in order to run the project:

*Você precisará dos sistemas de software descritos a seguir para executar o projeto:*

Compiler
```
JDK 8 or newer
```

HTTP Server
```
Nginx, Apache or equivalent to serve static files (images and PDFs).
```

IDE
```
STS - Spring Tool Suite 3 or newer
```

LOMBOK
```
The lombok project must be installed in the IDE: https://projectlombok.org/
```

DBMS
```
MySQL 5.8 or newer (may run in older versions as well)
An equivalent DBMS may be configure since the project uses JPA.
```

Google reCAPTCHA
```
A Google Recaptcha account.
```

A Zoho SMTP account
```
A Zoho SMTP account to send password recovery e-mails.
```

### Installing (Instalando)

In order to install the project, import it as a maven project in STS.
Moreover, you'll need to define the following environment variables:
* SITE_FATEC_DATABASE_HOST: The host name or IP in which the DBMS is installed.
* SITE_FATEC_DATABASE_PORT: The DBMS service port.
* SITE_FATEC_DATABASE_DATABASE: The database name.
* SITE_FATEC_DATABASE_USER: The database user.
* SITE_FATEC_DATABASE_PASSWORD: The database password.
* SITE_FATEC_STATIC_FILES_PATH: The folder where static files must be saved.
* SITE_FATEC_TEMP_FILES_PATH: The folder where temporary files may be saved.
* SITE_FATEC_RECAPTCHA_SITE: The reCAPTCHA site.
* SITE_FATEC_RECAPTCHA_SECRET: The reCAPTCHA secret key.
* SITE_FATEC_MAIL_FROM: The mail from which messages will be sent.
* SITE_FATEC_MAIL_ACCOUNT_ID: A Zoho SMTP API account id (See https://www.zoho.com/crm/help/api/using-authentication-token.html).
* SITE_FATEC_MAIL_AUTHTOKEN: A Zoho SMTP API auth token (See https://www.zoho.com/crm/help/api/using-authentication-token.html).

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

## Authors (Autores)

* **Leandro Luque** - *Initial work*

See also the list of [contributors](https://github.com/leluque/university-site-cms/graphs/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details
